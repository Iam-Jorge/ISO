package Presentación;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Dominio.Cliente;
import Dominio.Empleado;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IUAdministrador extends JFrame {

	private JPanel contentPane;
	private static Empleado administradorActual = null;
	private static Cliente clienteActual = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IUAdministrador frame = new IUAdministrador(administradorActual);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public IUAdministrador(Empleado administrador) {
		administradorActual = administrador;
		setTitle("Administración");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 609, 315);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenuItem mntmMisDatos = new JMenuItem("Mis datos");
		mntmMisDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IUDatos IUDatos = new IUDatos(clienteActual, administradorActual);
				IUDatos.setLocationRelativeTo(null);
				IUDatos.setVisible(true);
			}
		});
		menuBar.add(mntmMisDatos);
		
		JMenuItem mntmClientes = new JMenuItem("Clientes");
		mntmClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IUControlClientes IUConsultarClientes = new IUControlClientes();
				IUConsultarClientes.setLocationRelativeTo(null);
				IUConsultarClientes.setVisible(true);
			}
		});
		menuBar.add(mntmClientes);
		
		JMenuItem mntmReservas = new JMenuItem("Reservas");
		mntmReservas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IUControlReservas IUReservas = new IUControlReservas();
				IUReservas.setLocationRelativeTo(null);
				IUReservas.setVisible(true);
			}
		});
		menuBar.add(mntmReservas);
		
		JMenuItem mntmEmpleados = new JMenuItem("Empleados");
		mntmEmpleados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IUControlEmpleados IUConsultarEmpleados = new IUControlEmpleados();
				IUConsultarEmpleados.setLocationRelativeTo(null);
				IUConsultarEmpleados.setVisible(true);
			}
		});
		menuBar.add(mntmEmpleados);
		
		JMenuItem mntmTransacciones = new JMenuItem("Transacciones");
		mntmTransacciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IUTransacciones IUTransacciones = new IUTransacciones(clienteActual, administradorActual);
				IUTransacciones.setLocationRelativeTo(null);
				IUTransacciones.setVisible(true);
			}
		});
		menuBar.add(mntmTransacciones);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IULogin IULogin = new IULogin();
				IULogin.setLocationRelativeTo(null);
				IULogin.setVisible(true);
				dispose();
			}
		});
		menuBar.add(mntmLogout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

}
