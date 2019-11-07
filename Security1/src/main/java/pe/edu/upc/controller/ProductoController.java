package pe.edu.upc.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.entity.Producto;
import pe.edu.upc.service.ICategoriaService;
import pe.edu.upc.service.IProductoService;
import pe.edu.upc.service.IUploadFileService;

@Controller
@RequestMapping("/productos")
@SessionAttributes("producto")
public class ProductoController {
	@Autowired
	private IProductoService pService;
	@Autowired
	private ICategoriaService cService;

	@Autowired
	private IUploadFileService uploadFileService;// trae la carpeta del service para agregar la foto

	@GetMapping(value = "/upload/{filename:.+}") // OBTIENE EL LINK DE LA FOTO Y LO GUARDA 
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

	@GetMapping("/bienvenido")
	public String bienvenido(Model model) {
		return "bienvenido";
	}
	@Secured("ROLE_ADMIN")
	@GetMapping("/nuevo")
	public String nuevoProducto(Model model) {
		model.addAttribute("producto", new Producto());
		model.addAttribute("listaCategorias", cService.listar());
		return "producto/producto";
	}

	@PostMapping("/guardar")
	public String guardarProducto(@Valid Producto producto, BindingResult result, Model model, SessionStatus status,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash) throws Exception { // SE AGREGA EL MULTIPART PARA PODER REALIZAR EL MANEJO DE LA IMAGEN
		if (result.hasErrors()) {
			model.addAttribute("listaCategorias", cService.listar());
			return "/producto/producto";
		} else {
			if (!foto.isEmpty()) { //VE SI EL CAMPO SE ENCUENTRA LLENO

				if (producto.getIdProducto() > 0 && producto.getFoto() != null && producto.getFoto().length() > 0) { // VERIFICA LAS CONDICIONES

					uploadFileService.delete(producto.getFoto());
				}

				String uniqueFilename = null; 
				try {
					uniqueFilename = uploadFileService.copy(foto); // MANDA AL S¿UPLOAD SERVICE 
				} catch (IOException e) {
					e.printStackTrace();
				}

				flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'"); //MANDA U MENSAJE DE QUE LA FOTO SE HA GUARDADO CORRECTAMENTE
				producto.setFoto(uniqueFilename);
			} 
				pService.insertar(producto); // INSERTA EL PRODUCTO
				model.addAttribute("mensaje", "Se guardó correctamente");
				status.setComplete();
				return "redirect:/productos/listar";
			}
			
		

	}

	@GetMapping("/listar")
	public String listarProductos(Model model) {
		try {
			model.addAttribute("producto", new Producto());

			model.addAttribute("listaProductos", pService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/producto/listaProducto";
	}

	@GetMapping("/detalle/{id}")
	public String detailsProducto(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Producto> producto = pService.listarId(id);

			if (!producto.isPresent()) {
				model.addAttribute("info", "Producto no existe");
				return "redirect:/productos/listar";
			} else {
				model.addAttribute("producto", producto.get());

			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}

		return "/producto/producto";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				pService.eliminar(id);
				model.put("mensaje", "Se eliminÃ³ correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un producto");
		}
		model.put("listaProductos", pService.listar());

		return "redirect:/productos/listar";
	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Producto producto) throws ParseException {

		List<Producto> listaProductos;

		producto.setNombreProducto(producto.getNombreProducto());
		listaProductos = pService.buscar(producto.getNombreProducto());

		if (listaProductos.isEmpty()) {
			listaProductos = pService.buscarCategoria(producto.getNombreProducto());
		}

		if (listaProductos.isEmpty()) {
			model.put("mensaje", "No se encontrÃ³");
		}
		model.put("listaProductos", listaProductos);
		return "producto/listaProducto";

	}

	
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Integer id, Map<String, Object> model, RedirectAttributes flash) {

		Optional<Producto> producto = pService.listarId(id);
		if (producto == null) {
			flash.addFlashAttribute("error", "El Producto no existe en la base de datos");
			return "redirect:/productos/listar";
		}

		model.put("producto", producto.get());

		return "producto/ver";
	}
	@Secured("ROLE_ADMIN")
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) {
		Optional<Producto> objPro = pService.listarId(id);

		if (objPro == null) {
			objRedir.addFlashAttribute("mensaje", "OcurriÃ³Â³ un error");
			return "redirect:/productos/listar";
		} else {
			model.addAttribute("listaCategorias", cService.listar());

			model.addAttribute("producto", objPro.get());
			return "producto/producto";
		}
	}

}
