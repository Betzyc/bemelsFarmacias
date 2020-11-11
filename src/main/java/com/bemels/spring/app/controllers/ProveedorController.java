package com.bemels.spring.app.controllers;

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
import com.bemels.spring.app.repositories.ProveedorRepository;
import com.bemels.spring.app.repositories.DepartamentoRepository;
import com.bemels.spring.app.util.PageRender;
import com.bemels.spring.app.entities.Audit;
import com.bemels.spring.app.entities.Proveedor;
import com.bemels.spring.app.entities.Departamento;

@Controller
public class ProveedorController {

	@Autowired
	private ProveedorRepository proveedorRepository;
	@Autowired
	private DepartamentoRepository departamentoRepository;

	@RequestMapping(value = "/proveedores/detalle-proveedor/{id}", method = RequestMethod.GET)
	public String detalleProveedor(@PathVariable(value = "id") Long id, Model model) {

		Proveedor proveedor = proveedorRepository.findById(id).get();
		if (proveedor == null) {
			return "redirect:/proveedores/listar-proveedores";
		}

		model.addAttribute("titulo", "Detalle Proveedor: " + proveedor.getNombre());
		model.addAttribute("proveedor", proveedor);
		return "proveedores/detalle-proveedor-form";
	}

	@RequestMapping(value = "/proveedores/listar-proveedores", method = RequestMethod.GET)
	public String listarProveedores(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Proveedor> proveedores = proveedorRepository.findAll(pageRequest);

		List<Departamento> departamentos = (List<Departamento>)departamentoRepository.findAll();
		Map<Long, String> hmdepartamentos = new HashMap<>();
        for (Departamento departamento : departamentos) {
            hmdepartamentos.put(departamento.getId(), departamento.getNombre());
        }
		
		PageRender<Proveedor> pageRender = new PageRender<Proveedor>("/proveedores/listar-proveedores", proveedores);
		model.addAttribute("hmdepartamentos", hmdepartamentos);
		model.addAttribute("titulo", "Listado de proveedores");
		model.addAttribute("proveedores", proveedores);
		model.addAttribute("page", pageRender);
		return "proveedores/proveedores";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/proveedores/nuevo-proveedor", method = RequestMethod.GET)
	public String nuevoProveedor(Model model) {
		Proveedor proveedor = new Proveedor();
		
		List<Departamento> departamentos = (List<Departamento>) departamentoRepository.findAll();
		model.addAttribute("departamentos", departamentos);
		
		model.addAttribute("titulo", "Nuevo Proveedor");
		model.addAttribute("proveedor", proveedor);
		return "proveedores/form-proveedor";
	}

	@RequestMapping(value = "/proveedores/editar-proveedor/{id}", method = RequestMethod.GET)
	public String editarProveedor(@PathVariable(value = "id") Long id, Model model) {
		Proveedor proveedor = null;
		if (id > 0) {
			proveedor = proveedorRepository.findById(id).get();
		} else {
			return "redirect:/proveedores/listar-proveedores";
		}
		
		List<Departamento> departamentos = (List<Departamento>) departamentoRepository.findAll();
		model.addAttribute("departamentos", departamentos);
		
		model.addAttribute("titulo", "Editar Proveedor");
		model.addAttribute("proveedor", proveedor);
		return "proveedores/form-proveedor";
	}

	@RequestMapping(value = "/proveedores/eliminar-proveedor/{id}", method = RequestMethod.GET)
	public String eliminarProveedor(@PathVariable(value = "id") Long id, Model model) {
		Proveedor proveedor = null;
		if (id > 0) {
			proveedor = proveedorRepository.findById(id).get();
			proveedorRepository.delete(proveedor);
		} else {
			return "redirect:/proveedores/listar-proveedores";
		}

		return "redirect:/proveedores/listar-proveedores";
	}

	@RequestMapping(value = "/proveedores/nuevo-proveedor", method = RequestMethod.POST)
	public String guardarProveedor(Proveedor proveedor) {
		Audit audit = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (proveedor.getId() != null && proveedor.getId() > 0) {
			Proveedor proveedor2 = proveedorRepository.findById(proveedor.getId()).get();
			audit = new Audit(auth.getName());
			proveedor.setAudit(audit);
			proveedor.setId(proveedor2.getId());
			proveedor.getAudit().setTsCreated(proveedor2.getAudit().getTsCreated());
			proveedor.getAudit().setUsuCreated(proveedor2.getAudit().getUsuCreated());
		} else {
			audit = new Audit(auth.getName());
			proveedor.setAudit(audit);
		}

		proveedorRepository.save(proveedor);
		return "redirect:/proveedores/listar-proveedores";
	}

}
