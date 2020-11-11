package com.bemels.spring.app.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bemels.spring.app.entities.FormaPago;

@Repository
public interface FormaPagoRepository extends PagingAndSortingRepository<FormaPago, Long>{


}
