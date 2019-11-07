package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

	@Query("from Categoria c where c.nombreCategoria like %:nombreCategoria%")
	List<Categoria> findByNombreCategoria(String nombreCategoria);
	
}
