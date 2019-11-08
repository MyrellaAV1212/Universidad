package pe.edu.upc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue(value="host")
public class Host extends Persona implements Serializable {

	private static final long serialVersionUID = 1L;

	public Host() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Host(int idPersona, String namePersona, String telPersona, String direccionPersona, String dniPersona,
			Date birthDatePersona, String emailPersona, String estadoUsuario, String tipoUsuario, String userUsuario,
			String passwordUsuario) {
		super(idPersona, namePersona, telPersona, direccionPersona, dniPersona, birthDatePersona, emailPersona, estadoUsuario,
				tipoUsuario, userUsuario, passwordUsuario);
		// TODO Auto-generated constructor stub
	}


	
}
