package gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.Log;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class EditDBCon extends JFrame {

	private JPanel contentPane;
	private JTextField fieldJDBCDriver;
	private JTextField fieldURL;
	private JTextField fieldCarsTableName;
	private Properties prop;
	private JTextField fieldRecordsTableName;
	private Log logger;

	/**
	 * Create the frame.
	 */
	public EditDBCon() {

		logger = Log.getInstance();

		setTitle("Database configuration");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 515, 360);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);

		// center gui window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (getWidth() / 2), middle.y - (getHeight() / 2));
		setLocation(newLocation);

		loadProperties();

		JLabel lblTitle = new JLabel("Edit Database Connection");
		lblTitle.setFont(new Font("Verdana", Font.BOLD, 20));
		lblTitle.setBounds(30, 33, 307, 26);
		contentPane.add(lblTitle);

		JLabel lblJDBCDriver = new JLabel("JDBC Driver");
		lblJDBCDriver.setHorizontalAlignment(SwingConstants.RIGHT);
		lblJDBCDriver.setBounds(86, 105, 74, 16);
		contentPane.add(lblJDBCDriver);

		fieldJDBCDriver = new JTextField();
		fieldJDBCDriver.setBounds(172, 102, 277, 22);
		contentPane.add(fieldJDBCDriver);
		fieldJDBCDriver.setColumns(10);
		fieldJDBCDriver.setText(prop.getProperty("jdbc.driver"));

		JLabel lblURL = new JLabel("URL");
		lblURL.setHorizontalAlignment(SwingConstants.RIGHT);
		lblURL.setBounds(86, 142, 74, 16);
		contentPane.add(lblURL);

		fieldURL = new JTextField();
		fieldURL.setColumns(10);
		fieldURL.setBounds(172, 139, 277, 22);
		contentPane.add(fieldURL);
		fieldURL.setText(prop.getProperty("jdbc.url"));

		fieldCarsTableName = new JTextField();
		fieldCarsTableName.setColumns(10);
		fieldCarsTableName.setBounds(172, 174, 277, 22);
		contentPane.add(fieldCarsTableName);
		fieldCarsTableName.setText(prop.getProperty("tablename.cars"));

		JLabel lblCarsTableName = new JLabel("Cars Table Name");
		lblCarsTableName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCarsTableName.setBounds(54, 177, 106, 16);
		contentPane.add(lblCarsTableName);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try (OutputStream output = new FileOutputStream("dbconfig.properties")) {
					prop.setProperty("jdbc.driver", fieldJDBCDriver.getText());
					prop.setProperty("jdbc.url", fieldURL.getText());
					prop.setProperty("tablename.cars", fieldCarsTableName.getText());
					prop.setProperty("tablename.records", fieldRecordsTableName.getText());
					// save properties to project root folder
					prop.store(output, null);

					logger.addLog(Level.INFO, "Database config changed: " + prop);
					dispose();
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error Saving Config. Check AuctionSystem.log for more info.",
							"Inane error", JOptionPane.ERROR_MESSAGE);
					logger.addLog(Level.SEVERE, ex.toString());
				}
			}
		});
		btnUpdate.setBounds(325, 255, 97, 25);
		contentPane.add(btnUpdate);

		fieldRecordsTableName = new JTextField();
		fieldRecordsTableName.setText((String) null);
		fieldRecordsTableName.setColumns(10);
		fieldRecordsTableName.setBounds(172, 209, 277, 22);
		fieldRecordsTableName.setText(prop.getProperty("tablename.records"));
		contentPane.add(fieldRecordsTableName);

		JLabel lblRecordsTableName = new JLabel("Records Table Name");
		lblRecordsTableName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRecordsTableName.setBounds(23, 212, 137, 16);
		contentPane.add(lblRecordsTableName);
	}

	private void loadProperties() {
		try (InputStream input = new FileInputStream("dbconfig.properties")) {
			prop = new Properties();

			// load properties file
			prop.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
