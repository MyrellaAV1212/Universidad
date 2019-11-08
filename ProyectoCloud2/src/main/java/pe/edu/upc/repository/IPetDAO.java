package pe.edu.upc.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.model.Pet;
@Repository
public interface IPetDAO extends JpaRepository<Pet, Integer> {

	@Query("from Pet p where p.namePet like %:namePet%")
	List<Pet> buscarPet(@Param("namePet") String namePet);

	List<Pet> findByBirthDatePet(Date birthDatePet);
}
