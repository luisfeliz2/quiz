package com.example.demo;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Usuario {
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	@NotBlank(message="El correo electrónico no puede estar en blanco")
	@Email(message="Debe ser una dirección de correo electrónico válida")
	private String email;
	
	@NotBlank(message="la fecha de nacimiento no puede estar vacia")
	@Past(message="La fecha de nacimiento debe ser anterior ala fecha actual")
	private LocalDate fechaNac;
	
	@NotBlank(message="La contraseña no puede estar en blanco")
	@Size(min=8,message ="La contraseña debe tener al menos 8 caracteres")
	@Pattern(
			regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).*$",
			 message = "La contraseña debe contener al menos una letra mayúscula, una letra minúscula, un dígito y un carácter especial"
			)
	private String contrasena;
	
	
	
}
