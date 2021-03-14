package ExceptionHandlerLibrary;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class LogAction implements Action{
	

	

	@Override
	public void takeAction(Map<String, String> nvPair) 
	{
		// TODO Auto-generated method stub
		System.out.println("In log");
		
		log(nvPair.get("logFilePath"), nvPair.get("logType"));
	}
	
	private static void log(String path, String logType)
	{
		Logger logger = Logger.getLogger(LogAction.class.getName());
		
		FileHandler handler = null;
		try {
			handler = new FileHandler(path, true);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.addHandler(handler);
		logger.warning(logType);
	}

}