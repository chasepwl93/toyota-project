package gui.menu;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.RecordController;

import javax.swing.JScrollBar;
import java.awt.Color;
import javax.swing.SwingConstants;

public class AuctionSalesRecord extends JFrame {
	private JTextField fieldTotalBid;
	private JTextField fieldBidderNo;
	private RecordController recordController;
	private final String newline = "\n";
	private JTextField textField;

	public AuctionSalesRecord(int itemNo, String status, int bidderNo, String bidderName) {
		setTitle("Bid History");
		recordController = new RecordController();
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setMinimumSize(new Dimension(410, 340));
		setVisible(true);
		setResizable(false);
		
		//center window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
     	Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
     	Point newLocation = new Point(middle.x - (getWidth() / 2), middle.y - (getHeight() / 2));
     	setLocation(newLocation);

		JLabel lblTotalBid = new JLabel("Total Bid");
		lblTotalBid.setHorizontalAlignment(SwingConstants.LEFT);
		lblTotalBid.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTotalBid.setBounds(22, 42, 128, 25);
		getContentPane().add(lblTotalBid);

		JLabel lblBidderNo = new JLabel("Last Bidder No.");
		lblBidderNo.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblBidderNo.setBounds(22, 122, 128, 25);
		getContentPane().add(lblBidderNo);

		fieldTotalBid = new JTextField();
		fieldTotalBid.setFont(new Font("Verdana", Font.PLAIN, 15));
		fieldTotalBid.setHorizontalAlignment(SwingConstants.CENTER);
		fieldTotalBid.setBounds(22, 78, 128, 31);
		fieldTotalBid.setEditable(false);
		getContentPane().add(fieldTotalBid);
		fieldTotalBid.setColumns(10);

		fieldBidderNo = new JTextField();
		fieldBidderNo.setFont(new Font("Verdana", Font.PLAIN, 15));
		fieldBidderNo.setHorizontalAlignment(SwingConstants.CENTER);
		fieldBidderNo.setBounds(23, 153, 128, 31);
		fieldBidderNo.setEditable(false);
		getContentPane().add(fieldBidderNo);
		fieldBidderNo.setColumns(10);

		JTextArea fieldBidTime = new JTextArea();
		fieldBidTime.setEditable(false);

		fieldBidTime.setFont(new Font("Verdana", Font.PLAIN, 15));
		fieldBidTime.setLineWrap(true);
		fieldBidTime.setWrapStyleWord(true);

		JScrollPane scrollBar = new JScrollPane(fieldBidTime, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollBar.setBounds(199, 41, 156, 221);
		getContentPane().add(scrollBar);
		
		ArrayList<String> recordList = recordController.getByItemNo(itemNo);
		
		System.out.println(recordList.size());
		
		if( recordList.size() > 0 ) {
		
			fieldBidTime.append(recordList.get(0));
		
			for (int i = 1; i < recordList.size(); i++) { 
				fieldBidTime.append(newline + recordList.get(i));
			}
		
		}
		
		fieldTotalBid.setText(Integer.toString(recordList.size()));
		
		fieldBidderNo.setText(Integer.toString(bidderNo));
		
		JLabel lblBidderName = new JLabel("Last Bidder Name");
		lblBidderName.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblBidderName.setBounds(21, 200, 128, 25);
		getContentPane().add(lblBidderName);
		
		textField = new JTextField(bidderName);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Verdana", Font.PLAIN, 15));
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(22, 231, 128, 31);
		getContentPane().add(textField);
		
		getContentPane().revalidate();
		getContentPane().repaint();
		
	}
}
