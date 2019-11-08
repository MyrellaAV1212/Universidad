package pe.edu.upc.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.model.Pet;
import pe.edu.upc.service.IDuenoService;
import pe.edu.upc.service.IPetService;
import pe.edu.upc.service.IUploadFileService;

@Controller
@RequestMapping("/pet")
public class PetController {

	@Autowired
	private IPetService pService;

	@Autowired
	private IDuenoService dService;

	@Autowired
	private IUploadFileService uploadFileService;

	@GetMapping("/nuevo")
	public String nuevoPet(Model model) {
		model.addAttribute("pet", new Pet());
		model.addAttribute("listaDuenos", dService.listar());
		return "/pet";
	}

	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@RequestMapping("/guardar")
	public String guardarPet(@ModelAttribute @Valid Pet pet, BindingResult binRes, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status)
			throws ParseException {
		if (binRes.hasErrors()) {

			return "/pet";
		} else {
			if (!foto.isEmpty()) {

				if (pet.getIdPet() > 0 && pet.getFoto() != null && pet.getFoto().length() > 0) {

					uploadFileService.delete(pet.getFoto());
				}

				String uniqueFilename = null;
				try {
					uniqueFilename = uploadFileService.copy(foto);
				} catch (IOException e) {
					e.printStackTrace();
				}

				flash.addFlashAttribute("info", "Se ha registrado correctamente");
				pet.setFoto(uniqueFilename);
			}

		}
		pService.insertar(pet);
		model.addAttribute("mensaje", "Se guardó correctamente");
		status.setComplete();
		return "redirect:/pet/listar";
	}

	@GetMapping("/listar")
	public String listarProcesos(Model model) {
		try {
			model.addAttribute("pet", new Pet());

			model.addAttribute("listaPets", pService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/listaPet";
	}

	@GetMapping("/detalle/{id}")
	public String detailsProceso(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Pet> pet = pService.listarId(id);

			if (!pet.isPresent()) {
				model.addAttribute("info", "Proceso no existe");
				return "redirect:/pet/listar";
			} else {
				model.addAttribute("pet", pet.get());

			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "/pet";
	}

	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Pet> pet = pService.listarId(id);
		if (pet == null) {
			flash.addFlashAttribute("error", "La Mascota no existe en la base de datos");
			return "redirect:/pet/listar";
		}

		model.put("pet", pet.get());

		return "/verPet";
	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Pet pet) throws ParseException {
		List<Pet> listaPets;
		pet.setNamePet(pet.getNamePet());
		listaPets = pService.buscarPet(pet.getNamePet());

		if (listaPets.isEmpty()) {
			try {

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				pet.setBirthDatePet(sdf.parse(pet.getNamePet()));
				listaPets = pService.findByBirthDatePet(pet.getBirthDatePet());
			} catch (Exception e) {
				model.put("mensaje", "Formato incorreco");

			}
		}
		if (listaPets.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaPets", listaPets);
		return "/listaPet";

	}

	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Optional<Pet> objPet = pService.listarId(id);

		if (objPet == null) {
			objRedir.addFlashAttribute("mensaje", "OcurriÃ³Â³ un error");
			return "redirect:/pet/listar";
		} else {
			model.addAttribute("listaDuenos", dService.listar());

			model.addAttribute("pet", objPet.get());
			return "/pet";
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
			model.put("mensaje", "No se puede eliminar la mascota");
		}
		model.put("listaPets", pService.listar());

		return "redirect:/pet/listar";
	}

}
