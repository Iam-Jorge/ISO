package Presentación;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Dominio.Cliente;
import Dominio.Empleado;
import Persistencia.Agente;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IUDatos extends JFrame {

	private JPanel contentPane;
	private static Cliente clienteActual = null;
	private static Empleado empleadoActual = null;
	private JTextField textFieldNombre;
	private JTextField textFieldApellidos;
	private JTextField textFieldTelefono;
	private JTextField textFieldDNI;
	private JPasswordField passwordField;
	private JTextField textFieldEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IUDatos frame = new IUDatos(clienteActual, empleadoActual);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param clienteActual 
	 */
	public IUDatos(Cliente cliente, Empleado empleado) {
		clienteActual = cliente;
		empleadoActual = empleado;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 451, 235);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setEditable(false);
		textFieldNombre.setToolTipText("Nombre");
		textFieldNombre.setBounds(10, 42, 175, 20);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		try {
			if (clienteActual != null) {
				textFieldNombre.setText(clienteActual.getNombre());
			} else {
				textFieldNombre.setText(empleadoActual.getNombre());
			}
		} catch (Exception e1) {
		}
		
		textFieldApellidos = new JTextField();
		textFieldApellidos.setEditable(false);
		textFieldApellidos.setToolTipText("Apellidos");
		textFieldApellidos.setBounds(10, 73, 175, 20);
		contentPane.add(textFieldApellidos);
		textFieldApellidos.setColumns(10);
		try {
			if (clienteActual != null) {
				textFieldApellidos.setText(clienteActual.getApellidos());
			} else {
				textFieldApellidos.setText(empleadoActual.getApellidos());
			}
		} catch (Exception e1) {
		}
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setToolTipText("Teléfono");
		textFieldTelefono.setBounds(10, 134, 175, 20);
		contentPane.add(textFieldTelefono);
		textFieldTelefono.setColumns(10);
		try {
			if (clienteActual != null) {
				textFieldTelefono.setText(String.valueOf(clienteActual.getTelefono()));
			} else {
				textFieldTelefono.setText(String.valueOf(empleadoActual.getTelefono()));
			}
		} catch (Exception e1) {
		}
		
		textFieldDNI = new JTextField();
		textFieldDNI.setEditable(false);
		textFieldDNI.setToolTipText("DNI");
		textFieldDNI.setBounds(10, 11, 175, 20);
		contentPane.add(textFieldDNI);
		textFieldDNI.setColumns(10);
		try {
			if (clienteActual != null) {
				textFieldDNI.setText(clienteActual.getDni());
			} else {
				textFieldDNI.setText(empleadoActual.getDni());
			}
		} catch (Exception e1) {
		}
		
		textFieldEmail = new JTextField();
		textFieldEmail.setToolTipText("Email");
		textFieldEmail.setBounds(10, 103, 175, 20);
		contentPane.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		try {
			if (clienteActual != null) {
				textFieldEmail.setText(clienteActual.getEmail());
			} else {
				textFieldEmail.setText(empleadoActual.getEmail());
			}
		} catch (Exception e1) {
		}
		
		passwordField = new JPasswordField();
		passwordField.setToolTipText("Contraseña");
		passwordField.setBounds(10, 165, 175, 20);
		contentPane.add(passwordField);
		try {
			if (clienteActual != null) {
				passwordField.setText(clienteActual.getPassword());
			} else {
				passwordField.setText(empleadoActual.getPassword());
			}
		} catch (Exception e1) {
		}
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVolver.setBounds(336, 164, 89, 23);
		contentPane.add(btnVolver);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Agente agente = null;
				try {
					agente = Agente.getAgente();
					if (clienteActual != null) {
						clienteActual.setEmail(textFieldEmail.getText());
						clienteActual.setTelefono(Integer.parseInt(textFieldTelefono.getText()));
						clienteActual.setPassword(passwordField.getText());
						int res = agente.updateCliente(clienteActual);
						clienteActual = agente.getCliente(textFieldDNI.getText());
						if (res == 0) {
							JOptionPane.showMessageDialog(null, "Datos actualizados con exito","", JOptionPane.INFORMATION_MESSAGE);
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "Error al actualizar los datos","", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						empleadoActual.setEmail(textFieldEmail.getText());
						empleadoActual.setTelefono(Integer.parseInt(textFieldTelefono.getText()));
						empleadoActual.setPassword(passwordField.getText());
						int res = agente.updateEmpleado(empleadoActual);
						empleadoActual = agente.getEmpleado(textFieldDNI.getText());
						if (res == 0) {
							JOptionPane.showMessageDialog(null, "Datos actualizados con exito","", JOptionPane.INFORMATION_MESSAGE);
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "Error al actualizar los datos","", JOptionPane.ERROR_MESSAGE);
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnModificar.setBounds(336, 133, 89, 23);
		contentPane.add(btnModificar);
		
		JLabel lblNewLabel = new JLabel("DNI");
		lblNewLabel.setBounds(195, 14, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre");
		lblNewLabel_1.setBounds(195, 45, 66, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Apellidos");
		lblNewLabel_2.setBounds(195, 76, 66, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Email");
		lblNewLabel_3.setBounds(195, 106, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Teléfono");
		lblNewLabel_4.setBounds(195, 137, 66, 14);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Contraseña");
		lblNewLabel_5.setBounds(195, 168, 66, 14);
		contentPane.add(lblNewLabel_5);
		
	}
}
