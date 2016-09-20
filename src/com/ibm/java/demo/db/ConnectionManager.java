package com.ibm.java.demo.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionManager {
	
private static DataSource dataSource;

	/*
	 * static block is used here for initialization. 
	 * The code inside it will run when JVM loads the class, so we can have our instance ready.
	 */
	static {
		try {
			dataSource = (DataSource) new InitialContext().lookup("jdbc/mySQL");
		} catch (NamingException e) { 
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		
		return dataSource.getConnection();
	}
}