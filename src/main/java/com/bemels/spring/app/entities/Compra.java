package com.bemels.spring.app.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "compras")
public class Compra implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message = "Debe asignarle un valor")
	private String descripcion;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts_created")
	private Date createAt;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts_modified")
	private Date updateAt;
	@Column(name = "estado")
	private Boolean estado;
	@ManyToOne(fetch = FetchType.LAZY)
	private Proveedor proveedor;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "compra_id")
	private List<ItemCompra> items;
	@Column(name = "sucursal_id")
	private Long sucursalId; 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sucursal_id",insertable = false, nullable = false, updatable = false)
	private Sucursal sucursal;
	@Column(name = "formapago_id")
	private Long formapagoId; 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "formapago_id",insertable = false, nullable = false, updatable = false)
	private FormaPago formapago;
    
    
   	public Compra() {
		this.items = new ArrayList<ItemCompra>();
	}

	@PrePersist
	public void prePersist() {
		createAt = new Date();
		updateAt = new Date();
		estado = true;
	}
	
	
	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Long getFormapagoId() {
		return formapagoId;
	}

	public void setFormapagoId(Long formapagoId) {
		this.formapagoId = formapagoId;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

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

	public FormaPago getFormapago() {
		return formapago;
	}

	public void setFormapago(FormaPago formapago) {
		this.formapago = formapago;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	
	public List<ItemCompra> getItems() {
		return items;
	}

	public void setItems(List<ItemCompra> items) {
		this.items = items;
	}

	public void addItemCompra(ItemCompra item) {
		this.items.add(item);
	}

	public Double getTotal() {
		Double total = 0.0;

		int size = items.size();

		for (int i = 0; i < size; i++) {
			total += items.get(i).calcularImporte();
		}
		return total;
	}

	private static final long serialVersionUID = 1L;
}