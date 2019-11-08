package pe.edu.upc.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bienvenido")
public class Bienvenido {
	@RequestMapping("/")
	public String irBienvenido(Map<String, Object> model) {
		return "bienvenido";	
	}
}
