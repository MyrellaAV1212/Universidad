package pe.edu.upc.spring.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Host;
import pe.edu.upc.spring.repository.IHostDAO;
import pe.edu.upc.spring.service.IHostService;

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
	public void eliminar(int idHost) {

		dHost.deleteById(idHost);

	}

	
	@Override
	@Transactional(readOnly=true)
	public Optional<Host> listarId(int idHost) {
		return dHost.findById(idHost);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Host> buscarNombre(String nameHost) {

		return dHost.buscarNombre(nameHost);

	}

	@Override
	@Transactional(readOnly=true)
	public List<Host> listar() {
		return dHost.findAll();
	}

	@Override
	public List<Host> buscarEmail(String emailHost) {
		return dHost.buscarEmail(emailHost);
	}

	@Override
	public List<Host> findByBirthDateHost(Date birthDateHost) {
		return dHost.findByBirthDateHost(birthDateHost);
	}

	@Override
	public List<Host> findByDniHost(String dni) {
		return dHost.findByDniHost(dni);
	}

}
