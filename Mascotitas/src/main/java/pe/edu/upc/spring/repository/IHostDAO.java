package pe.edu.upc.spring.repository;
	
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Host;

@Repository
public interface IHostDAO extends JpaRepository<Host, Integer>{
	
List<Host> findByDniHost(String dni);
	
	@Query("from Host h where h.nameHost like %:nameHost%")
	List<Host> buscarNombre(@Param("nameHost")String nameHost);
	
	@Query("from Host h where h.emailHost like %:emailHost%")
	List<Host> buscarEmail(@Param("emailHost")String emailHost);
	
	List<Host> findByBirthDateHost(Date birthDateHost);
	
}
