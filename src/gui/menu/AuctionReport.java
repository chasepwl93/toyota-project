package gui.menu;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import utils.Log;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import org.apache.commons.io.FileUtils;

import controller.CarController;
import controller.RecordController;
import model.pojo.Car;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

public class AuctionReport {

	private JPanel panelAuctionReport = new JPanel();
	private Log logger = Log.getInstance();
	private JTextField searchBar;
	private JTable tableAuctionReport;
	private TableRowSorter<DefaultTableModel> sorter;

	// create a table model to set Column headers to this table
	Object[] columns = { "Item No.", "Brand", "Model/Grade", "Insp", "Starting Price", "MSP", "Final Price", "Status",
			"Bidder No.", "Bidder Name" };

	private DefaultTableModel model = new DefaultTableModel();

	public AuctionReport() {
		panelAuctionReport.setForeground(Color.LIGHT_GRAY);
		panelAuctionReport.setBackground(new Color(230, 230, 250));
		panelAuctionReport.setLayout(new MigLayout("insets 0", "[grow]", "[85px][grow]"));
		initialize();
		logger.addLog(Level.INFO, "Auction Report panel constructed");
	}

	public JPanel getPanel() {
		return panelAuctionReport;
	}

	public void initialize() {

		JPanel panelTop = new JPanel();
		panelTop.setBackground(new Color(71, 120, 197));
		panelAuctionReport.add(panelTop, "cell 0 0,grow");
		panelTop.setLayout(new MigLayout("insets 0", "[][grow]", "[83px]"));

		JButton btnExportCSV = new JButton("Export CSV");
		btnExportCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ToCSV();
			}
		});

		btnExportCSV.setHorizontalAlignment(SwingConstants.LEADING);

		panelTop.add(btnExportCSV, "flowx,cell 0 0,growx,gapx 10 10,aligny center");
		JButton btnBidHistory = new JButton("Bid History");
		btnBidHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// i = the index of the selected row
				int i = tableAuctionReport.getSelectedRow();

				if (i >= 0) {
					int row = tableAuctionReport.convertRowIndexToModel(i);
					int itemNo = Integer.parseInt(model.getValueAt(row, 0).toString());
					String status = model.getValueAt(row, 7).toString();
					int bidderNo = Integer.parseInt(model.getValueAt(row, 8).toString());
					String bidderName = model.getValueAt(row, 9).toString();

					System.out.println("Row" + row + "Item No " + itemNo + "BidderNo" + bidderNo);

					new AuctionSalesRecord(itemNo, status, bidderNo, bidderName);
				}

			}
		});
		panelTop.add(btnBidHistory, "cell 0 0,aligny center");

		JLabel lblSearchImg = new JLabel("");
		lblSearchImg.setIcon(ResizeImage(new ImageIcon("resources//search.png")));

		panelTop.add(lblSearchImg, "cell 1 0,alignx right,gap right 5");

		searchBar = new JTextField();
		searchBar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panelTop.add(searchBar, "cell 1 0,gap right 15");
		searchBar.setColumns(15);
		searchBar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String query = searchBar.getText().toLowerCase();
				filter(query, 0);
			}
		});

		JButton btnDelete = new JButton("Clear All Data");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CarController cController = CarController.getInstance();
				cController.deleteAll();

				RecordController rController = RecordController.getInstance();
				rController.deleteAll();

				refreshTableData();

				File directory = new File("images\\");
				try {
					FileUtils.cleanDirectory(directory);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		panelTop.add(btnDelete, "cell 0 0,gapx 10 0");

		JPanel panelTable = new JPanel();
		panelAuctionReport.add(panelTable, "cell 0 1,grow");
		panelTable.setLayout(new MigLayout("", "[grow,fill]", "[grow,fill]"));

		JScrollPane scrCarTable = new JScrollPane();
		panelTable.add(scrCarTable, "cell 0 0,alignx left,aligny top");

		tableAuctionReport = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};

		scrCarTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
		tableAuctionReport.setFont(new java.awt.Font("Segoe UI", 0, 13));

		// set table headers
		model.setColumnIdentifiers(columns);

		// set table header color
		JTableHeader tableHeader = tableAuctionReport.getTableHeader();
		tableHeader.setBackground(Color.white);
		tableHeader.setForeground(Color.black);
		tableHeader.setFont(new java.awt.Font("Segoe UI", 0, 15));

		tableAuctionReport.setModel(model);
		tableAuctionReport.setGridColor(new java.awt.Color(255, 255, 255));
		tableAuctionReport.setRowHeight(22);
		scrCarTable.setViewportView(tableAuctionReport);

		refreshTableData();

	}

	private ImageIcon ResizeImage(ImageIcon MyImage) {
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}

	public void refreshTableData() {
		CarController cars = CarController.getInstance();
		ArrayList<Car> carsList = cars.getAllCars();

		model.setRowCount(0);

		for (Car car : carsList) {
			Object[] data = { car.getItemNo(), car.getBrand(), car.getModel(), car.getInspGrade(),
					car.getStartingPrice(), car.getMinSellPrice(), car.getFinalPrice(), car.getStatus(),
					car.getBidderNo(), car.getBidderName() };
			model.addRow(data);
		}
	}

	private void filter(String query, int column) {
		sorter = new TableRowSorter<>(model);
		tableAuctionReport.setRowSorter(sorter);
		sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, column));
	}

	private void ToCSV() {
		CarController carController = CarController.getInstance();
		RecordController recordController = RecordController.getInstance();
		recordController.ToCSV();
		carController.ToCSV();

	}
}
