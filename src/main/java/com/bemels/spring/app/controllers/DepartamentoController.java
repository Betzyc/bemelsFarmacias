package com.bemels.spring.app.controllers;

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

import com.bemels.spring.app.repositories.DepartamentoRepository;
import com.bemels.spring.app.util.PageRender;
import com.bemels.spring.app.entities.Audit;
import com.bemels.spring.app.entities.Departamento;

@Controller
public class DepartamentoController {

	@Autowired
	private DepartamentoRepository departamentoRepository;

	@RequestMapping(value = "/departamentos/detalle-departamento/{id}", method = RequestMethod.GET)
	public String detalleDepartamento(@PathVariable(value = "id") Long id, Model model) {

		Departamento departamento = departamentoRepository.findById(id).get();
		if (departamento == null) {
			return "redirect:/departamentos/listar-departamentos";
		}

		model.addAttribute("titulo", "Detalle Departamento: " + departamento.getNombre());
		model.addAttribute("departamento", departamento);
		return "departamentos/detalle-departamento-form";
	}

	@RequestMapping(value = "/departamentos/listar-departamentos", method = RequestMethod.GET)
	public String listarDepartamentos(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Departamento> departamentos = departamentoRepository.findAll(pageRequest);

		PageRender<Departamento> pageRender = new PageRender<Departamento>("/departamentos/listar-departamentos", departamentos);
		model.addAttribute("titulo", "Listado de departamentos");
		model.addAttribute("departamentos", departamentos);
		model.addAttribute("page", pageRender);
		return "/departamentos/departamentos";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/departamentos/nuevo-departamento", method = RequestMethod.GET)
	public String nuevoDepartamento(Model model) {
		Departamento departamento = new Departamento();
		model.addAttribute("titulo", "Nuevo Departamento");
		model.addAttribute("departamento", departamento);
		return "departamentos/form-departamento";
	}

	@RequestMapping(value = "/departamentos/editar-departamento/{id}", method = RequestMethod.GET)
	public String editarDepartamento(@PathVariable(value = "id") Long id, Model model) {
		Departamento departamento = null;
		if (id > 0) {
			departamento = departamentoRepository.findById(id).get();
		} else {
			return "redirect:/departamentos/listar-departamentos";
		}
		model.addAttribute("titulo", "Editar Departamento");
		model.addAttribute("departamento", departamento);
		return "departamentos/form-departamento";
	}

	@RequestMapping(value = "/departamentos/eliminar-departamento/{id}", method = RequestMethod.GET)
	public String eliminarDepartamento(@PathVariable(value = "id") Long id, Model model) {
		Departamento departamento = null;
		if (id > 0) {
			departamento = departamentoRepository.findById(id).get();
			departamentoRepository.delete(departamento);
		} else {
			return "redirect:/departamentos/listar-departamentos";
		}

		return "redirect:/departamentos/listar-departamentos";
	}

	@RequestMapping(value = "/departamentos/nuevo-departamento", method = RequestMethod.POST)
	public String guardarDepartamento(Departamento departamento) {
		Audit audit = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (departamento.getId() != null && departamento.getId() > 0) {
			Departamento departamento2 = departamentoRepository.findById(departamento.getId()).get();
			audit = new Audit(auth.getName());
			departamento.setAudit(audit);
			departamento.setId(departamento2.getId());
			departamento.getAudit().setTsCreated(departamento2.getAudit().getTsCreated());
			departamento.getAudit().setUsuCreated(departamento2.getAudit().getUsuCreated());
		} else {
			audit = new Audit(auth.getName());
			departamento.setAudit(audit);
		}

		departamentoRepository.save(departamento);
		return "redirect:/departamentos/listar-departamentos";
	}

}
