package com.ibm.java.demo.rest;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class DatabaseQuery {
	
	//test connection
	public void postData(String chair) throws NamingException{
		
		Context ctx = new InitialContext();
		DataSource dataSource = (DataSource) ctx.lookup("jdbc/mySQL");
		Connection con = null;
			
		try{
			
            con = dataSource.getConnection();      
            Statement stmt = null;      
            stmt = con.createStatement();   
            stmt.executeUpdate("INSERT INTO Chair (ChairId) VALUES (\""+chair+"\")");
            System.out.print("stmt executed successfully");
        }
        catch(SQLException e){      
            e.printStackTrace();
        }
        finally{
            
            if(con != null){        
                try{
                    con.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }		
	}
}