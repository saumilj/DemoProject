package com.ibm.java.demo.db;

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
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.java.demo.entity.Chair;
import com.ibm.java.demo.entity.Room;
import com.ibm.java.demo.exception.ChairException;
import com.ibm.java.demo.exception.CustomException;
import com.ibm.java.demo.exception.RoomException;

/*
 * Implement functions which create connection and execute queries
 */
public class DatabaseQuery {

	private InputStream inputStream;
	private Properties sqlProperties;
	public boolean Dev = false;
	private Connection con = null;
	private ResultSet rs = null;
	private PreparedStatement preparedStatement = null;
	private String propertiesFileName = "config.properties";
	

	public DatabaseQuery() {

		sqlProperties = new Properties();
		inputStream = this.getClass().getClassLoader().getResourceAsStream(propertiesFileName);

		try {
			if (inputStream != null) {
				sqlProperties.load(inputStream);
			} else {
				throw new IOException("Input Stream is null"); // comment
			}
				if (Dev == true) {
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TestDemoProject", "root", "sumisam");
				} else {
					con = ConnectionManager.getConnection();
				}
			
		} catch (IOException e) {

			e.printStackTrace();
			// throw new CustomException("property file '" + propertiesFileName
			// + "' not found in the classpath",e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Context ctx = null;
	// Comment @Resource and change con = DBUtility.getConnection();
	// @Resource(lookup = "jdbc/mysql")
	// private DataSource dataSource;
	

	/*
	 * Implement query to create association between chair and room.
	 */
	public JSONObject associateChairToRoom(String room, String chair) throws CustomException {

//		try {
//			if (Dev == true) {
//				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TestDemoProject", "root", "sumisam");
//			} else {
//				con = ConnectionManager.getConnection();
//			}
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//			throw new CustomException("SQLException occured while creating connection");
//		}

		try {

			/*
			 * Get query from properties file and execute the prepared statement
			 */
			String associateCheck = sqlProperties.getProperty("associateCheck");
			preparedStatement = con.prepareStatement(associateCheck);
			preparedStatement.setString(1, chair);
			rs = preparedStatement.executeQuery(); // try catch

			/*
			 * If the association already exists, send message to user saying
			 * that association for the given chair already exists.
			 */

			if (rs.next()) {
				JSONObject response = new JSONObject();
				response.put("response", "Association exists");
				response.put("status", 200);
				return response;
			}

			/*
			 * If no association exists, execute query from properties file to
			 * create an association
			 */
			else {
				String associate = sqlProperties.getProperty("associate");
				preparedStatement = con.prepareStatement(associate);
				preparedStatement.setString(1, room);
				preparedStatement.setString(2, chair);
				preparedStatement.executeUpdate(); // try catch

				JSONObject response = new JSONObject();
				response.put("response", "Association recorded successfully! Thank you!");
				response.put("status", 200);

				return response;
			}
		} catch (SQLException e) {

			e.printStackTrace();
			throw new CustomException("SQLException occured while executing query", e);
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

	public Chair createChair(Chair chair) throws CustomException, ChairException {

		//Dependency Injection
//		try {
//			if (Dev == true) {
//				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TestDemoProject", "root", "sumisam");
//			} else {
//				con = ConnectionManager.getConnection();
//			}
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//			throw new CustomException("SQLException occured while creating connection");
//		}

		try {
			String checkChair = sqlProperties.getProperty("checkChair");
			preparedStatement = con.prepareStatement(checkChair);
			preparedStatement.setString(1, chair.getChairName());
			rs = preparedStatement.executeQuery();

			/*
			 * Check if the ChairId to be created already exists. If yes, return
			 * appropriate message.
			 */
			if (rs.next()) {

				throw new ChairException(chair.getChairName() + " already exists. Please give other name");
			}

			/*
			 * If the requested ChairId does not already exist, create a new
			 * entry in Database
			 */
			else {
				String createChair = sqlProperties.getProperty("createChair");
				preparedStatement = con.prepareStatement(createChair);
				preparedStatement.setString(1, chair.getChairName());
				preparedStatement.executeUpdate();
			
				chair.setChairId(UUID.randomUUID().toString());			
				return chair;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException("SQLException occured while executing query");
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

	public Room createRoom(Room room) throws CustomException, RoomException {

		try {
			if (Dev == true) {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TestDemoProject", "root", "sumisam");
			} else {
				con = ConnectionManager.getConnection();
			}
		} catch (SQLException e) {

			e.printStackTrace();
			throw new CustomException("SQLException occured while creating connection");
		}

		try {
			
			String checkRoom = sqlProperties.getProperty("checkRoom");
			preparedStatement = con.prepareStatement(checkRoom);
			preparedStatement.setString(1, room.getRoomName());
			rs = preparedStatement.executeQuery();

			/*
			 * Check if the ChairId to be created already exists. If yes, return
			 * appropriate message.
			 */

			if (rs.next()) {

				throw new RoomException(room.getRoomName() + " already exists. Please give other name");
			}

			/*
			 * If the requested ChairId does not already exist, create a new
			 * entry in Database
			 */
			else {
				String createRoom = sqlProperties.getProperty("createRoom");
				preparedStatement = con.prepareStatement(createRoom);
				preparedStatement.setString(1, room.getRoomName());
				preparedStatement.executeUpdate();

				int Id = returnLastId();
				if (Id == -1) {
					throw new CustomException("Last Inserted Id was not retrieved");
				}
				
				room.setRoomId(UUID.randomUUID().toString());
				return room;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException("SQLException occured while executing query");
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

	public JSONObject getNames(String name) throws CustomException {

		JSONObject jobj = new JSONObject();
		try {

			/*
			 * Execute query to retreive all Rooms/Chairs that exist in database
			 */
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
			// if rs empty. exception
			jobj.put("status", 200);
			return jobj;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException("SQLException occured while executing query", e);

		} finally {
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

	public JSONObject deleteAssociation(String chair) throws CustomException {

		try {
			con = ConnectionManager.getConnection();
		} catch (SQLException e) {

			e.printStackTrace();
			throw new CustomException("SQLException occured while creating connection");
		}

		/*
		 * Delete association of Chair and Room using ChairId, which is a unique
		 * key
		 */
		try {
			String getNames = sqlProperties.getProperty("deleteRC");
			preparedStatement = con.prepareStatement(getNames);
			preparedStatement.setString(1, chair);
			preparedStatement.executeUpdate();

			JSONObject response = new JSONObject();
			response.put("response", "deleted");
			response.put("status", 200);
			return response;

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

	public JSONObject getReport() throws CustomException {

		try {
			con = ConnectionManager.getConnection();
		} catch (SQLException e) {

			e.printStackTrace();
			throw new CustomException("SQLException occured while creating connection");
		}
		try {

			/*
			 * Retrieve data of all room-chair associations from database
			 */
			String getNames = sqlProperties.getProperty("report");
			preparedStatement = con.prepareStatement(getNames);
			ResultSet rs = preparedStatement.executeQuery();

			/*
			 * Create a map which has all rooms as key, and all associate chairs
			 * as vlaues(as an array) This map can be easily converted to json
			 * object.
			 */
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
			// rs.exception

			String mapAsJson = new ObjectMapper().writeValueAsString(reportMap);
			JSONObject response = new JSONObject(mapAsJson);
			response.put("status", 200);

			return response;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException("SQLException occured while executing query", e);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("JsonProcessingException occured while executing query", e);
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

	public JSONObject deleteChair(String chair) throws CustomException {
		try {

			con = ConnectionManager.getConnection();
		} catch (SQLException e) {

			e.printStackTrace();
			throw new CustomException("SQLException occured while creating connection");
		}

		try {
			/*
			 * Delete the requested chair from Chair table
			 */
			String getNames = sqlProperties.getProperty("deleteChair");
			preparedStatement = con.prepareStatement(getNames);
			preparedStatement.setString(1, chair);
			preparedStatement.executeUpdate();

			JSONObject response = new JSONObject();
			response.put("response", "Chair was deleted successfully");
			response.put("status", 200);
			return response;

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

	public JSONObject deleteRoom(String room) throws CustomException {
		try {

			con = ConnectionManager.getConnection();
		} catch (SQLException e) {

			e.printStackTrace();
			throw new CustomException("SQLException occured while creating connection");
		}

		try {
			/*
			 * Delete the requested chair from Chair table
			 */
			String getNames = sqlProperties.getProperty("deleteRoom");
			preparedStatement = con.prepareStatement(getNames);
			preparedStatement.setString(1, room);
			preparedStatement.executeUpdate();

			JSONObject response = new JSONObject();
			response.put("response", "Room was deleted successfully");
			response.put("status", 200);
			return response;

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

	public JSONObject changeArrangement(String json) throws CustomException {

		try {
			con = ConnectionManager.getConnection();
		} catch (SQLException e) {

			e.printStackTrace();
			throw new CustomException("SQLException occured while creating connection");
		}

		try {
			/*
			 * Execute query to update association of rooms and chairs based on
			 * changes made through drag-drop. Input is received here as a json
			 * map, which has rooms as keys, and array of chairs as values.
			 */
			JSONObject jobj = new JSONObject(json);
			Iterator<String> iterator = jobj.keys();
			while (iterator.hasNext()) {
				String room = iterator.next();
				JSONArray jchairs = (JSONArray) jobj.get(room);
				for (int i = 0; i < jchairs.length(); i++) {
					String newArrangement = sqlProperties.getProperty("newArrangement");
					preparedStatement = con.prepareStatement(newArrangement);
					preparedStatement.setString(1, room);
					preparedStatement.setString(2, jchairs.getString(i));
					preparedStatement.executeUpdate();
				}
			}
			JSONObject response = new JSONObject();
			response.put("response", "New Allocations recorded");
			response.put("status", 200);
			return response;
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

	public int returnLastId() throws CustomException {

//		try {
//
//			if (Dev == true) {
//				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TestDemoProject", "root", "sumisam");
//			} else {
//				con = ConnectionManager.getConnection();
//			}
//
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//			throw new CustomException("SQLException occured while creating connection");
//		}

		String lastId = sqlProperties.getProperty("lastId");
		try {
			preparedStatement = con.prepareStatement(lastId);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				return rs.getInt("LAST_INSERT_ID()");
			}

			return -1;
		} catch (SQLException e) {

			e.printStackTrace();
			throw new CustomException("Query to retreive last Id was not executed");
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