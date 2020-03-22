package gui;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.HeadlessException;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.awt.Point;
import java.awt.Toolkit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frmLogin;
	private JTextField fieldUsername;
	private JPasswordField fieldPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 601, 409);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.setResizable(false);
		frmLogin.getContentPane().setLayout(null);

		// center gui window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (frmLogin.getWidth() / 2), middle.y - (frmLogin.getHeight() / 2));
		frmLogin.setLocation(newLocation);

		// create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);

		JLabel imgBanner = new JLabel();
		imgBanner.setBounds(11, 13, 573, 106);
		imgBanner.setIcon(ResizeImage(new ImageIcon("resources\\banner.JPG"), imgBanner));

		JLabel imgCar = new JLabel();
		imgCar.setBounds(11, 132, 267, 185);
		imgCar.setIcon(ResizeImage(new ImageIcon("resources\\car-logo.JPG"), imgCar));

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(317, 179, 71, 16);
		lblUsername.setHorizontalAlignment(JLabel.LEFT);
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(317, 214, 71, 16);
		JLabel lblCopyRight = new JLabel("Min Lwin @TTAS Copyright Reserved");
		lblCopyRight.setBounds(329, 291, 213, 16);
		fieldPassword = new JPasswordField();
		fieldPassword.setBounds(400, 211, 154, 22);
		frmLogin.getContentPane().setLayout(null);

		// cell column row width height
		frmLogin.getContentPane().add(imgBanner);
		frmLogin.getContentPane().add(imgCar);
		frmLogin.getContentPane().add(lblUsername);
		frmLogin.getContentPane().add(lblPassword);
		frmLogin.getContentPane().add(fieldPassword);
		frmLogin.getContentPane().add(lblCopyRight);

		fieldUsername = new JTextField("");
		fieldUsername.setBounds(400, 176, 154, 22);
		frmLogin.getContentPane().add(fieldUsername);

		JPanel panelButtons = new JPanel();
		panelButtons.setBounds(11, 330, 573, 34);
		frmLogin.getContentPane().add(panelButtons);
		panelButtons.setBorder(border);
		panelButtons.setLayout(null);

		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (verifyCredentials()) {
					new Menu();
					frmLogin.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Wrong username or Password.", "Invalid",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnOK.setBounds(381, 5, 81, 25);
		panelButtons.add(btnOK);
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCancel.setBounds(485, 5, 81, 25);
		panelButtons.add(btnCancel);

		// hit enter to login
		frmLogin.getRootPane().setDefaultButton(btnOK);

	}

	// image scaling by JLabel size
	private ImageIcon ResizeImage(ImageIcon MyImage, JLabel jLabel) {
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(jLabel.getWidth(), jLabel.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}

	private Boolean verifyCredentials() {
		String line = new String();

		try (FileReader filereader = new FileReader(new File("system-users.config"));
				BufferedReader br = new BufferedReader(filereader);) {

			String passText = new String(fieldPassword.getPassword());

			while ((line = br.readLine()) != null) {

				String[] split = line.split("#"); // split by # character [username#password]

				if ((fieldUsername.getText().equals(split[0]) && ((passText.equals(split[1]))))) {
					return true;
				}
			}

		} catch (HeadlessException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}
}
