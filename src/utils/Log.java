package utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import gui.Menu;

public class Log {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static Log single_instance = null; 
	private static FileHandler fh; 
	
	private Log() { 
		
	}
	
	public static Log getInstance() { 
        if (single_instance == null) {
        	addLogger();
            single_instance = new Log(); 
        }
        return single_instance; 
    } 
	
	private static void addLogger() { 
        try {
        	
        	String uriPath = new String();
    		uriPath = new File(Menu.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
    		String pathToJar = (new File(uriPath)).getParentFile().getPath();
    		
//    		File file = new File(pathToJar + "\\logs");
//
//    		file.mkdir();
        	
			fh = new FileHandler(pathToJar + "\\AuctionSystem.log");
		} catch (SecurityException | IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        LOGGER.addHandler(fh);
        LOGGER.setUseParentHandlers(false);
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  
	}
	
	public void addLog(Level level, String message ) {
		LOGGER.log(level, message);
	}

}
