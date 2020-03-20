package gui.auction;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.*;
import model.pojo.Car;
import net.miginfocom.swing.MigLayout;
import utils.ResizeImage;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.awt.event.ActionEvent;

public class AuctionController extends JFrame {

	private JPanel panelController;
	private AuctionDashboard dashboard;
	private Integer count = 0;
	private int newCurrPrice = 0;
	private CarController carController = CarController.getInstance();
	private RecordController recordController = new RecordController();

	private Properties prop;
	private Car car;
	private ArrayList<String> timeStampList = new ArrayList<>();

	private JPanel panelCarImg;
	private JLabel lblCount;

	private JLabel lblItemNoVal;
	private JLabel lblMinPriceVal;
	private JLabel lblCurrentPriceVal;
	private JLabel lblStartingPrice;
	private JButton btnBid;
	private int raiseAmount;
	private int currentAmount;

	private JLabel lblStatus1;
	private JLabel lblStatus2;
	private JLabel lblStatus3;

	private JButton btnNext;
	private JButton btnExit;

	/**
	 * Create the frame.
	 */
	public AuctionController() {
		dashboard = new AuctionDashboard();
		car = carController.getAuctionItem();
		initialize();
		loadRaiseAmount();

	}

	public JPanel getPanel() {
		return panelController;
	}

