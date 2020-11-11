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
@Table(name = "sucursales")
public class Sucursal extends AbstractEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(length = 50)
    private String nombre;
	@Column(length = 150)
	private String direccion;
	@Column(length = 25)
	private String telefono;
    @Column(length = 50)
    private String email;
    @Column(length = 100)
    private String foto;
   
   
    @OneToMany(mappedBy="sucursalorigen",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Traslado> trasladosorigen;
    @OneToMany(mappedBy="sucursaldestino",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Traslado> trasladosdestino;
    @OneToMany(mappedBy="sucursal",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Empleado> empleados;
    @OneToMany(mappedBy="sucursal",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   	private List<Activo> activos;
    @OneToMany(mappedBy="sucursal",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   	private List<Compra> compras;
    @OneToMany(mappedBy="sucursal",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   	private List<Venta> ventas;
    @OneToMany(mappedBy="sucursal",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   	private List<StockSucursal> stocksucursal;
    @Column(name = "departamento_id")
    private Long departamentoId;
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "departamento_id",insertable = false, nullable = false, updatable = false)
	private Departamento departamento;
    
    
    
    public Long getDepartamentoId() {
		return departamentoId;
	}
	public void setDepartamentoId(Long departamentoId) {
		this.departamentoId = departamentoId;
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

	public Departamento getDepartamento() {
		return departamento;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public List<Activo> getActivos() {
		return activos;
	}
	public void setActivos(List<Activo> activos) {
		this.activos = activos;
	}
	public List<Venta> getVentas() {
		return ventas;
	}
	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	}
	public List<Empleado> getEmpleados() {
		return empleados;
	}
	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}
	public List<Compra> getCompras() {
		return compras;
	}
	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}
	public List<Traslado> getTrasladosorigen() {
		return trasladosorigen;
	}
	public void setTrasladosorigen(List<Traslado> trasladosorigen) {
		this.trasladosorigen = trasladosorigen;
	}
	public List<Traslado> getTrasladosdestino() {
		return trasladosdestino;
	}
	public void setTrasladosdestino(List<Traslado> trasladosdestino) {
		this.trasladosdestino = trasladosdestino;
	}
	
    
    
    
}
