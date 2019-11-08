package pe.edu.upc.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.model.Dueno;
import pe.edu.upc.service.IDuenoService;

@Controller
@RequestMapping("/dueno")
public class DuenoController {

	@Autowired
	private IDuenoService pService;
	@Autowired
	private PersonaController personaEncryp;

	
	@GetMapping("/nuevo")
	public String nuevoDuenio(Model model) {
		model.addAttribute("dueno", new Dueno());
		return "dueno";
	}
	
	
	@RequestMapping("/guardar")
	public String guardarDueno(@ModelAttribute @Valid Dueno dueno, BindingResult binRes, Model model,
			SessionStatus status) throws ParseException {
		if (binRes.hasErrors()) {

			return "dueno";
		} else {
			dueno.setPasswordUsuario(personaEncryp.getPasswordEncoder2().encode(dueno.getPasswordUsuario()));
			pService.insertar(dueno);
			model.addAttribute("mensaje", "Se guardó correctamente");
			status.setComplete();
			return "redirect:/dueno/listar";
		}
	}

	@GetMapping("/listar")
	public String listarDuenos(Model model) {
		try {
			model.addAttribute("dueno", new Dueno());

			model.addAttribute("listaDuenos", pService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "listaDueno";
	}

	@GetMapping("/detalle/{id}")
	public String detailsPrograma(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Dueno> dueno = pService.listarId(id);

			if (!dueno.isPresent()) {
				model.addAttribute("info", "Dueno no existe");
				return "redirect:/dueno/listar";
			} else {
				model.addAttribute("Dueno", dueno.get());

			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "dueno";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Dueno dueno) throws ParseException {

		List<Dueno> listaDuenos;

		dueno.setDniPersona(dueno.getDniPersona());
		listaDuenos = pService.findByDniPersona(dueno.getDniPersona());

		if (listaDuenos.isEmpty()) {
			listaDuenos = pService.buscarNombre(dueno.getDniPersona());
		}
		
		
		if (listaDuenos.isEmpty()) {
			listaDuenos = pService.buscarEmail(dueno.getDniPersona());
		}

		if (listaDuenos.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaDuenos", listaDuenos);
		return "listaDueno";

	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Optional<Dueno> objPr = pService.listarId(id);

		if (objPr == null) {
			objRedir.addFlashAttribute("mensaje", "OcurriÃ³Â³ un error");
			return "redirect:/dueno/listar";
		} else {
			model.addAttribute("listaDuenos", pService.listar());

			model.addAttribute("dueno", objPr.get());
			return "dueno";
		}
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				pService.eliminar(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un dueno");
		}
		model.put("listaDuenos", pService.listar());

		return "redirect:/dueno/listar";
	}

}
