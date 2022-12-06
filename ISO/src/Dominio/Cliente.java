package Dominio;

public class Cliente {

	private String dni;
	private String nombre;
	private String apellidos;
	private int telefono;
	private int numCuenta;
	private boolean esVip;
	private String password;
	private String email;
	
	public Cliente() {
		super();
	}

	public Cliente(String dni, String nombre, String apellidos, int telefono, int numCuenta, boolean esVip, String password, String email) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.numCuenta = numCuenta;
		this.esVip = esVip;
		this.password = password;
		this.email = email;
	}
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public int getNumCuenta() {
		return numCuenta;
	}
	public void setNumCuenta(int numCuenta) {
		this.numCuenta = numCuenta;
	}
	public boolean isEsVip() {
		return esVip;
	}
	public void setEsVip(boolean esVip) {
		this.esVip = esVip;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Cliente con DNI:" + dni + ", Nombre:" + nombre + ", Apellidos:" + apellidos + ", Teléfono:" + telefono + ", Email:" + email;
	}
}
