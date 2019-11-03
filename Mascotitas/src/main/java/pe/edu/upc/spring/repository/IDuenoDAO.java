package pe.edu.upc.spring.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Dueno;

@Repository
public interface IDuenoDAO extends JpaRepository<Dueno, Integer>{
	
	List<Dueno> findByDniDueno(String dni);
	
	@Query("from Dueno d where d.nameDueno like %:nameDueno%")
	List<Dueno> buscarNombre(@Param("nameDueno")String nameDueno);
	
	@Query("from Dueno d where d.emailDueno like %:emailDueno%")
	List<Dueno> buscarEmail(@Param("emailDueno")String emailDueno);
	
	List<Dueno> findByBirthDateDueno(Date birthDateDueno);

}
