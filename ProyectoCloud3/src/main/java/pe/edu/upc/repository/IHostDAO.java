package pe.edu.upc.repository;
	
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.model.Host;

@Repository
public interface IHostDAO extends JpaRepository<Host, Integer>{
	
List<Host> findByDniPersona(String dniPersona);
	
	@Query("from Host h where h.namePersona like %:namePersona%")
	List<Host> buscarNombre(@Param("namePersona")String namePersona);
	
	@Query("from Host h where h.emailPersona like %:emailPersona%")
	List<Host> buscarEmail(@Param("emailPersona")String emailPersona);
	
	List<Host> findByBirthDatePersona(Date birthDatePersona);
	
}
