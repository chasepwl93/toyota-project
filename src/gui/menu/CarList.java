package gui.menu;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import controller.CarController;

import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.RowFilter;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JScrollPane;

import javax.swing.JTable;

import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

import java.io.File;
import java.util.ArrayList;

import model.pojo.Car;

public class CarList {
	private CarController carController = CarController.getInstance();
	private JPanel panelCarList = new JPanel();
	private JTextField fieldSearch;
	private JTable tableCar;
	private TableRowSorter<DefaultTableModel> sorter;

	// create a table model to set Column headers to this table
	Object[] columns = { "id", "Item No.", "Brand", "Model/Grade", "Year", "Milage", "Insp", "Starting Price", "MSP" };

	private DefaultTableModel model = new DefaultTableModel();

	public CarList() {
		panelCarList.setForeground(Color.LIGHT_GRAY);
		panelCarList.setBackground(new Color(230, 230, 250));
		panelCarList.setLayout(new MigLayout("insets 0", "[grow]", "[85px][grow]"));

		initialize();
	}

	public JPanel getPanel() {
		return panelCarList;
	}

	public void initialize() {

		JPanel panelTop = new JPanel();
		panelTop.setBackground(new Color(71, 120, 197));
		panelCarList.add(panelTop, "cell 0 0,grow");
		panelTop.setLayout(new MigLayout("insets 0", "[][grow]", "[83px]"));

		JButton btnDetail = new JButton("Detail");
		btnDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// i = the index of the selected row
				int i = tableCar.getSelectedRow();

				if (i >= 0) {
					int row = tableCar.convertRowIndexToModel(i);
					String id = model.getValueAt(row, 0).toString();
					editCarPopup(id);
				}
			}
		});

		btnDetail.setHorizontalAlignment(SwingConstants.LEADING);

		panelTop.add(btnDetail, "flowx,cell 0 0,growx,gapx 10 10,aligny center");
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTableData();
			}
		});
		panelTop.add(btnRefresh, "cell 0 0,aligny center");
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// i = the index of the selected row
				int i = tableCar.getSelectedRow();

				if (i >= 0) {
					int row = tableCar.convertRowIndexToModel(i);
					String id = model.getValueAt(row, 0).toString();
					carController.deleteCarByID(id);
					refreshTableData();
				}

			}
		});
		panelTop.add(btnDelete, "cell 0 0,gapx 10 10,aligny center");

		JLabel lblSearchImg = new JLabel("");
		lblSearchImg.setIcon(ResizeImage(new ImageIcon("resources/search.png")));

		panelTop.add(lblSearchImg, "cell 1 0,alignx right,gap right 5");

		fieldSearch = new JTextField();
		fieldSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panelTop.add(fieldSearch, "cell 1 0,gap right 15");
		fieldSearch.setColumns(15);
		fieldSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String query = fieldSearch.getText().toLowerCase();
				filter(query, 1);
			}
		});

		JButton btnImportCSV = new JButton("Import Excel");
		btnImportCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser file = null;
				LookAndFeel previousLF = UIManager.getLookAndFeel();
				int result = 0;
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					file = new JFileChooser();
					file.setCurrentDirectory(new File(System.getProperty("user.home")));
					// filter the files
					FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Excel", "xlsx", "xls");
					file.addChoosableFileFilter(filter);
					result = file.showSaveDialog(null);
					UIManager.setLookAndFeel(previousLF);
				} catch (Exception exp) {
				}

				// if the user click on save in Jfilechooser
				if (result == JFileChooser.APPROVE_OPTION) {

					File selectedFile = file.getSelectedFile();
					String sourcePath = selectedFile.getAbsolutePath();
					System.out.println(sourcePath);
					carController.importExcel(sourcePath);

					refreshTableData();

				}
				// if the user click on cancel in Jfilechooser
				else if (result == JFileChooser.CANCEL_OPTION) {
					System.out.println("No File Select");
				}

			}
		});
		panelTop.add(btnImportCSV, "cell 0 0");

		JPanel panelTable = new JPanel();
		panelCarList.add(panelTable, "cell 0 1,grow");
		panelTable.setLayout(new MigLayout("", "[grow,fill]", "[grow,fill]"));

		JScrollPane scrCarTable = new JScrollPane();
		panelTable.add(scrCarTable, "cell 0 0,alignx left,aligny top");

		tableCar = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};

		scrCarTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
		// tableCar.setEditingRow(0);
		tableCar.setFont(new java.awt.Font("Segoe UI", 0, 13));

		// set table headers
		model.setColumnIdentifiers(columns);

		// set table header color
		JTableHeader tableHeader = tableCar.getTableHeader();
		tableHeader.setBackground(Color.white);
		tableHeader.setForeground(Color.black);
		tableHeader.setFont(new java.awt.Font("Segoe UI", 0, 15));

		tableCar.setModel(model);

		// hide column
		TableColumnModel tcm = tableCar.getColumnModel();
		tcm.removeColumn(tcm.getColumn(0)); // hide id

		refreshTableData();

		tableCar.setGridColor(new java.awt.Color(255, 255, 255));
		tableCar.setRowHeight(22);
		scrCarTable.setViewportView(tableCar);

	}

	private ImageIcon ResizeImage(ImageIcon MyImage) {
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}

	public void refreshTableData() {

		ArrayList<Car> carsList = carController.getAllCars();

		model.setRowCount(0);

		for (Car car : carsList) {
			Object[] data = { car.getId(), car.getItemNo(), car.getBrand(), car.getModel(), car.getYear(),
					car.getMilage(), car.getInspGrade(), car.getStartingPrice(), car.getMinSellPrice() };
			model.addRow(data);
		}
	}

	private void editCarPopup(String id) {

		final JFrame detailsFrame = new JFrame();
		JPanel panelCarDetail = new JPanel();

		detailsFrame.setResizable(false);
		detailsFrame.setBounds(100, 100, 945, 717);
		detailsFrame.setVisible(true);
		detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// center gui window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (detailsFrame.getWidth() / 2),
				middle.y - (detailsFrame.getHeight() / 2));
		detailsFrame.setLocation(newLocation);

		Register register = new Register();

		register.setForUpdate(id);

		panelCarDetail = register.getPanel();
		
		register.btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				detailsFrame.dispose();
				refreshTableData();
			}
		});

		detailsFrame.getContentPane().add(panelCarDetail);
		detailsFrame.getContentPane().setLayout(new MigLayout("insets 0", "[grow,fill]", "[grow,fill]"));
	}

	private void filter(String query, int column) {
		sorter = new TableRowSorter<>(model);
		tableCar.setRowSorter(sorter);
		sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, column));
	}

}
