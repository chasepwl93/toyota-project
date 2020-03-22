package gui.menu;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;

import net.miginfocom.swing.MigLayout;
import utils.Log;
import utils.ResizeImage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;

import controller.CarController;
import model.pojo.Car;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 
 * This class is used by Menu gui's card layout to get panel to register new car
 * items. Also used by CarList class to edit the info of the chosen car item
 * from the table
 *
 */
public class Register {

	// Variables declaration - do not modify
	private JPanel panelRegister = new JPanel();
	private JTextField fieldItemNo = new JTextField();
	private JTextField fieldBrand = new JTextField();
	private JTextField fieldModel = new JTextField();
	private JTextField fieldYear = new JTextField();
	private JTextField fieldMilage = new JTextField();
	private JTextField fieldInspection = new JTextField();
	private JTextField fieldStartingPrice = new JTextField();
	private JTextField fieldMinPrice = new JTextField();
	private JTextArea fieldSellerInfo = new JTextArea();
	private JPanel imgCar = new JPanel();
	private CarController carController = CarController.getInstance();
	private String imgName = new String();
	private JLabel lblItemNoError;
	private JLabel lblYearError;
	private JLabel lblStartingPError;
	private JLabel lblMSPError;
	private Log logger = Log.getInstance();
	private Boolean SetUpdate = false;
	private Boolean updateImgFlag = false;
	public JButton btnSave;

	private File selectedFile;
	private String imageSourcePath;

	private Car car = new Car();

	public Register() {
		panelRegister.setForeground(Color.LIGHT_GRAY);
		panelRegister.setBackground(Color.WHITE);
		panelRegister.setLayout(new MigLayout("insets 15", "[10px:35%]20[10px:65%]",
				"[5%][5%]20[5%][5%]20[5%][5%]20[5%][5%]20[5%][5%]20[5%][5%]20[5%][:5%:5%]20[5%][5%]20[5%][15%]"));
		initialize();
	}

	public JPanel getPanel() {
		return panelRegister;
	}

