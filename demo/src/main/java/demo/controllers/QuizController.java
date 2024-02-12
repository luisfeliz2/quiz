package demo.controllers;




import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


import demo.model.Jugada;
import demo.model.Jugador;
import demo.model.Usuario;
import demo.respositories.JugadaRepository;
import demo.respositories.JugadorRepository;
import jakarta.servlet.http.HttpSession;

@Controller

public class QuizController {
	@Autowired
	private JugadaRepository jugadaRepository;
	@Autowired
    private JugadorRepository jugadorRepository;
    
	@GetMapping("")
	public String inicio(Model model, HttpSession sesion) {
	    // Verificar si la sesión es nueva
	    if (sesion.isNew()) {
	        // Si la sesión es nueva, crear un nuevo objeto Resultado y guardarlo en la sesión
	        sesion.setAttribute("jugador", new Jugador());
	        sesion.setAttribute("guardar", true);
	    }
	   
	    
	    mostrar();
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
		System.out.println(puntuacionActual);
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
	public String manejarPregunta4(@RequestParam(name = "respuesta4") String respuesta4, Model modelo, HttpSession sesion) {
	    // Obtener el usuario de la sesión
	    Usuario usuario = (Usuario) sesion.getAttribute("usuario");
	    
	    // Obtener la pregunta actual de la sesión
	    String preguntaActual = (String) sesion.getAttribute("pregunta");
	    
	    // Verificar si la pregunta actual es la misma que la pregunta que se está respondiendo
	    if (preguntaActual != null && preguntaActual.equals("4")) {
	        // Si es la misma pregunta, redirigir a la página de resultado sin realizar ninguna acción adicional
	        return "redirect:/resultado";
	    }
	    
	    // Calcular la puntuación para la pregunta 4
	    int nuevaPuntuacion = (int) usuario.getPuntuacion() + calcularPuntuacion(respuesta4, sesion);
	    
	    // Establecer la nueva puntuación para el usuario
	    usuario.setPuntuacion(nuevaPuntuacion);
	    sesion.setAttribute("usuario", usuario);
	    sesion.setAttribute("pregunta", "4");
	    
	    // Redirigir a la página de resultado
	    return "redirect:/resultado";
	}
	
	public List<Jugada> mostrarJugadas(long id) {
	  
	    List<Jugada> jugadasDelJugador = jugadaRepository.findByJugadorIdOrderByPuntosDesc(id);
	   
	   
	    return jugadasDelJugador;
	}


	@GetMapping("/resultado")
	public String mostrarResultado(HttpSession sesion, Model modelo) {
		// Obtener la puntuación final del usuario y determinar la categoría
		Usuario usuario = (Usuario) sesion.getAttribute("usuario");
		int puntuacionFinal = usuario.getPuntuacion();
		String categoria = determinarCategoria(puntuacionFinal, modelo);
	
		//obtenemos el jugador de la sesion
		Jugador jugador =(Jugador) sesion.getAttribute("jugador");
		if(jugador == null) {
			 jugador = new Jugador();
		}
		jugador.setNombreJugador((String) sesion.getAttribute("nombreJ"));
		//--------------------------------------------------//d
		//clase Jugada
		Jugada jugada = new Jugada();
		jugada.setCategoria(categoria);
		jugada.setPuntos(puntuacionFinal);
		jugador.anadirJugadas(jugada);
	
		Date fecha = new Date(System.currentTimeMillis());
		//añado la fecha fecha ala jugada
		jugada.setFecha(fecha);
		
		guardarResultado(jugador,categoria,puntuacionFinal, sesion);
		
		// Agregar la puntuación final y la categoría al modelo
		modelo.addAttribute("puntuacionFinal", puntuacionFinal);
		modelo.addAttribute("categoria", categoria);
		modelo.addAttribute("resultaJugador",jugador);
		modelo.addAttribute("jugadores",obtenerResultado());
	
		modelo.addAttribute("jugadas",obtenerResultadoJugada());
		
		List<Jugada> filtrado = (List<Jugada>) sesion.getAttribute("filtrado");
		if(filtrado ==null) {
			filtrado= new ArrayList<Jugada>();
		}
			modelo.addAttribute("filtrado",filtrado);
		
		return "resultado";
	}

	
	public List<Jugador> obtenerResultado (){
		
		return jugadorRepository.findAll();
		}

	
    public void guardarResultado(@RequestBody Jugador resultado, String categoria, int puntuacionFinal,HttpSession sesion) {
    	
    	Jugador jugadorExistente = jugadorRepository.findBynombreJugador(resultado.getNombreJugador());
    	System.out.println(jugadorExistente);
    	//si el jugador signi que no es null
    	if (jugadorExistente != null) {
    	
    		Jugada jugada = new Jugada();
    		jugada.setPuntos(puntuacionFinal);
    		jugada.setCategoria(categoria);
    		jugada.setJugador(jugadorExistente);
    		Date fecha = new Date(System.currentTimeMillis());
    		jugada.setFecha(fecha);
    		boolean guardar = (boolean) sesion.getAttribute("guardar");
    		if(guardar){
    			sesion.setAttribute("guardar",false);
    			guardarJugada(jugada);
    			
    		}
    		
    		
    	}else {
    		boolean guardar = (boolean) sesion.getAttribute("guardar");
    		if(guardar){
    			sesion.setAttribute("guardar",false);
    			jugadorRepository.save(resultado);	
    		}	 
    	}   
    }
   @PostMapping("/resultados")
  public String resultado(@RequestParam(name="jugadorId" ) long id, HttpSession sesion) {
	   
	   List<Jugada> jugadas = mostrarJugadas(id);
	    sesion.setAttribute("filtrado", jugadas);
	    System.out.println("Datos pasados al modelo: " + jugadas);
	    return "redirect:/resultado";
	   
   }
    public void guardarJugada(@RequestBody Jugada jugada) {
    	jugadaRepository.save(jugada);
    }
    public void mostrar() {
    	jugadorRepository.findAll().forEach(jugador->{
    		System.out.println(jugador.getNombreJugador());
    		jugador.getJugadas().forEach(jugada->{
    			System.out.println(jugada.getPuntos());
    		});
    	});
    
    }
	public List<Jugada> obtenerResultadoJugada (){
		return jugadaRepository.findAll();
	}


 	private int calcularPuntuacion(String respuesta, HttpSession sesion) {

		if (sesion.getAttribute("pregunta").equals("1")) {
			
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
			
			int puntos = 0;

			return puntos;

		} else if (sesion.getAttribute("pregunta").equals("4")) {
			

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
