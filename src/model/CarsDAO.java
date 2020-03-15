package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import gui.Menu;
import model.pojo.Car;

public class CarsDAO {

	private Connection con;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private Properties prop;

	private String tableName;

	public CarsDAO() {
		try (InputStream input = new FileInputStream("dbconfig.properties")) {
			// getClass().getResource("dbconfig.properties")
			prop = new Properties();

			// load a properties file
			prop.load(input);

			// db parameters
			String url = prop.getProperty("jdbc.url");
			// create a connection to the database
			Class.forName(prop.getProperty("jdbc.driver"));
			con = DriverManager.getConnection(url);

			// get table name
			tableName = prop.getProperty("tablename.cars");

			System.out.println("Connection to SQLite has been established.");

		} catch (IOException | SQLException | ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Database Connection Error. Check Properties file for connection.",
					"Inane error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	public ArrayList<Car> getAllItems() {

		ArrayList<Car> carsList = new ArrayList<>();

		// select all from db
		String query = "select * from " + tableName;
		try {
			Statement stmt = con.createStatement();
			resultSet = stmt.executeQuery(query); // execute query

			// for each employee in resultset add to arraylist
			while (resultSet.next()) {

				Car car = new Car();

				car.setId(resultSet.getInt("id"));
				car.setImageFile(resultSet.getString("image"));
				car.setItemNo(resultSet.getInt("item_no"));
				car.setBrand(resultSet.getString("brand"));
				car.setModel(resultSet.getString("model"));
				car.setYear(resultSet.getInt("year"));
				car.setMilage(resultSet.getString("milage"));
				car.setInspGrade(resultSet.getString("insp_grade"));
				car.setStartingPrice(resultSet.getInt("starting_price"));
				car.setMinSellPrice(resultSet.getInt("min_price"));
				car.setSellerInfo(resultSet.getString("seller_info"));
				car.setFinalPrice(resultSet.getInt("final_price"));
				car.setStatus(resultSet.getString("status"));
				car.setBidderNo(resultSet.getInt("bidder_no"));

				carsList.add(car);
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error Reading Database.",
					"Inane error", JOptionPane.ERROR_MESSAGE);
		}

		return carsList;

	}

	public Car getItembyId(String id) {

		int userID = Integer.parseInt(id);

		try (PreparedStatement preparedStatement = con
				.prepareStatement("Select * from " + tableName + " where `id` = ?;")) {

			preparedStatement.setInt(1, userID);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				Car car = new Car();
				car.setId(resultSet.getInt("id"));
				car.setImageFile(resultSet.getString("image"));
				car.setItemNo(resultSet.getInt("item_no"));
				car.setBrand(resultSet.getString("brand"));
				car.setModel(resultSet.getString("model"));
				car.setYear(resultSet.getInt("year"));
				car.setMilage(resultSet.getString("milage"));
				car.setInspGrade(resultSet.getString("insp_grade"));
				car.setStartingPrice(resultSet.getInt("starting_price"));
				car.setMinSellPrice(resultSet.getInt("min_price"));
				car.setSellerInfo(resultSet.getString("seller_info"));
				car.setFinalPrice(resultSet.getInt("final_price"));
				car.setStatus(resultSet.getString("status"));
				car.setBidderNo(resultSet.getInt("bidder_no"));

				return car;

			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			JOptionPane.showMessageDialog(null, "Error Reading Database.",
					"Inane error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		return null;

	}

	public Boolean updateOrSave(Car car) {
		// create the java mysql update preparedstatement
		String query = "INSERT INTO cars "
				+ "(image, item_no, brand, model, year, milage, insp_grade, starting_price, min_price, seller_info, final_price, status, bidder_no)"
				+ "  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" + "  ON CONFLICT(item_no)" + "  DO UPDATE SET "
				+ "image=excluded.image, brand=excluded.brand, model=excluded.model, year=excluded.year,"
				+ "milage=excluded.milage, insp_grade=excluded.insp_grade,starting_price=excluded.starting_price,"
				+ "min_price=excluded.min_price, seller_info=excluded.seller_info, final_price=excluded.final_price, status=excluded.status, bidder_no=excluded.bidder_no;";

		try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
			preparedStmt.setString(1, car.getImageFile());
			preparedStmt.setInt(2, car.getItemNo());
			preparedStmt.setString(3, car.getBrand());
			preparedStmt.setString(4, car.getModel());
			preparedStmt.setInt(5, car.getYear());
			preparedStmt.setString(6, car.getMilage());
			preparedStmt.setString(7, car.getInspGrade());
			preparedStmt.setInt(8, car.getStartingPrice());
			preparedStmt.setInt(9, car.getMinSellPrice());
			preparedStmt.setString(10, car.getSellerInfo());
			preparedStmt.setInt(11, car.getFinalPrice());
			preparedStmt.setString(12, car.getStatus());
			preparedStmt.setInt(13, car.getBidderNo());
			

			// execute the java preparedstatement
			preparedStmt.executeUpdate();

			return true;

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error Writing Database.",
					"Inane error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

	}

	public void importExcel(String file) {
		try {
			FileInputStream input = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(file);
			XSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			System.out.println("Sheet Last row" + sheet.getLastRowNum());
			for (int i = 1; i <= (sheet.getLastRowNum() - 1); i++) {

				System.out.println(i);

				row = sheet.getRow(i);

				Car car = new Car();
				// car.setId(resultSet.getInt("id"));

				// cellToString(row.getCell(0));
				car.setImageFile(cellToString(row.getCell(0)));
				car.setItemNo(formatNumber(cellToString(row.getCell(1))));
				car.setBrand(cellToString(row.getCell(2)));
				car.setModel(cellToString(row.getCell(3)));
				car.setYear(formatNumber(cellToString(row.getCell(4))));
				car.setMilage(cellToString(row.getCell(5)));
				car.setInspGrade(cellToString(row.getCell(6)));
				int StartingPrice = formatNumber(cellToString(row.getCell(7)));
				car.setStartingPrice(StartingPrice);
				car.setMinSellPrice(formatNumber(cellToString(row.getCell(8))));

				car.setSellerInfo(cellToString(row.getCell(9)));
				car.setFinalPrice(0);
				car.setStatus("");
				car.setBidderNo(0);

				// row = sheet.getRow(i);
				// String col2 = row.getCell(1).getStringCellValue();

				updateOrSave(car);
				System.out.println("Import rows " + i);
			
			}
			
			wb.close();
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error Importing Excel.",
					"Inane error", JOptionPane.ERROR_MESSAGE);
			LogError(e);
			e.printStackTrace();
		}
	}
	
	private static String cellToString(Cell cell) {
		Object result = null;
		try {
		CellType type = cell.getCellType();
		

			switch (type) {
			case NUMERIC:
				 result = cell.getNumericCellValue();
				break;
			case STRING:
				result = cell.getStringCellValue();
				break;
			default:
				throw new RuntimeException("there are no support for this type of cell");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			result = "";
		}
		return result.toString();
	}

	public Boolean deleteCar(String id) {
		
		Car car = getItembyId(id);
		String imgName = car.getImageFile();
		
		String imagePath = "images\\" + imgName;
		
		System.out.println("Full Destination Path " + imagePath);
		
		File dest = new File(imagePath);
		if (dest.exists()) {
			File deleteOld = new File(imagePath);
			deleteOld.delete();
		}
		
		int userID = Integer.parseInt(id);
		
		
		try (PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM " + tableName + " WHERE id=?")) {

			preparedStatement.setInt(1, userID);

			int confirm = preparedStatement.executeUpdate();

			// rows affected
			System.out.println("Deleted? : " + confirm);

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error Deleting Item.",
					"Inane error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

	}

	public void closeConnection() {
		try {
			System.out.println("Connection to sqlite cars db closing.");
			con.close();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private int formatNumber(String str) {
		
		str = str.replaceAll(",", "");
		
		double d = str.isEmpty() ? 0.0 : Double.parseDouble(str);

		int formattedNum = (int) d;

		return formattedNum;

	}



	public Car getAuctionItem() {

		try (PreparedStatement preparedStatement = con
				.prepareStatement("select * from " + tableName + " where status IS NULL OR status = '' limit 1;")) {

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				Car car = new Car();
				car.setId(resultSet.getInt("id"));
				car.setImageFile(resultSet.getString("image"));
				car.setItemNo(resultSet.getInt("item_no"));
				car.setBrand(resultSet.getString("brand"));
				car.setModel(resultSet.getString("model"));
				car.setYear(resultSet.getInt("year"));
				car.setMilage(resultSet.getString("milage"));
				car.setInspGrade(resultSet.getString("insp_grade"));
				car.setStartingPrice(resultSet.getInt("starting_price"));
				car.setMinSellPrice(resultSet.getInt("min_price"));
				car.setSellerInfo(resultSet.getString("seller_info"));
				car.setFinalPrice(resultSet.getInt("final_price"));
				car.setStatus(resultSet.getString("status"));
				car.setBidderNo(resultSet.getInt("bidder_no"));

				return car;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error Reading Database.",
					"Inane error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	public void ToCSV() {
		 try {        
		        String sqlSelect = "SELECT * FROM cars ORDER BY id";
		        
		        	String dateTime = new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date());
		     
		            // Execute query.
		            Statement statement  = con.createStatement();
		            resultSet = statement.executeQuery(sqlSelect);

		            // Open CSV file.
		            Path path = Paths.get("cars "+dateTime+".csv");
		     
		            BufferedWriter writer = Files.newBufferedWriter(path);

		            // Add table headers to CSV file.
		            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
		                    .withHeader(resultSet.getMetaData()).withQuoteMode(QuoteMode.ALL));
		            
		            // Add data rows to CSV file.
		            while (resultSet.next()) {

		                csvPrinter.printRecord(
		                		resultSet.getInt(1),
		                		resultSet.getString(2),
		                		resultSet.getInt(3),
		                		resultSet.getString(4),
		                		resultSet.getString(5),
		                		resultSet.getInt(6),
		                		resultSet.getString(7),
		                		resultSet.getString(8),
		                		resultSet.getInt(9),
		                		resultSet.getInt(10),
		                		resultSet.getString(11),
		                		resultSet.getInt(12),
		                		resultSet.getString(13),
		                		resultSet.getInt(14));

		            }
		            		           
		            Runtime.getRuntime().exec("explorer.exe /select," + path);
		            
		            // Close CSV file.
		            csvPrinter.flush();
		            csvPrinter.close();
		            
		            JOptionPane.showMessageDialog(null,"Data Exported Successfully.");

		        } catch (IOException | SQLException e) {

		            // Message stating export unsuccessful.
		            e.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Unable to Export CSV.",
							"Inane error", JOptionPane.ERROR_MESSAGE);

		        } 
	}
	
	private void LogError(Exception ex) {
		Logger logger = Logger.getLogger(CarsDAO.class.getName());

		// Create an instance of FileHandler that write log to a file called
		// app.log. Each new message will be appended at the at of the log file.
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("error.log", true);

			logger.addHandler(fileHandler);

			logger.severe(ex.toString());

		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void deleteAll() {
		
		
		String query = "DELETE FROM "+ tableName;
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(query); // execute query
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		query = " UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='"+tableName+"';";
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(query); // execute query
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
