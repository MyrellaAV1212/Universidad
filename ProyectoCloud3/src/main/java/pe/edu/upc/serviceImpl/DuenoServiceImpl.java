package pe.edu.upc.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.model.Dueno;
import pe.edu.upc.repository.IDuenoDAO;
import pe.edu.upc.service.IDuenoService;

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
	public void eliminar(int idPersona) {
		
		dDueno.deleteById(idPersona);
		
	}

	@Override
	public Optional<Dueno> listarId(int idPersona) {
		
		return dDueno.findById(idPersona);
		
	}

	@Override
	public List<Dueno> listar() {
		
		return dDueno.findAll();
		
	}

	@Override
	public List<Dueno> findByDniPersona(String dniPersona) {
		
		return dDueno.findByDniPersona(dniPersona);
		
	}

	@Override
	public List<Dueno> buscarNombre(String namePersona) {
		
		return dDueno.buscarNombre(namePersona);
		
	}

	@Override
	public List<Dueno> buscarEmail(String emailPersona) {
		
		return dDueno.buscarEmail(emailPersona);
		
	}

	@Override
	public List<Dueno> findByBirthDatePersona(Date birthDatePersona) {
		
		return dDueno.findByBirthDatePersona(birthDatePersona);
		
	}


}
