package pe.edu.upc.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.model.Host;
import pe.edu.upc.repository.IHostDAO;
import pe.edu.upc.service.IHostService;

@Service
public class HostServiceImpl implements IHostService {

	@Autowired
	private IHostDAO dHost;

	@Override
	@Transactional
	public boolean insertar(Host host) {
		Host objHost = dHost.save(host);
		if (objHost == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	@Transactional
	public boolean modificar(Host host) {
		boolean flag = false;
		try {
			dHost.save(host);
			flag = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return flag;
	}

	@Override
	@Transactional
	public void eliminar(int idPersona) {

		dHost.deleteById(idPersona);

	}

	
	@Override
	@Transactional(readOnly=true)
	public Optional<Host> listarId(int idPersona) {
		return dHost.findById(idPersona);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Host> buscarNombre(String namePersona) {

		return dHost.buscarNombre(namePersona);

	}

	@Override
	@Transactional(readOnly=true)
	public List<Host> listar() {
		return dHost.findAll();
	}

	@Override
	public List<Host> buscarEmail(String emailPersona) {
		return dHost.buscarEmail(emailPersona);
	}

	@Override
	public List<Host> findByBirthDatePersona(Date birthDatePersona) {
		return dHost.findByBirthDatePersona(birthDatePersona);
	}

	@Override
	public List<Host> findByDniPersona(String dniPersona) {
		return dHost.findByDniPersona(dniPersona);
	}

}
