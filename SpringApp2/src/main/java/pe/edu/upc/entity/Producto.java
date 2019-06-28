package pe.edu.upc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProducto;

	@NotEmpty(message = "Ingresa el nombre del Producto")
	@Column(name = "", nullable = false, length = 70, unique = true)
	private String nombreProducto;

	@NotEmpty(message = "Debe ingresar un marca de Producto")
	@Column(name = "marcaProducto", nullable = false, length = 70)
	private String marcaProducto;

	@ManyToOne
	@JoinColumn(name = "idTproducto")
	private Tproducto tproducto;

	public Producto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Producto(int idProducto, String nombreProducto, String marcaProducto, int cantidadProducto,
			Tproducto tproducto) {
		super();
		this.idProducto = idProducto;
		this.nombreProducto = nombreProducto;
		this.marcaProducto = marcaProducto;
		this.tproducto = tproducto;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getMarcaProducto() {
		return marcaProducto;
	}

	public void setMarcaProducto(String marcaProducto) {
		this.marcaProducto = marcaProducto;
	}

	public Tproducto getTproducto() {
		return tproducto;
	}

	public void setTproducto(Tproducto tproducto) {
		this.tproducto = tproducto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idProducto;
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
		Producto other = (Producto) obj;
		if (idProducto != other.idProducto)
			return false;
		return true;
	}

}