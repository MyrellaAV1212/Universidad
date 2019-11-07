package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Proyecto;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer>{

	@Query("select count(p.nombreProyecto) from Proyecto p where p.nombreProyecto =:nombreProyecto")

	public int buscarNombreProyecto (@Param("nombreProyecto")String nombreProyecto);
	
	@Query("from Proyecto p where p.nombreProyecto like %:nombreProyecto%")
	List<Proyecto> findByNombreProyecto(String nombreProyecto);
	
	@Query("select p from Proyecto p where p.sueldoProyecto > :sueldoProyecto")
	public List<Proyecto> buscarSueldoProyecto(double sueldoProyecto);
	
	@Query("from Proyecto p where p.tipoProyecto like %:tipoProyecto%")
	List<Proyecto> findByTipoProyecto(String tipoProyecto);
}
