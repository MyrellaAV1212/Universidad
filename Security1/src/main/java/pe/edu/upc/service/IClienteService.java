package pe.edu.upc.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Cliente;

public interface IClienteService {

	public void insertar(Cliente cliente);

	public void modificar(Cliente cliente);

	public void eliminar(int idCliente);

	Optional<Cliente> listarId(int idCliente);

	List<Cliente> listar();

	List<Cliente> buscarDni(String dniCliente);

	List<Cliente> buscarDatos(String datosCliente);

	List<Cliente> buscarEmail(String emailCliente);

	List<Cliente> buscarFecha(Date fechaNacimientoCliente);
}
