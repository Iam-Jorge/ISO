package Dominio;

import java.sql.Date;

public class Reserva {
	
	private int id;
	private double precio;
	private Date fechaInicio;
	private Date fechaFin;
	private Estado estado;
	private String localizacion;
	private double superficie;
	private boolean aparcamiento;
	private boolean piscina;
	private boolean wifi;
	
	public Reserva() {
		super();
	}
	
	public Reserva(double precio, Date fechaInicio, Date fechaFin, Estado estado, String localizacion,
			double superficie, boolean aparcamiento, boolean piscina, boolean wifi) {
		super();
		this.precio = precio;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estado = estado;
		this.localizacion = localizacion;
		this.superficie = superficie;
		this.aparcamiento = aparcamiento;
		this.piscina = piscina;
		this.wifi = wifi;
	}
	
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public String getLocalizacion() {
		return localizacion;
	}
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}
	public double getSuperficie() {
		return superficie;
	}
	public void setSuperficie(double superficie) {
		this.superficie = superficie;
	}
	public boolean getAparcamiento() {
		return aparcamiento;
	}
	public void setAparcamiento(boolean aparcamiento) {
		this.aparcamiento = aparcamiento;
	}
	public boolean getPiscina() {
		return piscina;
	}
	public void setPiscina(boolean piscina) {
		this.piscina = piscina;
	}
	public boolean getWifi() {
		return wifi;
	}
	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Reserva" + " ID: " + id + " precio=" + precio + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", estado="
				+ estado + ", localizacion=" + localizacion + ", superficie=" + superficie + ", aparcamiento="
				+ aparcamiento + ", piscina=" + piscina + ", wifi=" + wifi;
	}
}
