package pe.edu.upc.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.model.Pet;
import pe.edu.upc.repository.IPetDAO;
import pe.edu.upc.service.IPetService;
@Service
public class PetServiceImpl implements IPetService {

	@Autowired
	private IPetDAO dPet;

	@Override
	@Transactional
	public boolean insertar(Pet pet) {
		Pet objPet = dPet.save(pet);
		if (objPet == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	@Transactional
	public boolean modificar(Pet pet) {
		boolean flag = false;
		try {
			dPet.save(pet);
			flag = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return flag;
	}

	@Override
	@Transactional
	public void eliminar(int idPet) {

		dPet.deleteById(idPet);

	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Pet> listarId(int idPet) {
		return dPet.findById(idPet);
	}

	
	@Override
	public List<Pet> listar() {

		return dPet.findAll();

	}

	@Override
	public List<Pet> buscarPet(String namePet) {
		// TODO Auto-generated method stub
		return dPet.buscarPet(namePet);
	}

	@Override
	public List<Pet> findByBirthDatePet(Date birthDatePet) {
		// TODO Auto-generated method stub
		return dPet.findByBirthDatePet(birthDatePet);
	}
	

}
