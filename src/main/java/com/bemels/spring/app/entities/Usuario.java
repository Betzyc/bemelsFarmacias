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
@Table(name = "usuarios")
public class Usuario extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
    private String nombre;
	@Column(length = 50)
	private String apellido;
	
	@Column(length = 150)
	private String direccion;
	@Column(length = 25)
	private String telefono;
	@Column(length = 50)
    private String email;
	
	@Column(length = 30, unique = true)
	private String username;

	@Column(length = 60)
	private String password;

	private Boolean enabled;
	
	@Column(length = 100)
    private String foto;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id",insertable = true, nullable = false, updatable = false)
	private List<Role> roles;

	@OneToMany(mappedBy="cliente",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   	private List<Venta> ventas;
	
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

	public List<Venta> getVentas() {
		return ventas;
	}

	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
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

	public String getFullName() {
		return nombre+" "+apellido;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}