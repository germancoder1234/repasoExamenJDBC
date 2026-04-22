package ejercicioRepasoJDBC;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class AppFarmacia {

	private JFrame frame;
	DefaultTableModel model;

	private JTextField textFieldId;
	private JTextField textFieldNombre;
	private JTextField textFieldFecha;
	
	
	DefaultTableModel modelo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppFarmacia window = new AppFarmacia();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppFarmacia() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void cargarTabla() {
		try {
			Connection con = ConnectionSingleton.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM medicamento");

			model.setRowCount(0);

			while (rs.next()) {
				Object[] row = new Object[4];
				row[0] = rs.getInt("id");
				row[1] = rs.getString("nombre");
				row[2] = rs.getString("fecha");
				row[3] = rs.getString("formato");
				// row[4] = rs.getBoolean("stock");
				model.addRow(row);
			}

			rs.close();
			stmt.close();

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	
	private void cargarTabla2() {
		try {
			Connection con = ConnectionSingleton.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM medicamento");

			model.setRowCount(0);

			while (rs.next()) {
				Object[] row = new Object[4];
				row[0] = rs.getInt("id");
				row[1] = rs.getString("nombre");
				row[2] = rs.getString("fecha");
				row[3] = rs.getString("formato");
				// row[4] = rs.getBoolean("stock");
				model.addRow(row);
			}

			rs.close();
			stmt.close();

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 660, 494);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textFieldId = new JTextField();
		textFieldId.setEnabled(false);
		textFieldId.setBounds(22, 195, 114, 21);
		frame.getContentPane().add(textFieldId);

		textFieldFecha = new JTextField();
		textFieldFecha.setBounds(22, 240, 114, 21);
		frame.getContentPane().add(textFieldFecha);

		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(22, 287, 114, 21);
		frame.getContentPane().add(textFieldNombre);

		model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("Nombre");
		model.addColumn("Fecha");
		model.addColumn("Formato");
		model.addColumn("Stock");

		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(22, 0, 282, 171);
		frame.getContentPane().add(scrollPane);

		JComboBox comboBoxFormato = new JComboBox();
		comboBoxFormato.setModel(new DefaultComboBoxModel(new String[] { "pastillas", "jarabe", "pomada" }));
		comboBoxFormato.setBounds(22, 333, 114, 26);
		frame.getContentPane().add(comboBoxFormato);

		JCheckBox checkBoxStock = new JCheckBox("Stock");
		checkBoxStock.setBounds(22, 383, 114, 25);
		frame.getContentPane().add(checkBoxStock);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				textFieldId.setText(model.getValueAt(i, 0).toString());
				textFieldNombre.setText(model.getValueAt(i, 1).toString());
				textFieldFecha.setText(model.getValueAt(i, 2).toString());
				comboBoxFormato.setSelectedItem(model.getValueAt(i, 3).toString());
				

			}
		});

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(153, 192, 100, 27);
		frame.getContentPane().add(btnGuardar);

		btnGuardar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					if (textFieldNombre.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "introduce el nombre del medicamento");
					} else if (textFieldFecha.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "introduce la fecha de caducidad del medicamento");
				//		 } else if (!textFieldFecha.getText().matches("[A-Z]{3}2{1}[567]{1}")) {
			//			 JOptionPane.showMessageDialog(null, "el formato de fecha es MAR27");
					} else {
						Connection con = ConnectionSingleton.getConnection();
						PreparedStatement ps = con
								.prepareStatement("INSERT INTO medicamento (nombre, fecha, formato) VALUES (?, ?, ?)");

						ps.setString(1, textFieldNombre.getText());
						ps.setString(2, textFieldFecha.getText());
						ps.setString(3, (String) comboBoxFormato.getSelectedItem());

						ps.executeUpdate();
						ps.close();

						cargarTabla();
					}

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});

		JButton btnActualizarIng = new JButton("Actualizar");
		btnActualizarIng.setBounds(153, 237, 100, 27);
		frame.getContentPane().add(btnActualizarIng);

		btnActualizarIng.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {

					if (textFieldId.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "selecciona un medicamento de la tabla");
					} else if (textFieldNombre.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "introduce el nombre del medicamento");
					} else if (textFieldFecha.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "introduce la fecha de caducidad del medicamento");
					//	} else if (!textFieldFecha.getText().matches("[A-Z]{3}2{1}[567]")) {
					//	JOptionPane.showMessageDialog(null, "el formato de fecha es MAR27");
					} else {
						Connection con = ConnectionSingleton.getConnection();
						PreparedStatement ps = con.prepareStatement("UPDATE medicamento SET nombre=?, fecha=?, formato WHERE id=?");

						ps.setString(1, textFieldNombre.getText());
						ps.setString(2, textFieldFecha.getText());
						ps.setString(3, (String) comboBoxFormato.getSelectedItem());
						ps.setInt(3, Integer.parseInt(textFieldId.getText()));

						ps.executeUpdate();
						ps.close();

						cargarTabla();
					}

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		
		JButton btnBorrarIng = new JButton("Borrar");
		btnBorrarIng.setBounds(148, 284, 105, 27);
		frame.getContentPane().add(btnBorrarIng);

		btnBorrarIng.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {

					if (textFieldId.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "selecciona un medicamento de la tabla");
					} else {
						Connection con = ConnectionSingleton.getConnection();
						PreparedStatement ps = con.prepareStatement("DELETE FROM medicamento WHERE id=?");

						ps.setInt(1, Integer.parseInt(textFieldId.getText()));

						ps.executeUpdate();
						ps.close();

						cargarTabla();
					}

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});


		JLabel lblId = new JLabel("id");
		lblId.setBounds(22, 180, 60, 17);
		frame.getContentPane().add(lblId);

		JLabel lblNombre = new JLabel("nombre");
		lblNombre.setBounds(22, 225, 60, 17);
		frame.getContentPane().add(lblNombre);

		JLabel lblFecha = new JLabel("fecha");
		lblFecha.setBounds(22, 273, 60, 17);
		frame.getContentPane().add(lblFecha);

		JLabel lblFormato = new JLabel("formato");
		lblFormato.setBounds(22, 316, 60, 17);
		frame.getContentPane().add(lblFormato);

	
		modelo = new DefaultTableModel();
		modelo.addColumn("ID");
		modelo.addColumn("Nombre");
		modelo.addColumn("Fecha");
		modelo.addColumn("Formato");
		modelo.addColumn("Stock");
		
		
		JTable tableReceta = new JTable(modelo);
		JScrollPane scrollPane2 = new JScrollPane(tableReceta);
		scrollPane2.setBounds(349, 0, 282, 171);
		frame.getContentPane().add(scrollPane2);

		JLabel lblIngredientes = new JLabel("");
		lblIngredientes.setBounds(500, 342, 214, 14);
		frame.getContentPane().add(lblIngredientes);

		tableReceta.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = tableReceta.getSelectedRow();
				textFieldId.setText(model.getValueAt(i, 0).toString());
				textFieldNombre.setText(model.getValueAt(i, 1).toString());
				textFieldFecha.setText(model.getValueAt(i, 2).toString());
				comboBoxFormato.setSelectedItem(model.getValueAt(i, 3).toString());
				
			
			}
		});

		cargarTabla();
	}
}
