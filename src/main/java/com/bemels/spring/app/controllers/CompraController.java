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


import com.bemels.spring.app.entities.FormaPago;
import com.bemels.spring.app.entities.Compra;
import com.bemels.spring.app.repositories.FormaPagoRepository;
import com.bemels.spring.app.repositories.SucursalRepository;
import com.bemels.spring.app.entities.ItemCompra;
import com.bemels.spring.app.entities.Producto;
import com.bemels.spring.app.entities.Sucursal;
import com.bemels.spring.app.entities.Proveedor;
import com.bemels.spring.app.service.IProveedorService;

@Controller
@RequestMapping("/compras")
@SessionAttributes("compra")
public class CompraController {

	@Autowired
	private IProveedorService proveedorService;
	@Autowired
	private SucursalRepository sucursalRepository;
	@Autowired
	private FormaPagoRepository formapagoRepository;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Model model, RedirectAttributes flash) {

		Compra compra = proveedorService.fetchCompraByIdWithProveedorWhithItemCompraWithProducto(id); // proveedorService.findCompraById(id);

		if(compra == null) {
			flash.addFlashAttribute("error", "La compra no existe en la base de datos!");
			return "redirect:/proveedores/listar";
		}
		
		model.addAttribute("compra", compra);
		model.addAttribute("titulo", "Compra: ".concat(compra.getDescripcion()));
		
		return "compras/ver";
	}

	@GetMapping("/form/{proveedorId}")
	public String crear(@PathVariable(value = "proveedorId") Long proveedorId, Map<String, Object> model,
			RedirectAttributes flash) {

		Proveedor proveedor = proveedorService.findOne(proveedorId);

		if (proveedor == null) {
			flash.addFlashAttribute("error", "El proveedor no existe en la base de datos");
			return "redirect:/proveedores/listar-proveedores";
		}

		Compra compra = new Compra();
		compra.setProveedor(proveedor);
		
		List<Sucursal> sucursales = (List<Sucursal>) sucursalRepository.findAll();
		model.put("sucursales", sucursales);
		List<FormaPago> formaspago = (List<FormaPago>) formapagoRepository.findAll();
		model.put("formaspago", formaspago);
		
		model.put("compra", compra);
		model.put("titulo", "Crear Compra");

		return "compras/form";
	}

	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		return proveedorService.findByNombre(term);
	}
	
	@PostMapping("/form")
	public String guardar(@Valid Compra compra, 
			BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
			@RequestParam(name = "precio[]", required = false) Double[] precio,
			RedirectAttributes flash,
			SessionStatus status) {
		System.out.println(compra);
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Compra");
			return "compras/form";
		}

		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear Compra");
			model.addAttribute("error", "Error: La compra NO puede no grabarse sin detalle de medicamentos!");
			return "compras/form";
		}
		
		for (int i = 0; i < itemId.length; i++) {
			Producto producto = proveedorService.findProductoById(itemId[i]);
			Optional<Sucursal> sucursal = sucursalRepository.findById(compra.getSucursalId());
			ItemCompra linea = new ItemCompra();
			linea.setCantidad(cantidad[i]);
			linea.setPrecio(precio[i]);
			linea.setProducto(producto);
			linea.setSucursal(sucursal.get());
			compra.addItemCompra(linea);

			log.info("ID: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
		}

		proveedorService.saveCompra(compra);
		status.setComplete();

		flash.addFlashAttribute("success", "Compra creada con éxito!");
		
		return "redirect:/proveedores/detalle-proveedor/" + compra.getProveedor().getId();
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		
		Compra compra = proveedorService.findCompraById(id);
		
		if(compra != null) {
			proveedorService.deleteCompra(id);
			flash.addFlashAttribute("success", "Compra eliminada con éxito!");
			return "redirect:/proveedores/detalle-proveedor/" + compra.getProveedor().getId();
		}
		flash.addFlashAttribute("error", "La compra no existe en la base de datos, no se pudo eliminar!");
		
		return "redirect:/proveedores/listar-proveedores";
	}

}
