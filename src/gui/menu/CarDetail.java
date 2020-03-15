package gui.menu;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.pojo.Car;
import net.miginfocom.swing.MigLayout;

public class CarDetail {

	public JFrame frame;
	private Register register;
	private JPanel panelCarDetail;

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public CarDetail() {
	}
	
//	public CarDetail(Car car) {
//		
//	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	public void initialize(String id) {
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setBounds(100, 100, 945, 717);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// center gui window
     	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
     	Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
     	Point newLocation = new Point(middle.x - (frame.getWidth() / 2), middle.y - (frame.getHeight() / 2));
     	frame.setLocation(newLocation);
		
		register = new Register();
		
		register.setForUpdate(id);
		
		panelCarDetail = register.getPanel();

		
		frame.getContentPane().add(panelCarDetail);
		frame.getContentPane().setLayout(new MigLayout("insets 0", "[grow,fill]", "[grow,fill]"));
	}

}
