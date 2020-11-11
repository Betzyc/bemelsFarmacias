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

import com.bemels.spring.app.repositories.DepartamentoRepository;
import com.bemels.spring.app.repositories.UserRepository;
import com.bemels.spring.app.repositories.UsuarioRepository;
import com.bemels.spring.app.util.PageRender;
import com.bemels.spring.app.entities.Audit;
import com.bemels.spring.app.entities.Departamento;
import com.bemels.spring.app.entities.Usuario;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private DepartamentoRepository departamentoRepository;
	@Autowired
	private UserRepository userRepository;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/usuarios/detalle-usuario/{id}", method = RequestMethod.GET)
	public String detalleUsuario(@PathVariable(value = "id") Long id, Model model) {

		Usuario usuario = usuarioRepository.findById(id).get();
		if (usuario == null) {
			return "redirect:/usuarios/listar-usuarios";
		}

		model.addAttribute("titulo", "Pedidos Cliente: " + usuario.getFullName());
		model.addAttribute("usuario", usuario);
		return "usuarios/detalle-usuario-form";
	}
	
	@RequestMapping(value = "/usuarios/detalle-pedidos", method = RequestMethod.GET)
	public String detalleCompras(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		
		Usuario usuario = userRepository.findByUsername(auth.getName());
		if (usuario == null) {
			return "redirect:/";
		}

		model.addAttribute("titulo", "Mis Pedidos: " + usuario.getFullName());
		model.addAttribute("usuario", usuario);
		return "usuarios/detalle-usuario-form";
	}

	@RequestMapping(value = "/usuarios/listar-usuarios", method = RequestMethod.GET)
	public String listarUsuarios(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Usuario> usuarios = usuarioRepository.findAll(pageRequest);

		List<Departamento> departamentos = (List<Departamento>)departamentoRepository.findAll();
		Map<Long, String> hmdepartamentos = new HashMap<>();
        for (Departamento departamento : departamentos) {
            hmdepartamentos.put(departamento.getId(), departamento.getNombre());
        }

		
		PageRender<Usuario> pageRender = new PageRender<Usuario>("/usuarios/listar-usuarios", usuarios);
		model.addAttribute("hmdepartamentos", hmdepartamentos);
		model.addAttribute("titulo", "Listado de usuarios");
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("page", pageRender);
		return "usuarios/usuarios";
	}

	@RequestMapping(value = "/usuarios/editar-usuario/{id}", method = RequestMethod.GET)
	public String editarUsuario(@PathVariable(value = "id") Long id, Model model) {
		Usuario usuario = null;
		if (id > 0) {
			usuario = usuarioRepository.findById(id).get();
		} else {
			return "redirect:/usuarios/listar-usuarios";
		}
		
		List<Departamento> departamentos = (List<Departamento>) departamentoRepository.findAll();
		model.addAttribute("departamentos", departamentos);
		
		model.addAttribute("titulo", "Editar Usuario");
		model.addAttribute("usuario", usuario);
		return "usuarios/form-usuario";
	}
	
	@RequestMapping(value = "/usuarios/nuevo-usuario", method = RequestMethod.POST)
	public String guardarUsuario(Usuario usuario) {
		Audit audit = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (usuario.getId() != null && usuario.getId() > 0) {
			Usuario usuario2 = usuarioRepository.findById(usuario.getId()).get();
			audit = new Audit(auth.getName());
			usuario.setAudit(audit);
			usuario.setId(usuario2.getId());
			usuario.getAudit().setTsCreated(usuario2.getAudit().getTsCreated());
			usuario.getAudit().setUsuCreated(usuario2.getAudit().getUsuCreated());
			usuario.setPassword(usuario2.getPassword());
			usuario.setUsername(usuario2.getUsername());
			usuario.setEnabled(usuario2.getEnabled());
			usuario.setFoto(usuario2.getFoto());
		} else {
			audit = new Audit(auth.getName());
			usuario.setAudit(audit);
		}

		usuarioRepository.save(usuario);
		return "redirect:/usuarios/listar-usuarios";
	}
	
	@RequestMapping(value = "/usuarios/eliminar-usuario/{id}", method = RequestMethod.GET)
	public String eliminarUsuario(@PathVariable(value = "id") Long id, Model model) {
		Usuario usuario = null;
		if (id > 0) {
			usuario = usuarioRepository.findById(id).get();
			usuarioRepository.delete(usuario);
		} else {
			return "redirect:/usuarios/listar-usuarios";
		}

		return "redirect:/usuarios/listar-usuarios";
	}

}
