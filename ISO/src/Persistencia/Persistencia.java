package Persistencia;

import java.util.List;

import Dominio.Cliente;
import Dominio.Empleado;
import Dominio.Estado;
import Dominio.Reserva;
import Dominio.TarjetaCredito;

public interface Persistencia {
	
	// Queries clientes
	List<Cliente> getClientes();
	Cliente getCliente(String dni);
	int insertCliente(Cliente cliente);
	int updateCliente(Cliente cliente);
	int updateClienteCompleto(Cliente cliente);
	int deleteCliente(String dni);
	int updateNumTarjetaCliente(int numTarjeta, String dni);
	
	//Quieries Empledos
	Empleado getEmpleado(String dni);
	List<Empleado> getEmpleados();
	int insertEmpleado(Empleado empleado);
	int insertEmpleadoCompleto(Empleado empleado);
	int updateEmpleado(Empleado empleado);
	int deleteEmpleado(String dni);
	int updateNumTarjetaEmpleado(int numTarjeta, String dni);
	
	//Queries Reservas
	List<Reserva> getReservas();
	Reserva getReserva(int id);
	int insertReserva(Reserva reserva);
	int updateReserva(Reserva reserva);
	int deleteReserva(int id);
	int updateEstadoReserva(int id, Estado estado);
	
	//Queries Reservado
	List<Integer> getReservado(String dniCliente);
	int deleteReservado(int id);
	int deleteReservadoByIdReserva(int idReserva);
	int insertReservado(String dniCliente, int idReserva);
	
	//Queries Tarjeta de crédito
	TarjetaCredito getTarjeta(String dni);
	TarjetaCredito getTarjetaByNum(int numTarjeta);
	int insertTarjeta(TarjetaCredito tarjeta);
	int deleteTarjeta(String dni);
	int updateSaldo(String dni, double saldo);
}
