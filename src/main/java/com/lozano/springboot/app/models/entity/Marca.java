package com.lozano.springboot.app.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "marcas")
@NamedQuery(name = "Marca.findAll", query = "SELECT m FROM Marca m")
public class Marca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	//solamente string con notempty, int, fechas y de más NotNull
	private String logo;

	@NotEmpty
	private String nombre;

	@NotEmpty
	private String pais;

	@NotEmpty
	private String presidente;

	// bi-directional many-to-one association to Coche
	@OneToMany(mappedBy = "marca", cascade = CascadeType.ALL)
	private List<Coche> coches;

	public Marca() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getPresidente() {
		return presidente;
	}

	public void setPresidente(String presidente) {
		this.presidente = presidente;
	}

	public List<Coche> getCoches() {
		return coches;
	}

	public void setCoches(List<Coche> coches) {
		this.coches = coches;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}