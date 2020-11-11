package com.bemels.spring.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bemels.spring.app.entities.Usuario;

public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, Long>{

	@Query("select c from Usuario c left join fetch c.ventas f where c.id=?1")
	public Usuario fetchByIdWithVentas(Long id);
}
