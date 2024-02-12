package demo.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import demo.model.Jugada;


	@Repository
	public interface JugadaRepository extends JpaRepository<Jugada,Long>{
		List<Jugada> findByjugadorId(Long i);
		List<Jugada> findByJugadorIdOrderByPuntosDesc(Long jugadorId);
		
	}


