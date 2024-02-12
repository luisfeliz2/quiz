package demo;

public class Contacto {
	
	private String nombre;
	private String apellido;
	
	public Contacto(String n, String a) {
		nombre = n;
		apellido = a;
	}
	
	public Contacto() { }
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String texto) {
		this.nombre = texto;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String texto) {
		this.apellido = texto;
	}
	
	
}
