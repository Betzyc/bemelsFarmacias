package com.bemels.spring.app.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bemels.spring.app.entities.FormaEntrega;

@Repository
public interface FormaEntregaRepository extends PagingAndSortingRepository<FormaEntrega, Long>{


}
