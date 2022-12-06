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

public class IUCliente extends JFrame {

	private JPanel contentPane;
	private static Cliente clienteActual = null;
	private static Empleado empleadoActual = null;
	private JTable table;
	private JButton btnBajaReserva;
	private static DefaultTableModel tableModel = new DefaultTableModel();
	private static List<Integer> idReservas = new LinkedList<>();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IUCliente frame = new IUCliente(clienteActual);
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
	public IUCliente(Cliente cliente) {
		clienteActual = cliente;
		
		Agente agente = null;
		int idReserva = 0;
		Reserva reserva = null;
		try {
			agente = Agente.getAgente();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		idReservas = agente.getReservado(clienteActual.getDni());
		
		String col[] = {"Id", "Fecha inicio", "Fecha fin", "Localización", "Superficie", "Aparcamiento", "Piscina", "Wifi"};
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
		setBounds(100, 100, 526, 239);
		
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
		
		JMenuItem mntmReservar = new JMenuItem("Reservar");
		mntmReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IUReservar IUReservar = new IUReservar(clienteActual);
				IUReservar.setLocationRelativeTo(null);
				IUReservar.setVisible(true);
			}
		});
		menuBar.add(mntmReservar);
		
		JMenuItem mntmDarseDeBaja = new JMenuItem("Darse de baja");
		mntmDarseDeBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "¿De verdad desea darse de baja?", "Baja", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					Agente agente = null;
					try {
						agente = Agente.getAgente();
						agente.deleteCliente(clienteActual.getDni());
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
		btnBajaReserva = new JButton("Dar de baja reserva");
		btnBajaReserva.addActionListener(new ActionListener() {
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
					int reply = JOptionPane.showConfirmDialog(null, "Se eliminará la reserva con id " + id, "Eliminar reserva", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						try {
							agente = Agente.getAgente();
							res = agente.deleteReservadoByIdReserva(Integer.valueOf(value));
							agente.updateEstadoReserva(Integer.valueOf(value), Estado.disponible);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						if (res == 0) {
							JOptionPane.showMessageDialog(null, "Se ha eliminado la reserva con id " + id);
							idReservas = agente.getReservado(clienteActual.getDni());
							actualizarReservas(tableModel, idReservas);
						} else {
							JOptionPane.showMessageDialog(null, "No fue posible eliminar la reserva");
						}
					} else {
					    JOptionPane.showMessageDialog(null, "Operación cancelada");
					}
				}
			}
		});
		btnBajaReserva.setBounds(341, 141, 159, 23);
		contentPane.add(btnBajaReserva);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(table);
		
		JLabel lblNewLabel = new JLabel("Mis reservas");
		lblNewLabel.setBounds(10, 11, 108, 14);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 30, 490, 100);
		contentPane.add(scrollPane);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Agente agente = null;
				try {
					agente = Agente.getAgente();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				idReservas = agente.getReservado(clienteActual.getDni());
				actualizarReservas(tableModel, idReservas);
			}
		});
		btnActualizar.setToolTipText("Actualizar información");
		btnActualizar.setBounds(10, 141, 140, 23);
		contentPane.add(btnActualizar);
		
		actualizarReservas(tableModel, idReservas);
	}
	
	private void actualizarReservas(DefaultTableModel tableModel, List<Integer> idReservas) {
		Agente agente = null;
		try {
			agente = Agente.getAgente();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		List<Reserva> reservasR = new LinkedList<>();
		for (Integer idReserva : idReservas) {
			reservasR.add((Reserva) agente.getReserva(idReserva));
		}
		
		tableModel.setRowCount(0);
		for (int i = 0; i < reservasR.size(); i++) {
			int id = reservasR.get(i).getId();
			Date fechaIni = reservasR.get(i).getFechaInicio();
			Date fechaFin = reservasR.get(i).getFechaFin();
			String localizacion = reservasR.get(i).getLocalizacion();
			double superficie = reservasR.get(i).getSuperficie();
			boolean aparcamiento = reservasR.get(i).getAparcamiento();
			boolean piscina = reservasR.get(i).getPiscina();
			boolean wifi = reservasR.get(i).getWifi();
			Object[] data = {id, fechaIni, fechaFin, localizacion, superficie, 
					aparcamiento, piscina, wifi};
			tableModel.addRow(data);
		}
		table.setModel(tableModel);
	}
}
