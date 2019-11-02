package pe.edu.upc.spring.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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

import pe.edu.upc.spring.model.Pet;
import pe.edu.upc.spring.service.IDuenoService;
import pe.edu.upc.spring.service.IPetService;
import pe.edu.upc.spring.service.IUploadFileService;

@Controller
@RequestMapping("/pet")
public class PetController {

	@Autowired
	private IPetService pService;

	

	@Autowired
	private IDuenoService perService;

	@Autowired
	private IUploadFileService uploadFileService;

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

	// METODO PARA VER EL DETALLE EMPLEADO
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") int id, Map<String, Object> model, RedirectAttributes flash) {

		Pet pet = pService.listarId(id);

		if (pet == null) {
			flash.addFlashAttribute("error", "La mascota no existe en la base de datos");
			return "redirect:/listPet";
		}

		model.put("pet", pet);
		model.put("titulo", "Detalle de mascota: " + pet.getNamePet());

		return "/ver";
	}

	@RequestMapping("/")
	public String irPet(Map<String, Object> model) {
		model.put("listaMascotas", pService.listar());
		return "listPet";
	}

	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model) {
				model.addAttribute("listaDuenos", perService.listar());
		model.addAttribute("pet", new Pet());
		return "pet";
	}

	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute @Valid Pet objPet, BindingResult binRes, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status)
			throws ParseException {
		if (binRes.hasErrors()) {
			
			model.addAttribute("listaDuenos", perService.listar());
			return "pet";
		} else {
			if (!foto.isEmpty()) {

				if (objPet.getIdPet() > 0 && objPet.getFoto() != null && objPet.getFoto().length() > 0) {

					uploadFileService.delete(objPet.getFoto());
				}

				String uniqueFilename = null;
				try {
					uniqueFilename = uploadFileService.copy(foto);
				} catch (IOException e) {
					e.printStackTrace();
				}

				flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");
				objPet.setFoto(uniqueFilename);
			}
			boolean flag = pService.insertar(objPet);
			if (flag) {
				return "redirect:/pet/listar";
			} else {
				model.addAttribute("mensaje", "OcurriÃ³ un error");
				return "redirect:/pet/irRegistrar";
			}
		}
	}

	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid Pet objPet, BindingResult binRes, Model model,
			RedirectAttributes objRedir) throws ParseException {
		if (binRes.hasErrors()) {
			return "redirect:/pet/listar";
		} else {
			boolean flag = pService.modificar(objPet);

			if (flag) {
				objRedir.addFlashAttribute("mensaje", "Se actualizÃ³ correctamente");
				return "redirect:/pet/listar";

			} else {
				objRedir.addFlashAttribute("mensaje", "OcurriÃ³ un error");
				return "redirect:/pet/listar";
			}
		}
	}

	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		
		Pet objPet = pService.listarId(id);
		if (objPet == null) {
			objRedir.addFlashAttribute("mensaje", "OcurriÃ³ un error");
			return "redirect:/pet/listar";
		} else {
			model.addAttribute("listaDuenos", perService.listar());
			
			model.addAttribute("pet", objPet);
			return "pet";
		}
	}

	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id,
			RedirectAttributes flash) {
		try {
			Pet pet = pService.listarId(id);
			if (id != null && id > 0) {
				pService.eliminar(id);
				if (uploadFileService.delete(pet.getFoto())) {
					flash.addFlashAttribute("info", "Foto " + pet.getFoto() + " eliminada con exito!");
				}
				model.put("listaMascotas", pService.listar());
			}
		} catch (Exception e) {
			model.put("mensaje", "Se eliminÃ³");
			model.put("listaMascotas", pService.listar());

		}
		return "listPet";
	}

	@RequestMapping("/listar")
	public String listar(Map<String, Object> model) {

		model.put("listaMascotas", pService.listar());
		return "listPet";

	}

	@RequestMapping("/listarId")
	public String listarId(Map<String, Object> model, @ModelAttribute Pet pet) {
		pService.listarId(pet.getIdPet());
		return "listPet";

	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Pet pet) throws ParseException {

		List<Pet> listaMascotas;
		pet.setNamePet(pet.getNamePet());
		listaMascotas = pService.buscarPet(pet.getNamePet());

			if (listaMascotas.isEmpty()) {
				try {

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					pet.setBirthDatePet(sdf.parse(pet.getNamePet()));
					listaMascotas = pService.findByBirthDatePet(pet.getBirthDatePet());
				} catch (Exception e) {
					model.put("mensaje", "Formato incorreco");

				}
			}

		if (listaMascotas.isEmpty())

		{

			model.put("mensaje", "No se encontrÃ³");
		}

		model.put("listaMascotas", listaMascotas);
		return "buscarPet";

	}

	@RequestMapping("/irBuscar")
	public String irBuscar(Model model) {

		model.addAttribute("pet", new Pet());
		return "buscarPet";

	}

}
