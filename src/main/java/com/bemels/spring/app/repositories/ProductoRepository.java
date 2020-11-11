package com.bemels.spring.app.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bemels.spring.app.entities.Producto;

@Repository
public interface ProductoRepository extends PagingAndSortingRepository<Producto, Long>{


}
