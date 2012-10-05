package act.mashup.util;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import act.mashup.global.KV;


public class Log {
	
	private static final Log instance = new Log ();
	public static Logger logger=Logger.getLogger(Log.class.getName());
	
	private Log(){
		PropertyConfigurator.configure("C:\\"+KV.log4JPropertiesFile);
	}
	
	public static void main(String[] args){
		Log.logger.debug("The next yecol log statement should be an error message.");
	}

}
