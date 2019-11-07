package pe.edu.upc.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.entity.Proyecto;
import pe.edu.upc.service.IProyectoService;

@Controller
@RequestMapping("/proyectos")
public class ProyectoController {

	@Autowired
	private IProyectoService pService;

	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}

	@GetMapping("/nuevo")
	public String nuevoProyecto(Model model) {
		model.addAttribute("proyecto", new Proyecto());
		return "proyecto/proyecto";
	}

	@PostMapping("/guardar")
	public String guardarProyecto(@Valid Proyecto proyecto, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "proyecto/proyecto";
		} else {
			int rpta = pService.insertar(proyecto);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe un proyecto con ese nombre.");
				return "/proyecto/proyecto";
			} else {
				model.addAttribute("mensaje", "Se guardó correctamente.");
				status.setComplete();
			}

		}
		model.addAttribute("listaProyectos", pService.listar());

		return "/proyecto/listaProyecto";
	}

	@GetMapping("/listar")
	public String listarProyectos(Model model) {
		try {
			model.addAttribute("proyecto", new Proyecto());
			model.addAttribute("listaProyectos", pService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/proyecto/listaProyecto";
	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Proyecto proyecto) throws ParseException {

		List<Proyecto> listaproyectos;

		proyecto.setNombreProyecto(proyecto.getNombreProyecto());
		listaproyectos = pService.buscarNombre(proyecto.getNombreProyecto());

		if (listaproyectos.isEmpty()) {
			listaproyectos = pService.buscarTipo(proyecto.getNombreProyecto());
		}

		if (listaproyectos.isEmpty()) {
			try {

				proyecto.setSueldoProyecto(Double.parseDouble(proyecto.getNombreProyecto()));
				listaproyectos = pService.buscarSueldoProyecto(proyecto.getSueldoProyecto());
			} catch (Exception e) {
				model.put("mensaje", "Formato incorreco");

			}
		}

		if (listaproyectos.isEmpty()) {
			model.put("mensaje", "No se encontró el proyecto.");
		}
		model.put("listaProyectos", listaproyectos);
		return "proyecto/listaProyecto";

	}

}
