package Presentación;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Dominio.Cargo;
import Dominio.Cliente;
import Dominio.Empleado;
import Persistencia.Agente;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class IULogin extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldDNI;
	private JPasswordField passwordField;
	private Agente agente = null;
	// Distinguir entre clientes y empleados (0 = cliente, 1 = empleado)
	private int tipoLogin = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IULogin frame = new IULogin();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public IULogin() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 451, 177);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenuItem mntmCliente = new JMenuItem("Cliente");
		mntmCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTitle("Login clientes");
				tipoLogin = 0;
			}
		});
		menuBar.add(mntmCliente);

		JMenuItem mntmEmpleado = new JMenuItem("Empleado");
		mntmEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTitle("Login empleados");
				tipoLogin = 1;
			}
		});
		menuBar.add(mntmEmpleado);

		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		menuBar.add(mntmSalir);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDNI = new JLabel("DNI");
		lblDNI.setToolTipText("DNI");
		lblDNI.setBounds(174, 14, 46, 14);
		contentPane.add(lblDNI);

		JLabel lblContraseña = new JLabel("Contraseña");
		lblContraseña.setToolTipText("Conraseña");
		lblContraseña.setBounds(174, 45, 77, 14);
		contentPane.add(lblContraseña);

		textFieldDNI = new JTextField();
		textFieldDNI.setToolTipText("DNI");
		textFieldDNI.setBounds(25, 11, 140, 20);
		contentPane.add(textFieldDNI);
		textFieldDNI.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setToolTipText("Contraseña");
		passwordField.setBounds(25, 42, 140, 20);
		contentPane.add(passwordField);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pattern pat = Pattern.compile("[0-9]{7,8}[A-Z a-z]");
				Matcher mat = pat.matcher(textFieldDNI.getText());
				Agente agente = null;
				if (!mat.matches()) {
					JOptionPane.showMessageDialog(null, "El formato del DNI introducido es incorrecto","Error", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						agente = Agente.getAgente();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} 
				switch (tipoLogin) {
				case 0:
					// Login cliente
					Cliente cliente = null;
					try {
						cliente = agente.getCliente(textFieldDNI.getText());
					} catch (Exception e1) {
					}
					if (cliente == null) {
						JOptionPane.showMessageDialog(null, "El el DNI introducido no existe","Error", JOptionPane.ERROR_MESSAGE);
					} else {
						char[] pass = cliente.getPassword().toCharArray();
						if (!Arrays.equals(pass, passwordField.getPassword())) {
							JOptionPane.showMessageDialog(null, "La contraseña es incorrecta","Error", JOptionPane.ERROR_MESSAGE);
						} else {
							IUCliente IUCliente = new IUCliente(cliente);
							IUCliente.setLocationRelativeTo(null);
							IUCliente.setVisible(true);
							dispose();
						}
					}
					break;
				case 1:
					Empleado empleado = agente.getEmpleado(textFieldDNI.getText());
					if (empleado == null) {
						JOptionPane.showMessageDialog(null, "El el DNI introducido no existe","Error", JOptionPane.ERROR_MESSAGE);
					} else {
						// Login administrador
						if (empleado.getCargo().equals(Cargo.administración)) {
							char[] pass = empleado.getPassword().toCharArray();
							if (!Arrays.equals(pass, passwordField.getPassword())) {
								JOptionPane.showMessageDialog(null, "La contraseña es incorrecta","Error", JOptionPane.ERROR_MESSAGE);
							} else {
								IUAdministrador IUAdministrador = new IUAdministrador(empleado);
								IUAdministrador.setLocationRelativeTo(null);
								IUAdministrador.setVisible(true);
								dispose();
							}
							// Login empleado
						} else {
							char[] pass = empleado.getPassword().toCharArray();
							if (!Arrays.equals(pass, passwordField.getPassword())) {
								JOptionPane.showMessageDialog(null, "La contraseña es incorrecta","Error", JOptionPane.ERROR_MESSAGE);
							} else {
								IUEmpleado IUEmpleado = new IUEmpleado(empleado);
								IUEmpleado.setLocationRelativeTo(null);
								IUEmpleado.setVisible(true);
								dispose();
							}
						}
					}
					break;
				default:
					break;
				}
			}
		});
		btnLogin.setBounds(321, 41, 89, 23);
		contentPane.add(btnLogin);

		JButton btnRegistro = new JButton("Registro");
		btnRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IURegistro IURegistro = new IURegistro();
				IURegistro.setLocationRelativeTo(null);
				IURegistro.setVisible(true);
				dispose();
			}
		});
		btnRegistro.setBounds(321, 75, 89, 23);
		contentPane.add(btnRegistro);

		JLabel lblNewLabel = new JLabel("¿Aún no estás registrado?");
		lblNewLabel.setBounds(155, 79, 156, 14);
		contentPane.add(lblNewLabel);
	}
}
