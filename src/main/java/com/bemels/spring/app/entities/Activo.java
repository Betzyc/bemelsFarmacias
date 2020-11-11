package com.bemels.spring.app.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "activos")
public class Activo extends AbstractEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private Integer cantidad;
	@Column(length = 100)
    private String nombre;
    private Double unitario;
    @Column(length = 100)
    private String foto;
    
    @Column(name = "sucursal_id")
    private Long sucursalId;
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sucursal_id",insertable = false, nullable = false, updatable = false)
	private Sucursal sucursal;
    
    
    
    public Long getSucursalId() {
		return sucursalId;
	}
	public void setSucursalId(Long sucursalId) {
		this.sucursalId = sucursalId;
	}
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getUnitario() {
		return unitario;
	}
	public void setUnitario(Double unitario) {
		this.unitario = unitario;
	}
	
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
}
