package com.ibm.java.demo.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DatabaseQuery {

	Context ctx = null;
	// Comment @Resource and change con = DBUtility.getConnection();
	 @Resource(lookup = "jdbc/mysql")
	private DataSource dataSource;
	private Connection con = null;
	private ResultSet rs = null;
	public PreparedStatement preparedStatement = null;

	/*
	 * This method reads generic create queries from properties file, and
	 * creates resource, which is either a new room or a new chair.
	 */

	public String associate(String room, String chair) throws SQLException, NamingException, IOException, Exception {

		Properties prop = new Properties();
		String propFileName = "config.properties";
		JSONObject jres = new JSONObject();
		try {
			con = DBUtility.getConnection();
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
				inputStream.close();
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			String associateCheck = prop.getProperty("associateCheck");
			preparedStatement = con.prepareStatement(associateCheck);
			preparedStatement.setString(1, chair);
			rs = preparedStatement.executeQuery();

			/*
			 * Check if the ChairId to be created already exists. If yes, return
			 * appropriate message. Else, create the requested ChairId
			 */

			if (rs.next()) {
				jres.put("response", "exists");
				jres.put("code", 200);
				return jres.toString();
			}

			else {
				String associate = prop.getProperty("associate");
				preparedStatement = con.prepareStatement(associate);
				preparedStatement.setString(1, room);
				preparedStatement.setString(2, chair);
				preparedStatement.executeUpdate();
				jres.put("response", "Association recorded successfully! Thank you!");
				jres.put("code", 200);
				return jres.toString();
			}

		}
		// catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// jres.put("response","IO Exception!");
		// return jres.toString();
		// }
		finally {

			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public String createResource(String name, String attribute)
			throws SQLException, NamingException, IOException, Exception {

		Properties prop = new Properties();
		String propFileName = "config.properties";
		JSONObject jres = new JSONObject();

		try {
			con = DBUtility.getConnection();
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
				inputStream.close();
			}

			String checkResource = MessageFormat.format((String) prop.get("checkResource"), attribute + "Id", attribute,
					attribute + "Id");
			preparedStatement = con.prepareStatement(checkResource);
			preparedStatement.setString(1, name);
			rs = preparedStatement.executeQuery();

			/*
			 * Check if the ChairId to be created already exists. If yes, return
			 * appropriate message. Else, create the requested ChairId
			 */
			if (rs.next()) {

				jres.put("response",
						attribute + " with name '" + name + "' already exists. Please give a different Name!");
				jres.put("code", 200);
				return jres.toString();
			}

			else {
				String createResource = MessageFormat.format((String) prop.get("createResource"), attribute,
						attribute + "Id");
				preparedStatement = con.prepareStatement(createResource);
				preparedStatement.setString(1, name);
				preparedStatement.executeUpdate();
				jres.put("response", attribute + " '" + name + "' inserted successfully");
				jres.put("code", 200);
				return jres.toString();
			}
		} finally {

			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String getNames(String name) throws SQLException, NamingException, IOException {

		Properties prop = new Properties();
		String propFileName = "config.properties";
		JSONObject jobj = new JSONObject();
		try {
			con = DBUtility.getConnection();
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
				inputStream.close();
			}

			String getNames = MessageFormat.format((String) prop.get("getNames"), name + "Id", name);
			preparedStatement = con.prepareStatement(getNames);
			ResultSet rs = preparedStatement.executeQuery();

			int i = 0;
			String s = null;

			while (rs.next()) {
				s = rs.getString(name + "Id");
				jobj.put(name + i, s);
				i++;
			}
			return jobj.toString();

		} finally {
			if (con != null)
				con.close();
			if (rs != null)
				rs.close();
			if (preparedStatement != null)
				preparedStatement.close();
		}
	}

	public String delete(String room, String chair) throws FileNotFoundException, SQLException, NamingException {

		Properties prop = new Properties();
		String propFileName = "config.properties";
		JSONObject jres = new JSONObject();

		try {
			con = DBUtility.getConnection();
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
				inputStream.close();
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			String getNames = prop.getProperty("deleteRC");
			preparedStatement = con.prepareStatement(getNames);
			preparedStatement.setString(1, chair);
			preparedStatement.executeUpdate();
			return "deleted";

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		} finally {

			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String getReport() throws FileNotFoundException, SQLException, NamingException, JsonProcessingException {

		Properties prop = new Properties();
		String propFileName = "config.properties";

		try {
			con = DBUtility.getConnection();
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				try {
					prop.load(inputStream);
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			String getNames = prop.getProperty("report");
			preparedStatement = con.prepareStatement(getNames);
			ResultSet rs = preparedStatement.executeQuery();

			HashMap<String, List<String>> reportMap = new HashMap<>();
			while (rs.next()) {

				String room = rs.getString("RoomId");
				String chair = rs.getString("ChairId");
				List<String> chairList = reportMap.get(room);
				if (chairList == null) {
					chairList = new ArrayList<String>();
					chairList.add(chair);
					reportMap.put(room, chairList);
				} else {
					if (!chairList.contains(chair))
						chairList.add(chair);
				}
			}

			String mapAsJson = new ObjectMapper().writeValueAsString(reportMap);
			return mapAsJson;

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}