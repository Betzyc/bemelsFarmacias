package com.bemels.spring.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bemels.spring.app.repositories.SucursalRepository;
import com.bemels.spring.app.repositories.DepartamentoRepository;
import com.bemels.spring.app.util.PageRender;
import com.bemels.spring.app.entities.Audit;
import com.bemels.spring.app.entities.Sucursal;
import com.bemels.spring.app.entities.Departamento;

@Controller
public class SucursalController {

	@Autowired
	private SucursalRepository sucursalRepository;
	@Autowired
	private DepartamentoRepository departamentoRepository;

	@RequestMapping(value = "/sucursales/detalle-sucursal/{id}", method = RequestMethod.GET)
	public String detalleSucursal(@PathVariable(value = "id") Long id, Model model) {

		Sucursal sucursal = sucursalRepository.findById(id).get();
		if (sucursal == null) {
			return "redirect:/sucursales/listar-sucursales";
		}

		model.addAttribute("titulo", "Detalle Sucursal: " + sucursal.getNombre());
		model.addAttribute("sucursal", sucursal);
		return "sucursales/detalle-sucursal-form";
	}

	@RequestMapping(value = "/sucursales/listar-sucursales", method = RequestMethod.GET)
	public String listarSucursales(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Sucursal> sucursales = sucursalRepository.findAll(pageRequest);
		
		List<Departamento> departamentos = (List<Departamento>)departamentoRepository.findAll();
		Map<Long, String> hmdepartamentos = new HashMap<>();
        for (Departamento departamento : departamentos) {
            hmdepartamentos.put(departamento.getId(), departamento.getNombre());
        }
		
		PageRender<Sucursal> pageRender = new PageRender<Sucursal>("/sucursales/listar-sucursales", sucursales);

		
		model.addAttribute("hmdepartamentos", hmdepartamentos);
		model.addAttribute("titulo", "Listado de sucursales");
		model.addAttribute("sucursales", sucursales);
		model.addAttribute("page", pageRender);
		return "sucursales/sucursales";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/sucursales/nueva-sucursal", method = RequestMethod.GET)
	public String nuevaSucursal(Model model) {
		Sucursal sucursal = new Sucursal();
		
		List<Departamento> departamentos = (List<Departamento>) departamentoRepository.findAll();
		model.addAttribute("departamentos", departamentos);
		
		model.addAttribute("titulo", "Nueva Sucursal");
		model.addAttribute("sucursal", sucursal);
		return "sucursales/form-sucursal";
	}

	@RequestMapping(value = "/sucursales/editar-sucursal/{id}", method = RequestMethod.GET)
	public String editarSucursal(@PathVariable(value = "id") Long id, Model model) {
		Sucursal sucursal = null;
		if (id > 0) {
			sucursal = sucursalRepository.findById(id).get();
		} else {
			return "redirect:/sucursales/listar-sucursales";
		}
		
		List<Departamento> departamentos = (List<Departamento>) departamentoRepository.findAll();
		model.addAttribute("departamentos", departamentos);
		
		model.addAttribute("titulo", "Editar Sucursal");
		model.addAttribute("sucursal", sucursal);
		return "sucursales/form-sucursal";
	}

	@RequestMapping(value = "/sucursales/eliminar-sucursal/{id}", method = RequestMethod.GET)
	public String eliminarSucursal(@PathVariable(value = "id") Long id, Model model) {
		Sucursal sucursal = null;
		if (id > 0) {
			sucursal = sucursalRepository.findById(id).get();
			sucursalRepository.delete(sucursal);
		} else {
			return "redirect:/sucursales/listar-sucursales";
		}

		return "redirect:/sucursales/listar-sucursales";
	}

	@RequestMapping(value = "/sucursales/nueva-sucursal", method = RequestMethod.POST)
	public String guardarSucursal(@RequestParam("file") MultipartFile foto, Sucursal sucursal) {
		Audit audit = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!foto.isEmpty()) {

			try {
				
				String path = "C:/Users/betzy/Desktop/farmacia/".concat(foto.getOriginalFilename());

				byte[] bytes = foto.getBytes();
				Path rutaCompleta = Paths.get(path);
				Files.write(rutaCompleta, bytes);
				sucursal.setFoto(foto.getOriginalFilename());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (sucursal.getId() != null && sucursal.getId() > 0) {
			Sucursal sucursal2 = sucursalRepository.findById(sucursal.getId()).get();
			audit = new Audit(auth.getName());
			sucursal.setAudit(audit);
			sucursal.setId(sucursal2.getId());
			sucursal.getAudit().setTsCreated(sucursal2.getAudit().getTsCreated());
			sucursal.getAudit().setUsuCreated(sucursal2.getAudit().getUsuCreated());
		} else {
			audit = new Audit(auth.getName());
			sucursal.setAudit(audit);
		}

		sucursalRepository.save(sucursal);
		return "redirect:/sucursales/listar-sucursales";
	}

}
