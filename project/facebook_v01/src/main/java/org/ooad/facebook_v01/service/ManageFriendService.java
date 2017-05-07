package org.ooad.facebook_v01.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.ooad.facebook_v01.database.DatabaseConnection;
import org.ooad.facebook_v01.model.Friend;
import org.ooad.facebook_v01.model.SearchPeople;

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
		dbconn.getConn().close();
		if(friendlist.size()==0)
			return 1;
		return 0;
	}
	
	//To get People You May Know -- Friends of Friends
	public int getFriendSuggestion(String user_id , List<Friend> friendlist) throws SQLException{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}
		ResultSet rs;
		String query = "select userdetails_pk,userdetails_firstname,userdetails_lastname,userdetails_picurl,userdetails_friend_count from USERDETAILS where userdetails_pk IN(select uids from (select friendlist_friend as uids from FRIENDLIST where friendlist_user in(select friendlist_friend from FRIENDLIST where friendlist_user = "+user_id+" and friendlist_status='accepted' union select friendlist_user from FRIENDLIST where friendlist_friend = "+user_id+" and friendlist_status='accepted') and friendlist_status='accepted' union select friendlist_user as uids from FRIENDLIST where friendlist_friend in(select friendlist_friend from FRIENDLIST where friendlist_user = "+user_id+" and friendlist_status='accepted' union select friendlist_user from FRIENDLIST where friendlist_friend = "+user_id+" and friendlist_status='accepted') and friendlist_status='accepted' Not in(select friendlist_friend as uids from FRIENDLIST where friendlist_user = "+user_id+" and friendlist_status='accepted' union select friendlist_user as uids from FRIENDLIST where friendlist_friend = "+user_id+" and friendlist_status='accepted') or friendlist_friend in(select friendlist_friend from FRIENDLIST where friendlist_user = "+user_id+" and friendlist_status='accepted' union select friendlist_user from FRIENDLIST where friendlist_friend = "+user_id+" and friendlist_status='accepted') and friendlist_status='accepted' union select friendlist_user as uids from FRIENDLIST where friendlist_friend in(select friendlist_friend from FRIENDLIST where friendlist_user = "+user_id+" and friendlist_status='accepted' union select friendlist_user from FRIENDLIST where friendlist_friend = "+user_id+" and friendlist_status='accepted') and friendlist_status='accepted' Not in(select friendlist_friend as uids from FRIENDLIST where friendlist_user = "+user_id+" and friendlist_status='accepted' union select friendlist_user as uids from FRIENDLIST where friendlist_friend = "+user_id+" and friendlist_status='accepted')) t1 LEFT OUTER JOIN (select friendlist_friend as uids1 from FRIENDLIST where friendlist_user = "+user_id+" and (friendlist_status='accepted' or friendlist_status='pending') union select friendlist_user as uids1 from FRIENDLIST where friendlist_friend = "+user_id+" and (friendlist_status='accepted' or friendlist_status='pending') union select "+user_id+") t2 on t1.uids=t2.uids1 where t2.uids1 IS NULL)";
