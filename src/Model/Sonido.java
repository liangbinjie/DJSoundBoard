package Model;

public class Sonido {
	private String ruta;
	private String nombre;
	
	public Sonido() {
		nombre = "";
		ruta = "";
	}
	
	public Sonido(String nombre, String ruta) {
		this.nombre = nombre;
		this.ruta = ruta;
	}
	
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
