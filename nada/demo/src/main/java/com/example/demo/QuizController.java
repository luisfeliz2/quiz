package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class QuizController {
	@GetMapping("")
	public String inicio(Model model, HttpSession sesion) {
	    // Verificar si la sesión es nueva
	    if (sesion.isNew()) {
	        // Si la sesión es nueva, crear un nuevo objeto Resultado y guardarlo en la sesión
	        sesion.setAttribute("resultado", new Resultado());
	    }
	    // Devolver el nombre de la vista "inicio"
	    return "inicio";
	}

	
	@PostMapping("/inicio")
	public String inicio(@RequestParam(name = "nombre") String respuesta, HttpSession sesion,
			Model modelo) {
		modelo.addAttribute("nombre",respuesta);
		sesion.setAttribute("nombreJ",respuesta);
				return "redirect:/pregunta1";
		
	}
	@GetMapping("/pregunta1")
	public String pregunta1(Model modelo) {

		modelo.addAttribute("usuario", new Usuario());
		return "pregunta1";
	}
	

	@PostMapping("/pregunta1")
	public String manejarRespuesta1(@RequestParam(name = "respuesta1") String respuesta, HttpSession sesion,
			Model modelo) {
		// Obtener el usuario de la sesión o crear uno nuevo si aún no existe
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");

		if (usuario == null) {
			usuario = new Usuario();
			sesion.setAttribute("usuario", usuario);
		}
		sesion.setAttribute("pregunta", "1");
		// Lógica para procesar la respuesta y actualizar la puntuación
		int puntuacionActual = usuario.getPuntuacion();
		int nuevaPuntuacion = puntuacionActual + calcularPuntuacion(respuesta, sesion);
		usuario.setPuntuacion(nuevaPuntuacion);
		sesion.setAttribute("usuario", usuario);

		// Redirigir a la siguiente pregunta
		return "redirect:/pregunta2";
	}

	@GetMapping("/pregunta2")
	public String pregunta2(Model modelo) {
		// Inicializar el modelo para la segunda pregunta
		return "pregunta2";
	}

	@PostMapping("/pregunta2")
	public String manejarRespuesta2(@RequestParam(name = "respuesta2") String respuesta, HttpSession sesion,
			Model modelo) {
		// Lógica para procesar la respuesta y actualizar la puntuación
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		sesion.setAttribute("pregunta", "2");
		usuario.setPuntuacion(usuario.getPuntuacion() + calcularPuntuacion(respuesta, sesion)); // Puedes ajustar la
																								// lógica según tus
																								// necesidades
		sesion.setAttribute("usuario", usuario);

		// Redirigir al resultado final
		return "redirect:/pregunta3";
	}

	@GetMapping("/pregunta3")
	public String pregunta3(Model model) {
		return "pregunta3";

	}

	@PostMapping("/pregunta3")
	public String manejarPregunta3(@RequestParam(name = "respuesta3") String[] repuesta, HttpSession sesion,
			Model modelo) {

		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		// para saber en que pregunta estoy
		sesion.setAttribute("pregunta", "3");
		usuario.setPuntuacion(usuario.getPuntuacion() + calPuntuacionPreg3(repuesta, sesion));
		sesion.setAttribute("usuario", usuario);

		return "redirect:/pregunta4";

	}

	@GetMapping("/pregunta4")
	public String pregunta4(Model model) {
		return "pregunta4";
	}

	@PostMapping("/pregunta4")
	public String manejarPregunta4(@RequestParam(name = "respuesta4") String respuesta4, Model modelo,
			HttpSession sesion) {
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		sesion.setAttribute("pregunta", "4");
		System.out.println("dsads");
		int nuevaPuntacion = (int) usuario.getPuntuacion() + calcularPuntuacion(respuesta4, sesion);
		usuario.setPuntuacion(nuevaPuntacion);

		sesion.setAttribute("usuario", usuario);
		return "redirect:/resultado";

	}

	@Autowired
	private ResultadoRepository resultadoRepository; // Inyección de dependencia del repositorio de Resultado

	@GetMapping("/resultado")
	public String mostrarResultado(HttpSession sesion, Model modelo) {
	    // Obtener la puntuación final del usuario y determinar la categoría
	    Usuario usuario = (Usuario) sesion.getAttribute("usuario"); // Obtener el objeto Usuario de la sesión
	    int puntuacionFinal = usuario.getPuntuacion(); // Obtener la puntuación final del usuario
	    String categoria = determinarCategoria(puntuacionFinal, modelo); // Determinar la categoría basada en la puntuación final
	    
	    // Obtener el objeto Resultado de la sesión y actualizar sus atributos
	    Resultado resultado = (Resultado) sesion.getAttribute("resultado");
	    resultado.setNombreJugador((String) sesion.getAttribute("nombreJ")); // Establecer el nombre del jugador en el resultado
	    resultado.setPuntuacion(puntuacionFinal); // Establecer la puntuación final en el resultado
	    resultado.setCategoria(categoria); // Establecer la categoría en el resultado
	    
	    // Guardar el resultado en la base de datos a través del repositorio
	    guardarResultado(resultado);
	    
	    // Agregar la puntuación final, la categoría y el resultado al modelo para mostrar en la vista
	    modelo.addAttribute("puntuacionFinal", puntuacionFinal);
	    modelo.addAttribute("categoria", categoria);
	    modelo.addAttribute("Resultado", obtenerResultado()); // Agregar el resultado al modelo
	    
	    return "resultado"; // Devolver el nombre de la vista "resultado"
	}

   
    public void guardarResultado(@RequestBody Resultado resultado) {
        resultadoRepository.save(resultado);
    }
    public List<Resultado> obtenerResultado(){
    	return resultadoRepository.findAll();
    }
 
	private int calcularPuntuacion(String respuesta, HttpSession sesion) {

		if (sesion.getAttribute("pregunta").equals("1")) {
			sesion.setAttribute("pregunta", "");
			if (respuesta.equalsIgnoreCase("cielo")) {

				return 10;
			} else if (respuesta.equalsIgnoreCase("mar")) {

				return 20;
			} else if (respuesta.equalsIgnoreCase("tierra")) {

				return 20;
			} else if (respuesta.equalsIgnoreCase("cueva")) {

				return 20;
			}

		} else if ((sesion.getAttribute("pregunta").equals("2"))) {
			sesion.setAttribute("pregunta", "");
			int punto = 0;
			switch (respuesta.toLowerCase()) {
			case "belleza":
				punto = 10;
				break;
			case "pasion":
				punto = 20;
				break;
			case "amabilidad":
				punto = 30;
				break;
			case "humor":
				punto = 40;
				break;
			case "estatus":
				punto = 20;
				break;

			}

			return punto;

		} else if (sesion.getAttribute("pregunta").equals("3")) {
			sesion.setAttribute("pregunta", "");
			int puntos = 0;

			return puntos;

		} else if (sesion.getAttribute("pregunta").equals("4")) {
			sesion.setAttribute("pregunta", "");

			int puntos = 0;
			switch (respuesta.toLowerCase()) {
			case "1":
				puntos = 10;
				break;
			case "2":
				puntos = 20;
				break;
			case "3":
				puntos = 30;
				break;
			case "4":
				puntos = 40;
				break;
				

			}
			return puntos;

		}
		return 0;
	}

	private int calPuntuacionPreg3(String[] respuesta, HttpSession sesion) {
		int puntos = 0;
		for (String respuest : respuesta) {

			switch (respuest.toLowerCase()) {
			case "independencia":
				puntos += 5;
				break;
			case "lealtad":
				puntos += 10;
				break;
			case "admitad":
				puntos += 15;
				break;
			case "liderar":
				puntos += 20;
				break;

			case "sabiduria":
				puntos += 25;
				break;

			}
		}
		return puntos;

	}

	private String determinarCategoria(int puntuacion, Model model) {
		// Lógica para determinar la categoría
		if (puntuacion >= 150) {
			return "delfin";
		} else if (puntuacion >= 100) {
			return "leon";
		} else if (puntuacion >= 50) {
			return "buo";
		} else {
			return "aguila";
		}
	}
}

//Halcón peregrino = 100
//Llama Angel  = 50
//leon = 150
//buo = 200
