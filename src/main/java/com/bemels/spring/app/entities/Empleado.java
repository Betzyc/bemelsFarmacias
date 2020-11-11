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
@Table(name = "empleados")
public class Empleado extends AbstractEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(length = 50)
    private String nombre;
	@Column(length = 50)
	private String apellido;
	@Column(name = "sucursal_id")
    private Long sucursalId;
	@Column(length = 25)
	private String dpi;
	@Column(length = 25)
	private String telefono;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="dd-MM-yyyy")
    private Date fechaNacimiento;
    @Column(length = 50)
    private Double sueldo;
    @Column(length = 100)
    private String foto;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sucursal_id",insertable = false, nullable = false, updatable = false)
	private Sucursal sucursal;
    
    public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public String getFoto() {
		return foto;
	}

	public Long getSucursalId() {
		return sucursalId;
	}

	public void setSucursalId(Long sucursalId) {
		this.sucursalId = sucursalId;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

	public Double getSueldo() {
		return sueldo;
	}

	public void setSueldo(Double sueldo) {
		this.sueldo = sueldo;
	}

}
