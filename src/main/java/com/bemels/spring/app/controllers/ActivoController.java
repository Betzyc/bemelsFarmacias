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

import com.bemels.spring.app.repositories.ActivoRepository;
import com.bemels.spring.app.repositories.SucursalRepository;
import com.bemels.spring.app.util.PageRender;
import com.bemels.spring.app.entities.Audit;
import com.bemels.spring.app.entities.Activo;
import com.bemels.spring.app.entities.Sucursal;

@Controller
public class ActivoController {

	@Autowired
	private ActivoRepository activoRepository;
	@Autowired
	private SucursalRepository sucursalRepository;

	@RequestMapping(value = "/activos/detalle-activo/{id}", method = RequestMethod.GET)
	public String detalleActivo(@PathVariable(value = "id") Long id, Model model) {

		Activo activo = activoRepository.findById(id).get();
		if (activo == null) {
			return "redirect:/activos/listar-activos";
		}

		model.addAttribute("titulo", "Detalle Activo: " + activo.getNombre());
		model.addAttribute("activo", activo);
		return "activos/detalle-activo-form";
	}

	@RequestMapping(value = "/activos/listar-activos", method = RequestMethod.GET)
	public String listarActivos(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Activo> activos = activoRepository.findAll(pageRequest);

		List<Sucursal> sucursales = (List<Sucursal>)sucursalRepository.findAll();
		Map<Long, String> hmsucursales = new HashMap<>();
        for (Sucursal sucursal : sucursales) {
            hmsucursales.put(sucursal.getId(), sucursal.getNombre());
        }
        
		PageRender<Activo> pageRender = new PageRender<Activo>("/activos/listar-activos", activos);
		model.addAttribute("hmsucursales", hmsucursales);
		model.addAttribute("titulo", "Listado de activos");
		model.addAttribute("activos", activos);
		model.addAttribute("page", pageRender);
		return "activos/activos";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/activos/nuevo-activo", method = RequestMethod.GET)
	public String nuevoActivo(Model model) {
		Activo activo = new Activo();
		
		List<Sucursal> sucursales = (List<Sucursal>) sucursalRepository.findAll();
		model.addAttribute("sucursales", sucursales);
		
		model.addAttribute("titulo", "Nuevo Activo");
		model.addAttribute("activo", activo);
		return "activos/form-activo";
	}

	@RequestMapping(value = "/activos/editar-activo/{id}", method = RequestMethod.GET)
	public String editarActivo(@PathVariable(value = "id") Long id, Model model) {
		Activo activo = null;
		if (id > 0) {
			activo = activoRepository.findById(id).get();
		} else {
			return "redirect:/activos/listar-activos";
		}
		
		List<Sucursal> sucursales = (List<Sucursal>) sucursalRepository.findAll();
		model.addAttribute("sucursales", sucursales);
		
		model.addAttribute("titulo", "Editar Activo");
		model.addAttribute("activo", activo);
		return "activos/form-activo";
	}

	@RequestMapping(value = "/activos/eliminar-activo/{id}", method = RequestMethod.GET)
	public String eliminarActivo(@PathVariable(value = "id") Long id, Model model) {
		Activo activo = null;
		if (id > 0) {
			activo = activoRepository.findById(id).get();
			activoRepository.delete(activo);
		} else {
			return "redirect:/activos/listar-activos";
		}

		return "redirect:/activos/listar-activos";
	}

	@RequestMapping(value = "/activos/nuevo-activo", method = RequestMethod.POST)
	public String guardarActivo(@RequestParam("file") MultipartFile foto, Activo activo) {
		Audit audit = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!foto.isEmpty()) {

			try {
				
				String path = "C:/Users/betzy/Desktop/farmacia/".concat(foto.getOriginalFilename());

				byte[] bytes = foto.getBytes();
				Path rutaCompleta = Paths.get(path);
				Files.write(rutaCompleta, bytes);
				activo.setFoto(foto.getOriginalFilename());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (activo.getId() != null && activo.getId() > 0) {
			Activo activo2 = activoRepository.findById(activo.getId()).get();
			audit = new Audit(auth.getName());
			activo.setAudit(audit);
			activo.setId(activo2.getId());
			activo.getAudit().setTsCreated(activo2.getAudit().getTsCreated());
			activo.getAudit().setUsuCreated(activo2.getAudit().getUsuCreated());
		} else {
			audit = new Audit(auth.getName());
			activo.setAudit(audit);
		}

		activoRepository.save(activo);
		return "redirect:/activos/listar-activos";
	}

}
