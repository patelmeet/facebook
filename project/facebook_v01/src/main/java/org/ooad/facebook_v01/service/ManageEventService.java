package org.ooad.facebook_v01.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.ooad.facebook_v01.database.DatabaseConnection;
import org.ooad.facebook_v01.model.Events;
import org.ooad.facebook_v01.model.Friend;

public class ManageEventService {

	public int newEvent(int user_id, Events event) throws SQLException {
		DatabaseConnection dbconn = new DatabaseConnection();
		if (!dbconn.isStatus()) {
			return -10;
		}

		String event_name = event.getEvent_name();
		String event_location = event.getEvent_location();
		String event_start_date = event.getEvent_start_date();
		String event_start_time = event.getEvent_start_time();
		String event_end_date = event.getEvent_end_date();
		String event_end_time = event.getEvent_end_time();
		String event_description = event.getEvent_description();
		String event_photo = event.getEvent_photo();		
//        System.out.println(event_end_time);
		String query = "insert into EVENT (event_pk,event_name,event_location,event_start_date,event_start_time,event_end_date,event_end_time,event_description,event_photo,userdetails_pk) values (null,'"
				+ event_name +"','" + event_location + "','" + event_start_date + "','" + event_start_time + "','"+ event_end_date + "','" + event_end_time +"','"+ event_description + "','"+ event_photo+ "',"+user_id+");";
		//System.out.println(query);
		dbconn.getStmt().executeUpdate(query);
		return 1;
	}
	
	public int getFriendList(String user_id ,String eventId, List<Friend> friendlist) throws SQLException{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}
		ResultSet rs;
		String query = "Select userdetails_pk,userdetails_firstname,userdetails_lastname,userdetails_picurl,userdetails_friend_count from USERDETAILS where userdetails_pk IN (Select f.friendlist_friend from FRIENDLIST as f where f.friendlist_status = 'accepted' and f.friendlist_user="+user_id+" union select u.friendlist_user from FRIENDLIST as u where u.friendlist_status = 'accepted' and u.friendlist_friend="+user_id+") and userdetails_pk NOT IN(Select eventlist_friend from EVENTLIST where event_pk="+eventId+")";
		rs = dbconn.getStmt().executeQuery(query);
		while(rs.next()){
			friendlist.add(new Friend(Integer.parseInt(rs.getString("userdetails_pk")),rs.getString("userdetails_firstname")+" "+rs.getString("userdetails_lastname"),Integer.parseInt(rs.getString("userdetails_friend_count")),rs.getString("userdetails_picurl")));
		}
		if(friendlist.size()==0)
			return 1;
		return 0;
	}
	
	public int getEventList(String user_id , List<Events> eventlist) throws SQLException{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}
		ResultSet rs;
		String query = "select * from EVENT where userdetails_pk="+user_id+" and (event_end_date > curdate() OR (event_end_date = curdate() and event_end_time > curtime()))";
		rs = dbconn.getStmt().executeQuery(query);
		while(rs.next()){
			eventlist.add(new Events(rs.getInt("event_pk"),rs.getString("event_name"),rs.getString("event_location"),rs.getDate("event_start_date").toString(),rs.getTime("event_start_time").toString(),rs.getDate("event_end_date").toString(),rs.getTime("event_end_time").toString(),rs.getString("event_description"),rs.getString("event_photo")));
		}
		if(eventlist.size()==0)
			return 1;
		return 0;
	}
	
	public int getPastEventList(String user_id , List<Events> eventlist) throws SQLException{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}
		ResultSet rs;
		String query = "select * from EVENT where userdetails_pk="+user_id+" and (event_end_date < curdate() OR (event_end_date = curdate() and event_end_time < curtime()))";

		rs = dbconn.getStmt().executeQuery(query);
		while(rs.next()){
			eventlist.add(new Events(rs.getInt("event_pk"),rs.getString("event_name"),rs.getString("event_location"),rs.getDate("event_start_date").toString(),rs.getTime("event_start_time").toString(),rs.getDate("event_end_date").toString(),rs.getTime("event_end_time").toString(),rs.getString("event_description"),rs.getString("event_photo")));
		}
		if(eventlist.size()==0)
			return 1;
		return 0;
	}
}