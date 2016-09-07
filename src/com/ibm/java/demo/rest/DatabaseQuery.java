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

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseQuery {

	Context ctx = null;
	//@Resource(lookup = "jdbc/mysql")
	DataSource dataSource = null;
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	private static final String VALUE = "value";
	
	/*
	 * This method reads generic create queries from properties file, and
	 * creates resource, which is either a new room or a new chair.
	 */
	
	public String associate(String room, String chair)
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

			String associateCheck = prop.getProperty("associateCheck");
			preparedStatement = con.prepareStatement(associateCheck);
			preparedStatement.setString(1, room);
			preparedStatement.setString(2, chair);
			rs = preparedStatement.executeQuery();

			/*
			 * Check if the ChairId to be created already exists. If yes, return
			 * appropriate message. Else, create the requested ChairId
			 */

			if (rs.next()) {

				return "association already exists!";
			}

			else {
				String associate = prop.getProperty("associate");
				preparedStatement = con.prepareStatement(associate);
				preparedStatement.setString(1, room);
				preparedStatement.setString(2, chair);			
				preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		    try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {e.printStackTrace();};
		}

		return "association successfull";
	}

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
			rs = preparedStatement.executeQuery();

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

			try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		    try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {e.printStackTrace();};
		}

		return attribute + " '" + name + "' inserted successfully";
	}
	
	public String getNames(String name) throws FileNotFoundException, SQLException, NamingException {

		Properties prop = new Properties();
		String propFileName = "config.properties";
		ctx = new InitialContext();
		dataSource = (DataSource) ctx.lookup("jdbc/mySQL");
		JSONObject jobj = new JSONObject();
		JSONObject jerror = new JSONObject();

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
					
			while (rs.next()) {

				s = rs.getString(name + "Id");
				jobj.put(name+i, s);
				i++;
			}

			System.out.print("JsonObject : "+ jobj.toString());
			return jobj.toString();

		} catch (SQLException e) {
			e.printStackTrace();
			return "{}";
		} finally {

			try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		    try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {e.printStackTrace();};
		}

		//return "select query was successful";
	}	
	
	public String getReport() throws FileNotFoundException, SQLException, NamingException {

		Properties prop = new Properties();
		String propFileName = "config.properties";
		ctx = new InitialContext();
		dataSource = (DataSource) ctx.lookup("jdbc/mySQL");
		JSONObject jobj = new JSONObject();
		JSONObject jerror = new JSONObject();

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

			String getNames = prop.getProperty("report");
			preparedStatement = con.prepareStatement(getNames);
			ResultSet rs = preparedStatement.executeQuery();
			/*
			 * Check if the ChairId to be created already exists. If yes, return
			 * appropriate message. Else, create the requested ChairId
			 */
			String roomid	= null;
			String chairid	= null;
					
			while (rs.next()) {
				roomid = rs.getString("RoomId");
				chairid = rs.getString("ChairId");
				jobj.put(roomid, chairid);
			}
			
			System.out.print("JsonObject : "+ jobj.toString());
			return jobj.toString();

		} catch (SQLException e) {
			e.printStackTrace();
			return "{}";
		} finally {

			try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
			try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
		    try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {e.printStackTrace();};
		}
	}	

}
//
//public String getChair(String chairname)
//		throws FileNotFoundException, SQLException, NamingException {
//
//	Properties prop = new Properties();
//	String propFileName = "config.properties";
//	ctx = new InitialContext();
//	dataSource = (DataSource) ctx.lookup("jdbc/mySQL");
//	JSONObject jobj = new JSONObject();
//
//	try {
//		con = dataSource.getConnection();
//		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);
//		if (inputStream != null) {
//			try {
//				prop.load(inputStream);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
//		}
//
//		String getChair = prop.getProperty("getChair");
//		preparedStatement = con.prepareStatement(getChair);
//		preparedStatement.setString(1, chairname);
//		rs = preparedStatement.executeQuery();
//		
//		if (rs.next()) {
//			jobj.put("Chair",rs.getString("ChairId"));
//		}
//
//	} catch (SQLException e) {
//		e.printStackTrace();
//	} finally {
//
//		try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
//		try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
//	    try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {e.printStackTrace();};
//	}
//	System.out.print("chairName: "+chairname);
//	System.out.print("Ideal response:"+jobj.toString());
//	return jobj.toString();
//}
//
//public String getRoom(String roomname)
//		throws FileNotFoundException, SQLException, NamingException {
//
//	Properties prop = new Properties();
//	String propFileName = "config.properties";
//	ctx = new InitialContext();
//	dataSource = (DataSource) ctx.lookup("jdbc/mySQL");
//	JSONObject jobj = new JSONObject();
//
//	try {
//		con = dataSource.getConnection();
//		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);
//		if (inputStream != null) {
//			try {
//				prop.load(inputStream);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
//		}
//
//		String getRoom = prop.getProperty("getRoom");
//		preparedStatement = con.prepareStatement(getRoom);
//		preparedStatement.setString(1, roomname);
//		rs = preparedStatement.executeQuery();
//		
//		if (rs.next()) {
//			jobj.put("Room",rs.getString("RoomId"));
//		}
//
//	} catch (SQLException e) {
//		e.printStackTrace();
//	} finally {
//
//		try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
//		try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
//	    try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {e.printStackTrace();};
//	}
//
//	return jobj.toString();
//}



//public String getName(String name) throws FileNotFoundException, SQLException, NamingException {
//
//	Properties prop = new Properties();
//	String propFileName = "config.properties";
//	ctx = new InitialContext();
//	dataSource = (DataSource) ctx.lookup("jdbc/mySQL");
//
//	try {
//		con = dataSource.getConnection();
//		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);
//		if (inputStream != null) {
//			try {
//				prop.load(inputStream);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			
//			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
//		}
//
//		String getName = MessageFormat.format((String) prop.get("getName"), name + "Id", name, name + "Id");
//		// SELECT {0} FROM {1} WHERE {2} = ? LIMIT 1
//		preparedStatement = con.prepareStatement(getName);
//		preparedStatement.setString(1, name);
//		ResultSet rs = preparedStatement.executeQuery();
//
//		/*
//		 * Check if the ChairId to be created already exists. If yes, return
//		 * appropriate message. Else, create the requested ChairId
//		 */
//		String s = null;
//		JSONObject jobj = new JSONObject();
//		
//		if (rs.next()) {
//			s = rs.getString(name + "Id");
//			jobj.put(name, s);
//			
//		}
//
//		System.out.print("JsonObject : "+ jobj.toString());
//		return jobj.toString();
//
//	} catch (SQLException e) {
//		e.printStackTrace();
//	} finally {
//
//		if (con != null) {
//			try {
//				con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	return "select query was successful";
//}
//
