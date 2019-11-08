package pe.edu.upc.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import pe.edu.upc.model.Dueno;

public interface IDuenoService {
	
	public boolean insertar(Dueno dueno);
	public boolean modificar(Dueno dueno);
	public void eliminar(int idPersona);
	Optional<Dueno>listarId(int idPersona);
	List<Dueno> listar();
	List<Dueno> findByDniPersona(String dniPersona);
	List<Dueno> buscarNombre(String namePersona);
	List<Dueno> buscarEmail(String emailPersona);
	List<Dueno> findByBirthDatePersona(Date birthDatePersona);

}
