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
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "informes")
public class Informe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idInforme;

	@ManyToOne
	@JoinColumn(name = "idProyecto")
	private Proyecto proyecto;

	@Column(name = "descripcionInforme", nullable = false, length = 200)
	private String descripcionInforme;

	@NotNull(message = "La fecha es obligatoria")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaInforme")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaInforme;

	@ManyToOne
	@JoinColumn(name = "idEstado")
	private Estado estado;
	
	@ManyToOne
	@JoinColumn(name = "idTinforme")
	private Tinforme tinforme;
	
	private String fotoInforme;

	public Informe(int idInforme, Proyecto proyecto, String descripcionInforme, Date fechaInforme, Estado estado, Tinforme tinforme, String fotoInforme) {
		super();
		this.idInforme = idInforme;
		this.proyecto = proyecto;
		this.descripcionInforme = descripcionInforme;
		this.fechaInforme = fechaInforme;
		this.estado = estado;
		this.tinforme = tinforme;
		this.fotoInforme = fotoInforme;
	}

	public Informe() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdInforme() {
		return idInforme;
	}

	public void setIdInforme(int idInforme) {
		this.idInforme = idInforme;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public String getDescripcionInforme() {
		return descripcionInforme;
	}

	public void setDescripcionInforme(String descripcionInforme) {
		this.descripcionInforme = descripcionInforme;
	}

	public Date getFechaInforme() {
		return fechaInforme;
	}

	public void setFechaInforme(Date fechaInforme) {
		this.fechaInforme = fechaInforme;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Tinforme getTinforme() {
		return tinforme;
	}

	public void setTinforme(Tinforme tinforme) {
		this.tinforme = tinforme;
	}

	public String getFotoInforme() {
		return fotoInforme;
	}

	public void setFotoInforme(String fotoInforme) {
		this.fotoInforme = fotoInforme;
	}

}
