package pe.edu.upc.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Cliente;
import pe.edu.upc.repository.ClienteRepository;
import pe.edu.upc.service.IClienteService;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private ClienteRepository cR;

	
	@Override
	@Transactional
	public void insertar(Cliente cliente) {
		cR.save(cliente);
		
	}

	@Override
	@Transactional
	public void modificar(Cliente cliente) {
		cR.save(cliente);
		
	}

	@Override
	@Transactional
	public void eliminar(int idCliente) {
		cR.deleteById(idCliente);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Cliente> listarId(int idCliente) {
		return cR.findById(idCliente);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> listar() {
		
		return cR.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> buscarDni(String dniCliente) {
		return cR.findByDniCliente(dniCliente);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> buscarDatos(String datosCliente) {
		return cR.findByDatosCliente(datosCliente);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> buscarEmail(String emailCliente) {
		return cR.findByEmailCliente(emailCliente);
	}

	@Override
	public List<Cliente> buscarFecha(Date fechaNacimientoCliente) {
		return cR.findByFechaNacimientoCliente(fechaNacimientoCliente);
	}

}
