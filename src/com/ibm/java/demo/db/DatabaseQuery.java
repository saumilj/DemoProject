package com.ibm.java.demo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.java.demo.entity.Chair;
import com.ibm.java.demo.entity.Room;
import com.ibm.java.demo.exception.ChairException;
import com.ibm.java.demo.exception.CustomException;
import com.ibm.java.demo.exception.RoomException;
import com.ibm.java.demo.properties.SqlProperties;


/*
 * Implement functions which create connection and execute queries
 */
public class DatabaseQuery {

	//private InputStream inputStream;
	//private Properties sqlProperties;
	private final static Logger logger = LoggerFactory.getLogger(DatabaseQuery.class);
	public int dev = 1;
	private Connection con =null;
	private ResultSet rs = null;
	private PreparedStatement preparedStatement = null;
	//private String propertiesFileName = "config.properties";
	//SqlProperties getSqlQueries = new SqlProperties();
	
	public DatabaseQuery() {

//			try {
//				sqlProperties = getSqlQueries.getQueries();
//			} catch (CustomException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	}

	/*
	 * Implement query to create association between chair and room.
	 */
	
	public JSONObject associateChairToRoom(String room, String chair) throws CustomException {
	
		try {
			/*
			 * Get query from properties file and execute the prepared statement
			 */
			con = ConnectionManager.getConnection();
			String associateCheck = SqlProperties.getKey("associateCheck");
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
				logger.info("Chair is already associated");
				return response;
			}

			/*
			 * If no association exists, execute query from properties file to
			 * create an association
			 */
			else {
				String associate = SqlProperties.getKey("associate");
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
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("Connection not closed properly.");
			}
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("PreparedStatement not closed properly.");
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("ResultSet not closed properly.");
			}
		}
	}

	public Chair createChair(Chair chair) throws CustomException, ChairException {

		
		try {
			con = ConnectionManager.getConnection();
			String checkChair = SqlProperties.getKey("checkChair");
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
				String createChair = SqlProperties.getKey("createChair");
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
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("Connection not closed properly.");
			}
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("PreparedStatement not closed properly.");
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("ResultSet not closed properly.");
			}
		}
	}

	public Room createRoom(Room room) throws CustomException, RoomException {

		try {
			con = ConnectionManager.getConnection();
			String checkRoom = SqlProperties.getKey("checkRoom");
			preparedStatement = con.prepareStatement(checkRoom);
			preparedStatement.setString(1, room.getRoomName());
			rs = preparedStatement.executeQuery();

			/*
			 * Check if the ChairId to be created already exists. If yes, return
			 * appropriate message.
			 */

			if (rs.next()) {

				logger.debug("Room with name already exists");
				throw new RoomException(room.getRoomName() + " already exists. Please give other name");
				
			}

			/*
			 * If the requested ChairId does not already exist, create a new
			 * entry in Database
			 */
			else {
				String createRoom = SqlProperties.getKey("createRoom");
				preparedStatement = con.prepareStatement(createRoom);
				preparedStatement.setString(1, room.getRoomName());
				preparedStatement.executeUpdate();

				room.setRoomId(UUID.randomUUID().toString());
				return room;

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException("SQLException occured while executing query");
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("Connection not closed properly.");
			}
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("PreparedStatement not closed properly.");
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("ResultSet not closed properly.");
			}
		}
	}

	/*
	 * Retrieves Names of entities from their tables
	 */
	public JSONObject getRooms() throws CustomException {
	
		try {
			/*
			 * Execute query to retreive all Rooms that exist in database
			 */
			con = ConnectionManager.getConnection();
			String getRooms = SqlProperties.getKey("getRooms");
			preparedStatement = con.prepareStatement(getRooms);
			ResultSet rs = preparedStatement.executeQuery();

			JSONObject jobj = new JSONObject();
			int i = 0;
			String s = null;
						
			while (rs.next()) {
				s = rs.getString("RoomId");
				jobj.put("Room" + i, s);
				i++;
			}

			return jobj;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException("SQLException occured while executing query", e);

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("Connection not closed properly.");
			}
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("PreparedStatement not closed properly.");
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("ResultSet not closed properly.");
			}
		}
	}

	public JSONObject getChairs() throws CustomException {
		
		try {
			/*
			 * Execute query to retreive all Chairs that exist in database
			 */
			con = ConnectionManager.getConnection();
			String getChairs = SqlProperties.getKey("getChairs");
			preparedStatement = con.prepareStatement(getChairs);
			ResultSet rs = preparedStatement.executeQuery();

			
			JSONObject jobj = new JSONObject();
			int i = 0;
			String s = null;

			while (rs.next()) {
				s = rs.getString("ChairId");
				jobj.put("Chair" + i, s);
				i++;
			}

			return jobj;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException("SQLException occured while executing query", e);

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("Connection not closed properly.");
			}
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("PreparedStatement not closed properly.");
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("ResultSet not closed properly.");
			}
		}
	}

	/*
	 * Delete the Room-Chair association
	 */
	public JSONObject deleteAssociation(String chair) throws CustomException {
	
		/*
		 * Delete association of Chair and Room using ChairId, which is a unique
		 * key
		 */
		try {
			con = ConnectionManager.getConnection();
			String getNames = SqlProperties.getKey("deleteRC");
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
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("Connection not closed properly.");
			}
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("PreparedStatement not closed properly.");
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("ResultSet not closed properly.");
			}
		}
	}

	/*
	 * Generates Report showing current allocation of Chairs to Rooms
	 */
	public JSONObject getReport() throws CustomException {

		
		try {
			/*
			 * Retrieve data of all room-chair associations from database
			 */
			con = ConnectionManager.getConnection();
			String getNames = SqlProperties.getKey("report");
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
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("Connection not closed properly.");
			}
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("PreparedStatement not closed properly.");
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("ResultSet not closed properly.");
			}
		}
	}

	/*
	 * Deletes given Chair
	 */
	public JSONObject deleteChair(String chair) throws CustomException {

		
		try {
			/*
			 * Delete the requested chair from Chair table
			 */
			con = ConnectionManager.getConnection();
			String deleteChair = SqlProperties.getKey("deleteChair");
			preparedStatement = con.prepareStatement(deleteChair);
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
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("Connection not closed properly.");
			}
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("PreparedStatement not closed properly.");
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("ResultSet not closed properly.");
			}
		}
	}

	/*
	 * Deletes given Room
	 */
	public JSONObject deleteRoom(String room) throws CustomException {

		try {
			/*
			 * Delete the requested chair from Chair table
			 */
			con = ConnectionManager.getConnection();
			String getNames = SqlProperties.getKey("deleteRoom");
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
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("Connection not closed properly.");
			}
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("PreparedStatement not closed properly.");
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("ResultSet not closed properly.");
			}
		}
	}

	/*
	 * Changes Room-Chair associations based on drag-drop activity
	 */
	public JSONObject changeArrangement(String json) throws CustomException {
		
		try {
			/*
			 * Execute query to update association of rooms and chairs based on
			 * changes made through drag-drop. Input is received here as a json
			 * map, which has rooms as keys, and array of chairs as values.
			 */
			con = ConnectionManager.getConnection();
			JSONObject jobj = new JSONObject(json);
			Iterator<?> iterator = jobj.keys();
			while (iterator.hasNext()) {
				String room = (String) iterator.next();
				JSONArray jchairs = (JSONArray) jobj.get(room);
				for (int i = 0; i < jchairs.length(); i++) {
					String newArrangement = SqlProperties.getKey("newArrangement");
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
			throw new CustomException("SQLException occured while creating new association");
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("Connection not closed properly.");
			}
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("PreparedStatement not closed properly.");
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException("ResultSet not closed properly.");
			}
		}
	}

	// Update and change
//	public JSONObject getNames(String name) throws CustomException {
//	
//		try {
//
//			/*
//			 * Execute query to retreive all Rooms/Chairs that exist in database
//			 */
//			con = ConnectionManager.getConnection();
//			String getNames = MessageFormat.format((String) SqlProperties.getKey("getNames"), name + "Id", name);
//			preparedStatement = con.prepareStatement(getNames);
//			ResultSet rs = preparedStatement.executeQuery();
//
//			JSONObject jobj = new JSONObject();
//			int i = 0;
//			String s = null;
//
//			while (rs.next()) {
//				s = rs.getString(name + "Id");
//				jobj.put(name + i, s);
//				i++;
//			}
//
//			jobj.put("status", 200);
//			return jobj;
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new CustomException("SQLException occured while executing query", e);
//
//		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new CustomException("Connection not closed properly.");
//			}
//			try {
//				if (preparedStatement != null)
//					preparedStatement.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new CustomException("PreparedStatement not closed properly.");
//			}
//			try {
//				if (con != null)
//					con.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new CustomException("ResultSet not closed properly.");
//			}
//		}
//	}
}