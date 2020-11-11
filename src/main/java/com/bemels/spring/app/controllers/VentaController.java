package com.bemels.spring.app.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.bemels.spring.app.entities.FormaEntrega;
import com.bemels.spring.app.entities.FormaPago;
import com.bemels.spring.app.entities.Venta;
import com.bemels.spring.app.repositories.FormaEntregaRepository;
import com.bemels.spring.app.repositories.FormaPagoRepository;
import com.bemels.spring.app.repositories.SucursalRepository;
import com.bemels.spring.app.entities.ItemVenta;
import com.bemels.spring.app.entities.Producto;
import com.bemels.spring.app.entities.Sucursal;
import com.bemels.spring.app.entities.Usuario;
import com.bemels.spring.app.service.IUsuarioService;

@Controller
@RequestMapping("/ventas")
@SessionAttributes("venta")
public class VentaController {

	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private SucursalRepository sucursalRepository;
	@Autowired
	private FormaEntregaRepository formaentregaRepository;
	@Autowired
	private FormaPagoRepository formapagoRepository;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Model model, RedirectAttributes flash) {

		Venta venta = usuarioService.fetchVentaByIdWithUsuarioWhithItemVentaWithProducto(id); // usuarioService.findVentaById(id);

		if(venta == null) {
			flash.addFlashAttribute("error", "La venta no existe en la base de datos!");
			//return "redirect:/usuarios/listar";
			return "redirect:/usuarios/detalle-pedidos";
		}
		
		model.addAttribute("venta", venta);
		model.addAttribute("titulo", "Venta: ".concat(venta.getDescripcion()));
		
		return "ventas/ver";
	}

	@GetMapping("/form/{usuarioId}")
	public String crear(@PathVariable(value = "usuarioId") Long usuarioId, Map<String, Object> model,
			RedirectAttributes flash) {

		Usuario usuario = usuarioService.findOne(usuarioId);

		if (usuario == null) {
			flash.addFlashAttribute("error", "El usuario no existe en la base de datos");
			return "redirect:/";
		}

		Venta venta = new Venta();
		venta.setCliente(usuario);
		
		List<Sucursal> sucursales = (List<Sucursal>) sucursalRepository.findAll();
		model.put("sucursales", sucursales);
		List<FormaEntrega> formasentrega = (List<FormaEntrega>) formaentregaRepository.findAll();
		model.put("formasentrega", formasentrega);
		List<FormaPago> formaspago = (List<FormaPago>) formapagoRepository.findAll();
		model.put("formaspago", formaspago);
		
		model.put("venta", venta);
		model.put("titulo", "Crear Venta");

		return "ventas/form";
	}

	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		return usuarioService.findByNombre(term);
	}
	
	@PostMapping("/form")
	public String guardar(@Valid Venta venta, 
			BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
			@RequestParam(name = "precio[]", required = false) Double[] precio,
			RedirectAttributes flash,
			SessionStatus status) {
		System.out.println(venta);
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Venta");
			return "ventas/form";
		}

		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear Venta");
			model.addAttribute("error", "Error: La venta NO puede no grabarse sin detalle de medicamentos!");
			return "ventas/form";
		}
		
		for (int i = 0; i < itemId.length; i++) {
			Producto producto = usuarioService.findProductoById(itemId[i]);
			Optional<Sucursal> sucursal = sucursalRepository.findById(venta.getSucursalId());
			ItemVenta linea = new ItemVenta();
			linea.setCantidad(cantidad[i]);
			linea.setPrecio(precio[i]);
			linea.setProducto(producto);
			linea.setSucursal(sucursal.get());
			venta.addItemVenta(linea);

			log.info("ID: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
		}

		usuarioService.saveVenta(venta);
		status.setComplete();

		flash.addFlashAttribute("success", "Venta creada con éxito!");
		return "redirect:/";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		
		Venta venta = usuarioService.findVentaById(id);
		
		if(venta != null) {
			usuarioService.deleteVenta(id);
			flash.addFlashAttribute("success", "Venta eliminada con éxito!");
			return "redirect:/";
		}
		flash.addFlashAttribute("error", "La venta no existe en la base de datos, no se pudo eliminar!");
		
		//return "redirect:/usuarios/listar-usuarios";
		return "redirect:/";
	}

}
