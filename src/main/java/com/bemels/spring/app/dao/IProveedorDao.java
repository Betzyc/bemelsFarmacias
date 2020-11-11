package com.bemels.spring.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bemels.spring.app.entities.Proveedor;

public interface IProveedorDao extends PagingAndSortingRepository<Proveedor, Long>{

	@Query("select c from Proveedor c left join fetch c.compras f where c.id=?1")
	public Proveedor fetchByIdWithCompras(Long id);
}
