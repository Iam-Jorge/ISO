package Presentación;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import Dominio.Cliente;
import Dominio.Empleado;
import Dominio.Estado;
import Dominio.Reserva;
import Persistencia.Agente;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class IUEmpleado extends JFrame {

	private JPanel contentPane;
	private static Cliente clienteActual = null;
	private static Empleado empleadoActual = null;
	private JTable table;
	private JButton btnModificarEstado;
	private static DefaultTableModel tableModel = new DefaultTableModel();
	private static List<Integer> idReservas = new LinkedList<>();
	private JComboBox comboBox;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IUEmpleado frame = new IUEmpleado(empleadoActual);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param cliente 
	 */
	public IUEmpleado(Empleado empleado) {
		empleadoActual = empleado;
		
		Agente agente = null;
		int idReserva = 0;
		Reserva reserva = null;
		try {
			agente = Agente.getAgente();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		String col[] = {"Id", "Estado", "Localización", "Superficie", "Aparcamiento", "Piscina", "Wifi"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		table.setBounds(20, 36, 385, 104);
		
		setTitle("Mi perfil");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 239);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenuItem mntmDatos = new JMenuItem("Mis datos");
		mntmDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IUDatos IUDatos = new IUDatos(clienteActual, empleadoActual);
				IUDatos.setLocationRelativeTo(null);
				IUDatos.setVisible(true);
			}
		});
		menuBar.add(mntmDatos);
		
		JMenuItem mntmDarseDeBaja = new JMenuItem("Darse de baja");
		mntmDarseDeBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "¿De verdad desea darse de baja?", "Baja", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					Agente agente = null;
					try {
						agente = Agente.getAgente();
						agente.deleteEmpleado(empleadoActual.getDni());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				    JOptionPane.showMessageDialog(null, "Se ha dado de baja correctamente");
				    IURegistro IURegistro = new IURegistro();
				    IURegistro.setLocationRelativeTo(null);
				    IURegistro.setVisible(true);
				    dispose();
				} else {
				    JOptionPane.showMessageDialog(null, "Operación cancelada");
				}
			}
		});
		menuBar.add(mntmDarseDeBaja);
		
		JMenuItem mntmTransacciones = new JMenuItem("Transacciones");
		mntmTransacciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IUTransacciones IUTransacciones = new IUTransacciones(clienteActual, empleadoActual);
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
		btnModificarEstado = new JButton("Modificar estado");
		btnModificarEstado.addActionListener(new ActionListener() {
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
					int res = 0;
					int reply = JOptionPane.showConfirmDialog(null, "Se modificará la reserva con id " + id, "Modificar reserva", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						try {
							agente = Agente.getAgente();
							String estadoString = comboBox.getSelectedItem().toString();
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
							agente.updateEstadoReserva(Integer.valueOf(value), estado);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						if (res == 0) {
							JOptionPane.showMessageDialog(null, "Se ha modificado la reserva con id " + id);
							actualizarReservas(tableModel, idReservas);
						} else {
							JOptionPane.showMessageDialog(null, "No fue posible modificar la reserva");
						}
					} else {
					    JOptionPane.showMessageDialog(null, "Operación cancelada");
					}
				}
			}
		});
		btnModificarEstado.setBounds(265, 141, 159, 23);
		contentPane.add(btnModificarEstado);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(table);
		
		JLabel lblNewLabel = new JLabel("Reservas");
		lblNewLabel.setBounds(10, 11, 108, 14);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 30, 414, 100);
		contentPane.add(scrollPane);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Disponible", "Limpieza", "Desperfecto"}));
		comboBox.setBounds(10, 141, 172, 22);
		contentPane.add(comboBox);
		
		actualizarReservas(tableModel, idReservas);
	}
	
	private void actualizarReservas(DefaultTableModel tableModel, List<Integer> idReservas) {
		Agente agente = null;
		try {
			agente = Agente.getAgente();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		List<Reserva> reservas = agente.getReservas();
		
		tableModel.setRowCount(0);
		for (int i = 0; i < reservas.size(); i++) {
			int id = reservas.get(i).getId();
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
			Object[] data = {id, estadoString, localizacion, superficie, 
					aparcamiento, piscina, wifi};
			if (!estadoString.equalsIgnoreCase("ocupado")) {
				tableModel.addRow(data);
			}
		}
		table.setModel(tableModel);
	}
}
