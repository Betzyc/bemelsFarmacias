package com.bemels.spring.app.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "departamentos")
public class Departamento extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 50, unique = true)
	private String nombre;
	@OneToMany(mappedBy="departamento",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   	private List<Usuario> usuarios;
	@OneToMany(mappedBy="departamento",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   	private List<Sucursal> sucursales;
	@OneToMany(mappedBy="departamento",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   	private List<Proveedor> proveedores;
	
	public List<Sucursal> getSucursales() {
		return sucursales;
	}

	public void setSucursales(List<Sucursal> sucursales) {
		this.sucursales = sucursales;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
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


}