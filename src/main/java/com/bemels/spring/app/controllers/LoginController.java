package com.bemels.spring.app.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bemels.spring.app.entities.Audit;
import com.bemels.spring.app.entities.Departamento;
import com.bemels.spring.app.entities.Role;
import com.bemels.spring.app.entities.Usuario;
import com.bemels.spring.app.repositories.DepartamentoRepository;
import com.bemels.spring.app.repositories.UserRepository;



@Controller
public class LoginController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DepartamentoRepository departamentoRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
		
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String nuevoUsuario(Model model) {
		Usuario usuario = new Usuario();
		
		List<Departamento> departamentos = (List<Departamento>) departamentoRepository.findAll();
		model.addAttribute("departamentos", departamentos);
		
		model.addAttribute("titulo", "Formulario de Registro");
		model.addAttribute("usuario", usuario);
		return "formRegistro";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String guardarUsuario(Usuario usuario, RedirectAttributes flash) {
		Audit audit = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (usuario.getId() != null && usuario.getId() > 0) {
			Usuario usuario2 = userRepository.findById(usuario.getId()).get();
			audit = new Audit(auth.getName());
			usuario.setAudit(audit);
			usuario.setId(usuario2.getId());
			usuario.getAudit().setTsCreated(usuario2.getAudit().getTsCreated());
			usuario.getAudit().setUsuCreated(usuario2.getAudit().getUsuCreated());
		} else {
			audit = new Audit(auth.getName());
			usuario.setAudit(audit);
		}
		usuario.setEnabled(true);

		String clave = usuario.getPassword();
		String claveEncriptada = passwordEncoder.encode(clave); 
		usuario.setPassword(claveEncriptada);	
		
		Role role = new Role();
		role.setAuthority("ROLE_USER");
		List <Role> roles = new ArrayList<Role>();
		roles.add(role);
		usuario.setRoles(roles);
		userRepository.save(usuario);
		flash.addFlashAttribute("info", usuario.getFullName()+" Has sido registrado(a) exitosamente!");
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,
			RedirectAttributes flash) {

		if (principal != null) {
			flash.addFlashAttribute("info", "Ya ha inciado sesión anteriormente");
			return "redirect:/";
		}

		if (error != null) {
			model.addAttribute("error",
					"Error en el login: Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo!");
		}

		if (logout != null) {
			model.addAttribute("success", "Ha cerrado sesión con éxito!");
		}

		return "login";
	}
}