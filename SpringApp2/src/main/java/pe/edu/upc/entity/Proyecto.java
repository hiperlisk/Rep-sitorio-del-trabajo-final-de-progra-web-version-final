package pe.edu.upc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "proyectos")
public class Proyecto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProyecto;

	@Column(name = "", nullable = false, length = 70, unique = true)
	private String nombreProyecto;

	@Column(name = "descripcionProyecto", nullable = false, length = 200)
	private String descripcionProyecto;

	@ManyToOne
	@JoinColumn(name = "idUsuario")
	private Users usuario;

	@ManyToOne
	@JoinColumn(name = "idArea")
	private Area area;

	@ManyToOne
	@JoinColumn(name = "idProducto")
	private Producto producto;

	@Column(name = "cantidadProducto", nullable = false)
	private int cantidadProducto;

	@NotNull(message = "La fecha es obligatoria")
	@Future(message = "La fecha debe estar en el futuro")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaInicio")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;

	@NotNull(message = "La fecha es obligatoria")
	@Future(message = "La fecha debe estar en el futuro")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaFin")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaFin;

	

	public Proyecto(int idProyecto, String nombreProyecto, String descripcionProyecto, Users usuario, Area area,
			Producto producto, int cantidadProducto,
			@NotNull(message = "La fecha es obligatoria") @Future(message = "La fecha debe estar en el futuro") Date fechaInicio,
			@NotNull(message = "La fecha es obligatoria") @Future(message = "La fecha debe estar en el futuro") Date fechaFin) {
		super();
		this.idProyecto = idProyecto;
		this.nombreProyecto = nombreProyecto;
		this.descripcionProyecto = descripcionProyecto;
		this.usuario = usuario;
		this.area = area;
		this.producto = producto;
		this.cantidadProducto = cantidadProducto;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public Proyecto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(int idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	public String getDescripcionProyecto() {
		return descripcionProyecto;
	}

	public void setDescripcionProyecto(String descripcionProyecto) {
		this.descripcionProyecto = descripcionProyecto;
	}

	

	public Users getUsuario() {
		return usuario;
	}

	public void setUsuario(Users usuario) {
		this.usuario = usuario;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidadProducto() {
		return cantidadProducto;
	}

	public void setCantidadProducto(int cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idProyecto;
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
		Proyecto other = (Proyecto) obj;
		if (idProyecto != other.idProyecto)
			return false;
		return true;
	}

}
