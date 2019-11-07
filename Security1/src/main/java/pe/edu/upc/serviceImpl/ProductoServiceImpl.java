package pe.edu.upc.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Producto;
import pe.edu.upc.repository.ProductoRepository;
import pe.edu.upc.service.IProductoService;

@Service
public class ProductoServiceImpl implements IProductoService {
	@Autowired
	private ProductoRepository pR;

	@Override
	@Transactional
	public void insertar(Producto producto) {
		pR.save(producto);
	}

	@Override
	@Transactional
	public void modificar(Producto producto) {
		pR.save(producto);

	}

	@Override
	@Transactional
	public void eliminar(int idProducto) {
		pR.deleteById(idProducto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Producto> listarId(int idProducto) {
		return pR.findById(idProducto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> listar() {
		return pR.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> buscar(String nombreProducto) {
		return pR.findByNombreProducto(nombreProducto);
	}

	@Override
	public List<Producto> buscarCategoria(String nombreCategoria) {
		return pR.buscarCategoria(nombreCategoria);
	}

}
