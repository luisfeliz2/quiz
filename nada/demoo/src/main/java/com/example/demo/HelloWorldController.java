package demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	private static int contador =0;
	@GetMapping("/")
	public String contador() {
		return contador++ +"";
	}
	
	
	@GetMapping("/reset")
	public String resetContador() {
		return (contador=0)+"";
	}
	// Ejemplo inicial de https://spring.io/quickstart 
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	// Mismo que el anterior pero en español
	@GetMapping("/Saludo")
	public String saludos(@RequestParam(value = "nombre", defaultValue = "") String nombre) {
		return "".equals(nombre) ? "GET Bienvenida: No me has pasado tu nombre" : "GET Bienvendida: " + nombre;
	}

	// Ahora por POST, recibiendo un String y devolviendo un String
	@PostMapping("/SaludoPost")
	public String envioSaludos(@RequestBody(required=false) String nombre) {
		return "".equals(nombre) ? "<h1> POST Bienvenida no me has pasado tu nombre <h1/>" : "<h1>POST Bienvenida: " + nombre+"<h1/>";
	}

	// Por POST recibiendo Json y devolviendo String
	@PostMapping("/SaludoJson")
	public String envioJson(@RequestBody(required=false) Contacto persona) {
		return "".equals(persona.getNombre()) ? "BienvenidaJSON: "
				: "BienvenidaJSON: " + persona.getNombre() + " " + persona.getApellido();

	}

	// Por GET recibiendo String y devolviendo JSON
	@GetMapping("/SaludoJson2")
	public Contacto envioJson2(@RequestParam(value = "nombre", defaultValue = "Mundo") String nombre) {
		return new Contacto("Hola ", nombre);
	}

	// Por POST recibiendo JSON y devolviendo JSON
	@PostMapping("/SaludoJson2")
	public Contacto envioJson2(@RequestBody Contacto persona) {
		return new Contacto("Hola Mundo", persona.getNombre() + "," + persona.getApellido());
	}

	// Por POST recibiendo String y devolviendo JSON
	@PostMapping("/SaludoJson3")
	public Contacto envioJson3(@RequestBody(required = false) String nombre) {
		// return "".equals(persona.getNombre()) ? "BienvenidaJSON: " : "BienvenidaJSON:
		// " + persona.getNombre() + " " + persona.getApellido();
		if (nombre == null)
			nombre = "Mundo";
		return new Contacto("Hola", nombre);
	}

}
