package org.ooad.facebook_v01.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.ooad.facebook_v01.database.DatabaseConnection;
import org.ooad.facebook_v01.model.Events;

public class EventService {

	public int newEvent(int user_id, Events event) throws SQLException {
		DatabaseConnection dbconn = new DatabaseConnection();
		if (!dbconn.isStatus()) {
			return 10;
		}

		String event_name = event.getEvent_name();
		String event_location = event.getEvent_location();
		int event_start_date = event.getEvent_start_date();
		int event_start_time = event.getEvent_start_time();
		int event_end_date = event.getEvent_end_date();
		int event_end_time = event.getEvent_end_time();
		String event_description = event.getEvent_description();
		String event_photo = event.getEvent_photo();		

		String query = "insert into facebook_v01.EVENT (event_name,event_location,event_start_date,event_start_time,event_end_date,event_end_time,event_description,event_photo,userdetails_pk) values ('"
				+ event_name +"','" + event_location + "'," + event_start_date + "," + event_start_time + ","+ event_end_date + "," + event_end_time +",'"+ event_description + "','"+ event_photo+ "',"+user_id+");";
		dbconn.getStmt().executeUpdate(query);
		return 0;
	}
	
	public int getEvent(int user_id, ArrayList<Events> events) throws SQLException {
		DatabaseConnection dbconn = new DatabaseConnection();
		if (!dbconn.isStatus()) {
			return 10;
		}
		ResultSet rs;
		String query = "SELECT * FROM facebook_v01.EVENT where userdetails_pk=" + user_id + ";";
		rs = dbconn.getStmt().executeQuery(query);
		
		while (rs.next()) {
			Events event=new Events();
			event.setEvent_name(rs.getString("event_name"));
			event.setEvent_location(rs.getString("event_location"));
			event.setEvent_start_date(rs.getInt("event_start_date"));
			event.setEvent_start_time(rs.getInt("event_start_time"));
			event.setEvent_end_date(rs.getInt("event_end_date"));
			event.setEvent_end_time(rs.getInt("event_end_time"));
			event.setEvent_description(rs.getString("event_description"));
			event.setEvent_photo(rs.getString("event_photo"));
			events.add(event);
		}
		
		if(events.size()>0){return 0;}
		return 1;
	}
}
