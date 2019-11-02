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
@Table(name = "Host")
public class Host implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idHost;


	@NotEmpty(message = "Ingrese su nombre.")
	@NotBlank(message = "No puede estar en blanco.")
	@Column(name = "nameHost", length = 60, nullable = false)
	private String nameHost;
	
	@NotEmpty(message = "Ingrese su apellido.")
	@NotBlank(message = "No puede estar en blanco.")
	@Column(name = "apellidoHost", length = 60, nullable = false)
	private String apellidoHost;
	
	@Size(min = 8, max = 8)
	@Column(name = "dniHost", length = 8, nullable = false, unique=true)
	@NotEmpty(message = "Ingrese su DNI.")
	@NotBlank(message = "No puede estar en blanco.")
	@Pattern(regexp = "[0-9]+", message="El DNI solo puede tener números.")
	private String dniHost;
	
	@Size(min = 9, max = 9)
	@Column(name = "celHost", length = 8, nullable = false, unique=true)
	@NotEmpty(message = "Ingrese su número de celular.")
	@NotBlank(message = "No puede estar en blanco.")
	@Pattern(regexp = "[0-9]+", message="Solo puede tener números.")
	private String celHost;

	@NotNull(message = "La fecha de nacimiento es obligatoria.")
	@Past(message = "La fecha debe estar en el pasado.")
	@Temporal(TemporalType.DATE)
	@Column(name = "birthDateHost")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDateHost;
	
	@NotEmpty(message = "Ingrese su email.")
	@NotBlank(message = "No puede estar en blanco.")
	@Email
	@Column(name = "emailHost", length = 60, nullable = false)
	private String emailHost;

	public Host() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Host(int idHost, String nameHost, String apellidoHost, String dniHost, String celHost,
			Date birthDateHost, String emailHost) {
		super();
		this.idHost = idHost;
		this.nameHost = nameHost;
		this.apellidoHost = apellidoHost;
		this.dniHost = dniHost;
		this.celHost = celHost;
		this.birthDateHost = birthDateHost;
		this.emailHost = emailHost;
	}

	public int getIdHost() {
		return idHost;
	}

	public void setIdHost(int idHost) {
		this.idHost = idHost;
	}

	public String getNameHost() {
		return nameHost;
	}

	public void setNameHost(String nameHost) {
		this.nameHost = nameHost;
	}

	public String getApellidoHost() {
		return apellidoHost;
	}

	public void setApellidoHost(String apellidoHost) {
		this.apellidoHost = apellidoHost;
	}

	public String getDniHost() {
		return dniHost;
	}

	public void setDniHost(String dniHost) {
		this.dniHost = dniHost;
	}

	public String getCelHost() {
		return celHost;
	}

	public void setCelHost(String celHost) {
		this.celHost = celHost;
	}

	public Date getBirthDateHost() {
		return birthDateHost;
	}

	public void setBirthDateHost(Date birthDateHost) {
		this.birthDateHost = birthDateHost;
	}

	public String getEmailHost() {
		return emailHost;
	}

	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idHost;
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
		Host other = (Host) obj;
		if (idHost != other.idHost)
			return false;
		return true;
	}
	
}
