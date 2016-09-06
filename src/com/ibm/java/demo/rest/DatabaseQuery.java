package com.ibm.java.demo.rest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseQuery {

	Context ctx = null;
	DataSource dataSource = null;
	Connection con = null;
	PreparedStatement preparedStatement = null;

	private static final String VALUE = "value";
	
	/*
	 * This method reads generic create queries from properties file, and
	 * creates resource, which is either a new room or a new chair.
	 */
	public String createResource(String name, String attribute)
			throws FileNotFoundException, SQLException, NamingException {

		Properties prop = new Properties();
		String propFileName = "config.properties";
		ctx = new InitialContext();
		dataSource = (DataSource) ctx.lookup("jdbc/mySQL");

		try {
			con = dataSource.getConnection();
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				try {
					prop.load(inputStream);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			String checkResource = MessageFormat.format((String) prop.get("checkResource"), attribute + "Id", attribute,
					attribute + "Id");
			preparedStatement = con.prepareStatement(checkResource);
			preparedStatement.setString(1, name);
			ResultSet rs = preparedStatement.executeQuery();

			/*
			 * Check if the ChairId to be created already exists. If yes, return
			 * appropriate message. Else, create the requested ChairId
			 */

			if (rs.next()) {

				return attribute + " with name '" + name + "' already exists. Please give a different Name!";
			}

			else {
				String createResource = MessageFormat.format((String) prop.get("createResource"), attribute, attribute + "Id");
				preparedStatement = con.prepareStatement(createResource);
				preparedStatement.setString(1, name);
				preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return attribute + " '" + name + "' inserted successfully";

	}

	public String getNames(String name) throws FileNotFoundException, SQLException, NamingException {

		Properties prop = new Properties();
		String propFileName = "config.properties";
		ctx = new InitialContext();
		dataSource = (DataSource) ctx.lookup("jdbc/mySQL");

		try {
			con = dataSource.getConnection();
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				try {
					prop.load(inputStream);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			String getNames = MessageFormat.format((String) prop.get("getNames"), name + "Id", name);
			preparedStatement = con.prepareStatement(getNames);
			ResultSet rs = preparedStatement.executeQuery();

			/*
			 * Check if the ChairId to be created already exists. If yes, return
			 * appropriate message. Else, create the requested ChairId
			 */

			int i = 0;
			String s = null;
			JSONObject jobj = new JSONObject();
			
			while (rs.next()) {

				s = rs.getString(name + "Id");
				jobj.put(name+i, s);
				i++;
			}

			System.out.print("JsonObject : "+ jobj.toString());
			return jobj.toString();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return "select query was successful";
	}
}