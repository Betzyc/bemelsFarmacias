package com.bemels.spring.app.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bemels.spring.app.entities.Activo;

@Repository
public interface ActivoRepository extends PagingAndSortingRepository<Activo, Long>{


}
