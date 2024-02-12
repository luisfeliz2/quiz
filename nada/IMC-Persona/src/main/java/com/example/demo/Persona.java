package demo;

public class Persona {
	private double peso;
	private double altura;
	private String genero;
	

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public double calcularIMC() {
		return peso/Math.pow(altura, 2);
	}

}
