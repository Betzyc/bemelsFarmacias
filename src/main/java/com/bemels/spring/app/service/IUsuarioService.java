package com.bemels.spring.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bemels.spring.app.entities.Usuario;
import com.bemels.spring.app.entities.Producto;
import com.bemels.spring.app.entities.Venta;

public interface IUsuarioService {

	public List<Usuario> findAll();

	public Page<Usuario> findAll(Pageable pageable);

	public void save(Usuario usuario);

	public Usuario findOne(Long id);
	
	public Usuario fetchByIdWithVentas(Long id);

	public void delete(Long id);

	public List<Producto> findByNombre(String term);
	
	public void saveVenta(Venta venta);
	
	public Producto findProductoById(Long id);
	
	public Venta findVentaById(Long id);
	
	public void deleteVenta(Long id);
	
	public Venta fetchVentaByIdWithUsuarioWhithItemVentaWithProducto(Long id);
}
