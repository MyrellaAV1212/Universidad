package pe.edu.upc.spring.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Dueno;

public interface IDuenoService {
	
	public boolean insertar(Dueno dueno);
	public boolean modificar(Dueno dueno);
	public void eliminar(int idDueno);
	Optional<Dueno>listarId(int idDueno);
	List<Dueno> listar();
	List<Dueno> findByDniDueno(String dni);
	List<Dueno> buscarNombre(String nameDueno);
	List<Dueno> buscarEmail(String emailDueno);
	List<Dueno> findByBirthDateDueno(Date birthDateDueno);

}
