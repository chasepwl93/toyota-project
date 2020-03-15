package model;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.logging.SimpleFormatter;

import javax.swing.JOptionPane;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import model.pojo.Record;

public class RecordsDAO {

	private Connection con;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private Properties prop;
	private FileHandler fh; 

	private String tableName;
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 
	
	public RecordsDAO() {
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
			tableName = prop.getProperty("tablename.records");

			LOGGER.log(Level.INFO, "Connection to SQLite has been established."); 
			System.out.println("Connection to SQLite has been established.");

		} catch (IOException | SQLException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Database Connection Error. Check AuctionSales.log for connection.",
					"Inane error", JOptionPane.ERROR_MESSAGE);
			LOGGER.log(Level.SEVERE, e.toString()); 
		}
	}
	
	public void save(int ItemNo, ArrayList<String> timeRecords) {
		
		String query = "Insert into records (item_no, time_record) VALUES ( ? , ?);";
		
		try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
			
			for (String time : timeRecords) { 
		
			preparedStmt.setInt(1, ItemNo);
			preparedStmt.setString(2, time);
			
			preparedStmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Unable to Save Data",
					"Inane error", JOptionPane.ERROR_MESSAGE);
			LOGGER.log(Level.SEVERE, e.toString()); 
		}
	}
	
	public ArrayList<String> getByItemNo(int ItemNo) {
		ArrayList<String> recordTime = new ArrayList<>();
		
		try (PreparedStatement preparedStatement = con
				.prepareStatement("Select time_record from "+ tableName +" where item_no = ?")) {

			preparedStatement.setInt(1, ItemNo);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				recordTime.add(resultSet.getString("time_record"));
			}
			
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			return recordTime;
		}
		
		return recordTime;
	}
	
	public void ToCSV() {
		 try {        
		        String sqlSelect = "SELECT * FROM records ORDER BY id";
		        
		        	String dateTime = new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date());
		     
		            // Execute query.
		            Statement statement  = con.createStatement();
		            resultSet = statement.executeQuery(sqlSelect);

		            // Open CSV file.
		            Path path = Paths.get("records "+dateTime+".csv");
		     
		            BufferedWriter writer = Files.newBufferedWriter(path);

		            // Add table headers to CSV file.
		            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
		                    .withHeader(resultSet.getMetaData()).withQuoteMode(QuoteMode.ALL));
		            
		            // Add data rows to CSV file.
		            while (resultSet.next()) {

		                csvPrinter.printRecord(
		                		resultSet.getInt(1),
		                		resultSet.getString(2),
		                		resultSet.getString(3));

		            }
		            		           		            
		            // Close CSV file.
		            csvPrinter.flush();
		            csvPrinter.close();
		            
		        } catch (IOException | SQLException e) {

		            // Message stating export unsuccessful.
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

	public void closeConnection() {
		try {
			System.out.println("Connection to sqlite recordsDB closing.");
			con.close();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void addLogger() { 
		// This block configure the logger with handler and formatter  
        try {
			fh = new FileHandler("C:\\Users\\William\\eclipse-workspace\\ToyotaProject\\AuctionSales.log");
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        LOGGER.addHandler(fh);
        LOGGER.setUseParentHandlers(false);
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  
		
	}
	
	private void writeFile() {
		
	}
	
	
}
