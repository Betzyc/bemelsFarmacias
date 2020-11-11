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

import com.bemels.spring.app.repositories.LaboratorioRepository;
import com.bemels.spring.app.util.PageRender;
import com.bemels.spring.app.entities.Audit;
import com.bemels.spring.app.entities.Laboratorio;

@Controller
public class LaboratorioController {

	@Autowired
	private LaboratorioRepository laboratorioRepository;

	@RequestMapping(value = "/laboratorios/detalle-laboratorio/{id}", method = RequestMethod.GET)
	public String detalleLaboratorio(@PathVariable(value = "id") Long id, Model model) {

		Laboratorio laboratorio = laboratorioRepository.findById(id).get();
		if (laboratorio == null) {
			return "redirect:/laboratorios/listar-laboratorios";
		}

		model.addAttribute("titulo", "Detalle Laboratorio: " + laboratorio.getNombre());
		model.addAttribute("laboratorio", laboratorio);
		return "laboratorios/detalle-laboratorio-form";
	}

	@RequestMapping(value = "/laboratorios/listar-laboratorios", method = RequestMethod.GET)
	public String listarLaboratorios(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Laboratorio> laboratorios = laboratorioRepository.findAll(pageRequest);

		PageRender<Laboratorio> pageRender = new PageRender<Laboratorio>("/laboratorios/listar-laboratorios", laboratorios);
		model.addAttribute("titulo", "Listado de laboratorios");
		model.addAttribute("laboratorios", laboratorios);
		model.addAttribute("page", pageRender);
		return "laboratorios/laboratorios";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/laboratorios/nuevo-laboratorio", method = RequestMethod.GET)
	public String nuevoLaboratorio(Model model) {
		Laboratorio laboratorio = new Laboratorio();
		model.addAttribute("titulo", "Nuevo Laboratorio");
		model.addAttribute("laboratorio", laboratorio);
		return "laboratorios/form-laboratorio";
	}

	@RequestMapping(value = "/laboratorios/editar-laboratorio/{id}", method = RequestMethod.GET)
	public String editarLaboratorio(@PathVariable(value = "id") Long id, Model model) {
		Laboratorio laboratorio = null;
		if (id > 0) {
			laboratorio = laboratorioRepository.findById(id).get();
		} else {
			return "redirect:/laboratorios/listar-laboratorios";
		}
		model.addAttribute("titulo", "Editar Laboratorio");
		model.addAttribute("laboratorio", laboratorio);
		return "laboratorios/form-laboratorio";
	}

	@RequestMapping(value = "/laboratorios/eliminar-laboratorio/{id}", method = RequestMethod.GET)
	public String eliminarLaboratorio(@PathVariable(value = "id") Long id, Model model) {
		Laboratorio laboratorio = null;
		if (id > 0) {
			laboratorio = laboratorioRepository.findById(id).get();
			laboratorioRepository.delete(laboratorio);
		} else {
			return "redirect:/laboratorios/listar-laboratorios";
		}

		return "redirect:/laboratorios/listar-laboratorios";
	}

	@RequestMapping(value = "/laboratorios/nuevo-laboratorio", method = RequestMethod.POST)
	public String guardarLaboratorio(Laboratorio laboratorio) {
		Audit audit = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (laboratorio.getId() != null && laboratorio.getId() > 0) {
			Laboratorio laboratorio2 = laboratorioRepository.findById(laboratorio.getId()).get();
			audit = new Audit(auth.getName());
			laboratorio.setAudit(audit);
			laboratorio.setId(laboratorio2.getId());
			laboratorio.getAudit().setTsCreated(laboratorio2.getAudit().getTsCreated());
			laboratorio.getAudit().setUsuCreated(laboratorio2.getAudit().getUsuCreated());
		} else {
			audit = new Audit(auth.getName());
			laboratorio.setAudit(audit);
		}

		laboratorioRepository.save(laboratorio);
		return "redirect:/laboratorios/listar-laboratorios";
	}

}
