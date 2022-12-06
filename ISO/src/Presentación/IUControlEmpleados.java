package Presentación;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import com.github.javafaker.Faker;
import Dominio.Cargo;
import Dominio.Cliente;
import Dominio.Empleado;
import Persistencia.Agente;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class IUControlEmpleados extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textFieldNombre;
	private JTextField textFieldApellidos;
	private JTextField textFieldEmail;
	private JTextField textFieldTelefono;
	private JTextField textFieldDni;
	private JPasswordField passwordField;
	private JTextField textFieldSueldo;
	private static JComboBox comboBoxCargo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IUControlEmpleados frame = new IUControlEmpleados();
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
	public IUControlEmpleados() {
		setTitle("Consulta empleados");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 551, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		String col[] = {"Dni", "Nombre", "Apellidos", "Teléfono", "Email", "Sueldo", "Cargo"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int column = 0;
				int row = table.getSelectedRow();
				String value = null;
				Empleado empleado = null;
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
						empleado = agente.getEmpleado(value);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					textFieldDni.setText(empleado.getDni());
					textFieldNombre.setText(empleado.getNombre());
					textFieldApellidos.setText(empleado.getApellidos());
					textFieldEmail.setText(empleado.getEmail());
					textFieldTelefono.setText(String.valueOf(empleado.getTelefono()));
					textFieldSueldo.setText(String.valueOf(empleado.getSueldo()));
					Cargo cargo = empleado.getCargo();
					int indexComboBox = 1;
					switch (cargo) {
					case administración:
						indexComboBox = 0;
						break;
					case limpieza:
						indexComboBox = 1;
						break;
					case conserjería:
						indexComboBox = 2;
						break;
					default:
						break;
					}
					comboBoxCargo.setSelectedIndex(indexComboBox);
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
							textFieldNombre.setText("");
							textFieldApellidos.setText("");
							textFieldDni.setText("");
							textFieldTelefono.setText("");
							passwordField.setText("");
							textFieldEmail.setText("");
							textFieldSueldo.setText("");
							comboBoxCargo.setSelectedIndex(-1);
						} else {
							JOptionPane.showMessageDialog(null, "No fue posible eliminar el cliente");
						}
					} else {
					    JOptionPane.showMessageDialog(null, "Operación cancelada");
					}
				}
			}
		});
		btnBorrar.setBounds(316, 369, 89, 23);
		contentPane.add(btnBorrar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVolver.setBounds(415, 369, 89, 23);
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
		
		textFieldDni = new JTextField();
		textFieldDni.setToolTipText("DNI");
		textFieldDni.setBounds(21, 213, 164, 20);
		contentPane.add(textFieldDni);
		textFieldDni.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("DNI");
		lblNewLabel_4.setBounds(195, 218, 46, 14);
		contentPane.add(lblNewLabel_4);
		
		JButton btnGenerarDni = new JButton("Generar empleado");
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
		btnModificar.setToolTipText("Modifique un empleado existente");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Agente agente = null;
				try {
					agente = Agente.getAgente();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				Empleado empleado = agente.getEmpleado(textFieldDni.getText());
				empleado.setNombre(textFieldNombre.getText());
				empleado.setApellidos(textFieldApellidos.getText());
				empleado.setTelefono(Integer.parseInt(textFieldTelefono.getText()));
				empleado.setEmail(textFieldEmail.getText());
				empleado.setPassword(passwordField.getText());
				empleado.setSueldo(Double.valueOf(textFieldSueldo.getText()));
				String cargoString = comboBoxCargo.getSelectedItem().toString();
				Cargo cargo = null;
				switch (cargoString) {
				case "Administración":
					cargo = Cargo.administración;
					break;
				case "Limpieza":
					cargo = Cargo.limpieza;
					break;
				case "Conserjería":
					cargo = Cargo.conserjería;
					break;
				default:
					break;
				}
				empleado.setCargo(cargo);
				agente.updateEmpleadoCompleto(empleado);
				actualizarTabla(tableModel);
				JOptionPane.showMessageDialog(null, "Actualizado el empleado con dni " + textFieldDni.getText());
				textFieldNombre.setText("");
				textFieldApellidos.setText("");
				textFieldDni.setText("");
				textFieldTelefono.setText("");
				passwordField.setText("");
				textFieldEmail.setText("");
				textFieldSueldo.setText("");
				comboBoxCargo.setSelectedIndex(-1);
			}
		});
		btnModificar.setBounds(217, 369, 89, 23);
		contentPane.add(btnModificar);
		
		JButton btnAñadir = new JButton("Añadir");
		btnAñadir.setToolTipText("Añade un empleado nuevo");
		btnAñadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Empleado empleado= new Empleado();
				empleado.setDni(textFieldDni.getText());
				empleado.setNombre(textFieldNombre.getText());
				empleado.setApellidos(textFieldApellidos.getText());
				empleado.setTelefono(Integer.parseInt(textFieldTelefono.getText()));
				empleado.setEmail(textFieldEmail.getText());
				empleado.setPassword(passwordField.getText());
				empleado.setSueldo(Double.parseDouble(textFieldSueldo.getText()));
				String cargoString = comboBoxCargo.getSelectedItem().toString();
				Cargo cargo = null;
				switch (cargoString) {
				case "Administración":
					cargo = Cargo.administración;
					break;
				case "Limpieza":
					cargo = Cargo.limpieza;
					break;
				case "Conserjería":
					cargo = Cargo.conserjería;
					break;
				default:
					break;
				}
				empleado.setCargo(cargo);
				Agente agente = null;
				try {
					agente = Agente.getAgente();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					Empleado empleadoRegistrado = agente.getEmpleado(textFieldDni.getText());
					if (empleadoRegistrado == null) {
						agente.insertEmpleadoCompleto(empleado);
					} else {
						JOptionPane.showMessageDialog(null, "El empleado ya existe");
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
				textFieldSueldo.setText("");
				comboBoxCargo.setSelectedIndex(-1);
			}
		});
		btnAñadir.setBounds(117, 369, 89, 23);
		contentPane.add(btnAñadir);
		
		passwordField = new JPasswordField();
		passwordField.setToolTipText("Contraseña");
		passwordField.setBounds(21, 306, 164, 20);
		contentPane.add(passwordField);
		
		JLabel lblNewLabel_5 = new JLabel("Contraseña");
		lblNewLabel_5.setBounds(195, 308, 68, 14);
		contentPane.add(lblNewLabel_5);
		
		textFieldSueldo = new JTextField();
		textFieldSueldo.setToolTipText("Sueldo");
		textFieldSueldo.setBounds(265, 303, 166, 20);
		contentPane.add(textFieldSueldo);
		textFieldSueldo.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Sueldo");
		lblNewLabel_6.setBounds(441, 308, 59, 14);
		contentPane.add(lblNewLabel_6);
		
		comboBoxCargo = new JComboBox();
		comboBoxCargo.setModel(new DefaultComboBoxModel(new String[] {"Administración", "Limpieza", "Conserjería"}));
		comboBoxCargo.setBounds(21, 337, 164, 22);
		contentPane.add(comboBoxCargo);
		
		JLabel lblNewLabel_7 = new JLabel("Cargo");
		lblNewLabel_7.setBounds(195, 341, 46, 14);
		contentPane.add(lblNewLabel_7);
		actualizarTabla(tableModel);
	}

	private void actualizarTabla(DefaultTableModel tableModel) {
		Agente agente = null;
		try {
			agente = Agente.getAgente();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		List<Empleado> empleados = agente.getEmpleados();
		tableModel.setRowCount(0);
		for (int i = 0; i < empleados.size(); i++) {
			String dni = empleados.get(i).getDni();
			String nombre = empleados.get(i).getNombre();
			String apellidos = empleados.get(i).getApellidos();
			int telefono = empleados.get(i).getTelefono();
			String email = empleados.get(i).getEmail();
			double sueldo = empleados.get(i).getSueldo();
			Cargo cargo = empleados.get(i).getCargo();
			String cargoString = null;
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
			Object[] data = {dni, nombre, apellidos, telefono, email, sueldo, cargoString};
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
