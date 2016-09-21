package com.ibm.java.demo.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionManager {

	private static DataSource dataSource;
	private final static Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

	static {
		try {
			dataSource = (DataSource) new InitialContext().lookup("jdbc/mySQL");
			//logger.warn("***Datasource instance created");
		} catch (NamingException e) {
			e.printStackTrace();
			logger.error("Naming Exception occured, failed to lookup jdbc/mySQL");
		}
	}

	public static Connection getConnection() throws SQLException {

		return dataSource.getConnection();
	}
}
