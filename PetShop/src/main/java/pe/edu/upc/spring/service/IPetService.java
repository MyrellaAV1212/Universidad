package pe.edu.upc.spring.service;

import java.util.Date;
import java.util.List;

import pe.edu.upc.spring.model.Pet;

public interface IPetService {

	public boolean insertar(Pet Pet);

	public boolean modificar(Pet Pet);

	public void eliminar(int idPet);

	Pet  listarId(int idPet);

	List<Pet> listar();

	List<Pet> buscarPet(String namePet);

	List<Pet> findByBirthDatePet(Date birthDatePet);
}
