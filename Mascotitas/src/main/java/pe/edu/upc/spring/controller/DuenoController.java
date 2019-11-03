package pe.edu.upc.spring.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.spring.model.Dueno;
import pe.edu.upc.spring.service.IDuenoService;

@Controller
@RequestMapping("/dueno")
public class DuenoController {

	@Autowired
	private IDuenoService pService;

	@RequestMapping("/")
	public String irDueno(Map<String, Object> model) {
		model.put("listaDuenos", pService.listar());
		return "listDueno";
	}

	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {

		model.addAttribute("dueno", new Dueno());
		return "dueno";
	}

	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute @Valid Dueno objDueno, BindingResult binRes, Model model)
			throws ParseException {
		if (binRes.hasErrors()) {
			return "dueno";
		} else {
			boolean flag = pService.insertar(objDueno);
			if (flag) {
				return "redirect:/dueno/listar";
			} else {
				model.addAttribute("mensaje", "Ocurrió un error");
				return "redirect:/dueno/irRegistrar";
			}
		}
	}

	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid Dueno objDueno, BindingResult binRes, Model model,
			RedirectAttributes objRedir) throws ParseException {
		if (binRes.hasErrors()) {
			return "redirect:/dueno/listar";
		} else {
			boolean flag = pService.modificar(objDueno);

			if (flag) {
				objRedir.addFlashAttribute("mensaje", "Se actualizó correctamente");
				return "redirect:/dueno/listar";

			} else {
				objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
				return "redirect:/dueno/listar";
			}
		}
	}

	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Optional<Dueno> objDueno = pService.listarId(id);
		if (objDueno == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
			return "redirect:/dueno/listar";
		} else {
			model.addAttribute("dueno", objDueno);
			return "dueno";

		}
	}

	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {

				pService.eliminar(id);
				model.put("listaDuenos", pService.listar());
			}
		} catch (

		Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un dueño asignado.");
			model.put("listaDuenos", pService.listar());

		}
		return "listDueno";
	}

	@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {

		model.put("listaDuenos", pService.listar());
		return "listDueno";

	}

	@RequestMapping("/listarId")
	public String listarId(Map<String, Object> model, @ModelAttribute Dueno dueno) {

		pService.listarId(dueno.getIdDueno());
		return "listDueno";

	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Dueno dueno) throws ParseException {

		List<Dueno> listaDuenos;

		if (StringUtils.isNumeric(dueno.getDniDueno())) {

			listaDuenos = pService.findByDniDueno(dueno.getDniDueno());

		} else {

			dueno.setNameDueno(dueno.getDniDueno());
			listaDuenos = pService.buscarNombre(dueno.getNameDueno());

			if (listaDuenos.isEmpty()) {

				dueno.setEmailDueno(dueno.getDniDueno());
				listaDuenos = pService.buscarEmail(dueno.getEmailDueno());

			}

			if (listaDuenos.isEmpty()) {
				try {

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

					dueno.setBirthDateDueno(sdf.parse(dueno.getDniDueno()));
					listaDuenos = pService.findByBirthDateDueno(dueno.getBirthDateDueno());

				} catch (Exception e) {
					model.put("mensaje", "Formato incorrecto");

				}
			}

		}

		if (listaDuenos.isEmpty()) {

			model.put("mensaje", "No se encontró");
		}

		model.put("listaDuenos", listaDuenos);
		return "buscarDueno";

	}

	@RequestMapping("/irBuscar")
	public String irBuscar(Model model) {

		model.addAttribute("dueno", new Dueno());
		return "buscarDueno";

	}
	
	

}