	private void initialize() {
		JLabel lblItemNo = new JLabel("Item No.");
		lblItemNo.setForeground(Color.DARK_GRAY);
		lblItemNo.setFont(new Font("Verdana", Font.PLAIN, 14));
		JLabel lblBrand = new JLabel("Brand");
		lblBrand.setForeground(Color.DARK_GRAY);
		lblBrand.setFont(new Font("Verdana", Font.PLAIN, 14));
		JLabel lblModel = new JLabel("Model/Grade");
		lblModel.setForeground(Color.DARK_GRAY);
		lblModel.setFont(new Font("Verdana", Font.PLAIN, 14));
		JLabel lblYear = new JLabel("Year");
		lblYear.setForeground(Color.DARK_GRAY);
		lblYear.setFont(new Font("Verdana", Font.PLAIN, 14));
		JLabel lblMilage = new JLabel("Milage");
		lblMilage.setForeground(Color.DARK_GRAY);
		lblMilage.setFont(new Font("Verdana", Font.PLAIN, 14));
		JLabel lblInspection = new JLabel("Inspection Result");
		lblInspection.setForeground(Color.DARK_GRAY);
		lblInspection.setFont(new Font("Verdana", Font.PLAIN, 14));
		JLabel lblSellerInfo = new JLabel("Seller Information");
		lblSellerInfo.setForeground(Color.DARK_GRAY);
		lblSellerInfo.setFont(new Font("Verdana", Font.PLAIN, 14));
		JLabel lblStartingPrice = new JLabel("Starting Price");
		lblStartingPrice.setForeground(Color.DARK_GRAY);
		lblStartingPrice.setFont(new Font("Verdana", Font.PLAIN, 14));
		JLabel lblMinPrice = new JLabel("Minimum Selling Price");
		lblMinPrice.setForeground(Color.DARK_GRAY);
		lblMinPrice.setFont(new Font("Verdana", Font.PLAIN, 14));

		fieldItemNo.setFont(new Font("Verdana", Font.PLAIN, 15));
		fieldBrand.setFont(new Font("Verdana", Font.PLAIN, 15));
		fieldModel.setFont(new Font("Verdana", Font.PLAIN, 15));
		fieldYear.setFont(new Font("Verdana", Font.PLAIN, 15));
		fieldMilage.setFont(new Font("Verdana", Font.PLAIN, 15));
		fieldInspection.setFont(new Font("Verdana", Font.PLAIN, 15));
		fieldStartingPrice.setFont(new Font("Verdana", Font.PLAIN, 15));
		fieldMinPrice.setFont(new Font("Verdana", Font.PLAIN, 15));

		fieldSellerInfo.setFont(new Font("Verdana", Font.PLAIN, 15));
		fieldSellerInfo.setLineWrap(true);
		fieldSellerInfo.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(fieldSellerInfo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(450, 150));

		panelRegister.add(lblItemNo, "flowx,cell 0 0");
		panelRegister.add(fieldItemNo, "cell 0 1,height :25:,grow");
		panelRegister.add(lblBrand, "cell 0 2");
		panelRegister.add(fieldBrand, "cell 0 3,height :25:, grow");
		panelRegister.add(lblModel, "cell 0 4");
		panelRegister.add(fieldModel, "cell 0 5,height :25:, grow");
		panelRegister.add(lblYear, "flowx,cell 0 6");
		panelRegister.add(fieldYear, "cell 0 7,height :25:, grow");
		panelRegister.add(lblMilage, "cell 0 8");
		panelRegister.add(fieldMilage, "cell 0 9,height :25:,grow");
		panelRegister.add(lblInspection, "cell 0 10");
		panelRegister.add(fieldInspection, "cell 0 11,height :25:, grow");
		panelRegister.add(lblStartingPrice, "flowx,cell 0 12");
		panelRegister.add(fieldStartingPrice, "cell 0 13,height :25:,grow");
		panelRegister.add(lblMinPrice, "flowx,cell 0 14");
		panelRegister.add(fieldMinPrice, "cell 0 15,height :25:,grow");

		fieldItemNo.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				checkIntInput(ke, fieldItemNo, lblItemNoError);
			}

		});

		fieldYear.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				checkIntInput(ke, fieldYear, lblYearError);
			}
		});

		fieldStartingPrice.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				checkIntInput(ke, fieldStartingPrice, lblStartingPError);
			}
		});

		fieldMinPrice.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				checkIntInput(ke, fieldMinPrice, lblMSPError);
			}
		});

		JButton btnUpload = new JButton("Upload");
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser file = null;
				// Temporarily set System Look and Feel for the File choose.
				LookAndFeel previousLF = UIManager.getLookAndFeel(); // save current Look and Feel
				int result = 0;

				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // set System Look and Feel
					file = new JFileChooser();

					file.setCurrentDirectory(new File(System.getProperty("user.home")));
					// filter the files
					FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
					file.addChoosableFileFilter(filter);
					result = file.showSaveDialog(null);

					UIManager.setLookAndFeel(previousLF); // reverse back to previous Look and Feel
				} catch (Exception exp) {

				}

				// if the user click on save in Jfilechooser
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						selectedFile = file.getSelectedFile();
						imageSourcePath = selectedFile.getAbsolutePath();
						updateImgFlag = true;
						setImagePanel();
					} catch (NullPointerException ex) {
						JOptionPane.showMessageDialog(null, "Please choose a valid Image file.", "Inane error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				// if the user click on cancel in Jfilechooser
				else if (result == JFileChooser.CANCEL_OPTION) {
					System.out.println("No File Select");
				}
			}
		});
		panelRegister.add(btnUpload, "cell 1 15,alignx right,gapright 30");
		panelRegister.add(lblSellerInfo, "cell 0 16");
		panelRegister.add(scroll, "cell 0 17, grow");
		btnSave = new JButton("Save");
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (updateImgFlag == true) {
					copyImageToDest();
				}

				try {
					car.setItemNo(Integer.parseInt(fieldItemNo.getText()));

					car.setBrand(fieldBrand.getText());
					car.setModel(fieldModel.getText());
					car.setYear(Integer.parseInt(fieldYear.getText()));
					car.setMilage(fieldMilage.getText());
					car.setInspGrade(fieldInspection.getText());
					car.setStartingPrice(Integer.parseInt(fieldStartingPrice.getText()));
					car.setMinSellPrice(Integer.parseInt(fieldMinPrice.getText()));
					car.setSellerInfo(fieldSellerInfo.getText());
					car.setFinalPrice(Integer.parseInt("0"));
					car.setStatus("");

					carController.updateOrSaveCar(car);

					clearAllFields();

					car = new Car();

					JOptionPane.showMessageDialog(null, "Item saved.");
					
				} catch (NumberFormatException numErr) {
					JOptionPane.showMessageDialog(null,
							"Error Saving Car. Please recheck if all data is input correctly.", "Inane error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSave.setHorizontalAlignment(SwingConstants.LEFT);
		panelRegister.add(btnSave, "flowx,cell 1 17,alignx right,gapright 15,aligny bottom");

		lblItemNoError = new JLabel();
		lblItemNoError.setForeground(Color.RED);
		lblItemNoError.setHorizontalAlignment(SwingConstants.RIGHT);
		panelRegister.add(lblItemNoError, "cell 0 0,growx");

		lblYearError = new JLabel();
		lblYearError.setHorizontalAlignment(SwingConstants.RIGHT);
		lblYearError.setForeground(Color.RED);
		panelRegister.add(lblYearError, "cell 0 6,growx");

		lblStartingPError = new JLabel();
		lblStartingPError.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStartingPError.setForeground(Color.RED);
		panelRegister.add(lblStartingPError, "cell 0 12,growx");

		lblMSPError = new JLabel();
		lblMSPError.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMSPError.setForeground(Color.RED);
		panelRegister.add(lblMSPError, "cell 0 14,growx");

		logger.addLog(Level.INFO, "Register panel constructed");
	}

	public void clearAllFields() {

		panelRegister.remove(imgCar);

		fieldItemNo.setText("");
		fieldBrand.setText("");
		fieldModel.setText("");
		fieldYear.setText("");
		fieldMilage.setText("");
		fieldInspection.setText("");
		fieldStartingPrice.setText("");
		fieldMinPrice.setText("");
		fieldSellerInfo.setText("");

		panelRegister.revalidate();
		panelRegister.repaint();

	}

	public void setForUpdate(String id) {

		Car car = carController.getCarbyID(id);
		
		this.car = car;

		fieldItemNo.setText(Integer.toString(car.getItemNo()));
		fieldBrand.setText(car.getBrand());
		fieldModel.setText(car.getModel());
		fieldYear.setText(Integer.toString(car.getYear()));
		fieldMilage.setText(car.getMilage());
		fieldInspection.setText(car.getInspGrade());
		fieldStartingPrice.setText(Integer.toString(car.getStartingPrice()));
		fieldMinPrice.setText(Integer.toString(car.getMinSellPrice()));
		fieldSellerInfo.setText(car.getSellerInfo());

		imageSourcePath = "images\\" + car.getImageFile();
		
		setImagePanel();
		
		SetUpdate = true;

	}

	private void setImagePanel() {
		try {
			System.out.println(imageSourcePath);
			BufferedImage image = ImageIO.read(new File(imageSourcePath));

			ResizeImage resizedImg = new ResizeImage(image);
			resizedImg.setBackground(Color.WHITE);

			if (imgCar != null) {
				panelRegister.remove(imgCar);
				panelRegister.revalidate();
			}

			imgCar = resizedImg;

		} catch (IOException e1) {
			e1.printStackTrace();
			imgCar.setBackground(new Color(245, 245, 245));
		}

		panelRegister.add(imgCar, "cell 1 3 1 11,grow");
		panelRegister.revalidate();

	}

	// copies Jfilechooser image to destination /image folder
	private void copyImageToDest() {

		// if image from jFileChoose is not null
		if (imageSourcePath != null) {
			// parse file
			File source = new File(imageSourcePath);

			// get image name
			imgName = source.getName();
			// path to image file
			String fullDestinationFilePath = "images\\" + imgName; // full path to image file

			System.out.println("Full Destination Path " + fullDestinationFilePath);
			System.out.println("Source " + imageSourcePath);

			File fileOld = new File("images\\" + car.getImageFile());
			if (fileOld.exists()) {
				File deleteOld = new File("images\\" + car.getImageFile());
				System.out.println("Deleting image: " + "images\\" + car.getImageFile());
				logger.addLog(Level.SEVERE, "Found old image file. Deleting image: " + "images\\" + car.getImageFile());
				deleteOld.delete();
			}

			car.setImageFile(imgName);

			try {
				FileUtils.copyFile(source, new File("images\\" + imgName));
				System.out.println("Saved Image File: " + car.getImageFile());
				logger.addLog(Level.INFO, "Saved Image File" + car.getImageFile());
			} catch (IOException e) {
				e.printStackTrace();
				logger.addLog(Level.INFO, "");
			}
		}
	}

	// verifies the textfields only accept numbers. else error label is shown
	private void checkIntInput(KeyEvent ke, JTextField tf, JLabel lblError) {
		if (tf.getText().matches("^[0-9]*$")) {
			lblError.setText("");
		} else {
			lblError.setText("Input values between 0 - 9");
		}
	}

}
