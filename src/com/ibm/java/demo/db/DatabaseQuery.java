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
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.java.demo.exception.CustomException;

public class DatabaseQuery {

	//Context ctx = null;
	//Comment @Resource and change con = DBUtility.getConnection();
	//@Resource(lookup = "jdbc/mysql")
	//private DataSource dataSource;
	private Connection con = null;
	private ResultSet rs = null;
	public PreparedStatement preparedStatement = null;
	private Properties sqlProperties = new Properties();
	private String propertiesFileName = "config.properties";

	public JSONObject associateChairToRoom(String room, String chair) throws CustomException{
		
		InputStream inputStream = null;
		try {
			
			inputStream = this.getClass().getClassLoader().getResourceAsStream(propertiesFileName);
			if (inputStream != null) {
				sqlProperties.load(inputStream);			
			} else {
				throw new CustomException("Input Stream is null"); // comment
			}			
			con = DBUtility.getConnection();
		}
		catch(SQLException e){
			
			e.printStackTrace();		
			throw new CustomException("SQLException occured while creating connection");
		} catch (IOException e) {
			
			e.printStackTrace();
			throw new CustomException("property file '" + propertiesFileName + "' not found in the classpath",e);
		}
		finally{
			
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
		try{
			
			String associateCheck = sqlProperties.getProperty("associateCheck");
			preparedStatement = con.prepareStatement(associateCheck);
			preparedStatement.setString(1, chair);
			rs = preparedStatement.executeQuery(); // try catch
		
			// break to 2 methods
			if (rs.next()) {
				JSONObject response = new JSONObject();
				response.put("response", "Association exists");
				return response;
			}

			else {
				String associate = sqlProperties.getProperty("associate");
				preparedStatement = con.prepareStatement(associate);
				preparedStatement.setString(1, room);
				preparedStatement.setString(2, chair);
				preparedStatement.executeUpdate(); // try catch
				
				JSONObject response = new JSONObject();
				response.put("response", "Association recorded successfully! Thank you!");
				return response;
			}

		}
		catch(SQLException e){
			
			e.printStackTrace();		
			throw new CustomException("SQLException occured while executing query",e);			
		}
		
		finally {

			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace(); // custom Exception
			}
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();// custom Exception
			}
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();// custom Exception
			}								
		}
	}

	public JSONObject createResource(String name, String attribute)
			throws CustomException {

		InputStream inputStream = null;
		try {			
			inputStream = this.getClass().getClassLoader().getResourceAsStream(propertiesFileName);
			if (inputStream != null) {
				sqlProperties.load(inputStream);
				inputStream.close();
			}
			con = DBUtility.getConnection();
		}catch(SQLException e){
			
			e.printStackTrace();
			throw new CustomException("SQLException occured while creating connection");
		}
		catch(IOException e){
			
			e.printStackTrace();
			throw new CustomException("property file '" + propertiesFileName + "' not found in the classpath",e);
		}
		finally{
			
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace(); // customException
			}	 
		}
		try{
		
			// break into 2 methods
			String checkResource = MessageFormat.format((String) sqlProperties.get("checkResource"), attribute + "Id", attribute, attribute + "Id");
			preparedStatement = con.prepareStatement(checkResource);
			preparedStatement.setString(1, name);
			rs = preparedStatement.executeQuery();

			/*
			 * Check if the ChairId to be created already exists. If yes, return
			 * appropriate message. Else, create the requested ChairId
			 */
			
			if (rs.next()) {
				
				JSONObject response = new JSONObject();
				response.put("response", attribute + " with name '" + name + "' already exists. Please give a different Name!");				
				return response;
			}

			else {
				String createResource = MessageFormat.format((String) sqlProperties.get("createResource"), attribute, attribute + "Id");
				preparedStatement = con.prepareStatement(createResource);
				preparedStatement.setString(1, name);
				preparedStatement.executeUpdate();
				
				JSONObject jres = new JSONObject();
				jres.put("response", attribute + " '" + name + "' inserted successfully");
				return jres;
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new CustomException("SQLException occured while executing query");
		}
		
		finally {

			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public JSONObject getNames(String name) throws CustomException {

		JSONObject jobj = new JSONObject();
		InputStream inputStream = null;
		try {
			
			inputStream = this.getClass().getClassLoader().getResourceAsStream(propertiesFileName);
			if (inputStream != null) {
				sqlProperties.load(inputStream);
			}
			con = DBUtility.getConnection();
		}catch(SQLException e){
			
			e.printStackTrace();
			throw new CustomException("SQLException occured while creating connection");
		}
		catch(IOException e){
			
			e.printStackTrace();
			throw new CustomException("property file '" + propertiesFileName + "' not found in the classpath",e);
		}
		finally{
			
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace(); // customException
			}	 
		}
		try{

			String getNames = MessageFormat.format((String) sqlProperties.get("getNames"), name + "Id", name);
			preparedStatement = con.prepareStatement(getNames);
			ResultSet rs = preparedStatement.executeQuery();

			int i = 0;
			String s = null;

			while (rs.next()) {
				s = rs.getString(name + "Id");
				jobj.put(name + i, s);
				i++;
			}
			return jobj;

		} catch(SQLException e){
			e.printStackTrace();
			throw new CustomException("SQLException occured while executing query",e);
			
		}finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (preparedStatement != null)
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	public String delete(String room, String chair) throws FileNotFoundException, SQLException, NamingException, CustomException {
	
		InputStream inputStream = null;
		try {
			
			inputStream = this.getClass().getClassLoader().getResourceAsStream(propertiesFileName);
			if (inputStream != null) {
				sqlProperties.load(inputStream);
			}
			con = DBUtility.getConnection();
		}catch(SQLException e){
			
			e.printStackTrace();
			throw new CustomException("SQLException occured while creating connection");
		}
		catch(IOException e){
			
			e.printStackTrace();
			throw new CustomException("property file '" + propertiesFileName + "' not found in the classpath",e);
		}
		finally{
			
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace(); // customException
			}	 
		}
		try{
			String getNames = sqlProperties.getProperty("deleteRC");
			preparedStatement = con.prepareStatement(getNames);
			preparedStatement.setString(1, chair);
			preparedStatement.executeUpdate();
			
			return "deleted";

		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new CustomException("SQLException occured while executing Delete query");
		} finally {

			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public String getReport() throws CustomException {

		InputStream inputStream = null;
		try {
			
			inputStream = this.getClass().getClassLoader().getResourceAsStream(propertiesFileName);
			if (inputStream != null) {
				sqlProperties.load(inputStream);
			}
			con = DBUtility.getConnection();
		}catch(SQLException e){
			
			e.printStackTrace();
			throw new CustomException("SQLException occured while creating connection");
		}
		catch(IOException e){
			
			e.printStackTrace();
			throw new CustomException("property file '" + propertiesFileName + "' not found in the classpath",e);
		}
		finally{
			
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace(); // customException
			}	 
		}
		try{

			String getNames = sqlProperties.getProperty("report");
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
			throw new CustomException("SQLException occured while executing query",e);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("JsonProcessingException occured while executing query",e);
		} finally {

			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}