package pe.edu.upc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	
	@Query("select p from Producto p where p.nombreProducto like %?1%")
	public List<Producto> findByNombreProducto(String term);

	@Query("select p from Producto p where p.categoria.nombreCategoria like %?1%")
	public List<Producto> buscarCategoria(String nombreCategoria);

	public List<Producto> findByNombreProductoLikeIgnoreCase(String term);
}
