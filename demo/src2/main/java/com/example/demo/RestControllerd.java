package demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllerd {
	 @Autowired
	    private ResultadoRepository resultadoRepository;

	    @GetMapping("/resultados")
	    public List<Resultado> obtenerResultados() {
	        return resultadoRepository.findAll(); // Puedes ordenar o limitar la cantidad de resultados según tus necesidades
	    }
}
