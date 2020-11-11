package com.bemels.spring.app.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stock_sucursal")
public class StockSucursal implements Serializable {

    /**
	 * 
	 */
private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sucursal_id",insertable = false, nullable = false, updatable = false)
	private Sucursal sucursal;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "producto_id",insertable = false, nullable = false, updatable = false)
	private Producto producto;
    
    private Integer stock;

    
    
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}


	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
    
}
