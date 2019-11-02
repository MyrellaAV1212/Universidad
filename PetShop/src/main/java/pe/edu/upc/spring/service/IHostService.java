package pe.edu.upc.spring.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Host;

public interface IHostService {

	public boolean insertar(Host host);

	public boolean modificar(Host host);

	public void eliminar(int idHost);

	Optional<Host> listarId(int idHost);

	List<Host> listar();

	List<Host> buscarNombre(String nameHost);

	List<Host> findByDniHost(String dni);

	List<Host> buscarEmail(String emailHost);

	List<Host> findByBirthDateHost(Date birthDateHost);

}
