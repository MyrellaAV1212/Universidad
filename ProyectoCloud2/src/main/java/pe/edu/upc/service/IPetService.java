package pe.edu.upc.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import pe.edu.upc.model.Pet;

public interface IPetService {

	public boolean insertar(Pet Pet);

	public boolean modificar(Pet Pet);

	public void eliminar(int idPet);

	Optional<Pet> listarId(int idPet);

	List<Pet> listar();

	List<Pet> buscarPet(String namePet);

	List<Pet> findByBirthDatePet(Date birthDatePet);
}
