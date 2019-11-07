package pe.edu.upc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "proyectos")
public class Proyecto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProyecto;

	@NotEmpty(message = "Ingresa el nombre del proyecto.")
	@NotBlank(message = "No puede estar en blanco.")
	@Column(name = "nombreProyecto", nullable = false, length = 45, unique=true)
	private String nombreProyecto;
	
	@NotEmpty(message = "Ingresa la descripción del proyecto.")
	@NotBlank(message = "No puede estar en blanco.")
	@Column(name = "descripcionProyecto", nullable = false, length = 200)
	private String descripcionProyecto;
	
	@Min(500)
	@Max(1500)
	@Column(name = "sueldoProyecto", nullable = false)
	private double sueldoProyecto;
	
	@NotNull(message = "La fecha es obligatoria.")
	@Past(message = "La fecha debe estar en el pasado.")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaProyecto")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaProyecto;
	
	@NotEmpty(message = "Ingresa el tipo del proyecto.")
	@NotBlank(message = "No puede estar en blanco.")
	@Column(name = "tipoProyecto", nullable = false, length = 45)
	private String tipoProyecto;

	public Proyecto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Proyecto(int idProyecto, String nombreProyecto, String descripcionProyecto,
			double sueldoProyecto, Date fechaProyecto, String tipoProyecto) {
		super();
		this.idProyecto = idProyecto;
		this.nombreProyecto = nombreProyecto;
		this.descripcionProyecto = descripcionProyecto;
		this.sueldoProyecto = sueldoProyecto;
		this.fechaProyecto = fechaProyecto;
		this.tipoProyecto=tipoProyecto;
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

	public double getSueldoProyecto() {
		return sueldoProyecto;
	}

	public void setSueldoProyecto(double sueldoProyecto) {
		this.sueldoProyecto = sueldoProyecto;
	}

	public Date getFechaProyecto() {
		return fechaProyecto;
	}

	public void setFechaProyecto(Date fechaProyecto) {
		this.fechaProyecto = fechaProyecto;
	}

	public String getTipoProyecto() {
		return tipoProyecto;
	}

	public void setTipoProyecto(String tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
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
