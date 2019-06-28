package pe.edu.upc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "tinformes")
public class Tinforme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTinforme;
	@NotEmpty(message = "Ingrese nombre de Tipo Informe")
	@Column(name = "", nullable = false, length = 45, unique = true)
	private String nombreTinforme;
	@NotEmpty(message = "Ingrese una Descripcion")
	private String descripcionTinforme;

	public Tinforme() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tinforme(int idTinforme, String nombreTinforme, String descripcionTinforme) {
		super();
		this.idTinforme = idTinforme;
		this.nombreTinforme = nombreTinforme;
		this.descripcionTinforme = descripcionTinforme;
	}

	public int getIdTinforme() {
		return idTinforme;
	}

	public void setIdTinforme(int idTinforme) {
		this.idTinforme = idTinforme;
	}

	public String getNombreTinforme() {
		return nombreTinforme;
	}

	public void setNombreTinforme(String nombreTinforme) {
		this.nombreTinforme = nombreTinforme;
	}

	public String getDescripcionTinforme() {
		return descripcionTinforme;
	}

	public void setDescripcionTinforme(String descripcionTinforme) {
		this.descripcionTinforme = descripcionTinforme;
	}

}
