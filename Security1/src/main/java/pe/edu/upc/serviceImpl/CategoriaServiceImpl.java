package pe.edu.upc.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Categoria;
import pe.edu.upc.repository.CategoriaRepository;
import pe.edu.upc.service.ICategoriaService;

@Service
public class CategoriaServiceImpl implements ICategoriaService{

	@Autowired
	private CategoriaRepository cR;
	
	@Override
	@Transactional
	public void insertar(Categoria categoria) {
		cR.save(categoria);
		
	}

	@Override
	@Transactional
	public void modificar(Categoria categoria) {
		cR.save(categoria);
		
	}

	@Override
	@Transactional
	public void eliminar(int idCategoria) {
		cR.deleteById(idCategoria);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Categoria> listarId(int idCategoria) {
		
		return cR.findById(idCategoria);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Categoria> listar() {
		
		return cR.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Categoria> buscarNombre(String nombreCategoria) {
		
		return cR.findByNombreCategoria(nombreCategoria);
	}

}
