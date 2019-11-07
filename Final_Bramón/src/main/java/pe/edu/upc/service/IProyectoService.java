package pe.edu.upc.service;

import java.util.List;

import pe.edu.upc.entity.Proyecto;

public interface IProyectoService {

	public Integer insertar(Proyecto proyecto);

	List<Proyecto> listar();
	
	List<Proyecto> buscarNombre(String nombreProyecto);
	
	List<Proyecto> buscarTipo(String tipoProyecto);
	
	List<Proyecto> buscarSueldoProyecto(double sueldoProyecto);
	
}
