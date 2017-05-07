package org.ooad.facebook_v01.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ooad.facebook_v01.database.DatabaseConnection;
import org.ooad.facebook_v01.model.Login;
import org.ooad.facebook_v01.model.User;

public class LoginService {
	
	public int validateLogin(Login login,User user) throws SQLException{
		
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}
		ResultSet rs;
		String unm = login.getLogin_Id();
		String pass = login.getLogin_Password();
		String count_query = "select * from USERDETAILS where (userdetails_username='"+unm+"' and userdetails_password='"+pass+"') or (userdetails_email='"+unm+"'and userdetails_password='"+pass+"') or (userdetails_mobile='"+unm+"' and userdetails_password='"+pass+"')";						
		rs = dbconn.getStmt().executeQuery(count_query);
		
		if(rs.next()){
			user.setUserDetails_pk(rs.getString("userdetails_pk"));
			user.setUserDetails_userName(rs.getString("userdetails_username"));
			user.setUserDetails_firstName(rs.getString("userdetails_firstname"));
			user.setUserDetails_lastName(rs.getString("userdetails_lastname"));
			user.setUserDetails_mobile(rs.getString("userdetails_mobile"));
			user.setUserDetails_email(rs.getString("userdetails_email"));
			user.setUserdetails_picurl(rs.getString("userdetails_picurl"));
			user.setUserDetails_gender(rs.getString("userdetails_gender").charAt(0));
			String date = rs.getString("userdetails_dob");
			user.setUserDetails_year(date.substring(0,4));
			user.setUserDetails_month(date.substring(5,7));
			user.setUserDetails_day(date.substring(8,10));
			user.setUserdetails_coverpicurl(rs.getString("userdetails_coverpicurl"));
//			System.out.println(rs.getString("userdetails_coverpicurl"));
			return 0;
		}
		else
			return 1;
	}
}