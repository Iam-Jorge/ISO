package Dominio;

public class TarjetaCredito {
	
	private int numTarjeta;
	private String dni;
	private double saldo;
	
	public TarjetaCredito() {
		super();
	}

	public TarjetaCredito(int numTarjeta, String dni, double saldo) {
		super();
		this.numTarjeta = numTarjeta;
		this.dni = dni;
		this.saldo = saldo;
	}
	
	public int getNumTarjeta() {
		return numTarjeta;
	}
	public void setNumTarjeta(int numTarjeta) {
		this.numTarjeta = numTarjeta;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	@Override
	public String toString() {
		return "Tarjeta de crédito con número " + numTarjeta + ", y propietario " + dni + ", dispone de un saldo de =" + saldo + "]";
	}
	
	

}
