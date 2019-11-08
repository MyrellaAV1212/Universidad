package pe.edu.upc.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.model.Dueno;

@Repository
public interface IDuenoDAO extends JpaRepository<Dueno, Integer>{
	
	List<Dueno> findByDniPersona(String dniPersona);
	
	@Query("from Dueno d where d.namePersona like %:namePersona%")
	List<Dueno> buscarNombre(@Param("namePersona")String namePersona);
	
	@Query("from Dueno d where d.emailPersona like %:emailPersona%")
	List<Dueno> buscarEmail(@Param("emailPersona")String emailPersona);
	
	List<Dueno> findByBirthDatePersona(Date birthDatePersona);

}
