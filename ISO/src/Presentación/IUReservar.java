package Presentación;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Dominio.Cliente;
import Dominio.Estado;
import Dominio.Reserva;
import Persistencia.Agente;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class IUReservar extends JFrame {

	private JPanel contentPane;
	private static List<Reserva> reservas = null;
	private JTable table;
	private JTextField textFieldSeleccion;
	private JLabel lblNewLabel;
	private static Cliente clienteActual = null;
	private JComboBox comboBoxFechaInicioDia;
	private JComboBox comboBoxFechaInicioMes;
	private JComboBox comboBoxFechaInicioAño;
	private JComboBox comboBoxFechaFinDia;
	private JComboBox comboBoxFechaFinMes;
	private JComboBox comboBoxFechaFinAño;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				IUReservar frame = new IUReservar(clienteActual);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public IUReservar(Cliente cliente) {
		clienteActual = cliente;
		setTitle("Reservas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 707, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String col[] = {"Id", "Precio", "Fecha inicio", "Fecha fin", "Estado", "Localización", "Superficie", "Aparcamiento", "Piscina", "Wifi"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int column = 0;
				int row = table.getSelectedRow();
				String value = table.getModel().getValueAt(row, column).toString();
				textFieldSeleccion.setText(value);
				if (Integer.valueOf(value) == -1) {
					JOptionPane.showMessageDialog(null, "Debes seleccionar una reserva antes");
				} else {
					Agente agente = null;
					Reserva reserva = null;
					try {
						agente = Agente.getAgente();
						reserva = agente.getReserva(Integer.valueOf(value));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
			}
		});
		table.setBounds(22, 22, 388, 194);
		contentPane.add(table);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVolver.setBounds(592, 209, 89, 23);
		contentPane.add(btnVolver);
		
		JButton btnReservar = new JButton("Reservar");
		btnReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int column = 0;
				int row = table.getSelectedRow();
				String value = null;
				try {
					value = table.getModel().getValueAt(row, column).toString();
				} catch (Exception e2) {
					value = "-1";
				}
				if (Integer.valueOf(value) == -1) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar una reserva ");
				} else {
					Integer id = Integer.valueOf(table.getModel().getValueAt(row, 0).toString());
					Agente agente = null;
					int reply = JOptionPane.showConfirmDialog(null, "Se hará una reserva con id " + id, "Nueva reserva", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						try {
							agente = Agente.getAgente();
							Reserva reserva = agente.getReserva(id);
							
							String stringFechaInicio = "";
							stringFechaInicio += comboBoxFechaInicioDia.getSelectedItem() + "-";
							stringFechaInicio += comboBoxFechaInicioMes.getSelectedItem() + "-";
							stringFechaInicio += comboBoxFechaInicioAño.getSelectedItem();
							String stringFechaFin = "";
							stringFechaFin += comboBoxFechaFinDia.getSelectedItem() + "-";
							stringFechaFin += comboBoxFechaFinMes.getSelectedItem() + "-";
							stringFechaFin += comboBoxFechaFinAño.getSelectedItem();
							
							SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
					        Date parsedInicio = null;
					        Date parsedFin = null;
							try {
								parsedInicio = format.parse(stringFechaInicio);
								parsedFin = format.parse(stringFechaFin);
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
					        java.sql.Date dateInicio = new java.sql.Date(parsedInicio.getTime());
					        java.sql.Date dateFin = new java.sql.Date(parsedFin.getTime());
					        
							reserva.setFechaInicio(dateInicio);
							reserva.setFechaFin(dateFin);
							agente.updateReserva(reserva);
							
							agente.updateEstadoReserva(id, Estado.ocupado);
							agente.insertReservado(cliente.getDni(), Integer.valueOf(value));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					    JOptionPane.showMessageDialog(null, "Reservado con exito");
					    tableModel.setRowCount(0);
						actualizarReservas(tableModel);
					} else {
					    JOptionPane.showMessageDialog(null, "Operación cancelada");
					}
				}
			}
		});
		btnReservar.setBounds(493, 209, 89, 23);
		contentPane.add(btnReservar);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 671, 188);
		contentPane.add(scrollPane);
		
		textFieldSeleccion = new JTextField();
		textFieldSeleccion.setEditable(false);
		textFieldSeleccion.setBounds(102, 210, 25, 20);
		contentPane.add(textFieldSeleccion);
		textFieldSeleccion.setColumns(10);
		
		comboBoxFechaInicioDia = new JComboBox();
		comboBoxFechaInicioDia.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		comboBoxFechaInicioDia.setBounds(137, 209, 40, 22);
		contentPane.add(comboBoxFechaInicioDia);
		
		comboBoxFechaFinDia = new JComboBox();
		comboBoxFechaFinDia.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		comboBoxFechaFinDia.setBounds(311, 210, 40, 22);
		contentPane.add(comboBoxFechaFinDia);
		
		comboBoxFechaInicioMes = new JComboBox();
		comboBoxFechaInicioMes.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		comboBoxFechaInicioMes.setBounds(180, 209, 40, 22);
		contentPane.add(comboBoxFechaInicioMes);
		
		comboBoxFechaFinMes = new JComboBox();
		comboBoxFechaFinMes.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		comboBoxFechaFinMes.setBounds(355, 210, 40, 22);
		contentPane.add(comboBoxFechaFinMes);
		
		comboBoxFechaInicioAño = new JComboBox();
		comboBoxFechaInicioAño.setModel(new DefaultComboBoxModel(new String[] {"2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"}));
		comboBoxFechaInicioAño.setBounds(223, 209, 60, 22);
		contentPane.add(comboBoxFechaInicioAño);
		
		comboBoxFechaFinAño = new JComboBox();
		comboBoxFechaFinAño.setModel(new DefaultComboBoxModel(new String[] {"2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"}));
		comboBoxFechaFinAño.setBounds(398, 210, 60, 22);
		contentPane.add(comboBoxFechaFinAño);
		
		lblNewLabel = new JLabel("Su selección");
		lblNewLabel.setBounds(20, 213, 89, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Fecha inicio");
		lblNewLabel_1.setBounds(137, 242, 146, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Fecha fin");
		lblNewLabel_2.setBounds(311, 243, 147, 14);
		contentPane.add(lblNewLabel_2);
		
		actualizarReservas(tableModel);
	}

	private void actualizarReservas(DefaultTableModel tableModel) {
		Agente agente = null;
		try {
			agente = Agente.getAgente();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		reservas = agente.getReservas();
		for (int i = 0; i < reservas.size(); i++) {
			Estado estado = reservas.get(i).getEstado();
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
			if (estadoString == "disponible") {
				int id = reservas.get(i).getId();
				double precio = reservas.get(i).getPrecio();
				Date fechaIni = reservas.get(i).getFechaInicio();
				Date fechaFin = reservas.get(i).getFechaFin();
				
				String localizacion = reservas.get(i).getLocalizacion();
				double superficie = reservas.get(i).getSuperficie();
				boolean aparcamiento = reservas.get(i).getAparcamiento();
				boolean piscina = reservas.get(i).getPiscina();
				boolean wifi = reservas.get(i).getWifi();
				Object[] data = {id, precio, fechaIni, fechaFin, estadoString, localizacion, superficie, 
						aparcamiento, piscina, wifi};
				tableModel.addRow(data);
			}
		}
	}
}
