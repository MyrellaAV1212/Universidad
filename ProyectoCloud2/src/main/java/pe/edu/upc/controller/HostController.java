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

import pe.edu.upc.model.Host;
import pe.edu.upc.service.IHostService;

@Controller
@RequestMapping("/host")
public class HostController {

	@Autowired
	private IHostService hService;
	
	@Autowired
	private PersonaController personaEncryp;

	@GetMapping("/nuevo")
	public String nuevoHost(Model model) {
		model.addAttribute("host", new Host());
		return "host/host";
	}
	
	
	@RequestMapping("/guardar")
	public String guardarDueno(@ModelAttribute @Valid Host host, BindingResult binRes, Model model,
			SessionStatus status) throws ParseException {
		if (binRes.hasErrors()) {

			return "/host/host";
		} else {
			host.setPasswordUsuario(personaEncryp.getPasswordEncoder2().encode(host.getPasswordUsuario()));
			hService.insertar(host);
			model.addAttribute("mensaje", "Se guardó correctamente");
			status.setComplete();
			return "redirect:/host/listar";
		}
	}

	@GetMapping("/listar")
	public String listarDuenos(Model model) {
		try {
			model.addAttribute("host", new Host());

			model.addAttribute("listaHosts", hService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/host/listaHost";
	}

	@GetMapping("/detalle/{id}")
	public String detailsPrograma(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Host> host = hService.listarId(id);

			if (!host.isPresent()) {
				model.addAttribute("info", "Host no existe");
				return "redirect:/host/listar";
			} else {
				model.addAttribute("Host", host.get());

			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "/host/host";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Host host) throws ParseException {

		List<Host> listaHosts;

		host.setDniPersona(host.getDniPersona());
		listaHosts = hService.findByDniPersona(host.getDniPersona());

		if (listaHosts.isEmpty()) {
			listaHosts = hService.buscarNombre(host.getDniPersona());
		}
		
		
		if (listaHosts.isEmpty()) {
			listaHosts = hService.buscarEmail(host.getDniPersona());
		}

		if (listaHosts.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaHosts", listaHosts);
		return "host/listaHost";

	}
	
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Optional<Host> objPr = hService.listarId(id);

		if (objPr == null) {
			objRedir.addFlashAttribute("mensaje", "OcurriÃ³Â³ un error");
			return "redirect:/host/listar";
		} else {
			model.addAttribute("listaHosts", hService.listar());

			model.addAttribute("host", objPr.get());
			return "host/host";
		}
	}
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				hService.eliminar(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un Host");
		}
		model.put("listaHosts", hService.listar());

		return "redirect:/host/listar";
	}


}
