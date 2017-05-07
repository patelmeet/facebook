package org.ooad.facebook_v01.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.ooad.facebook_v01.database.Convertor;
import org.ooad.facebook_v01.database.DatabaseConnection;
import org.ooad.facebook_v01.model.ContactAndBasicInfo;

public class ContactAndBasicInfoService {
	private ResultSet myResult;
	private DatabaseConnection myConn;
	
	private void estabilishConnection(){
		myConn = new DatabaseConnection();
	}
	
	private void closeConnection() throws SQLException{
		myConn.getConn().close();
	}
	
	public int checkExistance(int userId) throws Exception{
		estabilishConnection();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String query = "select userdetails_pk from CONTACT_AND_BASIC_INFO"+
							" where userdetails_pk="+userId+";";
			myResult = myConn.getStmt().executeQuery(query);
			if(!myResult.next()){return 0;}
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
			return -1;
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
			return -2;
		}		
		closeConnection();
		return 1;
	}
	
	public boolean insertContactAndBasicInfo(int userId,ContactAndBasicInfo cbi) throws Exception{
		estabilishConnection();
		boolean res;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String query = "insert into CONTACT_AND_BASIC_INFO"+
							" values("+userId+",'"+
							cbi.getReligions_religion_name()+"','"+
							cbi.getContact_and_basic_info_interested_in()+"','"+
							cbi.getContact_and_basic_info_user_address()+"','"+
							cbi.getContact_and_basic_info_religious_view()+"');";
			res = myConn.getStmt().execute(query);
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
			return false;
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
			return false;
		}		
		closeConnection();
		return res;
	}
	
	public int editReligiousView(int userId,String religious_view) throws Exception{
		estabilishConnection();
		int res;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String query = "UPDATE CONTACT_AND_BASIC_INFO"+
							" SET contact_and_basic_info_religious_view='"+religious_view+"'"+ 
							" WHERE userdetails_pk='"+userId+"';";
			res = myConn.getStmt().executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
			return -1;
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
			return -2;
		}		
		closeConnection();
		return res;
	}
	
	public int editReligionName(int userId,String religion_name) throws Exception{
		estabilishConnection();
		int res;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String query = "update CONTACT_AND_BASIC_INFO SET religions_religion_name="+religion_name+
							" where userdetails_pk="+userId+";";
			res = myConn.getStmt().executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
			return -1;
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
			return -2;
		}		
		closeConnection();
		return res;
	}
	
	public int editInterestedIn(int userId,char gender) throws Exception{
		estabilishConnection();
		int res;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String query = "UPDATE CONTACT_AND_BASIC_INFO"+
							" SET contact_and_basic_info_interested_in='"+gender+"'"+ 
							" WHERE userdetails_pk='"+userId+"';";
			res = myConn.getStmt().executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
			return -1;
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
			return -2;
		}		
		closeConnection();
		return res;
	}
	
	public int editUserAddress(int userId,String address) throws Exception{
		estabilishConnection();
		int res;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String query = "UPDATE CONTACT_AND_BASIC_INFO"+
							" SET contact_and_basic_info_user_address='"+address+"'"+ 
							" WHERE userdetails_pk='"+userId+"';";
			res = myConn.getStmt().executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
			return -1;
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
			return -2;
		}		
		closeConnection();
		return res;
	}
	
	public int editGender(int userId,char gender) throws Exception{
		estabilishConnection();
		int res;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String query = "UPDATE USERDETAILS"+ 
							" SET userdetails_gender='"+gender+"'"+ 
							" WHERE userdetails_pk='"+userId+"';";
			res = myConn.getStmt().executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
			return -1;
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
			return -2;
		}		
		closeConnection();
		return res;
	}
	
	public int editDOB(int userId,String dob) throws Exception{
		estabilishConnection();
		int res;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String query = "UPDATE USERDETAILS"+ 
							" SET userdetails_dob='"+dob+"'"+ 
							" WHERE userdetails_pk='"+userId+"';";
			res = myConn.getStmt().executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
			return -1;
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
			return -2;
		}		
		closeConnection();
		return res;
	}
	
	//if needed in case
	/*public int deleteEmailAddress(int userId) throws Exception{
		estabilishConnection();
		int res;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String query = "UPDATE USERDETAILS "+ 
							"SET userdetails_email='' "+ 
							"WHERE userdetails_pk='"+userId+"';";
			res = myConn.getStmt().executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
			return -1;
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
			return -2;
		}		
		closeConnection();
		return res;
	}*/
	
	public int editEmailAddress(int userId,String email) throws Exception{
		estabilishConnection();
		int res;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String query = "UPDATE USERDETAILS "+ 
							"SET userdetails_email='"+email+"' "+ 
							"WHERE userdetails_pk='"+userId+"';";
			res = myConn.getStmt().executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
			return -1;
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
			return -2;
		}		
		closeConnection();
		return res;
	}
	
	//if needed in case
	/*public int deletePhoneNumber(int userId) throws Exception{
		estabilishConnection();
		int res;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String query = "UPDATE USERDETAILS "+ 
							"SET userdetails_mobile='' "+ 
							"WHERE userdetails_pk='"+userId+"';";
			res = myConn.getStmt().executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
			return -1;
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
			return -2;
		}		
		closeConnection();
		return res;
	}*/
	
	public int editPhoneNumber(int userId,String phnNo) throws Exception{
		estabilishConnection();
		int res;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String query = "UPDATE USERDETAILS "+ 
							"SET userdetails_mobile='"+phnNo+"' "+ 
							"WHERE userdetails_pk='"+userId+"';";
			res = myConn.getStmt().executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
			return -1;
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
			return -2;
		}		
		closeConnection();
		return res;
	}
	
	public int getContactAndBasicInfo(int userId,ContactAndBasicInfo cbi) throws Exception{
		if(checkExistance(userId)==1){
			estabilishConnection();
			try{
				Class.forName("com.mysql.jdbc.Driver");
				String query = "select userdetails_mobile,userdetails_email,contact_and_basic_info_user_address,"+
								"userdetails_dob,userdetails_gender,religions_religion_name,"+
								"contact_and_basic_info_religious_view,"+
								"contact_and_basic_info_interested_in "+
								"from USERDETAILS U,CONTACT_AND_BASIC_INFO C"+
								" where "+
								"U.userdetails_pk="+userId+" and U.userdetails_pk=C.userdetails_pk";
				myResult = myConn.getStmt().executeQuery(query);
				while (myResult.next()){
					cbi.setUserdetails_mobile(myResult.getString("userdetails_mobile"));
					cbi.setUserdetails_email(myResult.getString("userdetails_email"));
					cbi.setContact_and_basic_info_user_address(myResult.getString("contact_and_basic_info_user_address"));
					cbi.setUserdetails_dob(myResult.getString("userdetails_dob"));
					cbi.setUserdetails_gender(myResult.getString("userdetails_gender").charAt(0));
					cbi.setReligions_religion_name(myResult.getString("religions_religion_name"));
					cbi.setContact_and_basic_info_religious_view(myResult.getString("contact_and_basic_info_religious_view"));
					cbi.setContact_and_basic_info_interested_in(myResult.getString("contact_and_basic_info_interested_in").charAt(0));
				}
				
			}
			catch(SQLException e){
				System.out.println(""+e.getMessage());
				return -1;
			}
			catch(ClassNotFoundException e){
				System.out.println(""+e.getMessage());
				return -2;
			}
			closeConnection();
			return 1;
		}
		else{
			estabilishConnection();
			try{
				Class.forName("com.mysql.jdbc.Driver");
				String query = "select userdetails_mobile,userdetails_email,"+
								"userdetails_dob,userdetails_gender"+
								" from USERDETAILS U"+
								" where "+
								"U.userdetails_pk="+userId;
				myResult = myConn.getStmt().executeQuery(query);
				while (myResult.next()){
					cbi.setUserdetails_mobile(myResult.getString("userdetails_mobile"));
					cbi.setUserdetails_email(myResult.getString("userdetails_email"));
					cbi.setUserdetails_dob(myResult.getString("userdetails_dob"));
					cbi.setUserdetails_gender(myResult.getString("userdetails_gender").charAt(0));
					cbi.setContact_and_basic_info_interested_in('\0');
					cbi.setContact_and_basic_info_religious_view("");
					cbi.setContact_and_basic_info_user_address("");
					cbi.setReligions_religion_name("");
				}
				
			}
			catch(SQLException e){
				System.out.println(""+e.getMessage());
				return -1;
			}
			catch(ClassNotFoundException e){
				System.out.println(""+e.getMessage());
				return -2;
			}
			closeConnection();
			return 1;
		}
	}
	
}
