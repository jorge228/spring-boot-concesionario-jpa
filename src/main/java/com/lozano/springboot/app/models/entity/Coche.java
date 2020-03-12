package com.lozano.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "coches")
@NamedQuery(name = "Coche.findAll", query = "SELECT c FROM Coche c")
public class Coche implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;

	private String imagen;

	@NotEmpty
	private String modelo;

	@NotNull
	private int precio;

	@NotNull
	private int velocidad;

	// bi-directional many-to-one association to Marca
	@ManyToOne
	@JoinColumn(name = "id_marca")
	private Marca marca;

	public Coche() {
	}
	
	

	public Coche(int id, Date fecha, String imagen, String modelo, int precio, int velocidad) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.imagen = imagen;
		this.modelo = modelo;
		this.precio = precio;
		this.velocidad = velocidad;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	public int getMarca() {
		return marca.getId();
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
