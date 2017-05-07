package org.ooad.facebook_v01.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.ooad.facebook_v01.database.DatabaseConnection;
import org.ooad.facebook_v01.model.College;
import org.ooad.facebook_v01.model.School;
import org.ooad.facebook_v01.model.Work;

public class ManageAboutPageService {
	
	public int setProfileWorkService(Work work) throws SQLException{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}		
		String insert_Query = "insert into WORK values(NULL,?,?,?,?,?,?,?)";
		PreparedStatement ps = dbconn.getConn().prepareStatement(insert_Query);
		ps.setString(1,work.getWork_company());
		ps.setString(2, work.getWork_position());
		ps.setString(3, work.getWork_city());
		ps.setString(4,work.getWork_description());
		ps.setInt(5,work.getWork_start_year());
		ps.setInt(6,work.getWork_end_year());
		ps.setInt(7,work.getUserdetails_pk());
		ps.executeUpdate();
		ps.close();
		dbconn.getConn().close();
		return 0;
	}
	
	public int editProfileWorkService(Work work,String workid) throws SQLException{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}

		String update_Query = "update WORK set work_company = ? , work_position = ? , work_city = ? , work_description = ? , work_start_year = ? , work_end_year = ?  where work_pk="+workid+";";
		PreparedStatement ps = dbconn.getConn().prepareStatement(update_Query);
		ps.setString(1,work.getWork_company());
		ps.setString(2, work.getWork_position());
		ps.setString(3, work.getWork_city());
		ps.setString(4,work.getWork_description());
		ps.setInt(5,work.getWork_start_year());
		ps.setInt(6,work.getWork_end_year());
		ps.executeUpdate();
		ps.close();
		dbconn.getConn().close();
		return 0;
	}
	
	public int getProfileWorkService(ArrayList<Work> work,String userid) throws SQLException {
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}
		
		String select_Query = "select * from WORK where userdetails_pk="+userid+";";
		ResultSet rs = dbconn.getStmt().executeQuery(select_Query);
		while(rs.next()){
			Work tempwork =  new Work();
			tempwork.setWork_pk(rs.getInt("work_pk"));
			tempwork.setWork_city(rs.getString("work_city"));
			tempwork.setWork_company(rs.getString("work_company"));
			tempwork.setWork_description(rs.getString("work_description"));
			tempwork.setWork_end_year(rs.getInt("work_end_year"));
			tempwork.setWork_position(rs.getString("work_position"));
			tempwork.setWork_start_year(rs.getInt("work_start_year"));
			work.add(tempwork);
		}
		return 0;
	}
	
	public int newCollege(int user_id, College clg) throws SQLException {
		DatabaseConnection dbconn = new DatabaseConnection();
		if (!dbconn.isStatus()) {
			return 10;
		}

		String college_name = clg.getCollege_name();
		int college_start_year = clg.getCollege_start_year();
		int college_end_year = clg.getCollege_end_year();
		char college_graduated = clg.getCollege_graduated();
		String college_description = clg.getCollege_description();
		String college_concentration = clg.getCollege_concentration();
		String college_attended_for = clg.getCollege_attended_for();

		ResultSet rs;
		String query = "insert into COLLEGE (college_name,college_start_year,college_end_year,college_graduated,college_description,college_concentration,college_attended_for,userdetails_pk) values ('"
				+ college_name + "'," + college_start_year + "," + college_end_year + ",'" + college_graduated + "','"
				+ college_description + "','" + college_concentration + "','" + college_attended_for + "',"+user_id+");";
		dbconn.getStmt().executeUpdate(query);
		return 0;
	}

	public int editCollege(College clg) throws SQLException {
		DatabaseConnection dbconn = new DatabaseConnection();
		if (!dbconn.isStatus()) {
			return 10;
		}

		int college_pk = clg.getCollege_pk();
		String college_name = clg.getCollege_name();
		int college_start_year = clg.getCollege_start_year();
		int college_end_year = clg.getCollege_end_year();
		char college_graduated = clg.getCollege_graduated();
		String college_description = clg.getCollege_description();
		String college_concentration = clg.getCollege_concentration();
		String college_attended_for = clg.getCollege_attended_for();

		ResultSet rs;
		String query = "update COLLEGE set college_name='" + college_name + "', college_start_year="
				+ college_start_year + ",college_end_year=" + college_end_year + ", college_graduated= '"
				+ college_graduated + "',college_description='" + college_description + "', college_concentration='"
				+ college_concentration + "',college_attended_for='" + college_attended_for + "'where college_pk="
				+ college_pk + ";";
		rs = dbconn.getStmt().executeQuery(query);
		return 0;
	}

	public int getCollege(int user_id, ArrayList<College> colleges) throws SQLException {
		DatabaseConnection dbconn = new DatabaseConnection();
		if (!dbconn.isStatus()) {
			return 10;
		}
		ResultSet rs;
		String query = "SELECT * FROM COLLEGE where userdetails_pk=" + user_id + ";";
		rs = dbconn.getStmt().executeQuery(query);
		
		while (rs.next()) {
			College clg=new College();
			clg.setCollege_attended_for(rs.getString("college_attended_for"));
			clg.setCollege_concentration(rs.getString("college_concentration"));
			clg.setCollege_description(rs.getString("college_description"));
			clg.setCollege_end_year(Integer.parseInt(rs.getString("college_end_year")));
			clg.setCollege_graduated(rs.getString("college_graduated").charAt(0));
			clg.setCollege_name(rs.getString("college_name"));
			clg.setCollege_pk(Integer.parseInt(rs.getString("college_pk")));
			clg.setCollege_start_year(Integer.parseInt(rs.getString("college_start_year")));
			colleges.add(clg);
		}
		
		if(colleges.size()>0){return 0;}
		return 1;
	}
	
	public int newSchool(int user_id, School school) throws SQLException {
		DatabaseConnection dbconn = new DatabaseConnection();
		if (!dbconn.isStatus()) {
			return 10;
		}
		String school_name = school.getSchool_name();
		int school_start_year = school.getSchool_start_year();
		int school_end_year = school.getSchool_end_year();
		String school_description = school.getSchool_description();

		ResultSet rs;
		String query = "insert into SCHOOL (school_name,school_start_year,school_end_year,school_description,userdetails_pk) values ('"
				+ school_name + "'," + school_start_year + "," + school_end_year + ",'" + school_description + "',"+user_id+");";
		dbconn.getStmt().executeUpdate(query);
		return 0;
	}

	public int editSchool(School school) throws SQLException {
		DatabaseConnection dbconn = new DatabaseConnection();
		if (!dbconn.isStatus()) {
			return 10;
		}
		int school_pk = school.getSchool_pk();
		String school_name = school.getSchool_name();
		int school_start_year = school.getSchool_start_year();
		int school_end_year = school.getSchool_end_year();
		String school_description = school.getSchool_description();

		ResultSet rs;
		String query = "update SCHOOL set school_name='" + school_name + "', school_start_year="
				+ school_start_year + ",school_end_year=" + school_end_year + ", school_description='" + school_description + "'where school_pk="
				+ school_pk + ";";
		rs = dbconn.getStmt().executeQuery(query);
		return 0;
	}

	public int getSchool(int user_id, ArrayList<School> schoollist) throws SQLException {
		DatabaseConnection dbconn = new DatabaseConnection();
		if (!dbconn.isStatus()) {
			return 10;
		}
		ResultSet rs;
		String query = "SELECT * FROM SCHOOL where userdetails_pk=" + user_id + ";";
		rs = dbconn.getStmt().executeQuery(query);
		
		while (rs.next()) {
			School school=new School();
			school.setSchool_description(rs.getString("school_description"));
			school.setSchool_end_year(Integer.parseInt(rs.getString("school_end_year")));
			school.setSchool_name(rs.getString("school_name"));
			school.setSchool_pk(Integer.parseInt(rs.getString("school_pk")));
			school.setSchool_start_year(Integer.parseInt(rs.getString("school_start_year")));
			schoollist.add(school);
		}
		
		if(schoollist.size()>0)
			return 0;
		return 1;
	}

}
