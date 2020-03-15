package gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;
import utils.Log;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.CarController;
import gui.auction.AuctionController;
import gui.menu.AuctionReport;
import gui.menu.CarList;
import gui.menu.Register;

import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Menu {

	// Variables declaration - do not modify
	private JFrame frame;
	private Register panelRegister;
	private CarList panelCarList;
	private AuctionController panelController;
	private JPanel layeredPanel = new JPanel();
	private JPanel dynamicPanel = new JPanel();
	private JPanel btnRegister = new JPanel();
	private JPanel btnCarList = new JPanel();
	private JLabel lblRegister = new JLabel("Register");
	private JLabel lblCarList = new JLabel("Car List");
	private JPanel panelSidebar = new JPanel();
	private JPanel btnAuctionReport = new JPanel();
	private AuctionReport panelAuctionReport = new AuctionReport();
	private JButton btnStartAuction = new JButton("Start Auction");
	private JButton btnEditDBCon = new JButton("Edit Database Connection");
	private JLabel lblAuctionReport = new JLabel("Auction Report");
	private JButton btnRaiseAmount = new JButton("Raise Amount");
	private Log logger = Log.getInstance();
	private final JButton button = new JButton("Add User");

	public Menu() {
		initialize();
		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		logger.addLog(Level.INFO, "Menu frame created");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Home");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 782, 526);
		frame.setMinimumSize(new Dimension(1185, 750));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane()
				.setLayout(new MigLayout("insets 0", "[200px:200.00:200px,grow]0[grow,fill]", "[grow][]"));
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				CarController carController = CarController.getInstance();
				carController.closeConnection();
			}
		});
		
		

		// Side bar
		lblAuctionReport.setHorizontalAlignment(SwingConstants.CENTER);

		panelSidebar.setBackground(new java.awt.Color(23, 35, 51));
		panelSidebar.setLayout(
				new MigLayout("", "[200px,grow,left][]", "[100px,fill][30px]10px[30px]10px[30px]30px[40px][40px][40px][grow]"));
		frame.getContentPane().add(panelSidebar, "flowx,cell 0 0 1 2,alignx left,growy");

		// Car List
		lblCarList.setHorizontalAlignment(SwingConstants.CENTER);
		btnCarList.setBackground(new java.awt.Color(23, 35, 51));
		btnCarList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnCarList.setBackground(new Color(41, 57, 80));
				resetColor(new JPanel[] { btnRegister, btnAuctionReport });
				dynamicPanel("CarList");
			}
		});
		
		// Start Auction button
		btnStartAuction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetColor(new JPanel[] { btnRegister, btnAuctionReport, btnCarList });
				dynamicPanel("Auction");
				logger.addLog(Level.INFO, "Live Auction Started");
			}
		});
		btnStartAuction.setBackground(new Color(0, 100, 0));
		btnStartAuction.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
		btnStartAuction.setForeground(new java.awt.Color(255, 255, 255));
		btnStartAuction.setOpaque(true);
		btnStartAuction.setFocusPainted(false);
		panelSidebar.add(btnStartAuction, "cell 0 0,gapy 30 30,grow");

		// Raise Amount button
		btnRaiseAmount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Properties prop = new Properties();
				String[] choices = { "50,000", "100,000", "150,000", "200,000", "250,000", "300,000" };
				String input = (String) JOptionPane.showInputDialog(null, "Choose now...", "Raise Amount",
						JOptionPane.QUESTION_MESSAGE, null, choices // array of choices
				, choices[0]); // Initial choice
				if (!(input == null || input.equals(""))) {
					try (OutputStream output = new FileOutputStream("auction.config")) {
						prop.setProperty("raise.amount", input.replaceAll(",", ""));
						prop.store(output, null);
						logger.addLog(Level.INFO, "Raise Amount changed: " + input);
					} catch (IOException ex) {
						ex.printStackTrace();
						logger.addLog(Level.SEVERE, "Unable to save raise amount: " + e);
					}
				}

			}
		});
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddUser addUser = new AddUser();
			}
		});
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(75, 0, 130));
		
		panelSidebar.add(button, "cell 0 1,grow");
		btnRaiseAmount.setForeground(Color.WHITE);
		btnRaiseAmount.setBackground(new Color(75, 0, 130));
		panelSidebar.add(btnRaiseAmount, "cell 0 2,grow");

		// Edit DB Con 
		btnEditDBCon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditDBCon editFrame = new EditDBCon();
				editFrame.setVisible(true);
			}
		});
		btnEditDBCon.setForeground(Color.WHITE);
		btnEditDBCon.setBackground(new Color(75, 0, 130));
		panelSidebar.add(btnEditDBCon, "cell 0 3,grow");

		btnCarList.setLayout(new MigLayout("", "[188px]", "[16px]"));
		lblCarList.setForeground(Color.WHITE);
		btnCarList.add(lblCarList, "cell 0 0,growx,aligny top");
		panelSidebar.add(btnCarList, "cell 0 4,growx");

		// Live Auction Button
		btnAuctionReport.setBackground(new java.awt.Color(23, 35, 51));
		btnAuctionReport.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnAuctionReport.setBackground(new Color(41, 57, 80));
				resetColor(new JPanel[] { btnRegister, btnCarList });
				dynamicPanel("Report");
			}
		});

		// Register button
		btnRegister.setBackground(new java.awt.Color(23, 35, 51));
		btnRegister.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnRegister.setBackground(new Color(41, 57, 80));
				resetColor(new JPanel[] { btnCarList, btnAuctionReport });
				dynamicPanel("Register");
			}
		});
		btnRegister.setLayout(new MigLayout("", "[188px]", "[16px]"));

		// Register label
		lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegister.setBounds(115, 75, 47, 16);
		lblRegister.setForeground(Color.WHITE);
		btnRegister.add(lblRegister, "cell 0 0,growx,aligny top");
		panelSidebar.add(btnRegister, "cell 0 5,growx");

		// Auction Report
		lblAuctionReport.setBounds(115, 75, 47, 16);
		lblAuctionReport.setForeground(Color.WHITE);
		btnAuctionReport.setLayout(new MigLayout("", "[188px]", "[16px]"));
		btnAuctionReport.add(lblAuctionReport, "cell 0 0,growx,aligny top");
		panelSidebar.add(btnAuctionReport, "cell 0 6,growx");
		layeredPanel.setBackground(Color.WHITE);

		frame.getContentPane().add(layeredPanel, "cell 1 0,grow");
		layeredPanel.setLayout(new CardLayout());

	}

	private void resetColor(JPanel[] pane) {
		for (int i = 0; i < pane.length; i++) {
			pane[i].setBackground(new Color(23, 35, 51));
		}
	}

	private void dynamicPanel(String choice) {
		switch (choice) {
		case ("Register"):
			if (panelRegister == null) {
				panelRegister = new Register();
			}
			panelRegister.clearAllFields();
			dynamicPanel = panelRegister.getPanel();
			layeredPanel.removeAll();
			layeredPanel.add(dynamicPanel);
			layeredPanel.repaint();
			layeredPanel.revalidate();
			break;

		case ("CarList"):
			if (panelCarList == null) {
				panelCarList = new CarList();
			} else {
				panelCarList.refreshTableData();
			}
			dynamicPanel = panelCarList.getPanel();

			layeredPanel.removeAll();
			layeredPanel.add(dynamicPanel);
			layeredPanel.repaint();
			layeredPanel.revalidate();
			break;

		case ("Report"):
			if (panelAuctionReport == null) {
				panelAuctionReport = new AuctionReport();
			} else {
				panelAuctionReport.refreshTableData();
			}
			dynamicPanel = panelAuctionReport.getPanel();
			layeredPanel.removeAll();
			layeredPanel.add(dynamicPanel);
			layeredPanel.repaint();
			layeredPanel.revalidate();
			break;

		case ("Auction"):
			if (panelController == null) {
				panelController = new AuctionController();
			}
			dynamicPanel = panelController.getPanel();
			layeredPanel.removeAll();
			layeredPanel.add(dynamicPanel);
			layeredPanel.repaint();
			layeredPanel.revalidate();
			break;
		}

	}
}
