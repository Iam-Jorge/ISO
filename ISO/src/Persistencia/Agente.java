package Persistencia;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import javax.swing.JOptionPane;

import Dominio.Cargo;
import Dominio.Cliente;
import Dominio.Empleado;
import Dominio.Estado;
import Dominio.Reserva;
import Dominio.TarjetaCredito;

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
				String cadenaConexion = "jdbc:mysql://" + servidor + ":" + puerto + "/" + baseDatos;
				conn = DriverManager.getConnection(cadenaConexion, usuario, password);
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, "No fue posible encontrar el Driver de MySql " + e.getCause(), "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "No fue establecer la conexión" + e.getCause(), "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		}
	}

	// Metodo para desconectar de la base de datos
	public void desconectar() throws Exception {
	}

	@Override
	public Cliente getCliente(String dni) {
		Cliente cliente = null;
		try {
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery ("SELECT * FROM clientes where dni = '" + dni + "'");
			while (rs.next()) { 
				cliente = new Cliente();
				cliente.setDni(rs.getString("dni"));
				cliente.setNombre(rs.getString("nombre"));
				cliente.setApellidos(rs.getString("apellidos"));
				cliente.setTelefono(rs.getInt("telefono"));
				cliente.setEsVip(rs.getBoolean("vip"));
				cliente.setNumCuenta(rs.getInt("numcuenta"));
				cliente.setPassword(rs.getString("password"));
				cliente.setEmail(rs.getString("email"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cliente;
	}

	@Override
	public int insertCliente(Cliente cliente) {
		PreparedStatement ps = null;
		try {
			String query = "INSERT INTO clientes (dni,nombre,apellidos,telefono,password,email) VALUES(?,?,?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, cliente.getDni());
			ps.setString(2, cliente.getNombre());
			ps.setString(3, cliente.getApellidos());
			ps.setInt(4, cliente.getTelefono());
			ps.setString(5, cliente.getPassword());
			ps.setString(6, cliente.getEmail());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	@Override
	public int updateCliente(Cliente cliente) {
		PreparedStatement ps = null;
		try {
			String query = ("UPDATE clientes SET telefono=?,email=?,password=? WHERE dni=?");
			ps = conn.prepareStatement(query);
			ps.setInt(1, cliente.getTelefono());
			ps.setString(2, cliente.getEmail());
			ps.setString(3, cliente.getPassword());
			ps.setString(4, cliente.getDni());
		    ps.executeUpdate();
		    ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateClienteCompleto(Cliente cliente) {
		PreparedStatement ps = null;
		try {
			String query = ("UPDATE clientes SET telefono=?,email=?,password=?,nombre=?,apellidos=?,vip=? WHERE dni=?");
			ps = conn.prepareStatement(query);
			ps.setInt(1, cliente.getTelefono());
			ps.setString(2, cliente.getEmail());
			ps.setString(3, cliente.getPassword());
			ps.setString(4, cliente.getNombre());
			ps.setString(5, cliente.getApellidos());
			ps.setBoolean(6, cliente.isEsVip());
			ps.setString(7, cliente.getDni());
		    ps.executeUpdate();
		    ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	
	@Override
	public int deleteCliente(String dni) {
		String query = ("DELETE FROM clientes WHERE dni = ?");
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, dni);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Empleado getEmpleado(String dni) {
		Empleado empleado = null;
		try {
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery ("SELECT * FROM empleados where dni = '" + dni + "'");
			while (rs.next()) { 
				empleado = new Empleado();
				empleado.setDni(rs.getString("dni"));
				empleado.setNombre(rs.getString("nombre"));
				empleado.setApellidos(rs.getString("apellidos"));
				empleado.setTelefono(rs.getInt("telefono"));
				empleado.setNumCuenta(rs.getInt("numcuenta"));
				empleado.setPassword(rs.getString("password"));
				empleado.setEmail(rs.getString("email"));
				String cargoString = rs.getString("cargo");
				Cargo cargo = null;
				switch (cargoString) {
				case "administración":
					cargo = Cargo.administración;
					break;
				case "conserjería":
					cargo = Cargo.conserjería;
					break;
				case "limpieza":
					cargo = Cargo.limpieza;
					break;
				default:
					break;
				}
				empleado.setCargo(cargo);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empleado;
	}

	@Override
	public int insertEmpleado(Empleado empleado) {
		PreparedStatement ps = null;
		try {
			String query = "INSERT INTO empleados (dni,nombre,apellidos,telefono,password,email) VALUES(?,?,?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, empleado.getDni());
			ps.setString(2, empleado.getNombre());
			ps.setString(3, empleado.getApellidos());
			ps.setInt(4, empleado.getTelefono());
			ps.setString(5, empleado.getPassword());
			ps.setString(6, empleado.getEmail());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int insertEmpleadoCompleto(Empleado empleado) {
		PreparedStatement ps = null;
		try {
			String query = "INSERT INTO empleados (dni,nombre,apellidos,telefono,password,email,sueldo,cargo) VALUES(?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, empleado.getDni());
			ps.setString(2, empleado.getNombre());
			ps.setString(3, empleado.getApellidos());
			ps.setInt(4, empleado.getTelefono());
			ps.setString(5, empleado.getPassword());
			ps.setString(6, empleado.getEmail());
			ps.setDouble(7, empleado.getSueldo());
			Cargo cargo = empleado.getCargo();
			String cargoString = "";
			switch (cargo) {
			case administración:
				cargoString = "administración";
				break;
			case limpieza:
				cargoString = "limpieza";
				break;
			case conserjería:
				cargoString = "conserjería";
				break;
			default:
				break;
			}
			ps.setString(8, cargoString);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateEmpleado(Empleado empleado) {
		PreparedStatement ps = null;
		try {
			String query = ("UPDATE empleados SET telefono=?,email=?,password=? WHERE dni=?");
			ps = conn.prepareStatement(query);
			ps.setInt(1, empleado.getTelefono());
			ps.setString(2, empleado.getEmail());
			ps.setString(3, empleado.getPassword());
			ps.setString(4, empleado.getDni());
		    ps.executeUpdate();
		    ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateEmpleadoCompleto(Empleado empleado) {
		PreparedStatement ps = null;
		try {
			String query = ("UPDATE empleados SET telefono=?,email=?,password=?,nombre=?,apellidos=?,sueldo=?,cargo=? WHERE dni=?");
			ps = conn.prepareStatement(query);
			ps.setInt(1, empleado.getTelefono());
			ps.setString(2, empleado.getEmail());
			ps.setString(3, empleado.getPassword());
			ps.setString(4, empleado.getNombre());
			ps.setString(5, empleado.getApellidos());
			ps.setDouble(6, empleado.getSueldo());
			Cargo cargo = empleado.getCargo();
			String cargoString = "";
			switch (cargo) {
			case administración:
				cargoString = "administración";
				break;
			case limpieza:
				cargoString = "limpieza";
				break;
			case conserjería:
				cargoString = "conserjería";
				break;
			default:
				break;
			}
			ps.setString(7, cargoString);
			ps.setString(8, empleado.getDni());
		    ps.executeUpdate();
		    ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int deleteEmpleado(String dni) {
		String query = ("DELETE FROM empleados WHERE dni = ?");
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, dni);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Reserva> getReservas() {
		Reserva reserva = null;
		List<Reserva> reservas = new LinkedList<Reserva>();
		try {
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery ("SELECT * FROM reservas");
			while (rs.next()) {
				reserva = new Reserva();
				reserva.setId(rs.getInt("id"));
				reserva.setPrecio(rs.getDouble("precio"));
				reserva.setFechaInicio(rs.getDate("fechaInicio"));
				reserva.setFechaFin(rs.getDate("fechaFin"));
				reserva.setLocalizacion(rs.getString("localizacion"));
				reserva.setSuperficie(rs.getDouble("superficie"));
				reserva.setAparcamiento(rs.getBoolean("aparcamiento"));
				reserva.setPiscina(rs.getBoolean("piscina"));
				reserva.setWifi(rs.getBoolean("wifi"));
				String tipoEstado = rs.getString("estado");
				Estado estado = null;
				switch (tipoEstado) {
				case "disponible":
					estado = Estado.disponible;
					break;
				case "ocupado":
					estado = Estado.ocupado;
					break;
				case "limpieza":
					estado = Estado.limpieza;
					break;
				case "desperfecto":
					estado = Estado.desperfecto;
					break;
				default:
					break;
				}
				reserva.setEstado(estado);
				reservas.add(reserva);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reservas;
	}

	@Override
	public int deleteReserva(int id) {
		String query = ("DELETE FROM reservas WHERE id = ?");
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateReserva(Reserva reserva) {
		PreparedStatement ps = null;
		try {
			String query = ("UPDATE reservas SET precio=?,fechaInicio=?,fechaFin=?,estado=?,localizacion=?,superficie=?,aparcamiento=?,piscina=?,wifi=? WHERE id=?");
			ps = conn.prepareStatement(query);
			ps.setDouble(1, reserva.getPrecio());
			ps.setDate(2, reserva.getFechaInicio());
			ps.setDate(3, reserva.getFechaFin());
			Estado estado = reserva.getEstado();
			String estadoString = "";
			switch (estado) {
			case disponible:
				estadoString = "disponible";
				break;
			case ocupado:
				estadoString = "ocupado";
				break;
			case limpieza:
				estadoString = "limpieza";
				break;
			case desperfecto:
				estadoString = "desperfecto";
				break;
			default:
				break;
			}
			ps.setString(4, estadoString);
			ps.setString(5, reserva.getLocalizacion());
			ps.setDouble(6, reserva.getSuperficie());
			ps.setBoolean(7, reserva.getAparcamiento());
			ps.setBoolean(8, reserva.getPiscina());
			ps.setBoolean(9, reserva.getWifi());
			ps.setInt(10, reserva.getId());
		    ps.executeUpdate();
		    ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int insertReserva(Reserva reserva) {
		PreparedStatement ps = null;
		try {
			String query = "INSERT INTO reservas (precio,fechaInicio,fechaFin,estado,localizacion,superficie,aparcamiento,piscina,wifi) VALUES(?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setDouble(1, reserva.getPrecio());
			ps.setDate(2, reserva.getFechaInicio());
			ps.setDate(3, reserva.getFechaFin());
			Estado estado = reserva.getEstado();
			String estadoString = "";
			switch (estado) {
			case disponible:
				estadoString = "disponible";
				break;
			case ocupado:
				estadoString = "ocupado";
				break;
			case limpieza:
				estadoString = "limpieza";
				break;
			case desperfecto:
				estadoString = "desperfecto";
				break;
			default:
				break;
			}
			ps.setString(4, estadoString);
			ps.setString(5, reserva.getLocalizacion());
			ps.setDouble(6, reserva.getSuperficie());
			ps.setBoolean(7, reserva.getAparcamiento());
			ps.setBoolean(8, reserva.getPiscina());
			ps.setBoolean(9, reserva.getWifi());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Reserva getReserva(int id) {
		Reserva reserva = null;
		try {
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery ("SELECT * FROM reservas where id = " + id);
			while (rs.next()) {
				reserva = new Reserva();
				reserva.setId(rs.getInt("id"));
				reserva.setPrecio(rs.getDouble("precio"));
				reserva.setFechaInicio(rs.getDate("fechaInicio"));
				reserva.setFechaFin(rs.getDate("fechaFin"));
				reserva.setLocalizacion(rs.getString("localizacion"));
				reserva.setSuperficie(rs.getDouble("superficie"));
				reserva.setAparcamiento(rs.getBoolean("aparcamiento"));
				reserva.setPiscina(rs.getBoolean("piscina"));
				reserva.setWifi(rs.getBoolean("wifi"));
				String tipoEstado = rs.getString("estado");
				Estado estado = null;
				switch (tipoEstado) {
				case "disponible":
					estado = Estado.disponible;
					break;
				case "ocupado":
					estado = Estado.ocupado;
					break;
				case "limpieza":
					estado = Estado.limpieza;
					break;
				case "desperfecto":
					estado = Estado.desperfecto;
					break;
				default:
					break;
				}
				reserva.setEstado(estado);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reserva;
	}

	@Override
	public List<Integer> getReservado(String dniCliente) {
		List<Integer> reservas = new LinkedList<Integer>();
		int idReserva = 0;
		try {
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery ("SELECT * FROM reservado where dniCliente = " + "'" + dniCliente + "'");
			 while (rs.next()) {
				 idReserva = Integer.parseInt(rs.getString("idReserva"));
				 reservas.add(idReserva);
		    }
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reservas;
	}

	@Override
	public int deleteReservado(int id) {
		String query = ("DELETE FROM reservado WHERE id = ?");
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateEstadoReserva(int id, Estado estado) {
		PreparedStatement ps = null;
		try {
			String query = ("UPDATE reservas SET estado=? WHERE id=?");
			ps = conn.prepareStatement(query);
			String estadoString = "";
			switch (estado) {
			case disponible:
				estadoString = "disponible";
				break;
			case ocupado:
				estadoString = "ocupado";
				break;
			case limpieza:
				estadoString = "limpieza";
				break;
			case desperfecto:
				estadoString = "desperfecto";
				break;
			default:
				break;
			}
			ps.setString(1, estadoString);
			ps.setInt(2, id);
		    ps.executeUpdate();
		    ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int insertReservado(String dniCliente, int idReserva) {
		PreparedStatement ps = null;
		try {
			String query = "INSERT INTO reservado (dniCliente, idReserva) VALUES(?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, dniCliente);
			ps.setInt(2, idReserva);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int deleteReservadoByIdReserva(int idReserva) {
		String query = ("DELETE FROM reservado WHERE idReserva = ?");
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idReserva);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Cliente> getClientes() {
		Cliente cliente = null;
		List<Cliente> clientes = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery ("SELECT * FROM clientes");
			while (rs.next()) { 
				cliente = new Cliente();
				cliente.setDni(rs.getString("dni"));
				cliente.setNombre(rs.getString("nombre"));
				cliente.setApellidos(rs.getString("apellidos"));
				cliente.setTelefono(rs.getInt("telefono"));
				cliente.setEsVip(rs.getBoolean("vip"));
				cliente.setNumCuenta(rs.getInt("numcuenta"));
				cliente.setPassword(rs.getString("password"));
				cliente.setEmail(rs.getString("email"));
				clientes.add(cliente);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clientes;
	}

	@Override
	public List<Empleado> getEmpleados() {
		List<Empleado> empleados = new ArrayList<>();
		Empleado empleado = null;
		try {
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery ("SELECT * FROM empleados");
			while (rs.next()) { 
				empleado = new Empleado();
				empleado.setDni(rs.getString("dni"));
				empleado.setNombre(rs.getString("nombre"));
				empleado.setApellidos(rs.getString("apellidos"));
				empleado.setTelefono(rs.getInt("telefono"));
				empleado.setNumCuenta(rs.getInt("numcuenta"));
				empleado.setPassword(rs.getString("password"));
				empleado.setEmail(rs.getString("email"));
				empleado.setSueldo(rs.getDouble("sueldo"));
				String cargoString = rs.getString("cargo");
				Cargo cargo = null;
				switch (cargoString) {
				case "administración":
					cargo = Cargo.administración;
					break;
				case "conserjería":
					cargo = Cargo.conserjería;
					break;
				case "limpieza":
					cargo = Cargo.limpieza;
					break;
				default:
					break;
				}
				empleado.setCargo(cargo);
				empleados.add(empleado);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empleados;
	}

	@Override
	public TarjetaCredito getTarjeta(String dni) {
		TarjetaCredito tarjeta = null;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery ("SELECT * FROM tarjetacredito WHERE dniPropietario = " + "'" + dni + "'");
			while (rs.next()) { 
				tarjeta = new TarjetaCredito();
				tarjeta.setDni(rs.getString("dniPropietario"));
				tarjeta.setNumTarjeta(rs.getInt("numTarjeta"));
				tarjeta.setSaldo(rs.getDouble("saldo"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return tarjeta;
	}

	@Override
	public int deleteTarjeta(String dni) {
		String query = ("DELETE FROM tarjetacredito WHERE dniPropietario = ?");
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, dni);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateSaldo(String dni, double saldo) {
		PreparedStatement ps = null;
		try {
			String query = ("UPDATE tarjetacredito SET saldo=? WHERE dniPropietario=?");
			ps = conn.prepareStatement(query);
			ps.setDouble(1, saldo);
			ps.setString(2, dni);
		    ps.executeUpdate();
		    ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int insertTarjeta(TarjetaCredito tarjeta) {
		PreparedStatement ps = null;
		try {
			String query = "INSERT INTO tarjetacredito (dniPropietario, numTarjeta, saldo) VALUES(?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, tarjeta.getDni());
			ps.setInt(2, tarjeta.getNumTarjeta());
			ps.setDouble(3, tarjeta.getSaldo());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public TarjetaCredito getTarjetaByNum(int numTarjeta) {
		TarjetaCredito tarjeta = null;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery ("SELECT * FROM tarjetacredito WHERE numTarjeta = " + "'" + numTarjeta + "'");
			while (rs.next()) { 
				tarjeta = new TarjetaCredito();
				tarjeta.setDni(rs.getString("dniPropietario"));
				tarjeta.setNumTarjeta(rs.getInt("numTarjeta"));
				tarjeta.setSaldo(rs.getDouble("saldo"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return tarjeta;
	}

	@Override
	public int updateNumTarjetaCliente(int numTarjeta, String dni) {
		PreparedStatement ps = null;
		try {
			String query = ("UPDATE clientes SET numcuenta=? WHERE dni=?");
			ps = conn.prepareStatement(query);
			ps.setInt(1, numTarjeta);
			ps.setString(2, dni);
		    ps.executeUpdate();
		    ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateNumTarjetaEmpleado(int numTarjeta, String dni) {
		PreparedStatement ps = null;
		try {
			String query = ("UPDATE empleados SET numCuenta=? WHERE dni=?");
			ps = conn.prepareStatement(query);
			ps.setInt(1, numTarjeta);
			ps.setString(2, dni);
		    ps.executeUpdate();
		    ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
}