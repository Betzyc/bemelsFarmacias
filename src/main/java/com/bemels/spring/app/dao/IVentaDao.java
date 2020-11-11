package com.bemels.spring.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bemels.spring.app.entities.Venta;

public interface IVentaDao extends CrudRepository<Venta, Long>{
	
	@Query("select f from Venta f join fetch f.cliente c join fetch f.items l join fetch l.producto where f.id=?1")
	public Venta fetchByIdWithUsuarioWhithItemVentaWithProducto(Long id);
}