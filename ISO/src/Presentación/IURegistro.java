package Presentación;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.javafaker.Faker;

import Dominio.Cliente;
import Persistencia.Agente;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class IURegistro extends JFrame {

	private JPanel contentPane;
	private JButton btnAutoGenerar;
	private JButton btnLimpiar;
	private JButton btnVolver;
	private JButton btnRegistrarse;
	private JTextField textFieldEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IURegistro frame = new IURegistro();
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
	public IURegistro() {
		setTitle("Registro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 480, 239);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setBounds(173, 11, 65, 14);
		contentPane.add(lblNewLabel);

		JTextField textFieldNombre = new JTextField();
		textFieldNombre.setToolTipText("Nombre");
		textFieldNombre.setBounds(10, 8, 153, 20);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Apellidos");
		lblNewLabel_1.setBounds(173, 42, 78, 14);
		contentPane.add(lblNewLabel_1);

		JTextField textFieldApellidos = new JTextField();
		textFieldApellidos.setToolTipText("Apellidos");
		textFieldApellidos.setBounds(10, 39, 153, 20);
		contentPane.add(textFieldApellidos);
		textFieldApellidos.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Teléfono");
		lblNewLabel_2.setBounds(173, 76, 68, 14);
		contentPane.add(lblNewLabel_2);

		JTextField textFieldTelefono = new JTextField();
		textFieldTelefono.setToolTipText("Teléfono");
		textFieldTelefono.setBounds(10, 73, 153, 20);
		contentPane.add(textFieldTelefono);
		textFieldTelefono.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("DNI");
		lblNewLabel_3.setBounds(173, 141, 46, 14);
		contentPane.add(lblNewLabel_3);

		JTextField textFieldDNI = new JTextField();
		textFieldDNI.setToolTipText("DNI");
		textFieldDNI.setBounds(10, 138, 153, 20);
		contentPane.add(textFieldDNI);
		textFieldDNI.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Contraseña");
		lblNewLabel_4.setBounds(173, 172, 72, 14);
		contentPane.add(lblNewLabel_4);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setToolTipText("Contraseña");
		passwordField.setBounds(10, 169, 153, 20);
		contentPane.add(passwordField);

		btnAutoGenerar = new JButton("Generar");
		btnAutoGenerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldDNI.setText(generarDNI());
				// generación de nombre y apellidos
				Faker faker = new Faker();
				String nombre = faker.name().firstName();
				String apellidos = faker.name().firstName() + " " + faker.name().lastName();
				textFieldNombre.setText(nombre);
				textFieldApellidos.setText(apellidos);
				textFieldTelefono.setText(generarTelefono());
				textFieldEmail.setText(generarEmail(nombre, apellidos));
			}
		});
		btnAutoGenerar.setToolTipText("Rellena automáticamente todos los campos");
		btnAutoGenerar.setBounds(337, 21, 106, 23);
		contentPane.add(btnAutoGenerar);

		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldNombre.setText("");
				textFieldApellidos.setText("");
				textFieldDNI.setText("");
				textFieldTelefono.setText("");
				passwordField.setText("");
				textFieldEmail.setText("");
			}
		});
		btnLimpiar.setToolTipText("Limpia todos los campos");
		btnLimpiar.setBounds(337, 55, 106, 23);
		contentPane.add(btnLimpiar);

		btnVolver = new JButton("Login");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IULogin IULogin = new IULogin();
				IULogin.setLocationRelativeTo(null);
				IULogin.setVisible(true);
				dispose();
			}
		});
		btnVolver.setToolTipText("Volver a la interfaz de login");
		btnVolver.setBounds(337, 89, 106, 23);
		contentPane.add(btnVolver);

		btnRegistrarse = new JButton("Registro");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Agente agente = null;
				try {
					agente = Agente.getAgente();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if(textFieldNombre.getText().equals("") || textFieldApellidos.getText().equals("") || textFieldDNI.getText().equals("")
						|| textFieldTelefono.getText().equals("") || passwordField.getText().equals("") || textFieldEmail.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos","Error", JOptionPane.ERROR_MESSAGE);
				} else {
					Cliente cliente = new Cliente();
					cliente.setDni(textFieldDNI.getText());
					cliente.setNombre(textFieldNombre.getText());
					cliente.setApellidos(textFieldApellidos.getText());
					cliente.setTelefono(Integer.parseInt(textFieldTelefono.getText()));
					cliente.setPassword(passwordField.getText());
					cliente.setEmail(textFieldEmail.getText());

					textFieldNombre.setText("");
					textFieldApellidos.setText("");
					textFieldDNI.setText("");
					textFieldTelefono.setText("");
					passwordField.setText("");
					textFieldEmail.setText("");

					agente.insertCliente(cliente);
					JOptionPane.showMessageDialog(null, "Usuario registrado correctamente","Exito", JOptionPane.INFORMATION_MESSAGE);
					IUCliente IUCliente =  new IUCliente(cliente);
					IUCliente.setLocationRelativeTo(null);
					IUCliente.setVisible(true);
					dispose();
				}
				
			}
		});
		btnRegistrarse.setToolTipText("Registrar nuevo cliente");
		btnRegistrarse.setBounds(337, 123, 106, 23);
		contentPane.add(btnRegistrarse);

		textFieldEmail = new JTextField();
		textFieldEmail.setToolTipText("Email");
		textFieldEmail.setBounds(10, 104, 153, 20);
		contentPane.add(textFieldEmail);
		textFieldEmail.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Email");
		lblNewLabel_5.setBounds(173, 110, 46, 14);
		contentPane.add(lblNewLabel_5);
	}

	protected String generarEmail(String nombre, String apellidos) {
		String email = "";
		String[] parts = apellidos.split(" ");
		email += nombre;
		email += parts[0].charAt(0);
		email += parts[1].charAt(0);
		email += "@gmail.com";
		return email;
	}

	protected String generarTelefono() {
		String telefono = "6";
		Random r = new Random();
		int min = 0;
		int max = 9;
		for (int i = 0; i < 8; i++) {
			int n = r.nextInt(max - min + 1) + min;
			telefono += Integer.toString(n);
		}
		return telefono;
	}

	protected String generarDNI() {
		String dni = "";
		Random r = new Random();
		int min = 0;
		int max = 9;
		for (int i = 0; i < 8; i++) {
			int n = r.nextInt(max - min + 1) + min;
			dni += Integer.toString(n);
		}
		int dniNumerico = Integer.parseInt(dni);
		//El string de letras es fijo y deberían de ir en este orden
		String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
		//La letra correspondiente será el resto de la división del número del DNI entre las 23 posibilidades que hay
		char letra = letras.charAt(dniNumerico % letras.length());
		String dniCompleto = "" + dni + letra;
		return dniCompleto;
	}
}
