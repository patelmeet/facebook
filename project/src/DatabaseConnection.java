package org.ooad.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	
Connection conn;
	
	public DatabaseConnection() 
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost/facebook_v01","root","");
			if(conn!=null)
			{
				System.out.println("Connection created");
				Statement s=conn.createStatement();
				ResultSet rs=s.executeQuery("select * from  USERDETAILS ");
				while(rs.next())
				{
					System.out.println(rs.getString("USERDETAILS_LASTNAME")+"    "+rs.getString(5));
				}
				
			}
			else
			{
				
			}
						
		}
		catch(SQLException e)
		{
			System.out.println(""+e.getMessage());
		}
		catch(ClassNotFoundException e)
		{
			System.out.println(""+e.getMessage());
		}
	

}
	public static void main(String st[]){
		DatabaseConnection ob1 = new DatabaseConnection();
		
	}
}
