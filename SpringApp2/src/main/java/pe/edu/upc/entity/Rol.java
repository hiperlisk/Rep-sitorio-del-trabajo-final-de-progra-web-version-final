package pe.edu.upc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "roles")
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idRol;

	@NotEmpty(message = "Ingresa el nombre del Rol")
	@Column(name = "", nullable = false, length = 70, unique = true)
	private String nombreRol;

	@NotEmpty(message = "Ingresa el nombre del Rol")
	@Column(name = "descripcionRol", nullable = false, length = 100)
	private String descripcionRol;

	public Rol(int idRol, String nombreRol, String descripcionRol) {
		super();
		this.idRol = idRol;
		this.nombreRol = nombreRol;
		this.descripcionRol = descripcionRol;
	}

	public Rol() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	public String getDescripcionRol() {
		return descripcionRol;
	}

	public void setDescripcionRol(String descripcionRol) {
		this.descripcionRol = descripcionRol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idRol;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rol other = (Rol) obj;
		if (idRol != other.idRol)
			return false;
		return true;
	}
}
