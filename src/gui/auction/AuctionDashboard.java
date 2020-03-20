package gui.auction;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import utils.ResizeImage;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AuctionDashboard extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel panelCarImg = new JPanel();
	private JPanel panelSoldImg = new JPanel();
	private JPanel panelSold = new JPanel();
	private CardLayout cardLayout;
	private JPanel parentPanel;
	private JPanel panelCount;
	private JPanel contentPane;
	private JLabel lblCurrentPrice;
	private JLabel lblStartingPrice;
	private JLabel lblCount;
	private JLabel lblInspectionVal;
	private JLabel lblBrandVal;
	private JLabel lblModelVal;
	private JLabel lblYearVal;
	private JLabel lblSold;
	private JLabel lblItemNo;
	private JLabel lblItemNoVal;
	private Boolean isWindowClosed = false;

	/**
	 * Create the frame.
	 */
	public AuctionDashboard() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				isWindowClosed = true;
			}
		});
		setTitle("Dashboard");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 754, 492);

		setMinimumSize(new Dimension(1185, 750));
		setMinimumSize(new Dimension(1366, 768));
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		addComponentListener(new ComponentListener() {

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

		JPanel blackPanel = new JPanel();
		blackPanel.setBackground(Color.black);

		parentPanel = new JPanel();

		setContentPane(parentPanel);
		parentPanel.setLayout(new CardLayout(0, 0));
		parentPanel.add(contentPane, "auctionPanel");
		parentPanel.add(blackPanel, "blackPanel");

		cardLayout = (CardLayout) parentPanel.getLayout();
		cardLayout.show(parentPanel, "auctionPanel");

		contentPane.setLayout(new MigLayout("", "[:50%:50%,fill][:30%:30%,fill][:20%:20%,fill]",
				"[:20%:20%,fill][:50%:50%,fill][:30%:30%,fill]"));
		showOnScreen(1, this);
		setVisible(true);
		setAlwaysOnTop(true);

		JPanel panelStartingPrice = new JPanel();
		panelStartingPrice.setBackground(Color.WHITE);
		panelStartingPrice.setBorder(BorderFactory.createTitledBorder(null, "Starting Price", TitledBorder.LEFT,
				TitledBorder.TOP, new Font("arial", Font.BOLD, setTitledFontSize()), Color.black));
		contentPane.add(panelStartingPrice, "cell 1 0,alignx center,growy");
		panelStartingPrice.setLayout(new MigLayout("", "[grow,fill]", "[grow,fill]"));

		lblStartingPrice = new JLabel("");
		lblStartingPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblStartingPrice.setBackground(Color.WHITE);
		// lblStartingPrice.setFont(new Font("SansSerif", Font.PLAIN, 40));
		// setFontSize(lblStartingPrice);
		panelStartingPrice.add(lblStartingPrice, "cell 0 0,wmin 10px,grow");

		panelCount = new JPanel();
		panelCount.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panelCount.setBackground(Color.GREEN);
		contentPane.add(panelCount, "cell 2 0,grow");
		panelCount.setLayout(new MigLayout("", "[grow,fill]", "[grow,fill]"));

		lblCount = new JLabel("-");

		lblCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblCount.setForeground(Color.DARK_GRAY);
		// lblCount.setFont(new Font("Tahoma", Font.PLAIN, 25));
		panelCount.add(lblCount, "cell 0 0,alignx center,aligny center");

		JPanel panelCurrentPrice = new JPanel();
		panelCurrentPrice.setBackground(Color.WHITE);

		contentPane.add(panelCurrentPrice, "cell 1 1 2 1,grow");
		panelCurrentPrice.setBorder(BorderFactory.createTitledBorder(null, "Current Price", TitledBorder.LEFT,
				TitledBorder.TOP, new Font("arial", Font.BOLD, setTitledFontSize()), Color.black));
		panelCurrentPrice.setLayout(new MigLayout("", "[grow,fill]", "[grow,center]"));

		lblCurrentPrice = new JLabel("");
		lblCurrentPrice.setBackground(Color.WHITE);
		lblCurrentPrice.setHorizontalAlignment(SwingConstants.CENTER);
		// lblCurrentPrice.setFont(new Font("SansSerif", Font.PLAIN, 50));

		panelCurrentPrice.add(lblCurrentPrice, "cell 0 0,wmin 10px,grow");
//		setFontSize(lblCurrentPrice);

		JPanel panelCarInfo = new JPanel();
		panelCarInfo.setBackground(Color.WHITE);
		panelCarInfo.setBorder(BorderFactory.createTitledBorder(null, "Information", TitledBorder.CENTER,
				TitledBorder.TOP, new Font("arial", Font.BOLD, setTitledFontSize()), Color.black));
		contentPane.add(panelCarInfo, "cell 0 2,grow");
		panelCarInfo.setLayout(new MigLayout("", "[]80px[]80px[]",
				"[:20%:20%]15px[:20%:20%]15px[:20%:20%]15px[:20%:20%]15px[:20%:20%]"));

		lblItemNo = new JLabel("Item No");
		lblItemNo.setBackground(Color.WHITE);
		lblItemNo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelCarInfo.add(lblItemNo, "cell 0 0");

		lblItemNoVal = new JLabel();
		lblItemNoVal.setBackground(Color.WHITE);
		lblItemNoVal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelCarInfo.add(lblItemNoVal, "cell 2 0");

		JLabel lblBrand = new JLabel("Brand");
		lblBrand.setBackground(Color.WHITE);
		lblBrand.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelCarInfo.add(lblBrand, "cell 0 1");

		lblBrandVal = new JLabel();
		lblBrandVal.setBackground(Color.WHITE);
		lblBrandVal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelCarInfo.add(lblBrandVal, "cell 2 1");

		JLabel lblModel = new JLabel("Model");
		lblModel.setBackground(Color.WHITE);
		lblModel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelCarInfo.add(lblModel, "cell 0 2");

		lblModelVal = new JLabel();
		lblModelVal.setBackground(Color.WHITE);
		lblModelVal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelCarInfo.add(lblModelVal, "cell 2 2");

		JLabel lblYear = new JLabel("Year");
		lblYear.setBackground(Color.WHITE);
		lblYear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelCarInfo.add(lblYear, "cell 0 3");

		lblYearVal = new JLabel();
		lblYearVal.setBackground(Color.WHITE);
		lblYearVal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelCarInfo.add(lblYearVal, "cell 2 3");

		JLabel lblInspection = new JLabel("Insp Grade");
		lblInspection.setBackground(Color.WHITE);
		lblInspection.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelCarInfo.add(lblInspection, "cell 0 4");

		lblInspectionVal = new JLabel();
		lblInspectionVal.setBackground(Color.WHITE);
		lblInspectionVal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelCarInfo.add(lblInspectionVal, "cell 2 4");

		panelSold.setBackground(Color.WHITE);
		contentPane.add(panelSold, "cell 1 2 2 1,grow");
		panelSold.setLayout(new MigLayout("", "[grow,fill][grow,fill]", "[grow,fill]"));

		lblSold = new JLabel();
		lblSold.setBackground(Color.WHITE);
		lblSold.setForeground(Color.BLACK);

		panelSold.add(lblSold, "cell 1 0,wmin 10px,alignx center,aligny center");
	}
	
	public Boolean isWindowClosed() {
		return isWindowClosed;
	}

	private void setFontSize() {

		int width = getWidth();
		int height = getHeight();
		lblStartingPrice.setFont(new Font(Font.SANS_SERIF, Font.BOLD, (width + height) / 60));
		lblCount.setFont(new Font(Font.SANS_SERIF, Font.BOLD, (width + height) / 40));
		lblCurrentPrice.setFont(new Font(Font.SANS_SERIF, Font.BOLD, (width + height) / 30));
		lblSold.setFont(new Font(Font.SANS_SERIF, Font.BOLD, (width + height) / 40));
		getContentPane().revalidate();
	}

	private int setTitledFontSize() {
		int width = getWidth();
		int height = getHeight();
		int fontSize = (width + height) / 130;

		return fontSize;
	}

	private static void showOnScreen(int screen, JDialog frame) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();

		if (screen > -1 && screen < gs.length) {
			if (!gs[screen].isFullScreenSupported()) {
				System.out.println("Full screen mode not supported");

			} else {

				gs[screen].setFullScreenWindow(frame);
			}

		} else if (gs.length > 0) {
			System.out.println("Second Screen not detected.");
			// gs[0].setFullScreenWindow(frame);
		} else {
			throw new RuntimeException("No Screens Found");
		}
	}

	public void setSold(Boolean sold) {
		try {
			BufferedImage image;
			if (sold == true) {
				image = ImageIO.read(new File("resources\\Correct.png"));
				lblSold.setText("Sold");
			} else {
				image = ImageIO.read(new File("resources\\Wrong.png"));
				lblSold.setText("Not Sold");
			}

			ResizeImage imgSold = new ResizeImage(image);
			imgSold.setBackground(Color.WHITE);

			panelSoldImg = imgSold;
		} catch (Exception exp) {
			exp.printStackTrace();
			panelSoldImg.setBackground(Color.white);
		}
		panelSold.add(panelSoldImg, "cell 0 0, gapy 30 30, wmin 10px");
	}

	public void setCarImage(String imagePath) {
		try {
			BufferedImage image = ImageIO.read(new File(imagePath));

			ResizeImage imgCar = new ResizeImage(image);
			imgCar.setBackground(Color.WHITE);

			panelCarImg = imgCar;
		} catch (Exception exp) {
			exp.printStackTrace();
			panelCarImg.setBackground(Color.white);
		}
		contentPane.add(panelCarImg, "cell 0 0 1 2,wmin 10px");
	}

	public void setColorPanelCount(Color color) {
		panelCount.setBackground(color);
	}

	public void setLblCurrentPrice(String str) {
		lblCurrentPrice.setText(str);
	}

	public void setLblStartingPrice(String str) {
		lblStartingPrice.setText(str);
	}

	public void setLblCount(String str) {
		lblCount.setText(str);
	}

	public void setLblInspectionVal(String str) {
		lblInspectionVal.setText(str);
	}

	public void setLblBrandVal(String str) {
		lblBrandVal.setText(str);
	}

	public void setLblModelVal(String str) {
		lblModelVal.setText(str);
	}

	public void setLblYearVal(String str) {
		lblYearVal.setText(str);
	}

	public void setLblItemNo(String str) {
		lblItemNoVal.setText(str);
	}

	public void setLblSold(String str) {
		lblSold.setText(str);
	}

	public void removeImages() {

		contentPane.remove(panelCarImg);
		panelSold.remove(panelSoldImg);

	}

	public void showBlackPanel() {
		cardLayout.show(parentPanel, "blackPanel");
	}

	public void showAuctionPanel() {
		cardLayout.show(parentPanel, "auctionPanel");
	}

}
