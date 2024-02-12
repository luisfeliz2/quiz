package demo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class Controlador {

	@GetMapping
	public String verFormulario(Model modelo) {
		modelo.addAttribute(modelo)
	}
}
