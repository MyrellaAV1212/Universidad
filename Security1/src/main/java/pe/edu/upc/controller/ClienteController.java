package pe.edu.upc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import pe.edu.upc.entity.Cliente;
import pe.edu.upc.service.IClienteService;

@Controller
@RequestMapping("/clientes")
@SessionAttributes("cliente")
public class ClienteController {
	
	@Autowired
	private IClienteService cService;
	@RequestMapping("/bienvenido")
	public String irBienvenido() {
		return "bienvenido";
	}
	@GetMapping("/nuevo")
	public String nuevoCliente(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "cliente/cliente";
	}

	@PostMapping("/guardar")
	public String guardarCliente(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status)
			throws Exception {
		if (result.hasErrors()) {
			return "/cliente/cliente";
		} else {
			cService.insertar(cliente);
			model.addAttribute("mensaje", "Se guardó correctamente");
			status.setComplete();
			return "redirect:/clientes/listar";
		}
	}

	@GetMapping("/listar")
	public String listarClientes(Model model) {
		try {
			model.addAttribute("cliente", new Cliente());
			model.addAttribute("listaClientes", cService.listar());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/cliente/listaCliente";
	}

	@GetMapping("/detalle/{id}")
	public String detailsCliente(@PathVariable(value = "id") int id, Model model) {
		try {
			Optional<Cliente> cliente = cService.listarId(id);
			if (!cliente.isPresent()) {
				model.addAttribute("info", "Cliente no existe");
				return "redirect:/clientes/listar";
			} else {
				model.addAttribute("cliente", cliente.get());
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "/cliente/cliente";
	}
	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				cService.eliminar(id);
				model.put("mensaje", "Se eliminó correctamente");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "No se puede eliminar un cliente");
		}
		model.put("listaClientes", cService.listar());

		return "redirect:/clientes/listar";
	}

	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model, @ModelAttribute Cliente cliente) throws ParseException {

		List<Cliente> listaClientes;

		cliente.setDatosCliente(cliente.getDatosCliente());
		listaClientes = cService.buscarDatos(cliente.getDatosCliente());

		if (listaClientes.isEmpty()) {
			listaClientes = cService.buscarDni(cliente.getDatosCliente());
		}
		if (listaClientes.isEmpty()) {
			listaClientes = cService.buscarEmail(cliente.getDatosCliente());
		}
		if (listaClientes.isEmpty()) {
			try {

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				cliente.setFechaNacimientoCliente(sdf.parse(cliente.getDatosCliente()));
				listaClientes = cService.buscarFecha(cliente.getFechaNacimientoCliente());
			} catch (Exception e) {
				model.put("mensaje", "Formato incorreco");

			}
		}
		if (listaClientes.isEmpty()) {
			model.put("mensaje", "No se encontró");
		}
		model.put("listaClientes", listaClientes);
		return "cliente/listaCliente";

	}





}
