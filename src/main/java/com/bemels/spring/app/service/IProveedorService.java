package com.bemels.spring.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bemels.spring.app.entities.Proveedor;
import com.bemels.spring.app.entities.Producto;
import com.bemels.spring.app.entities.Compra;

public interface IProveedorService {

	public List<Proveedor> findAll();

	public Page<Proveedor> findAll(Pageable pageable);

	public void save(Proveedor proveedor);

	public Proveedor findOne(Long id);
	
	public Proveedor fetchByIdWithCompras(Long id);

	public void delete(Long id);

	public List<Producto> findByNombre(String term);
	
	public void saveCompra(Compra compra);
	
	public Producto findProductoById(Long id);
	
	public Compra findCompraById(Long id);
	
	public void deleteCompra(Long id);
	
	public Compra fetchCompraByIdWithProveedorWhithItemCompraWithProducto(Long id);
}
