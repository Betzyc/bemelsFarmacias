package com.bemels.spring.app.repositories;
import org.springframework.data.repository.CrudRepository;

import com.bemels.spring.app.entities.Usuario;

public interface UserRepository extends CrudRepository<Usuario, Long>{

	public Usuario findByUsername(String username);
}
