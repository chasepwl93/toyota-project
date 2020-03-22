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
import java.util.logging.Level;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import utils.Log;

public class RecordsDAO {

	private Connection con;
	private ResultSet resultSet;
	private Properties prop;
	private Log logger = Log.getInstance();

	private String tableName;
	
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

			logger.addLog(Level.INFO, "Connection to SQLite has been established."); 
			System.out.println("Connection to SQLite has been established.");

		} catch (IOException | SQLException | ClassNotFoundException e) {
			logger.addErrorDialog("Database Connection Error.", "Connection Error for Records. Check AuctionSales.log for more detail.");
			logger.addLog(Level.SEVERE, "Error Calling RecordsDAO constructor" + e.toString()); 
			e.printStackTrace();
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
			
		logger.addLog(Level.INFO, "Auction Time records saved for item no. " + ItemNo);	
		} catch (SQLException e) {
			logger.addLog(Level.SEVERE, "Error saving records in RecordsDAO" + e.toString()); 
			logger.addErrorDialog("Database Error", "Error saving records. Check AuctionSystem.log for more detail.");
			e.printStackTrace();
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
			
			logger.addLog(Level.INFO, "Auction record time requested for item no: " + ItemNo);
			
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			logger.addLog(Level.SEVERE, "Error getting item by no. " + e.toString());
			logger.addErrorDialog("Database Error", "Error getting item by item no. Check AuctionSystem.log for more detail.");
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
		            
		            logger.addLog(Level.SEVERE, "Records table data successfully exported.");
		            
		        } catch (IOException | SQLException e) {
		        	logger.addLog(Level.SEVERE, "Error exporting to CSV. " + e.toString());
		        	logger.addErrorDialog("Database Error", "Error exporting to CSV. Check AuctionSystem.log for more detail.");
		            e.printStackTrace();
		        } 
	}
	
	public void deleteAll() {
		
		String query = "DELETE FROM "+ tableName;
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(query); // execute query
			logger.addLog(Level.INFO, "All records deleted.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.addLog(Level.SEVERE, "Error deleting in RecordsDAO: " + e.toString());
			logger.addErrorDialog("Database Error", "Error Deleting data. Check AuctionSystem.log for more detail.");
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
			logger.addLog(Level.INFO, "Connection to sqlite recordsDB closing.");
			con.close();
		}

		catch (SQLException e) {
			e.printStackTrace();
			logger.addErrorDialog("Database Error", "Unable to close database.");
		}
	}
	
	
	
}
