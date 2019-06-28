package pe.edu.upc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "areas")
public class Area implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idArea;

	@NotEmpty(message = "Ingresa el nombre del área.")
	@Column(name = "", nullable = false, length = 45, unique = true)
	private String nombreArea;

	@Min(10)
	@Max(50)
	@Column(name = "numeroPersonal", nullable = false)
	private int numeroPersonal;

	public Area(int idArea, String nombreArea, int numeroPersonal) {
		super();
		this.idArea = idArea;
		this.nombreArea = nombreArea;
		this.numeroPersonal = numeroPersonal;
	}

	public Area() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdArea() {
		return idArea;
	}

	public void setIdArea(int idArea) {
		this.idArea = idArea;
	}

	public String getNombreArea() {
		return nombreArea;
	}

	public void setNombreArea(String nombreArea) {
		this.nombreArea = nombreArea;
	}

	public int getNumeroPersonal() {
		return numeroPersonal;
	}

	public void setNumeroPersonal(int numeroPersonal) {
		this.numeroPersonal = numeroPersonal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idArea;
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
		Area other = (Area) obj;
		if (idArea != other.idArea)
			return false;
		return true;
	}

}
