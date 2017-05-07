package org.ooad.facebook_v01.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ooad.facebook_v01.database.DatabaseConnection;
import org.ooad.facebook_v01.model.User;

public class SignupService {
	
//	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
//	
//	public static boolean validateEmail(String emailStr) {
//        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
//        return matcher.find();
//}
	
	@SuppressWarnings("resource")
	public int setUserPersonalInfo(User user) throws SQLException{
		
		DatabaseConnection dbconn = new DatabaseConnection();
		
		if(! dbconn.isStatus()){
			return 10;
		}
		
		String check_query;
		ResultSet rs;
		String count_query = "select COUNT(*) from USERDETAILS";
		rs = dbconn.getStmt().executeQuery(count_query);
		rs.next();
		int count = Integer.parseInt(rs.getString(1)) + 1;
		user.setUserDetails_userName(String.valueOf(count));
		String insert_query;
		String birth_date = user.getUserDetails_year()+"-"+user.getUserDetails_month()+"-"+user.getUserDetails_day();
		
		
		if(user.getUserDetails_email().contains("@"))
		{
			//It is EmailID
			check_query = "select userdetails_pk from USERDETAILS where userdetails_email='"+user.getUserDetails_email()+"' ";
//			System.out.println(check_query);
			rs = dbconn.getStmt().executeQuery(check_query);
			if(rs.next())
				return 1;
			
			insert_query = "insert into USERDETAILS values(NULL,'"+user.getUserDetails_userName()+"','"+user.getUserDetails_firstName()+"','"+user.getUserDetails_lastName()+"',NULL,'"+user.getUserDetails_email()+"','"+user.getUserDetails_password()+"','"+user.getUserDetails_gender()+"','"+birth_date+"','default.jpg','black.jpg',0,NULL)";
			dbconn.getStmt().executeUpdate(insert_query);
			return 0;
		}
		else{
			//It is mobile
			user.setUserDetails_mobile(user.getUserDetails_email());
			user.setUserDetails_email("");
			check_query = "select userdetails_pk from USERDETAILS where userdetails_mobile='"+user.getUserDetails_mobile()+"' ";
//			System.out.println(check_query);
			rs = dbconn.getStmt().executeQuery(check_query);
			if(rs.next()){
				rs.close();
				return 1;
			}
			insert_query = "insert into USERDETAILS values(NULL,'"+user.getUserDetails_userName()+"','"+user.getUserDetails_firstName()+"','"+user.getUserDetails_lastName()+"','"+user.getUserDetails_mobile()+"',NULL,'"+user.getUserDetails_password()+"','"+user.getUserDetails_gender()+"','"+birth_date+"','default.jpg','black.jpg',0,NULL)";
			dbconn.getStmt().executeUpdate(insert_query);
			rs.close();
			return 0;
		}
	}
	
	
	public void getUserPersonalInfo(){
	}
	
	public void getUserPic(){		
	}
}
