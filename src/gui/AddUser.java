package gui;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;

public class AddUser {
	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private Properties prop;
	char defaultEchoChar;

	public AddUser() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setTitle("Add User");
		frame.setBounds(100, 100, 400, 293);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		// center gui window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (frame.getWidth() / 2), middle.y - (frame.getHeight() / 2));
		frame.setLocation(newLocation);

		usernameField = new JTextField();
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		usernameField.setBounds(112, 63, 245, 25);
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsername.setBounds(30, 63, 83, 25);
		frame.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPassword.setBounds(30, 108, 83, 25);
		frame.getContentPane().add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordField.setBounds(112, 108, 216, 25);
		frame.getContentPane().add(passwordField);
		defaultEchoChar = passwordField.getEchoChar();
		passwordField.setColumns(10);

		JLabel btnShow = new JLabel();
		btnShow.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnShow.setIcon(ResizeImage(new ImageIcon("resources/show-512.png"), btnShow));
				passwordField.setEchoChar((char) 0);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				btnShow.setIcon(ResizeImage(new ImageIcon("resources/hide-512.png"), btnShow));
				passwordField.setEchoChar(defaultEchoChar);
			}
		});
		btnShow.setBounds(332, 108, 25, 25);
		try {
			// Image img = ImageIO.read(getClass().getResource("resources/show-512.png"));
			btnShow.setIcon(ResizeImage(new ImageIcon("resources/hide-512.png"), btnShow));

		} catch (Exception ex) {
			System.out.println(ex);
		}

		frame.getContentPane().add(btnShow);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Files.write(Paths.get("system-users.config"),
							(usernameField.getText() + "#" + new String(passwordField.getPassword())).getBytes(),
							StandardOpenOption.APPEND);

					frame.dispose();
					JOptionPane.showMessageDialog(null, "User Saved.");

				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error Saving User. Check AuctionSystem.log for more info.",
							"Inane error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSave.setBounds(260, 184, 97, 25);
		frame.getContentPane().add(btnSave);

		frame.repaint();
	}

	private ImageIcon ResizeImage(ImageIcon MyImage, JLabel jLabel) {
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(jLabel.getWidth(), jLabel.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
}
