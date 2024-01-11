package com.example.demo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PersonaFormulario {

	@NotNull
	@Size(min=2, max=30)
	private String nombre;

	@NotNull
	@Min(18)
	private Integer edad;

	public String toString() {
		return "Persona(con Nombre: " + this.nombre + ", Edad: " + this.edad + ")";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}
}
	

