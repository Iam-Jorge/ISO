package Presentación;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.github.javafaker.Faker;

import Dominio.Cliente;
import Dominio.Estado;
import Persistencia.Agente;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;

public class IUControlClientes extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textFieldNombre;
	private JTextField textFieldApellidos;
	private JTextField textFieldEmail;
	private JTextField textFieldTelefono;
	private JCheckBox chckbxEsVip;
	private JTextField textFieldDni;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IUControlClientes frame = new IUControlClientes();
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
	public IUControlClientes() {
		setTitle("Consulta clientes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 551, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String col[] = {"Dni", "Nombre", "Apellidos", "Teléfono", "Vip", "Email"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int column = 0;
				int row = table.getSelectedRow();
				String value = null;
				try {
					value = table.getModel().getValueAt(row, column).toString();
				} catch (Exception e2) {
					value = "-1";
				}
				if (value.equals("-1")) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente");
				} else {
					Agente agente = null;
					try {
						agente = Agente.getAgente();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					Cliente cliente = agente.getCliente(value);
					textFieldDni.setText(cliente.getDni());
					textFieldNombre.setText(cliente.getNombre());
					textFieldApellidos.setText(cliente.getApellidos());
					textFieldEmail.setText(cliente.getEmail());
					textFieldTelefono.setText(String.valueOf(cliente.getTelefono()));
					chckbxEsVip.setSelected(cliente.isEsVip());
				}
			}
		});
		table.setBounds(23, 25, 1, 1);
		contentPane.add(table);
		table.setModel(tableModel);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(21, 22, 483, 185);
		contentPane.add(scrollPane);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.setToolTipText("Borrar el cliente seleccionado");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int column = 0;
				int row = table.getSelectedRow();
				String value = null;
				try {
					value = table.getModel().getValueAt(row, column).toString();
				} catch (Exception e2) {
					value = "-1";
				}
				if (value.equals("-1")) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente");
				} else {
					Agente agente = null;
					try {
						agente = Agente.getAgente();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					int res = 0;
					int reply = JOptionPane.showConfirmDialog(null, "Se eliminará el cliente con dni " + value, "Eliminar reserva", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						if (res == 0) {
							JOptionPane.showMessageDialog(null, "Se ha eliminado el cliente con dni " + value);
							agente.deleteCliente(value);
							actualizarTabla(tableModel);
						} else {
							JOptionPane.showMessageDialog(null, "No fue posible eliminar el cliente");
						}
					} else {
					    JOptionPane.showMessageDialog(null, "Operación cancelada");
					}
				}
			}
		});
		btnBorrar.setBounds(316, 337, 89, 23);
		contentPane.add(btnBorrar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVolver.setBounds(415, 337, 89, 23);
		contentPane.add(btnVolver);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setToolTipText("Nombre");
		textFieldNombre.setBounds(21, 244, 164, 20);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setBounds(195, 247, 68, 14);
		contentPane.add(lblNewLabel);
		
		textFieldApellidos = new JTextField();
		textFieldApellidos.setToolTipText("Apellidos");
		textFieldApellidos.setBounds(21, 275, 164, 20);
		contentPane.add(textFieldApellidos);
		textFieldApellidos.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Apellidos");
		lblNewLabel_1.setBounds(195, 278, 68, 14);
		contentPane.add(lblNewLabel_1);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setToolTipText("Email");
		textFieldEmail.setBounds(265, 275, 166, 20);
		contentPane.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setToolTipText("Teléfono");
		textFieldTelefono.setBounds(265, 245, 166, 20);
		contentPane.add(textFieldTelefono);
		textFieldTelefono.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Teléfono");
		lblNewLabel_2.setBounds(441, 251, 84, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Email");
		lblNewLabel_3.setBounds(445, 278, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		chckbxEsVip = new JCheckBox("Es vip");
		chckbxEsVip.setBounds(298, 302, 107, 23);
		contentPane.add(chckbxEsVip);
		
		textFieldDni = new JTextField();
		textFieldDni.setToolTipText("DNI");
		textFieldDni.setBounds(21, 213, 164, 20);
		contentPane.add(textFieldDni);
		textFieldDni.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("DNI");
		lblNewLabel_4.setBounds(195, 218, 46, 14);
		contentPane.add(lblNewLabel_4);
		
		JButton btnGenerarDni = new JButton("Generar cliente");
		btnGenerarDni.setToolTipText("Genera de forma aleatoria los datos de un cliente");
		btnGenerarDni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldDni.setText(generarDNI());
				Faker faker = new Faker();
				String nombre = faker.name().firstName();
				String apellidos = faker.name().firstName() + " " + faker.name().lastName();
				textFieldNombre.setText(nombre);
				textFieldApellidos.setText(apellidos);
				textFieldTelefono.setText(generarTelefono());
				textFieldEmail.setText(generarEmail(nombre, apellidos));
			}
		});
		btnGenerarDni.setBounds(265, 212, 166, 23);
		contentPane.add(btnGenerarDni);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setToolTipText("Modifica un cliente existente");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Agente agente = null;
				try {
					agente = Agente.getAgente();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				Cliente cliente = agente.getCliente(textFieldDni.getText());
				cliente.setNombre(textFieldNombre.getText());
				cliente.setApellidos(textFieldApellidos.getText());
				cliente.setTelefono(Integer.parseInt(textFieldTelefono.getText()));
				cliente.setEmail(textFieldEmail.getText());
				cliente.setEsVip(chckbxEsVip.isSelected());
				cliente.setPassword(passwordField.getText());
				agente.updateClienteCompleto(cliente);
				actualizarTabla(tableModel);
				JOptionPane.showMessageDialog(null, "Actualizado el cliente con dni " + textFieldDni.getText());
			}
		});
		btnModificar.setBounds(217, 337, 89, 23);
		contentPane.add(btnModificar);
		
		JButton btnAñadir = new JButton("Añadir");
		btnAñadir.setToolTipText("Añade un cliente nuevo");
		btnAñadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cliente cliente = new Cliente();
				cliente.setDni(textFieldDni.getText());
				cliente.setNombre(textFieldNombre.getText());
				cliente.setApellidos(textFieldApellidos.getText());
				cliente.setTelefono(Integer.parseInt(textFieldTelefono.getText()));
				cliente.setEmail(textFieldEmail.getText());
				cliente.setEsVip(chckbxEsVip.isSelected());
				cliente.setPassword(passwordField.getText());
				Agente agente = null;
				try {
					agente = Agente.getAgente();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					Cliente clienteExistente = agente.getCliente(textFieldDni.getText());
					if (clienteExistente == null) {
						agente.insertCliente(cliente);
					} else {
						JOptionPane.showMessageDialog(null, "El cliente ya existe");
					}
				} catch (Exception e1) {
					
				}
				actualizarTabla(tableModel);
				
				textFieldNombre.setText("");
				textFieldApellidos.setText("");
				textFieldDni.setText("");
				textFieldTelefono.setText("");
				passwordField.setText("");
				textFieldEmail.setText("");
				chckbxEsVip.setSelected(false);
			}
		});
		btnAñadir.setBounds(118, 337, 89, 23);
		contentPane.add(btnAñadir);
		
		passwordField = new JPasswordField();
		passwordField.setToolTipText("Contraseña");
		passwordField.setBounds(21, 306, 164, 20);
		contentPane.add(passwordField);
		
		JLabel lblNewLabel_5 = new JLabel("Contraseña");
		lblNewLabel_5.setBounds(195, 308, 68, 14);
		contentPane.add(lblNewLabel_5);
		actualizarTabla(tableModel);
	}

	private void actualizarTabla(DefaultTableModel tableModel) {
		Agente agente = null;
		try {
			agente = Agente.getAgente();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		List<Cliente> clientes = agente.getClientes();
		tableModel.setRowCount(0);
		for (int i = 0; i < clientes.size(); i++) {
			String dni = clientes.get(i).getDni();
			String nombre = clientes.get(i).getNombre();
			String apellidos = clientes.get(i).getApellidos();
			int telefono = clientes.get(i).getTelefono();
			Boolean vip = clientes.get(i).isEsVip();
			String email = clientes.get(i).getEmail();
			Object[] data = {dni, nombre, apellidos, telefono, vip, email};
			tableModel.addRow(data);
		}		
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
