package com.bemels.spring.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bemels.spring.app.entities.Compra;

public interface ICompraDao extends CrudRepository<Compra, Long>{
	
	@Query("select f from Compra f join fetch f.proveedor c join fetch f.items l join fetch l.producto where f.id=?1")
	public Compra fetchByIdWithProveedorWhithItemCompraWithProducto(Long id);
}