package Presentación;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Dominio.Estado;
import Dominio.Reserva;
import Persistencia.Agente;

import javax.swing.JScrollPane;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IUControlReservas extends JFrame {

	private JPanel contentPane;
	private static List<Reserva> reservas = null;
	private JTable table;
	private JLabel lblNuevaReserva;
	private JTextField textFieldId;
	private JTextField textFieldLocalizacion;
	private JTextField textFieldSuperficie;
	private JCheckBox chckbxAparcamiento;
	private JCheckBox chckbxPiscina;
	private JCheckBox chckbxWifi;
	private JRadioButton rdbtnDisponible;
	private JRadioButton rdbtnOcupado;
	private JRadioButton rdbtnLimpieza;
	private JRadioButton rdbtnDesperfecto;
	private JComboBox comboBoxFechaInicioDia;
	private JComboBox comboBoxFechaInicioMes;
	private JComboBox comboBoxFechaInicioAño;
	private JComboBox comboBoxFechaFinDia;
	private JComboBox comboBoxFechaFinMes;
	private JComboBox comboBoxFechaFinAño;
	private JTextField textFieldPrecio;
	private ButtonGroup buttonGroup;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				IUControlReservas frame = new IUControlReservas();
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public IUControlReservas() {
		setTitle("Reservas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 707, 401);
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
				lblNuevaReserva.setText("Su selección");
				textFieldId.setVisible(true);
				int column = 0;
				int row = table.getSelectedRow();
				String value = table.getModel().getValueAt(row, column).toString();
				textFieldId.setText(value);
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
					Estado estado = null;
					try {
						textFieldPrecio.setText(String.valueOf(reserva.getPrecio()));
						textFieldLocalizacion.setText(reserva.getLocalizacion());
						textFieldSuperficie.setText(String.valueOf(reserva.getSuperficie()));
						textFieldId.setText(String.valueOf(reserva.getId()));
						chckbxAparcamiento.setSelected(reserva.getAparcamiento());
						chckbxPiscina.setSelected(reserva.getPiscina());
						chckbxWifi.setSelected(reserva.getWifi());
						estado = reserva.getEstado();
						switch (estado) {
						case disponible:
							rdbtnDisponible.setSelected(true);
							break;
						case ocupado:
							rdbtnOcupado.setSelected(true);
							break;
						case limpieza:
							rdbtnLimpieza.setSelected(true);
							break;
						case desperfecto:
							rdbtnDesperfecto.setSelected(true);
							break;
						default:
							break;
						}
					} catch (Exception e1) {
					}
					
				}
			}
		});
		table.setBounds(22, 22, 388, 194);
		contentPane.add(table);
		
		JButton btnAñadir = new JButton("Añadir");
		btnAñadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNuevaReserva.setText("Nueva reserva");
				textFieldId.setVisible(false);
				boolean estadoSeleccionado = false;
				String estadoString = "";
				for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
		            AbstractButton button = buttons.nextElement();
		            if (button.isSelected()) {
		                estadoString = button.getText();
		                estadoSeleccionado = true;
		                break;
		            } else {
		            	estadoSeleccionado = false;
		            }
		        }
				if (estadoSeleccionado == false) {
					JOptionPane.showMessageDialog(null, "Debes indicar el estado de la reserva");
				} else {
					Reserva reserva = new Reserva();
					try {
						reserva.setPrecio(Double.parseDouble(textFieldPrecio.getText()));
						reserva.setSuperficie(Double.parseDouble(textFieldSuperficie.getText()));
						reserva.setLocalizacion(textFieldLocalizacion.getText());
						reserva.setWifi(chckbxAparcamiento.isSelected());
						reserva.setAparcamiento(chckbxAparcamiento.isSelected());
						reserva.setPiscina(chckbxPiscina.isSelected());
					} catch (Exception e1) {
					}
					String stringFechaInicio = "";
					stringFechaInicio += comboBoxFechaInicioDia.getSelectedItem() + "-";
					stringFechaInicio += comboBoxFechaInicioMes.getSelectedItem() + "-";
					stringFechaInicio += comboBoxFechaInicioAño.getSelectedItem();
					String stringFechaFin = "";
					stringFechaFin += comboBoxFechaFinDia.getSelectedItem() + "-";
					stringFechaFin += comboBoxFechaFinMes.getSelectedItem() + "-";
					stringFechaFin += comboBoxFechaFinAño.getSelectedItem();
					
					Estado estado = null;
					switch (estadoString) {
					case "Disponible":
						estado = Estado.disponible;
						break;
					case "Ocupado":
						estado = Estado.ocupado;
						break;
					case "Limpieza":
						estado = Estado.limpieza;
						break;
					case "Desperfecto":
						estado = Estado.desperfecto;
						break;
					default:
						break;
					}
					reserva.setEstado(estado);
					
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
					JOptionPane.showMessageDialog(null, "Se ha creado la reserva \n" + reserva);
					Agente agente = null;
					try {
						agente = Agente.getAgente();
						agente.insertReserva(reserva);
						tableModel.setRowCount(0);
						actualizarReservas(tableModel);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnAñadir.setBounds(296, 328, 89, 23);
		contentPane.add(btnAñadir);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int column = 0;
				int row = table.getSelectedRow();
				String value = null;
				try {
					value = table.getModel().getValueAt(row, column).toString();
				} catch (Exception e2) {
					value = "-1";
				}
				if (Integer.parseInt(value) == -1) {
					JOptionPane.showMessageDialog(null, "Debes seleccionar una reserva antes");
				} else {
					textFieldId.setText(String.valueOf(value));
					Agente agente = null;
					Reserva reserva = null;
					try {
						agente = Agente.getAgente();
						reserva = agente.getReserva(Integer.valueOf(value));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					reserva.setPrecio(Double.parseDouble(textFieldPrecio.getText()));
					reserva.setSuperficie(Double.parseDouble(textFieldSuperficie.getText()));
					reserva.setLocalizacion(textFieldLocalizacion.getText());
					reserva.setWifi(chckbxAparcamiento.isSelected());
					reserva.setAparcamiento(chckbxAparcamiento.isSelected());
					reserva.setPiscina(chckbxPiscina.isSelected());
						
						
					String stringFechaInicio = "";
					stringFechaInicio += comboBoxFechaInicioDia.getSelectedItem() + "-";
					stringFechaInicio += comboBoxFechaInicioMes.getSelectedItem() + "-";
					stringFechaInicio += comboBoxFechaInicioAño.getSelectedItem();
					String stringFechaFin = "";
					stringFechaFin += comboBoxFechaFinDia.getSelectedItem() + "-";
					stringFechaFin += comboBoxFechaFinMes.getSelectedItem() + "-";
					stringFechaFin += comboBoxFechaFinAño.getSelectedItem();
					
					String estadoString = "";
					for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
			            AbstractButton button = buttons.nextElement();
			            if (button.isSelected()) {
			                estadoString = button.getText();
			            }
			        }
					Estado estado = null;
					switch (estadoString) {
					case "Disponible":
						estado = Estado.disponible;
						break;
					case "Ocupado":
						estado = Estado.ocupado;
						break;
					case "Limpieza":
						estado = Estado.limpieza;
						break;
					case "Desperfecto":
						estado = Estado.desperfecto;
						break;
					default:
						break;
					}
					reserva.setEstado(estado);
					
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
					
					try {
						int res = agente.updateReserva(reserva);
						if (res == 0) {
							JOptionPane.showMessageDialog(null, "Se ha actualizado la reserva con id " + value);
						} else {
							JOptionPane.showMessageDialog(null, "No fue posible actualizar la reserva");
						}
						tableModel.setRowCount(0);
						actualizarReservas(tableModel);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnModificar.setBounds(395, 328, 89, 23);
		contentPane.add(btnModificar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVolver.setBounds(592, 328, 89, 23);
		contentPane.add(btnVolver);
		
		JButton btnBorrar = new JButton("Borrar");
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
				if (Integer.valueOf(value) == -1) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar una reserva ");
				} else {
					Integer id = Integer.valueOf(table.getModel().getValueAt(row, 0).toString());
					Agente agente = null;
					int reply = JOptionPane.showConfirmDialog(null, "Se eliminará la reserva con id " + id, "Eliminar reserva", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						try {
							agente = Agente.getAgente();
							agente.deleteReserva(id);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					    JOptionPane.showMessageDialog(null, "Se ha eliminado la reserva con id " + id);
					    tableModel.setRowCount(0);
						actualizarReservas(tableModel);
					} else {
					    JOptionPane.showMessageDialog(null, "Operación cancelada");
					}
				}
			}
		});
		btnBorrar.setBounds(493, 328, 89, 23);
		contentPane.add(btnBorrar);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 671, 165);
		contentPane.add(scrollPane);
		
		lblNuevaReserva = new JLabel("Su selección");
		lblNuevaReserva.setBounds(10, 186, 89, 14);
		contentPane.add(lblNuevaReserva);
		
		textFieldId = new JTextField();
		textFieldId.setEditable(false);
		textFieldId.setToolTipText("Id reserva");
		textFieldId.setBounds(88, 183, 31, 20);
		contentPane.add(textFieldId);
		textFieldId.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Estado de la reserva");
		lblNewLabel_1.setBounds(10, 211, 130, 14);
		contentPane.add(lblNewLabel_1);
		
		rdbtnDisponible = new JRadioButton("Disponible");
		rdbtnDisponible.setBounds(10, 232, 109, 23);
		contentPane.add(rdbtnDisponible);
		
		rdbtnOcupado = new JRadioButton("Ocupado");
		rdbtnOcupado.setBounds(10, 258, 109, 23);
		contentPane.add(rdbtnOcupado);
		
		rdbtnLimpieza = new JRadioButton("Limpieza");
		rdbtnLimpieza.setBounds(10, 284, 109, 23);
		contentPane.add(rdbtnLimpieza);
		
		rdbtnDesperfecto = new JRadioButton("Desperfecto");
		rdbtnDesperfecto.setBounds(10, 311, 109, 23);
		contentPane.add(rdbtnDesperfecto);
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnDisponible);
		buttonGroup.add(rdbtnOcupado);
		buttonGroup.add(rdbtnLimpieza);
		buttonGroup.add(rdbtnDesperfecto);
		
		chckbxAparcamiento = new JCheckBox("Aparcamiento");
		chckbxAparcamiento.setBounds(572, 186, 109, 23);
		contentPane.add(chckbxAparcamiento);
		
		chckbxPiscina = new JCheckBox("Piscina");
		chckbxPiscina.setBounds(572, 207, 112, 27);
		contentPane.add(chckbxPiscina);
		
		chckbxWifi = new JCheckBox("WiFi");
		chckbxWifi.setBounds(572, 232, 97, 23);
		contentPane.add(chckbxWifi);
		
		JLabel lblNewLabel_2 = new JLabel("Fecha inicio");
		lblNewLabel_2.setBounds(168, 187, 97, 14);
		contentPane.add(lblNewLabel_2);
		
		comboBoxFechaInicioDia = new JComboBox();
		comboBoxFechaInicioDia.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		comboBoxFechaInicioDia.setBounds(137, 209, 40, 22);
		contentPane.add(comboBoxFechaInicioDia);
		
		comboBoxFechaFinDia = new JComboBox();
		comboBoxFechaFinDia.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		comboBoxFechaFinDia.setBounds(340, 209, 40, 22);
		contentPane.add(comboBoxFechaFinDia);
		
		comboBoxFechaInicioMes = new JComboBox();
		comboBoxFechaInicioMes.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		comboBoxFechaInicioMes.setBounds(180, 209, 40, 22);
		contentPane.add(comboBoxFechaInicioMes);
		
		comboBoxFechaFinMes = new JComboBox();
		comboBoxFechaFinMes.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		comboBoxFechaFinMes.setBounds(384, 209, 40, 22);
		contentPane.add(comboBoxFechaFinMes);
		
		comboBoxFechaInicioAño = new JComboBox();
		comboBoxFechaInicioAño.setModel(new DefaultComboBoxModel(new String[] {"2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"}));
		comboBoxFechaInicioAño.setBounds(223, 209, 60, 22);
		contentPane.add(comboBoxFechaInicioAño);
		
		comboBoxFechaFinAño = new JComboBox();
		comboBoxFechaFinAño.setModel(new DefaultComboBoxModel(new String[] {"2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"}));
		comboBoxFechaFinAño.setBounds(427, 209, 60, 22);
		contentPane.add(comboBoxFechaFinAño);
		
		JLabel lblNewLabel_3 = new JLabel("Fecha fin");
		lblNewLabel_3.setBounds(366, 186, 71, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Localización");
		lblNewLabel_4.setBounds(285, 288, 100, 14);
		contentPane.add(lblNewLabel_4);
		
		textFieldLocalizacion = new JTextField();
		textFieldLocalizacion.setBounds(366, 285, 86, 20);
		contentPane.add(textFieldLocalizacion);
		textFieldLocalizacion.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Superficie");
		lblNewLabel_5.setBounds(498, 288, 71, 14);
		contentPane.add(lblNewLabel_5);
		
		textFieldSuperficie = new JTextField();
		textFieldSuperficie.setBounds(565, 284, 86, 20);
		contentPane.add(textFieldSuperficie);
		textFieldSuperficie.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Precio");
		lblNewLabel.setBounds(125, 288, 46, 14);
		contentPane.add(lblNewLabel);
		
		textFieldPrecio = new JTextField();
		textFieldPrecio.setBounds(168, 285, 86, 20);
		contentPane.add(textFieldPrecio);
		textFieldPrecio.setColumns(10);
		
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
			int id = reservas.get(i).getId();
			double precio = reservas.get(i).getPrecio();
			Date fechaIni = reservas.get(i).getFechaInicio();
			Date fechaFin = reservas.get(i).getFechaFin();
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
