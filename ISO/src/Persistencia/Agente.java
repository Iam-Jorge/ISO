package Persistencia;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class Agente implements Persistencia {
	
	//Instancia del agente
	protected static Agente mInstancia = null;
	protected static Connection conn;
	
	//Parámetros de conexión
	private static Properties prop = new Properties();
	private static String servidor = null;
	private static String baseDatos = null;
	private static String puerto = null;
	private static String usuario = null;
	private static String password = null;
	
	// Constructor
	private Agente() throws Exception {
		conectar();
	}

	//Patrón Singleton
	public static Agente getAgente() throws Exception {
		if (mInstancia == null) {
			mInstancia = new Agente();
		}
		return mInstancia;
	}

	// Metodo para realizar la conexion a la base de datos
	private void conectar() {
		//Cargar CFG.INI (Archivo que contiene los parámetros de conexión)
		try {
			prop.load(new FileReader("CFG.INI"));
		} catch (IOException e) {
			System.err.println("No ha sido posible encontrar el archivo de configuración");
		}
		// Leer los parámetros de conexión
		servidor=prop.getProperty("mysqlJDBC.servidor");
		baseDatos=prop.getProperty("mysqlJDBC.basedatos");
		puerto=prop.getProperty("mysqlJDBC.puerto");
		usuario=prop.getProperty("mysqlJDBC.usuario");
		password=prop.getProperty("mysqlJDBC.password");
		if (servidor.length() == 0 || baseDatos.length() == 0 || puerto.length() == 0 || usuario.length() == 0 || password.length() == 0) {
			JOptionPane.showMessageDialog(null, "Faltan parámetros en el archivo de configuración.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, "No fue posible encontrar el Driver de MySql " + e.getCause(), "Error",
				JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		}
	}

	// Metodo para desconectar de la base de datos
	public void desconectar() throws Exception {
	}


	public void buscarClub(String nombre) {
		System.out.println("Prueba");
	}
}