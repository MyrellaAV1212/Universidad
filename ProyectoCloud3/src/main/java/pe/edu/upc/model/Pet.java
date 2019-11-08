package pe.edu.upc.model;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Pet")
public class Pet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPet;

	@NotEmpty(message = "No puede estar vacío")
	@NotBlank(message = "No puede estar en blanco")
	@Column(name = "namePet", length = 60, nullable = false)
	private String namePet;

	@NotNull
	@Past(message = "La fecha debe de estar en el pasado.")
	@Temporal(TemporalType.DATE)
	@Column(name = "birthDatePet")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDatePet;

	@NotEmpty(message = "Ingrese la raza de su mascota.")
	@NotBlank(message = "No puede estar en blanco.")
	@Column(name = "razaPet", length = 60, nullable = false)
	private String razaPet;

	@NotEmpty(message = "Ingrese la clase de mascota.")
	@NotBlank(message = "No puede estar en blanco.")
	@Column(name = "tipoPet", length = 60, nullable = false)
	private String tipoPet;

	@NotEmpty(message = "Ingrese la edad de su mascota.")
	@NotBlank(message = "No puede estar en blanco")
	@Column(name = "edadPet", length = 60, nullable = false)
	private String edadPet;

	@ManyToOne
	@JoinColumn(name = "idDueno")
	private Dueno dueno;

	private String foto;

	public Pet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Pet(int idPet, String namePet, Date birthDatePet, String razaPet, String tipoPet, String edadPet,
			Dueno dueno, String foto) {
		super();
		this.idPet = idPet;
		this.namePet = namePet;
		this.birthDatePet = birthDatePet;
		this.razaPet = razaPet;
		this.tipoPet = tipoPet;
		this.edadPet = edadPet;
		this.dueno = dueno;
		this.foto = foto;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public int getIdPet() {
		return idPet;
	}

	public void setIdPet(int idPet) {
		this.idPet = idPet;
	}

	public String getNamePet() {
		return namePet;
	}

	public void setNamePet(String namePet) {
		this.namePet = namePet;
	}

	public Date getBirthDatePet() {
		return birthDatePet;
	}

	public void setBirthDatePet(Date birthDatePet) {
		this.birthDatePet = birthDatePet;
	}

	public String getRazaPet() {
		return razaPet;
	}

	public void setRazaPet(String razaPet) {
		this.razaPet = razaPet;
	}

	public String getTipoPet() {
		return tipoPet;
	}

	public void setTipoPet(String tipoPet) {
		this.tipoPet = tipoPet;
	}

	public String getEdadPet() {
		return edadPet;
	}

	public void setEdadPet(String edadPet) {
		this.edadPet = edadPet;
	}

	public Dueno getDueno() {
		return dueno;
	}

	public void setDueno(Dueno dueno) {
		this.dueno = dueno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idPet;
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
		Pet other = (Pet) obj;
		if (idPet != other.idPet)
			return false;
		return true;
	}

}
