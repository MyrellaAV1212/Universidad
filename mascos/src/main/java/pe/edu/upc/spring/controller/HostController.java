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

import pe.edu.upc.spring.model.Host;
import pe.edu.upc.spring.service.IHostService;

@Controller
@RequestMapping("/host")
public class HostController {

	@Autowired
	private IHostService hService;

	@RequestMapping("/")
	public String irHost(Map<String, Object> model) {
		model.put("listaHosts", hService.listar());
		return "listHost";
	}

	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {

		model.addAttribute("host", new Host());
		return "host";
	}

	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute @Valid Host objHost, BindingResult binRes, Model model)
			throws ParseException {
		if (binRes.hasErrors()) {
			return "host";
		} else {
			boolean flag = hService.insertar(objHost);
			if (flag) {
				return "redirect:/host/listar";
			} else {
				model.addAttribute("mensaje", "Ocurrió un error");
				return "redirect:/host/irRegistrar";
			}
		}
	}

	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid Host objHost, BindingResult binRes, Model model,
			RedirectAttributes objRedir) throws ParseException {
		if (binRes.hasErrors()) {
			return "redirect:/host/listar";
		} else {
			boolean flag = hService.modificar(objHost);

			if (flag) {
				objRedir.addFlashAttribute("mensaje", "Se actualizó correctamente");
				return "redirect:/host/listar";

			} else {
				objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
				return "redirect:/host/listar";
			}
		}
	}

	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Optional<Host> objHost = hService.listarId(id);
		if (objHost == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
			return "redirect:/host/listar";
		} else {
			model.addAttribute("host", objHost);
			return "host";

		}
	}

	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {

				hService.eliminar(id);
				model.put("listaHosts", hService.listar());
			}
		} catch (

		Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un host asignado.");
			model.put("listaHosts", hService.listar());

		}
		return "listHost";
	}

	@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {

		model.put("listaHosts", hService.listar());
		return "listHost";

	}

	@RequestMapping("/listarId")
	public String listarId(Map<String, Object> model, @ModelAttribute Host host) {

		hService.listarId(host.getIdHost());
		return "listHost";

	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Host host) throws ParseException {

		List<Host> listaHosts;

		if (StringUtils.isNumeric(host.getDniHost())) {

			listaHosts = hService.findByDniHost(host.getDniHost());

		} else {

			host.setNameHost(host.getDniHost());
			listaHosts = hService.buscarNombre(host.getNameHost());

			if (listaHosts.isEmpty()) {

				host.setEmailHost(host.getDniHost());
				listaHosts = hService.buscarEmail(host.getEmailHost());

			}

			if (listaHosts.isEmpty()) {
				try {

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

					host.setBirthDateHost(sdf.parse(host.getDniHost()));
					listaHosts = hService.findByBirthDateHost(host.getBirthDateHost());

				} catch (Exception e) {
					model.put("mensaje", "Formato incorrecto");

				}
			}

		}

		if (listaHosts.isEmpty()) {

			model.put("mensaje", "No se encontró");
		}

		model.put("listaHosts", listaHosts);
		return "buscarHost";

	}

	@RequestMapping("/irBuscar")
	public String irBuscar(Model model) {

		model.addAttribute("host", new Host());
		return "buscarHost";

	}

}
