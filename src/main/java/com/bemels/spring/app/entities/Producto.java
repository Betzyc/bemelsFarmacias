package com.bemels.spring.app.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "productos")
public class Producto extends AbstractEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;
	private String codigobarras;
	private String foto;
	private Double precio;
	private Double costo;
	private Integer maximo;
	private Integer minimo;
	@Column(name = "laboratorio_id")
	private Long laboratorioId;

	
	public Long getLaboratorioId() {
		return laboratorioId;
	}


	public void setLaboratorioId(Long laboratorioId) {
		this.laboratorioId = laboratorioId;
	}


	public String getCodigobarras() {
		return codigobarras;
	}


	public void setCodigobarras(String codigobarras) {
		this.codigobarras = codigobarras;
	}


	public String getFoto() {
		return foto;
	}


	public void setFoto(String foto) {
		this.foto = foto;
	}


	public Double getCosto() {
		return costo;
	}


	public void setCosto(Double costo) {
		this.costo = costo;
	}


	public Integer getMaximo() {
		return maximo;
	}


	public void setMaximo(Integer maximo) {
		this.maximo = maximo;
	}


	public Integer getMinimo() {
		return minimo;
	}


	public void setMinimo(Integer minimo) {
		this.minimo = minimo;
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

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}


	private static final long serialVersionUID = 1L;

}
