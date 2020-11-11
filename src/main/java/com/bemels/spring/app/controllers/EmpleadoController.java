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

import com.bemels.spring.app.repositories.EmpleadoRepository;
import com.bemels.spring.app.repositories.SucursalRepository;
import com.bemels.spring.app.repositories.UserRepository;
import com.bemels.spring.app.util.PageRender;
import com.bemels.spring.app.entities.Audit;
import com.bemels.spring.app.entities.Empleado;
import com.bemels.spring.app.entities.Sucursal;
import com.bemels.spring.app.entities.Usuario;

@Controller
public class EmpleadoController {

	@Autowired
	private EmpleadoRepository empleadoRepository;
	@Autowired
	private SucursalRepository sucursalRepository;
	@Autowired
	private UserRepository usuarioRepository;

	@RequestMapping(value = "/empleados/detalle-empleado/{id}", method = RequestMethod.GET)
	public String detalleEmpleado(@PathVariable(value = "id") Long id, Model model) {

		Empleado empleado = empleadoRepository.findById(id).get();
		if (empleado == null) {
			return "redirect:/empleados/listar-empleados";
		}

		model.addAttribute("titulo", "Detalle Empleado: " + empleado.getNombre());
		model.addAttribute("empleado", empleado);
		return "empleados/detalle-empleado-form";
	}

	@RequestMapping(value = "/empleados/listar-empleados", method = RequestMethod.GET)
	public String listarEmpleados(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Empleado> empleados = empleadoRepository.findAll(pageRequest);

		List<Sucursal> sucursales = (List<Sucursal>)sucursalRepository.findAll();
		Map<Long, String> hmsucursales = new HashMap<>();
        for (Sucursal sucursal : sucursales) {
            hmsucursales.put(sucursal.getId(), sucursal.getNombre());
        }
		
		PageRender<Empleado> pageRender = new PageRender<Empleado>("/empleados/listar-empleados", empleados);
		model.addAttribute("hmsucursales", hmsucursales);
		model.addAttribute("titulo", "Listado de empleados");
		model.addAttribute("empleados", empleados);
		model.addAttribute("page", pageRender);
		return "empleados/empleados";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/empleados/nuevo-empleado", method = RequestMethod.GET)
	public String nuevoEmpleado(Model model) {
		Empleado empleado = new Empleado();
		
		List<Sucursal> sucursales = (List<Sucursal>) sucursalRepository.findAll();
		model.addAttribute("sucursales", sucursales);
		
		List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
		model.addAttribute("usuarios", usuarios);
		
		model.addAttribute("titulo", "Nuevo Empleado");
		model.addAttribute("empleado", empleado);
		return "empleados/form-empleado";
	}

	@RequestMapping(value = "/empleados/editar-empleado/{id}", method = RequestMethod.GET)
	public String editarEmpleado(@PathVariable(value = "id") Long id, Model model) {
		Empleado empleado = null;
		if (id > 0) {
			empleado = empleadoRepository.findById(id).get();
		} else {
			return "redirect:/empleados/listar-empleados";
		}
		
		List<Sucursal> sucursales = (List<Sucursal>) sucursalRepository.findAll();
		model.addAttribute("sucursales", sucursales);
		
		List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
		model.addAttribute("usuarios", usuarios);
		
		model.addAttribute("titulo", "Editar Empleado");
		model.addAttribute("empleado", empleado);
		return "empleados/form-empleado";
	}

	@RequestMapping(value = "/empleados/eliminar-empleado/{id}", method = RequestMethod.GET)
	public String eliminarEmpleado(@PathVariable(value = "id") Long id, Model model) {
		Empleado empleado = null;
		if (id > 0) {
			empleado = empleadoRepository.findById(id).get();
			empleadoRepository.delete(empleado);
		} else {
			return "redirect:/empleados/listar-empleados";
		}

		return "redirect:/empleados/listar-empleados";
	}

	@RequestMapping(value = "/empleados/nuevo-empleado", method = RequestMethod.POST)
	public String guardarEmpleado(@RequestParam("file") MultipartFile foto, Empleado empleado) {
		Audit audit = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!foto.isEmpty()) {

			try {
				
				String path = "C:/Users/betzy/Desktop/farmacia/".concat(foto.getOriginalFilename());

				byte[] bytes = foto.getBytes();
				Path rutaCompleta = Paths.get(path);
				Files.write(rutaCompleta, bytes);
				empleado.setFoto(foto.getOriginalFilename());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (empleado.getId() != null && empleado.getId() > 0) {
			Empleado empleado2 = empleadoRepository.findById(empleado.getId()).get();
			audit = new Audit(auth.getName());
			empleado.setAudit(audit);
			empleado.setId(empleado2.getId());
			empleado.getAudit().setTsCreated(empleado2.getAudit().getTsCreated());
			empleado.getAudit().setUsuCreated(empleado2.getAudit().getUsuCreated());
		} else {
			audit = new Audit(auth.getName());
			empleado.setAudit(audit);
		}

		empleadoRepository.save(empleado);
		return "redirect:/empleados/listar-empleados";
	}

}
