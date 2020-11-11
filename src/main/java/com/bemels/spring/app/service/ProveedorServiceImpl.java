package com.bemels.spring.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bemels.spring.app.dao.IProveedorDao;
import com.bemels.spring.app.dao.ICompraDao;
import com.bemels.spring.app.dao.IProductoDao;

import com.bemels.spring.app.entities.Proveedor;
import com.bemels.spring.app.entities.Compra;
import com.bemels.spring.app.entities.Producto;

@Service
public class ProveedorServiceImpl implements IProveedorService {

	@Autowired
	private IProveedorDao proveedorDao;

	@Autowired
	private IProductoDao productoDao;

	@Autowired
	private ICompraDao compraDao;

	@Override
	@Transactional(readOnly = true)
	public List<Proveedor> findAll() {
		// TODO Auto-generated method stub
		return (List<Proveedor>) proveedorDao.findAll();
	}

	@Override
	@Transactional
	public void save(Proveedor proveedor) {
		proveedorDao.save(proveedor);

	}

	@Override
	@Transactional(readOnly = true)
	public Proveedor findOne(Long id) {
		// TODO Auto-generated method stub
		return proveedorDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Proveedor fetchByIdWithCompras(Long id) {
		return proveedorDao.fetchByIdWithCompras(id);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		proveedorDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<Proveedor> findAll(Pageable pageable) {
		return proveedorDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByNombre(String term) {
		return productoDao.findByNombreLikeIgnoreCase("%" + term + "%");
	}

	@Override
	@Transactional
	public void saveCompra(Compra compra) {
		compraDao.save(compra);
	}

	@Override
	@Transactional(readOnly=true)
	public Producto findProductoById(Long id) {
		// TODO Auto-generated method stub
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Compra findCompraById(Long id) {
		// TODO Auto-generated method stub
		return compraDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteCompra(Long id) {
		compraDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Compra fetchCompraByIdWithProveedorWhithItemCompraWithProducto(Long id) {
		return compraDao.fetchByIdWithProveedorWhithItemCompraWithProducto(id);
	}
}
