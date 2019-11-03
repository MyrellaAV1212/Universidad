package pe.edu.upc.spring.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.spring.model.Dueno;
import pe.edu.upc.spring.repository.IDuenoDAO;
import pe.edu.upc.spring.service.IDuenoService;

@Service
public class DuenoServiceImpl implements IDuenoService{
	
	@Autowired
	private IDuenoDAO dDueno;

	@Override
	public boolean insertar(Dueno dueno) {
		Dueno objDueno = dDueno.save(dueno);
		if (objDueno == null){
			return false;
		}else{
			return true;
		}		
	}

	@Override
	public boolean modificar(Dueno dueno) {
		boolean flag = false;
		try {
			dDueno.save(dueno);
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		return flag;
	}

	@Override
	public void eliminar(int idDueno) {
		
		dDueno.deleteById(idDueno);
		
	}

	@Override
	public Optional<Dueno> listarId(int idDueno) {
		
		return dDueno.findById(idDueno);
		
	}

	@Override
	public List<Dueno> listar() {
		
		return dDueno.findAll();
		
	}

	@Override
	public List<Dueno> findByDniDueno(String dni) {
		
		return dDueno.findByDniDueno(dni);
		
	}

	@Override
	public List<Dueno> buscarNombre(String nameDueno) {
		
		return dDueno.buscarNombre(nameDueno);
		
	}

	@Override
	public List<Dueno> buscarEmail(String emailDueno) {
		
		return dDueno.buscarEmail(emailDueno);
		
	}

	@Override
	public List<Dueno> findByBirthDateDueno(Date birthDateDueno) {
		
		return dDueno.findByBirthDateDueno(birthDateDueno);
		
	}


}
