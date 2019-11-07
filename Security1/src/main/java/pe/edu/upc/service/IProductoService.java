package pe.edu.upc.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Producto;

public interface IProductoService {
	
	public void insertar(Producto producto);

	public void modificar(Producto producto);

	public void eliminar(int idProducto);

	Optional<Producto> listarId(int idProducto);

	List<Producto> listar();

	List<Producto> buscar(String nombreProducto);
	List<Producto> buscarCategoria(String nombreCategoria);

}

