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

import com.bemels.spring.app.repositories.ProductoRepository;
import com.bemels.spring.app.repositories.LaboratorioRepository;
import com.bemels.spring.app.repositories.UserRepository;
import com.bemels.spring.app.util.PageRender;
import com.bemels.spring.app.entities.Audit;
import com.bemels.spring.app.entities.Producto;
import com.bemels.spring.app.entities.Laboratorio;
import com.bemels.spring.app.entities.Usuario;

@Controller
public class ProductoController {

	@Autowired
	private ProductoRepository productoRepository;
	@Autowired
	private LaboratorioRepository laboratorioRepository;
	@Autowired
	private UserRepository usuarioRepository;

	@RequestMapping(value = "/productos/detalle-producto/{id}", method = RequestMethod.GET)
	public String detalleProducto(@PathVariable(value = "id") Long id, Model model) {

		Producto producto = productoRepository.findById(id).get();
		if (producto == null) {
			return "redirect:/productos/listar-productos";
		}

		model.addAttribute("titulo", "Detalle de Medicamento: " + producto.getNombre());
		model.addAttribute("producto", producto);
		return "productos/detalle-producto-form";
	}

	@RequestMapping(value = "/productos/listar-productos", method = RequestMethod.GET)
	public String listarProductos(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Producto> productos = productoRepository.findAll(pageRequest);

		List<Laboratorio> laboratorios = (List<Laboratorio>)laboratorioRepository.findAll();
		Map<Long, String> hmlaboratorios = new HashMap<>();
        for (Laboratorio laboratorio : laboratorios) {
            hmlaboratorios.put(laboratorio.getId(), laboratorio.getNombre());
        }
		System.out.println(hmlaboratorios);
		PageRender<Producto> pageRender = new PageRender<Producto>("/productos/listar-productos", productos);
		model.addAttribute("hmlaboratorios", hmlaboratorios);
		model.addAttribute("titulo", "Listado de medicamentos");
		model.addAttribute("productos", productos);
		model.addAttribute("page", pageRender);
		return "productos/productos";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/productos/nuevo-producto", method = RequestMethod.GET)
	public String nuevoProducto(Model model) {
		Producto producto = new Producto();
		
		List<Laboratorio> laboratorios = (List<Laboratorio>) laboratorioRepository.findAll();
		model.addAttribute("laboratorios", laboratorios);
		
		List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
		model.addAttribute("usuarios", usuarios);
		
		model.addAttribute("titulo", "Nuevo Medicamento");
		model.addAttribute("producto", producto);
		return "productos/form-producto";
	}

	@RequestMapping(value = "/productos/editar-producto/{id}", method = RequestMethod.GET)
	public String editarProducto(@PathVariable(value = "id") Long id, Model model) {
		Producto producto = null;
		if (id > 0) {
			producto = productoRepository.findById(id).get();
		} else {
			return "redirect:/productos/listar-productos";
		}
		
		List<Laboratorio> laboratorios = (List<Laboratorio>) laboratorioRepository.findAll();
		model.addAttribute("laboratorios", laboratorios);
		
		List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();
		model.addAttribute("usuarios", usuarios);
		
		model.addAttribute("titulo", "Editar Medicamento");
		model.addAttribute("producto", producto);
		return "productos/form-producto";
	}

	@RequestMapping(value = "/productos/eliminar-producto/{id}", method = RequestMethod.GET)
	public String eliminarProducto(@PathVariable(value = "id") Long id, Model model) {
		Producto producto = null;
		if (id > 0) {
			producto = productoRepository.findById(id).get();
			productoRepository.delete(producto);
		} else {
			return "redirect:/productos/listar-productos";
		}

		return "redirect:/productos/listar-productos";
	}

	@RequestMapping(value = "/productos/nuevo-producto", method = RequestMethod.POST)
	public String guardarProducto(@RequestParam("file") MultipartFile foto, Producto producto) {
		Audit audit = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!foto.isEmpty()) {

			try {
				
				String path = "C:/Users/betzy/Desktop/farmacia/".concat(foto.getOriginalFilename());

				byte[] bytes = foto.getBytes();
				Path rutaCompleta = Paths.get(path);
				Files.write(rutaCompleta, bytes);
				producto.setFoto(foto.getOriginalFilename());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (producto.getId() != null && producto.getId() > 0) {
			Producto producto2 = productoRepository.findById(producto.getId()).get();
			audit = new Audit(auth.getName());
			producto.setAudit(audit);
			producto.setId(producto2.getId());
			producto.getAudit().setTsCreated(producto2.getAudit().getTsCreated());
			producto.getAudit().setUsuCreated(producto2.getAudit().getUsuCreated());
		} else {
			audit = new Audit(auth.getName());
			producto.setAudit(audit);
		}

		productoRepository.save(producto);
		return "redirect:/productos/listar-productos";
	}

}
