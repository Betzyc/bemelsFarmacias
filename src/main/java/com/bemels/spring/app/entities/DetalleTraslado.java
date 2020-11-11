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
@Table(name = "detalle_traslados")
public class DetalleTraslado implements Serializable {

    /**
	 * 
	 */
private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "traslado_id", insertable = false, nullable = false, updatable = false)
	private Traslado traslado;
	
  
		
	private Integer cantidad;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Traslado getTraslado() {
		return traslado;
	}
	public void setTraslado(Traslado traslado) {
		this.traslado = traslado;
	}
	
	
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
 
}
