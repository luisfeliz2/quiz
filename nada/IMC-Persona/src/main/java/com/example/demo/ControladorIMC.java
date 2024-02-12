package demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Controller
public class ControladorIMC {
	
	
	
	@GetMapping("/")
	public String verFormulario(Model demo.model) {
		
		demo.model.addAttribute("person",new Persona());
		return "formularioIMC";
		
		
	}
	@PostMapping("/")
	public String calIMC(Persona persona,Model demo.model) {
		double imc = persona.calcularIMC();
		String resultado = resultado(persona.getGenero(),imc);
		Map<String, Object> atributos = new HashMap<>();
		
		atributos.put("imc",imc);
		atributos.put("resultado",resultado);
		atributos.put("person",persona);
		
		demo.model.addAllAttributes(atributos);
		return "resultado";
		
	}
	
	public String resultado(String genero,double IMC) {
		if(genero.equalsIgnoreCase("hombre")) {
			return calIMCH(IMC);
		}else if(genero.equalsIgnoreCase("mujer")) {
			return calIMCM(IMC);
		}else {
			return "opcion no valida";
		}
		
	}
	
	public String calIMCH(double imc) {
		if(imc<20) {
			return "peso bajo";
		}else if(imc<25) {
			return "peso normal";
			
		}else {
			return "sobrePeso";
		}
		
	}
	public String calIMCM(double imc) {
		if(imc<19) {
			return "peso bajo";
		}else if(imc<24) {
			return "peso normal";
			
		}else {
			return "sobrePeso";
		}
		
	}
	
}
