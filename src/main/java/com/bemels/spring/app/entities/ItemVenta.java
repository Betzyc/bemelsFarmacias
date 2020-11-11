package com.bemels.spring.app.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "detalle_ventas")
public class ItemVenta implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer cantidad;
	private Double precio;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "producto_id")
	private Producto producto;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sucursal_id")
	private Sucursal sucursal;
	@Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private Date fechaVencimiento;
	
	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double calcularImporte() {
		return cantidad.doubleValue() * producto.getPrecio();
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	private static final long serialVersionUID = 1L;

}