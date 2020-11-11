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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "traslados")
public class Traslado extends AbstractEntity implements Serializable {

    /**
	 * 
	 */
private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(length = 150)
    private String motivo;
    private Boolean recibido;
    	
    @OneToMany(mappedBy="traslado",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   	private List<DetalleTraslado> detalletraslados;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sucursalorigen_id",insertable = false, nullable = false, updatable = false)
	private Sucursal sucursalorigen;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sucursaldestino_id",insertable = false, nullable = false, updatable = false)
	private Sucursal sucursaldestino;
    
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Sucursal getSucursalorigen() {
		return sucursalorigen;
	}
	public void setSucursalorigen(Sucursal sucursalorigen) {
		this.sucursalorigen = sucursalorigen;
	}

	public Sucursal getSucursaldestino() {
		return sucursaldestino;
	}
	public void setSucursaldestino(Sucursal sucursaldestino) {
		this.sucursaldestino = sucursaldestino;
	}
	public Boolean getRecibido() {
		return recibido;
	}
	public void setRecibido(Boolean recibido) {
		this.recibido = recibido;
	}
	public List<DetalleTraslado> getDetalletraslados() {
		return detalletraslados;
	}
	public void setDetalletraslados(List<DetalleTraslado> detalletraslados) {
		this.detalletraslados = detalletraslados;
	}
   	      
}