	public void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 971, 594);
		panelController = new JPanel();
		panelController.setBackground(Color.WHITE);
		panelController.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelController);
		panelController.setLayout(new MigLayout("insets 0",
				"[:20%:20%,grow,fill]20[:30%:30%,grow]20[25%,grow]20[25%,grow]",
				"[:10%:10%,grow,fill]20[:30%:30%,grow,fill]20[30%:30%:30%,grow,fill]20[15%,grow,fill]20[15%,grow,fill]"));

		getContentPane().addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				setFontSize();

			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentShown(ComponentEvent e) {
				setFontSize();

			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

		});

		panelCarImg = new JPanel();
		panelCarImg.setBackground(Color.WHITE);
		// panelController.add(imgCar, "flowx,cell 0 0 2 3");

		// Item No. Panel
		JPanel panelItemNo = new JPanel();
		panelItemNo.setBorder(BorderFactory.createTitledBorder("Item No."));
		panelItemNo.setBackground(Color.WHITE);
		panelItemNo.setLayout(new MigLayout("insets 0", "[grow,fill]", "[grow,fill]"));
		lblItemNoVal = new JLabel("");
		panelItemNo.add(lblItemNoVal, "cell 0 0,grow"); // add to panel
		panelController.add(panelItemNo, "cell 2 0 2 1,grow"); // add to frame

		// Minimum Selling Price JPanel
		JPanel panelMinPrice = new JPanel();
		panelMinPrice.setBorder(BorderFactory.createTitledBorder("Minimum Selling Price"));
		panelMinPrice.setBackground(Color.WHITE);
		panelMinPrice.setLayout(new MigLayout("insets 0", "[grow,center]", "[grow,fill]"));
		lblMinPriceVal = new JLabel("");
		// lblMinPriceVal.setFont(new Font("SansSerif", Font.PLAIN, 40));
		panelMinPrice.add(lblMinPriceVal, "cell 0 0");
		panelController.add(panelMinPrice, "cell 2 1 2 1,grow");

		// Current Selling Price JPanel
		JPanel panelCurrentPrice = new JPanel();
		panelCurrentPrice.setBorder(BorderFactory.createTitledBorder("Current Selling Price"));
		panelCurrentPrice.setBackground(Color.WHITE);
		panelCurrentPrice.setLayout(new MigLayout("insets 0", "[grow,center]", "[grow,fill]"));
		lblCurrentPriceVal = new JLabel("");
		// lblCurrentPriceVal.setFont(new Font("SansSerif", Font.PLAIN, 40));
		panelCurrentPrice.add(lblCurrentPriceVal, "cell 0 0");
		panelController.add(panelCurrentPrice, "cell 2 2 2 1,grow");

		JPanel panelStatus = new JPanel();
		panelStatus.setBorder(BorderFactory.createTitledBorder("Status"));
		panelStatus.setBackground(Color.WHITE);
		panelController.add(panelStatus, "cell 0 3 1 2,alignx center,aligny center");
		panelStatus.setLayout(new MigLayout("", "[grow,center]", "[grow,fill]10[grow]10[grow]"));

		lblStatus1 = new JLabel();
		panelStatus.add(lblStatus1, "cell 0 0,alignx center,aligny center");

		lblStatus2 = new JLabel();
		panelStatus.add(lblStatus2, "cell 0 1");

		lblStatus3 = new JLabel();
		panelStatus.add(lblStatus3, "cell 0 2");

		JPanel panelStartingPrice = new JPanel();
		panelStartingPrice.setBorder(BorderFactory.createTitledBorder("Starting Price"));
		panelStartingPrice.setBackground(Color.WHITE);
		panelController.add(panelStartingPrice, "cell 1 3,grow");
		panelStartingPrice.setLayout(new MigLayout("", "[grow,center]", "[grow,fill]"));

		lblStartingPrice = new JLabel("");
		// lblStartingPrice.setFont(new Font("SansSerif", Font.PLAIN, 20));
		panelStartingPrice.add(lblStartingPrice, "cell 0 0");

		JPanel panelCount = new JPanel();
		panelCount.setBorder(BorderFactory.createTitledBorder("Count"));
		panelCount.setBackground(Color.WHITE);
		panelController.add(panelCount, "cell 2 3 1 2,grow");
		panelCount.setLayout(new MigLayout("", "[grow,center]", "[grow][grow,fill]"));

		lblCount = new JLabel("-");
		panelCount.add(lblCount, "cell 0 0");

		JButton btnCount = new JButton("Count");
		btnCount.setEnabled(false);
		btnCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count++;

				switch (count) {
				case (1):
					dashboard.setColorPanelCount(Color.yellow);
					break;
				case (2):
					dashboard.setColorPanelCount(Color.orange);
					break;
				case (3):
					dashboard.setColorPanelCount(Color.red);
					checkSold();
					btnNext.setEnabled(true);
					btnExit.setEnabled(true);
					btnCount.setEnabled(false);
					btnBid.setEnabled(false);
					break;
				default:
					count = 0;
					dashboard.setColorPanelCount(Color.green);
					lblCount.setText("-");
				}

				lblCount.setText(count.toString());
				dashboard.setLblCount(count.toString());

			}
		});
		dashboard.setColorPanelCount(Color.black);
		panelCount.add(btnCount, "cell 0 1,growx");

		JPanel panelBidButton = new JPanel();
		panelBidButton.setBorder(null);
		panelBidButton.setBackground(Color.WHITE);
		panelController.add(panelBidButton, "cell 3 3 1 2,grow");
		panelBidButton.setLayout(new MigLayout("", "[grow,fill]", "[grow]"));

		btnBid = new JButton("BID");
		btnBid.setEnabled(false);
		btnBid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// count
				count = 0;
				dashboard.setLblCount("-");
				dashboard.setColorPanelCount(Color.green);
				lblCount.setText("-");

				// current price
				newCurrPrice = IncreasePrice();
				lblCurrentPriceVal.setText(formatNumber(newCurrPrice));
				dashboard.setLblCurrentPrice(formatNumber(newCurrPrice));

				// set Status
				setTimeStatus();
			}
		});
		btnBid.setBackground(UIManager.getColor("InternalFrame.borderLight"));
		panelBidButton.add(btnBid, "cell 0 0,grow");

		JPanel panelNextEnd = new JPanel();
		panelNextEnd.setBackground(Color.WHITE);
		panelController.add(panelNextEnd, "cell 1 4,growx,aligny center");
		panelNextEnd.setLayout(new MigLayout("", "[grow,center]20[grow,center]20[grow,center]", "[grow,center]"));

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNext.setEnabled(false);
				btnExit.setEnabled(false);
				btnCount.setEnabled(true);
				btnBid.setEnabled(true);
				btnStart.setEnabled(false);
				dashboard.setColorPanelCount(Color.green);
			}
		});
		panelNextEnd.add(btnStart, "flowx,cell 0 0");

		btnExit = new JButton("END");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dashboard.dispose();
			}
		});

		btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dashboard.showBlackPanel();
				
				// loops till user enters a number
				Boolean catchInvalidNum = false;
				do {
					try {
						String bidderNo = JOptionPane.showInputDialog(null, "Enter Last Bidder No.", "Bidder Info", JOptionPane.QUESTION_MESSAGE);
						car.setBidderNo(Integer.parseInt(bidderNo));
						catchInvalidNum = false;
					} catch (NumberFormatException ex) {
						catchInvalidNum = true;
					}
				} while (catchInvalidNum);

				// bidder name
				String bidderName = JOptionPane.showInputDialog(null, "Enter Last Bidder Name", "Bidder Name", JOptionPane.QUESTION_MESSAGE);
				car.setBidderName(bidderName);

				updateCartoDB();
				
				recordController.save(car.getItemNo(), timeStampList);

				emptyTimeList(); // empty status

				dashboard.removeImages(); // remove image
				
				car = carController.getAuctionItem(); // get next auction item
				
				if (car != null) {
					setData();
					dashboard.showAuctionPanel();
					btnStart.setEnabled(true);
					count = 0;
					lblCount.setText("-");
					dashboard.setColorPanelCount(Color.black);
					dashboard.setLblSold("");
					dashboard.setLblCount("-");
					dashboard.revalidate();
					repaint();
					dashboard.repaint();
				} else {
					dashboard.showBlackPanel();
					JOptionPane.showMessageDialog(null, "Reached the end of Auction Item");
				}

			}
		});
		panelNextEnd.add(btnNext, "cell 1 0,alignx center");
		panelNextEnd.add(btnExit, "cell 2 0");

		if (car != null) {
			setData();
		} else {
			dashboard.showBlackPanel();
			JOptionPane.showMessageDialog(null, "No auction item to load. Register some cars.");
		}

	}

	// launches a new dashboard only if it is closed
	public void launchDashboard() {
		if (dashboard.isWindowClosed()) {
			dashboard = new AuctionDashboard();
			setData();
		}
	}

	// removes current image panel and sets new image
	public void setCarImage(String imagePath) {

		if (panelCarImg != null) {
			panelController.remove(panelCarImg);
			panelController.revalidate();
		}

		try {
			BufferedImage image = ImageIO.read(new File(imagePath));

			ResizeImage imgCar = new ResizeImage(image);
			imgCar.setBackground(Color.WHITE);

			panelCarImg = imgCar;
		} catch (Exception exp) {
			exp.printStackTrace();
			panelCarImg.setBackground(Color.white);
		}
		panelController.add(panelCarImg, "flowx,cell 0 0 2 3,wmin 10px");
		panelController.setBackground(Color.white);
	}

	private void setData() {

		// set Car Image
		setCarImage("images\\" + car.getImageFile());
		dashboard.setCarImage("images\\" + car.getImageFile());

		// set Item No.
		lblItemNoVal.setText(Integer.toString(car.getItemNo()));
		dashboard.setLblItemNo(Integer.toString(car.getItemNo()));

		// set Brand
		dashboard.setLblBrandVal(car.getBrand());

		// set Model
		dashboard.setLblModelVal(car.getModel());

		// set Year
		dashboard.setLblYearVal(Integer.toString(car.getYear()));

		// set InspGrade
		dashboard.setLblInspectionVal(car.getInspGrade());

		// set Starting Price
		dashboard.setLblStartingPrice(formatNumber(car.getStartingPrice()));
		lblStartingPrice.setText(formatNumber(car.getStartingPrice()));

		// set Current Price
		lblCurrentPriceVal.setText(formatNumber(car.getStartingPrice()));
		dashboard.setLblCurrentPrice(formatNumber(car.getStartingPrice()));
		currentAmount = car.getStartingPrice();

		// set Minimum Price
		lblMinPriceVal.setText(formatNumber(car.getMinSellPrice()));

	}

	public void loadRaiseAmount() {
		try (InputStream input = new FileInputStream("auction.config")) {
			prop = new Properties();

			prop.load(input);

			raiseAmount = Integer.parseInt(prop.getProperty("raise.amount"));

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	// returns loadRaiseAmount
	private int IncreasePrice() {

		currentAmount = raiseAmount + currentAmount;

		return currentAmount;
	}

	private String formatNumber(Integer currency) {

		String newFormat = NumberFormat.getNumberInstance(Locale.US).format(currency);

		return "K " + newFormat;
	}

	private void checkSold() {

		if (currentAmount >= car.getMinSellPrice()) {

			dashboard.setSold(true);
			car.setStatus("Sold");

		} else {
			dashboard.setSold(false);
			car.setStatus("Not Sold");
		}

	}

	private void updateCartoDB() {
		System.out.println("Final Price " + newCurrPrice);
		car.setFinalPrice(newCurrPrice);

		carController.updateOrSaveCar(car);
	}

	private void setTimeStatus() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime time = java.time.LocalTime.now();
		String timeStamp = formatter.format(time);

		timeStampList.add(timeStamp);

		String status1 = timeStampList.size() > 0 ? timeStampList.get(timeStampList.size() - 1) : "";

		lblStatus1.setText(status1);

		String status2 = timeStampList.size() > 1 ? timeStampList.get(timeStampList.size() - 2) : "";

		lblStatus2.setText(status2);

		String status3 = timeStampList.size() > 2 ? timeStampList.get(timeStampList.size() - 3) : "";

		lblStatus3.setText(status3);

	}

	public void emptyTimeList() {
		timeStampList.clear();

		lblStatus1.setText("");
		lblStatus2.setText("");
		lblStatus3.setText("");
	}

	private void setFontSize() {

		int width = getContentPane().getWidth();
		int height = getContentPane().getHeight();
		lblStartingPrice.setFont(new Font(Font.SANS_SERIF, Font.BOLD, (width + height) / 60));
		lblMinPriceVal.setFont(new Font(Font.SANS_SERIF, Font.BOLD, (width + height) / 40));
		lblCurrentPriceVal.setFont(new Font(Font.SANS_SERIF, Font.BOLD, (width + height) / 30));
		lblCount.setFont(new Font(Font.SANS_SERIF, Font.BOLD, (width + height) / 40));
		getContentPane().revalidate();
	}

}
