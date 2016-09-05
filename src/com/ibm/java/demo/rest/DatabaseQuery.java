package com.ibm.java.demo.rest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseQuery {

	Context ctx = null;
	DataSource dataSource = null;
	Connection con = null;
	PreparedStatement preparedStatement = null;

	public String CreateChair(String chair) throws NamingException {

		ctx = new InitialContext();
		dataSource = (DataSource) ctx.lookup("jdbc/mySQL");

		try {

			con = dataSource.getConnection();
			String checkQuery = "SELECT ChairId FROM Chair WHERE ChairId = ? LIMIT 1";
			preparedStatement = con.prepareStatement(checkQuery);
			preparedStatement.setString(1, chair);
			ResultSet rs = preparedStatement.executeQuery();

			/*
			 * Check if the ChairId to be created already exists. If yes, return
			 * appropriate message. Else, create the requested ChairId
			 */

			if (rs.next()) {

				System.out.print("Chair with name '" + chair + "' already exists");
				return "Chair with name '" + chair + "' already exists. Please give a different Name!";
			}

			else {

				String query = "insert into Chair (ChairId) values (?)";
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, chair);
				preparedStatement.executeUpdate();
				System.out.print("preparedstmt executed successfully");
				return "Chair inserted successfully";
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
		return "Error during inserting chair";
	}

	public String CreateRoom(String room) throws NamingException {

		ctx = new InitialContext();
		dataSource = (DataSource) ctx.lookup("jdbc/mySQL");

		try {

			con = dataSource.getConnection();
			String checkQuery = "SELECT RoomId FROM Room WHERE RoomId = ? LIMIT 1";
			preparedStatement = con.prepareStatement(checkQuery);
			preparedStatement.setString(1, room);
			ResultSet rs = preparedStatement.executeQuery();

			/*
			 * Check if the RoomId to be created already exists. If yes, return
			 * appropriate message. Else, create the requested RoomId
			 */

			if (rs.next()) {

				System.out.print("Room with name '" + room + "' already exists");
				return "Room with name '" + room + "' already exists. Please give a different Name!";
			}

			else {

				String query = "INSERT INTO Room (RoomId) values (?)";
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, room);
				preparedStatement.executeUpdate();
				System.out.print("preparedstmt executed successfully");
				return "Room inserted successfully";
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
		return "Error during inserting room";
	}

	/*
	 * This method reads generic create queries from properties file, and
	 * creates resource, which is either a new room or a new chair.
	 */
	public String createResource(String name, String attribute) throws FileNotFoundException, SQLException, NamingException {

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

			String checkResource = MessageFormat.format((String) prop.get("checkResource"), attribute + "Id", attribute, attribute + "Id");
			preparedStatement = con.prepareStatement(checkResource);
			preparedStatement.setString(1, name);
			ResultSet rs = preparedStatement.executeQuery();

			/*
			 * Check if the ChairId to be created already exists. If yes, return
			 * appropriate message. Else, create the requested ChairId
			 */

			if (rs.next()) {

				return attribute+" with name '" + name + "' already exists. Please give a different Name!";
			}

			else {

				String createQ = MessageFormat.format((String) prop.get("createResource"), attribute, attribute + "Id");
				preparedStatement = con.prepareStatement(createQ);
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

		return attribute+" '"+name+"' inserted successfully";

	}
}