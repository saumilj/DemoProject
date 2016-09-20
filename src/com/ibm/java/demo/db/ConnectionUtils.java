//package com.ibm.java.demo.db;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class ConnectionUtils {
//	
//	private Connection con = null;
//	public Connection getConnection(int dev) throws SQLException{
//		
//		try {
//			switch(dev){
//				case(1):
//					con = ConnectionManager.getConnection();
//					break;
//				case(2):
//					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TestDemoProject", "root", "sumisam");
//					break;
//				case(3):
//					con = DriverManager.getConnection("jdbc:mysql://192.155.247.250:3307/de5d033ef3e7c413590052860e7e34975","u7IUAqqnXBGWE","pfuZDUgBQ0GAk");
//					break;
//				default:
//					con = ConnectionManager.getConnection();
//			}
//			
//			return con;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw e;
//		}		
//	}
//}
