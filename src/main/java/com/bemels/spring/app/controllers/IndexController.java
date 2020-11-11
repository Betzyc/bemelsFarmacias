package com.bemels.spring.app.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class IndexController {
	
//	@Autowired
//   	private IUsuariosService serviceUsuarios;
	
	@GetMapping({"/index"})
	public String Index() {
		return"index";
	}	

}
