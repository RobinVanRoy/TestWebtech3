
package edu.ap.jdbc;

import java.sql.*;
import java.util.ArrayList;

public class JDBConnection {
	
	private static JDBConnection instance = null;
	private Connection conn = null;
	
	public JDBConnection() {}
	
	public static synchronized JDBConnection getJDBConnection() {
		
		if(instance == null) {
            instance = new JDBConnection();
        }
        return instance;
	}
	
	public void openConnection(String database, String user, String pwd) {        
		try {
			Class.forName("com.mysql.jdbc.Driver");
		    String url = "jdbc:mysql://127.0.0.1/" + database;
		    conn = DriverManager.getConnection (url, user, pwd);
		}
		catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	public void closeConnection() {
		
		if (conn != null) {
			try {
				conn.close();
		     }
		     catch (SQLException ex) {
		    	 System.out.println("Error: " + ex);
		     }
		}
	}	

	public void executeInsert(String table, String lastName, String firstName, int grade) {
		
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO " + table + " VALUES('" + firstName + "','" + lastName + "','" + grade + ")");
			stmt.close();
		}
		catch(SQLException ex) {
			System.out.println("Error: " + ex);
		}
	}

	public String selectName(String lastName){
		
		ResultSet rs = null;
		String result = "";
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM STUDENT WHERE LASTNAME='" + lastName + "'");
			if(rs.next()){
				result = rs.getString(1) + " " + rs.getString(2) + " " + rs.getInt(3);
			}
			stmt.close();
		}
		catch(SQLException ex) {
			System.out.println("Error: " + ex);
		}
		
		return result;
	}
	
	public ArrayList<String> selectAll() {
		
		ResultSet rs = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM STUDENT ORDER BY LASTNAME DESC");
			while(rs.next()) {
				result.add(rs.getString(1) + ";" + rs.getString(2) + ";" + rs.getInt(3));
			}
			stmt.close();
		}
		catch(SQLException ex) {
			System.out.println("Error: " + ex);
		}
		
		return result;
	}

	public void executeDelete(String table, String lastName, String firstName, int grade){
		try{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM " + table + "WHERE FirstName= " + firstName + "AND LastName= " + lastName + "AND Grade= " + grade);
			stmt.close();
		}
		catch(SQLException ex) {
			System.out.println("Error: " + ex);
		}
	}
}
