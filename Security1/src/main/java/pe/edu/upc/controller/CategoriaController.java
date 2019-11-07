package pe.edu.upc.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.entity.Categoria;
import pe.edu.upc.service.ICategoriaService;

@Controller
@RequestMapping("/categorias")
@SessionAttributes("categoria")
public class CategoriaController {

	@Autowired
	private ICategoriaService cService;

	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}
	@Secured("ROLE_ADMIN")
	@GetMapping("/nuevo")
	public String nuevoCategoria(Model model) {
		model.addAttribute("categoria", new Categoria());
		return "categoria/categoria";
	}

	@PostMapping("/guardar")
	public String guardarCategoria(@Valid Categoria categoria, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "/categoria/categoria";
		} else {
			cService.insertar(categoria);
			model.addAttribute("mensaje", "Se guardó correctamente");
			status.setComplete();
			return "redirect:/categorias/listar";
		}
	}

	@GetMapping("/listar")
	public String listarCategorias(Model model) {
		try {
			model.addAttribute("categoria", new Categoria());
			model.addAttribute("listaCategorias", cService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/categoria/listaCategoria";
	}

	@GetMapping("/detalle/{id}")
	public String detailsCategoria(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Categoria> categoria = cService.listarId(id);
			if (!categoria.isPresent()) {
				model.addAttribute("info", "Categoria no existe");
				return "redirect:/categorias/listar";
			} else {
				model.addAttribute("categoria", categoria.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/categoria/categoria";
	}
	@Secured("ROLE_ADMIN")
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				cService.eliminar(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un categoria");
		}
		model.put("listaCategorias", cService.listar());

		return "redirect:/categorias/listar";
	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Categoria categoria) throws ParseException {

		List<Categoria> listacategorias;

		categoria.setNombreCategoria(categoria.getNombreCategoria());
		listacategorias = cService.buscarNombre(categoria.getNombreCategoria());

		if (listacategorias.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaCategorias", listacategorias);
		return "categoria/listaCategoria";

	}

}