//		String query = "select userdetails_pk,userdetails_firstname,userdetails_lastname,userdetails_picurl,userdetails_friend_count from USERDETAILS where userdetails_pk IN(select uids from (select friendlist_friend as uids from FRIENDLIST where friendlist_user in(select friendlist_friend from FRIENDLIST where friendlist_user = "+user_id+" and friendlist_status='accepted' union select friendlist_user from FRIENDLIST where friendlist_friend = "+user_id+" and friendlist_status='accepted') and friendlist_status='accepted' union select friendlist_user as uids from FRIENDLIST where friendlist_friend in(select friendlist_friend from FRIENDLIST where friendlist_user = "+user_id+" and friendlist_status='accepted' union select friendlist_user from FRIENDLIST where friendlist_friend = "+user_id+" and friendlist_status='accepted') and friendlist_status='accepted' Not in(select friendlist_friend as uids from FRIENDLIST where friendlist_user = "+user_id+" and friendlist_status='accepted' union select friendlist_user as uids from FRIENDLIST where friendlist_friend = "+user_id+" and friendlist_status='accepted')) t1 LEFT OUTER JOIN (select friendlist_friend as uids1 from FRIENDLIST where friendlist_user = "+user_id+" and friendlist_status='accepted' union select friendlist_user as uids1 from FRIENDLIST where friendlist_friend = "+user_id+" and friendlist_status='accepted' union select "+user_id+") t2 on t1.uids=t2.uids1 where t2.uids1 IS NULL)";
//		System.out.println(query);
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
		dbconn.getConn().close();
		if(friendlist.size()==0)
			return 1;
		return 0;
	}

	
	// For Suggesting Friends to My Friends - Chetan Sharma - 14/03/2017
	public int getUncommonFriendsList(String suggestedBy, String suggestedTo, List<Friend> uncommonFriends) throws SQLException {
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}
		ResultSet rs;
		String query = "SELECT userdetails_pk,userdetails_firstname,userdetails_lastname,userdetails_picurl,userdetails_friend_count FROM USERDETAILS WHERE userdetails_pk IN (SELECT friendlist_friend AS friend_list FROM FRIENDLIST WHERE friendlist_user ="+suggestedBy+" AND friendlist_status='accepted' UNION SELECT friendlist_user AS friend_list FROM FRIENDLIST WHERE friendlist_friend = "+suggestedBy+" AND friendlist_status='accepted') AND userdetails_pk NOT IN (SELECT userdetails_pk FROM USERDETAILS WHERE userdetails_pk IN (SELECT friendlist_friend AS friend_list FROM FRIENDLIST WHERE friendlist_user ="+suggestedTo+" AND (friendlist_status='accepted' or friendlist_status='pending') UNION SELECT friendlist_user AS friend_list FROM FRIENDLIST WHERE friendlist_friend ="+suggestedTo+" AND (friendlist_status='accepted' or friendlist_status='pending'))) AND userdetails_pk != "+suggestedTo+"";
//		System.out.println(query);
		rs = dbconn.getStmt().executeQuery(query);
		while(rs.next()){
			uncommonFriends.add(new Friend(Integer.parseInt(rs.getString("userdetails_pk")),rs.getString("userdetails_firstname")+" "+rs.getString("userdetails_lastname"),Integer.parseInt(rs.getString("userdetails_friend_count")),rs.getString("userdetails_picurl")));
		}
		if(uncommonFriends.size()==0)
			return 1;
		return 0;
	}
	// Ends Here ----For Suggesting Friends to My Friends - Chetan Sharma - 14/03/2017

	public int getOthersFriendList(String user_id, String fid, List<SearchPeople> friendlist)throws SQLException {
		// TODO Auto-generated method stub
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}
		ResultSet rs,rs1;
		String setstatus="";
		String query = "select userdetails_pk,userdetails_firstname,userdetails_lastname,userdetails_picurl,userdetails_friend_count from USERDETAILS where userdetails_pk IN(select friendlist_friend as friend_list from FRIENDLIST where friendlist_user = "+user_id+" and friendlist_status='accepted' union select friendlist_user as friend_list from FRIENDLIST where friendlist_friend = "+user_id+" and friendlist_status='accepted')";
		rs = dbconn.getStmt().executeQuery(query);
		while(rs.next()){
			int uid = Integer.parseInt(rs.getString("userdetails_pk"));
			String query2 = "SELECT friendlist_status FROM FRIENDLIST where friendlist_user = "+fid+" and friendlist_friend = "+uid+" or friendlist_user = "+uid+" and friendlist_friend = "+fid ; 
	//		System.out.println(query2);
			Statement stmt =dbconn.getConn().createStatement();
			rs1= stmt.executeQuery(query2);
			if(rs1.next()){
				String getstatus = rs1.getString("friendlist_status");
	//			System.out.println(getstatus);
				if(getstatus.equalsIgnoreCase("pending")) setstatus = "pending";
				else if(getstatus.equalsIgnoreCase("accepted")) setstatus = "friends";
				else if(getstatus.equalsIgnoreCase("deleted")) setstatus = "other";
			}
			else if(uid==Integer.parseInt(fid)){
				setstatus = "self";
			}
			else
			{
				setstatus = "other";
			}
			friendlist.add(new SearchPeople(uid,rs.getString("userdetails_firstname")+" "+rs.getString("userdetails_lastname"),Integer.parseInt(rs.getString("userdetails_friend_count")),rs.getString("userdetails_picurl"),setstatus));
		}
		dbconn.getConn().close();
		if(friendlist.size()==0)
			return 1;
		
		return 0;
	}
	
}