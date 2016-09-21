package com.ibm.java.demo.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlProperties {

	private final static Logger logger = LoggerFactory.getLogger(SqlProperties.class);
	
	private static Properties sqlProperties = new Properties();
	private static String propertiesFileName = "config.properties";
	private static InputStream inputStream = 
			SqlProperties.class.getClassLoader().getResourceAsStream(propertiesFileName);
	
	static{
		
		try {

			if (inputStream != null) {
				sqlProperties.load(inputStream);
			} else {
				throw new IOException("Input Stream is null"); // comment
			}

		} catch (IOException e) {
			
			e.printStackTrace();
			logger.error("Could not load sql properties file");
			//
			
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static String getKey(String queryKey) {
		
		return sqlProperties.getProperty(queryKey);
	}
}

//method static
// provide get key method - getKey() replaces getProperty()






//package com.ibm.java.demo.db;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//import com.ibm.java.demo.exception.CustomException;
//
//public class SqlProperties {
//
//	private InputStream inputStream;
//	private Properties sqlProperties;
//	private String propertiesFileName = "config.properties";
//	public Properties getQueries() throws CustomException {
//		
//		sqlProperties = new Properties();
//		inputStream = this.getClass().getClassLoader().getResourceAsStream(propertiesFileName);
//
//		try {
//
//			if (inputStream != null) {
//				sqlProperties.load(inputStream);
//			} else {
//				throw new IOException("Input Stream is null"); // comment
//			}
//
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//			// handle here
//			
//		} finally {
//			try {
//				if (inputStream != null)
//					inputStream.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return sqlProperties;
//	}
//	
//	
//	
//}

//method static
// provide get key method - getKey() replaces getProperty()

