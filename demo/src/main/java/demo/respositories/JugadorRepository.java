package demo.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demo.model.Jugador;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador,Long>{
	Jugador findBynombreJugador(String nomrbe);
	
}
