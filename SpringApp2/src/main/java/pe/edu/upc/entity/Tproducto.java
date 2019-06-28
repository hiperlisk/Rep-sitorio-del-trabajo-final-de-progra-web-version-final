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
@Table(name = "tproductos")
public class Tproducto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTproducto;
	@NotEmpty(message = "Ingrese nombre de Tipo producto")
	@Column(name = "", nullable = false, length = 45, unique = true)
	private String nombreTproducto;
	@NotEmpty(message = "Ingrese una Descripcion")
	private String descripcionTproducto;

	public Tproducto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tproducto(int idTproducto, String nombreTproducto, String descripcionTproducto) {
		super();
		this.idTproducto = idTproducto;
		this.nombreTproducto = nombreTproducto;
		this.descripcionTproducto = descripcionTproducto;
	}

	public int getIdTproducto() {
		return idTproducto;
	}

	public void setIdTproducto(int idTproducto) {
		this.idTproducto = idTproducto;
	}

	public String getNombreTproducto() {
		return nombreTproducto;
	}

	public void setNombreTproducto(String nombreTproducto) {
		this.nombreTproducto = nombreTproducto;
	}

	public String getDescripcionTproducto() {
		return descripcionTproducto;
	}

	public void setDescripcionTproducto(String descripcionTproducto) {
		this.descripcionTproducto = descripcionTproducto;
	}

}
