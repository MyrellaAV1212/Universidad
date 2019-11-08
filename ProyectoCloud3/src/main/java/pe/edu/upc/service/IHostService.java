package pe.edu.upc.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import pe.edu.upc.model.Host;

public interface IHostService {

	public boolean insertar(Host host);

	public boolean modificar(Host host);

	public void eliminar(int idPersona);

	Optional<Host> listarId(int idPersona);

	List<Host> listar();

	List<Host> buscarNombre(String namePersona);

	List<Host> findByDniPersona(String dniPersona);

	List<Host> buscarEmail(String emailPersona);

	List<Host> findByBirthDatePersona(Date birthDatePersona);

}
