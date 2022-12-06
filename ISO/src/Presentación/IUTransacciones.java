package Presentación;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Dominio.Cliente;
import Dominio.Empleado;
import Dominio.TarjetaCredito;
import Persistencia.Agente;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class IUTransacciones extends JFrame {

	private JPanel contentPane;
	private static Cliente clienteActual;
	private static Empleado empleadoActual;
	private JTextField textNumTarjeta;
	private JTextField textFieldSaldo;
	private JLabel lblNumTarjeta;
	private JLabel lblNoDispone;
	private JLabel lblSaldo;
	private JButton btnTarjetaBaja;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IUTransacciones frame = new IUTransacciones(clienteActual, empleadoActual);
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
	public IUTransacciones(Cliente cliente, Empleado empleado) {
		clienteActual = cliente;
		empleadoActual = empleado;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 371, 223);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenuItem mntmAñadirTarjeta = new JMenuItem("Añadir tarjeta");
		mntmAñadirTarjeta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		        int res = JOptionPane.showConfirmDialog(null,"Se creará una nueva tarjeta de crédito a su nombre", "Nueva tarjeta de crédito", JOptionPane.YES_NO_OPTION);
		        if(res == 0){
		        	Agente agente = null;
			        try {
						agente = Agente.getAgente();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			        TarjetaCredito tarjeta = null;
			        if (clienteActual != null) {
				    	tarjeta = agente.getTarjeta(clienteActual.getDni());
					} else {
						tarjeta= agente.getTarjeta(empleadoActual.getDni());
					}
				    if (tarjeta != null) {
				    	JOptionPane.showMessageDialog(null, "Usted ya dispone de una tarjeta");
					} else {
						tarjeta = new TarjetaCredito();
						tarjeta.setSaldo(0);
						int numTarjeta = generarNumTarjeta();
						tarjeta.setNumTarjeta(numTarjeta);
						if (clienteActual != null) {
					    	tarjeta.setDni(clienteActual.getDni());
					    	agente.updateNumTarjetaCliente(numTarjeta, clienteActual.getDni());
						} else {
							tarjeta.setDni(empleadoActual.getDni());
							agente.updateNumTarjetaEmpleado(numTarjeta, empleadoActual.getDni());
						}
						agente.insertTarjeta(tarjeta);
						actualizarDatos();
					}
		        }
		        else {
		            JOptionPane.showMessageDialog(null, "Operación cancelada");
		        }
			}
		});
		menuBar.add(mntmAñadirTarjeta);
		
		JMenuItem mntmTransaccion = new JMenuItem("Transacción");
		mntmTransaccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String transferencia = ""; 
				String destino = "";
				transferencia = JOptionPane.showInputDialog("Cantidad a transferir ");
				destino = JOptionPane.showInputDialog("Número de cuenta destino ");
				Agente agente = null;
			    try {
					agente = Agente.getAgente();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			    TarjetaCredito tarjetaDestino = new TarjetaCredito();
			    tarjetaDestino = agente.getTarjetaByNum(Integer.parseInt(destino));
			    if (tarjetaDestino != null) {
			    	if (transferencia.matches("[0-9]+")) {
					    TarjetaCredito tarjeta = null;
					    if (clienteActual != null) {
					    	tarjeta = agente.getTarjeta(clienteActual.getDni());
						} else {
							tarjeta= agente.getTarjeta(empleadoActual.getDni());
						}
					    if (tarjeta != null) {
					    	if (tarjeta.getSaldo() >= Double.parseDouble(transferencia)) {
					    		Double nuevoSaldo = tarjeta.getSaldo() - Double.parseDouble(transferencia);
								agente.updateSaldo(tarjeta.getDni(), nuevoSaldo);
								
								Double nuevoSaldoDestino = tarjetaDestino.getSaldo() + Double.parseDouble(transferencia);
								agente.updateSaldo(tarjetaDestino.getDni(), nuevoSaldoDestino);
								
								actualizarDatos();
							} else {
								JOptionPane.showMessageDialog(null, "No dispones de fondo suficiente");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Usted no dispone de tarjetas");
						}
				    } else {
				    	JOptionPane.showMessageDialog(null, "Debes introducir un valor numérico");
				    }
				} else {
					JOptionPane.showMessageDialog(null, "No existe el destinatario indicado");
				}
			}
		});
		menuBar.add(mntmTransaccion);
		
		JMenuItem mntmIngreso = new JMenuItem("Ingreso");
		mntmIngreso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ingreso = ""; 
			    ingreso = JOptionPane.showInputDialog("Introduce la cantidad que deseas ingresar ");
			    if (ingreso.matches("[0-9]+")) {
		    	 	Agente agente = null;
				    try {
						agente = Agente.getAgente();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				    TarjetaCredito tarjeta = null;
				    if (clienteActual != null) {
				    	tarjeta = agente.getTarjeta(clienteActual.getDni());
					} else {
						tarjeta= agente.getTarjeta(empleadoActual.getDni());
					}
				    if (tarjeta != null) {
				    	Double nuevoSaldo = tarjeta.getSaldo() + Double.parseDouble(ingreso);
						agente.updateSaldo(tarjeta.getDni(), nuevoSaldo);
						actualizarDatos();
					} else {
						JOptionPane.showMessageDialog(null, "Usted no dispone de tarjetas");
					}
			    } else {
			    	JOptionPane.showMessageDialog(null, "Debes introducir un valor numérico");
			    }
			}
		});
		menuBar.add(mntmIngreso);
		
		JMenuItem mntmVolver = new JMenuItem("Volver");
		mntmVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		menuBar.add(mntmVolver);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mis tarjetas de crédito");
		lblNewLabel.setBounds(10, 11, 132, 14);
		contentPane.add(lblNewLabel);
		
		textNumTarjeta = new JTextField();
		textNumTarjeta.setHorizontalAlignment(SwingConstants.RIGHT);
		textNumTarjeta.setEditable(false);
		textNumTarjeta.setToolTipText("Número tarjeta de crédito");
		textNumTarjeta.setBounds(10, 36, 270, 20);
		contentPane.add(textNumTarjeta);
		textNumTarjeta.setColumns(10);
		
		textFieldSaldo = new JTextField();
		textFieldSaldo.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldSaldo.setEditable(false);
		textFieldSaldo.setToolTipText("Saldo");
		textFieldSaldo.setBounds(163, 67, 117, 20);
		contentPane.add(textFieldSaldo);
		textFieldSaldo.setColumns(10);
		
		lblSaldo = new JLabel("Saldo");
		lblSaldo.setBounds(287, 70, 46, 14);
		contentPane.add(lblSaldo);
		
		lblNumTarjeta = new JLabel("CVV");
		lblNumTarjeta.setBounds(287, 39, 46, 14);
		contentPane.add(lblNumTarjeta);
		
		lblNoDispone = new JLabel("Usted no dispone de tarjeta de crédito");
		lblNoDispone.setBounds(10, 49, 230, 14);
		contentPane.add(lblNoDispone);
		
		btnTarjetaBaja = new JButton("Dar tarjeta de baja");
		btnTarjetaBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Agente agente = null;
				try {
					agente = Agente.getAgente();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				int reply = JOptionPane.showConfirmDialog(null, "¿De verdad desea dar de baja la tarjeta? ", "Baja", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					if (clienteActual != null) {
				    	agente.deleteTarjeta(clienteActual.getDni());
				    	agente.updateNumTarjetaCliente(0, clienteActual.getDni());
					} else {
						agente.deleteTarjeta(empleadoActual.getDni());
						agente.updateNumTarjetaCliente(0, empleadoActual.getDni());
					}
					textFieldSaldo.setVisible(false);
					textNumTarjeta.setVisible(false);
					lblSaldo.setVisible(false);
					lblNumTarjeta.setVisible(false);
					lblNoDispone.setVisible(true);
					textNumTarjeta.setText("");
					textFieldSaldo.setText("");
					btnTarjetaBaja.setVisible(false);
				    JOptionPane.showMessageDialog(null, "La tarjeta se ha dado de baja correctamente");
				} else {
				    JOptionPane.showMessageDialog(null, "Operación cancelada");
				}
			}
		});
		btnTarjetaBaja.setToolTipText("Dar tarjeta de baja");
		btnTarjetaBaja.setBounds(163, 115, 170, 23);
		contentPane.add(btnTarjetaBaja);
		
		actualizarDatos();
		
		
	}

	private void actualizarDatos() {
		textFieldSaldo.setVisible(false);
		textNumTarjeta.setVisible(false);
		lblSaldo.setVisible(false);
		lblNumTarjeta.setVisible(false);
		lblNoDispone.setVisible(false);
		Agente agente = null;
	    try {
			agente = Agente.getAgente();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	    TarjetaCredito tarjeta = null;
	    if (clienteActual != null) {
	    	tarjeta = agente.getTarjeta(clienteActual.getDni());
		} else {
			tarjeta = agente.getTarjeta(empleadoActual.getDni());
		}
	    if (tarjeta != null) {
			textFieldSaldo.setVisible(true);
			textNumTarjeta.setVisible(true);
			lblSaldo.setVisible(true);
			lblNumTarjeta.setVisible(true);
			lblNoDispone.setVisible(false);
			textNumTarjeta.setText(String.valueOf(tarjeta.getNumTarjeta()));
			textFieldSaldo.setText(String.valueOf(tarjeta.getSaldo()));
			btnTarjetaBaja.setVisible(true);
		} else {
			textFieldSaldo.setVisible(false);
			textNumTarjeta.setVisible(false);
			lblSaldo.setVisible(false);
			lblNumTarjeta.setVisible(false);
			lblNoDispone.setVisible(true);
			textNumTarjeta.setText("");
			textFieldSaldo.setText("");
			btnTarjetaBaja.setVisible(false);
		}
	}

	protected int generarNumTarjeta() {
		Random r = new Random();
		int low = 100000000;
		int high = 1000000000;
		int result = r.nextInt(high-low) + low;
		return result;
	}
}
