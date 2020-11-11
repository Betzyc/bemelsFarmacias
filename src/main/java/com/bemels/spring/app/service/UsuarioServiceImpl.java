package com.bemels.spring.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bemels.spring.app.dao.IUsuarioDao;
import com.bemels.spring.app.dao.IVentaDao;
import com.bemels.spring.app.dao.IProductoDao;

import com.bemels.spring.app.entities.Usuario;
import com.bemels.spring.app.entities.Venta;
import com.bemels.spring.app.entities.Producto;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioDao usuarioDao;

	@Autowired
	private IProductoDao productoDao;

	@Autowired
	private IVentaDao ventaDao;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional
	public void save(Usuario usuario) {
		usuarioDao.save(usuario);

	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findOne(Long id) {
		// TODO Auto-generated method stub
		return usuarioDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Usuario fetchByIdWithVentas(Long id) {
		return usuarioDao.fetchByIdWithVentas(id);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		usuarioDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> findAll(Pageable pageable) {
		return usuarioDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByNombre(String term) {
		return productoDao.findByNombreLikeIgnoreCase("%" + term + "%");
	}

	@Override
	@Transactional
	public void saveVenta(Venta venta) {
		ventaDao.save(venta);
	}

	@Override
	@Transactional(readOnly=true)
	public Producto findProductoById(Long id) {
		// TODO Auto-generated method stub
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Venta findVentaById(Long id) {
		// TODO Auto-generated method stub
		return ventaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteVenta(Long id) {
		ventaDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Venta fetchVentaByIdWithUsuarioWhithItemVentaWithProducto(Long id) {
		return ventaDao.fetchByIdWithUsuarioWhithItemVentaWithProducto(id);
	}
}
