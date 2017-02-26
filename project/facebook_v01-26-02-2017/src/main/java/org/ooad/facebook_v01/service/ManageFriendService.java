package org.ooad.facebook_v01.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.ooad.facebook_v01.database.DatabaseConnection;
import org.ooad.facebook_v01.model.Friend;

public class ManageFriendService {
	
	public int getPendingRequests(String user_id , List<Friend> friendlist) throws SQLException{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}
		ResultSet rs;
		String query = "select userdetails_pk,userdetails_firstname,userdetails_lastname,userdetails_picurl,userdetails_friend_count from USERDETAILS where userdetails_pk IN(select friendlist_user from FRIENDLIST where friendlist_status='pending' and friendlist_friend="+user_id+")";
		rs = dbconn.getStmt().executeQuery(query);
		while(rs.next()){
			friendlist.add(new Friend(Integer.parseInt(rs.getString("userdetails_pk")),rs.getString("userdetails_firstname")+" "+rs.getString("userdetails_lastname"),Integer.parseInt(rs.getString("userdetails_friend_count")),rs.getString("userdetails_picurl")));
		}
		
		if(friendlist.size()==0)
			return 1;
		return 0;
	}
	
	public int getFriendSuggestion(String user_id , List<Friend> friendlist) throws SQLException{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}
		ResultSet rs;
		String query = "select userdetails_pk,userdetails_firstname,userdetails_lastname,userdetails_picurl,userdetails_friend_count from USERDETAILS where userdetails_pk IN(select userdetails_pk as ids from userdetails where userdetails_pk NOT IN(select friendlist_user as ids from FRIENDLIST where friendlist_friend="+user_id+" and friendlist_status in('accepted','pending','blocked') union select friendlist_friend as ids from FRIENDLIST where friendlist_user="+user_id+" and friendlist_status in('accepted','pending','blocked') union SELECT "+user_id+"))";
		rs = dbconn.getStmt().executeQuery(query);
		while(rs.next()){
			friendlist.add(new Friend(Integer.parseInt(rs.getString("userdetails_pk")),rs.getString("userdetails_firstname")+" "+rs.getString("userdetails_lastname"),Integer.parseInt(rs.getString("userdetails_friend_count")),rs.getString("userdetails_picurl")));
		}
		if(friendlist.size()==0)
			return 1;
		return 0;
	}
	
	public int getFriendList(String user_id , List<Friend> friendlist) throws SQLException{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}
		ResultSet rs;
		String query = "select userdetails_pk,userdetails_firstname,userdetails_lastname,userdetails_picurl,userdetails_friend_count from USERDETAILS where userdetails_pk IN(select friendlist_friend as friend_list from FRIENDLIST where friendlist_user = "+user_id+" and friendlist_status='accepted' union select friendlist_user as friend_list from FRIENDLIST where friendlist_friend = "+user_id+" and friendlist_status='accepted')";
		rs = dbconn.getStmt().executeQuery(query);
		while(rs.next()){
			friendlist.add(new Friend(Integer.parseInt(rs.getString("userdetails_pk")),rs.getString("userdetails_firstname")+" "+rs.getString("userdetails_lastname"),Integer.parseInt(rs.getString("userdetails_friend_count")),rs.getString("userdetails_picurl")));
		}
		if(friendlist.size()==0)
			return 1;
		return 0;
	}
}
