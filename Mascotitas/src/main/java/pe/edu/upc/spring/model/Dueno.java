package pe.edu.upc.spring.model;

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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Dueno")
public class Dueno implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idDueno;

	@NotEmpty(message = "Ingrese su nombre.")
	@NotBlank(message = "No puede estar en blanco.")
	@Column(name = "nameDueno", length = 60, nullable = false)
	private String nameDueno;
	
	@NotEmpty(message = "Ingrese su apellido.")
	@NotBlank(message = "No puede estar en blanco.")
	@Column(name = "apellidoDueno", length = 60, nullable = false)
	private String apellidoDueno;

	@Size(min = 8, max = 8)
	@Column(name = "dniDueno", length = 8, nullable = false, unique=true)
	@NotEmpty(message = "Ingrese su DNI.")
	@NotBlank(message = "No puede estar en blanco.")
	@Pattern(regexp = "[0-9]+", message="El DNI solo puede tener números.")
	private String dniDueno;
	
	@Size(min = 9, max = 9)
	@Column(name = "celDueno", length = 8, nullable = false, unique=true)
	@NotEmpty(message = "Ingrese su número de celular.")
	@NotBlank(message = "No puede estar en blanco.")
	@Pattern(regexp = "[0-9]+", message="Solo puede tener números.")
	private String celDueno;

	@NotNull(message = "La fecha de nacimiento es obligatoria.")
	@Past(message = "La fecha debe estar en el pasado.")
	@Temporal(TemporalType.DATE)
	@Column(name = "birthDateDueno")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDateDueno;

	@NotEmpty(message = "Ingrese su email.")
	@NotBlank(message = "No puede estar en blanco.")
	@Email
	@Column(name = "emailDueno", length = 60, nullable = false)
	private String emailDueno;

	public Dueno() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Dueno(int idDueno, String nameDueno, String apellidoDueno, String dniDueno, String celDueno,
			Date birthDateDueno, String emailDueno) {
		super();
		this.idDueno = idDueno;
		this.nameDueno = nameDueno;
		this.apellidoDueno = apellidoDueno;
		this.dniDueno = dniDueno;
		this.celDueno = celDueno;
		this.birthDateDueno = birthDateDueno;
		this.emailDueno = emailDueno;
	}

	public int getIdDueno() {
		return idDueno;
	}

	public void setIdDueno(int idDueno) {
		this.idDueno = idDueno;
	}

	public String getNameDueno() {
		return nameDueno;
	}

	public void setNameDueno(String nameDueno) {
		this.nameDueno = nameDueno;
	}

	public String getApellidoDueno() {
		return apellidoDueno;
	}

	public void setApellidoDueno(String apellidoDueno) {
		this.apellidoDueno = apellidoDueno;
	}

	public String getDniDueno() {
		return dniDueno;
	}

	public void setDniDueno(String dniDueno) {
		this.dniDueno = dniDueno;
	}

	public String getCelDueno() {
		return celDueno;
	}

	public void setCelDueno(String celDueno) {
		this.celDueno = celDueno;
	}

	public Date getBirthDateDueno() {
		return birthDateDueno;
	}

	public void setBirthDateDueno(Date birthDateDueno) {
		this.birthDateDueno = birthDateDueno;
	}

	public String getEmailDueno() {
		return emailDueno;
	}

	public void setEmailDueno(String emailDueno) {
		this.emailDueno = emailDueno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idDueno;
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
		Dueno other = (Dueno) obj;
		if (idDueno != other.idDueno)
			return false;
		return true;
	}
	
}
