package demo.model;

import java.util.HashSet;
import java.util.Set;

import demo.respositories.JugadaRepository;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity

public class Jugador {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombreJugador;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombreJugador() {
		return nombreJugador;
	}
	public void setNombreJugador(String nombreJugador) {
		this.nombreJugador = nombreJugador;
	}
	
	@OneToMany(
	mappedBy ="jugador",
	cascade = CascadeType.ALL,
	orphanRemoval= true
			)
	private Set<Jugada> jugada = new HashSet<>();
	

	
	
	public Set<Jugada> getJugadas() {
		return jugada;
	}
	public void setJugadas(Set<Jugada> jugada) {
		this.jugada = jugada;
	}
	public boolean anadirJugadas(Jugada jugada) {
		jugada.setJugador(this);
		return getJugadas().add(jugada);
	}
	

	public void eliminarJugadas(Jugada jugada) {
		getJugadas().remove(jugada);
		}

	
	
}
