package pe.edu.upc.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entity.Proyecto;
import pe.edu.upc.repository.ProyectoRepository;
import pe.edu.upc.service.IProyectoService;

@Service
public class ProyectoServiceImpl implements IProyectoService{

	@Autowired
	private ProyectoRepository pR;
	
	@Override
	@Transactional
	public Integer insertar(Proyecto proyecto) {
		int rpta = pR.buscarNombreProyecto(proyecto.getNombreProyecto());
		if (rpta== 0) {		
			pR.save(proyecto);

		}
		return rpta;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Proyecto> listar() {
		return pR.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Proyecto> buscarNombre(String nombreProyecto) {
		return pR.findByNombreProyecto(nombreProyecto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Proyecto> buscarTipo(String tipoProyecto) {
		return pR.findByTipoProyecto(tipoProyecto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Proyecto> buscarSueldoProyecto(double sueldoProyecto) {
		
		return pR.buscarSueldoProyecto(sueldoProyecto);
	}

}
