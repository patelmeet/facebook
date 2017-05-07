package org.ooad.facebook_v01.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

import org.ooad.facebook_v01.database.DatabaseConnection;
import org.ooad.facebook_v01.model.SearchPeople;

public class SearchService {
	
	public int getFriendList(String user_id,String query,List<SearchPeople> friendlist) throws SQLException{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}
		String selectquery="";
		ResultSet rs;
		String tempquery = new String(query);
		StringTokenizer sto = new StringTokenizer(tempquery,",");
		int count = sto.countTokens();
//		System.out.println(count+" no of tokens");
		while(sto.hasMoreTokens()){
				String str = sto.nextToken();
//				System.out.println(str);
				selectquery += "select userdetails_pk,userdetails_firstname,userdetails_lastname,userdetails_picurl,userdetails_friend_count from USERDETAILS where (userdetails_firstname LIKE '%"+str+"%' or userdetails_lastname  LIKE '%"+str+"%') and userdetails_pk IN(select friendlist_friend as friend_list from FRIENDLIST where friendlist_user = "+user_id+" and friendlist_status='accepted' union select friendlist_user as friend_list from FRIENDLIST where friendlist_friend = "+user_id+" and friendlist_status='accepted')";
				count--;
				if(count > 0)
					selectquery += " union ";
		}
//		System.out.println(selectquery);
		rs = dbconn.getStmt().executeQuery(selectquery);
		while(rs.next()){
			friendlist.add(new SearchPeople(Integer.parseInt(rs.getString("userdetails_pk")),rs.getString("userdetails_firstname")+" "+rs.getString("userdetails_lastname"),Integer.parseInt(rs.getString("userdetails_friend_count")),rs.getString("userdetails_picurl"),"Friends"));
		}
		dbconn.getConn().close();
		if(friendlist.size()==0)
			return 1;
		return 0;
	}
	
	
	public int getPendingRequests(String user_id ,String query, List<SearchPeople> friendlist) throws SQLException{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}
		String selectquery="";
		ResultSet rs;
		StringTokenizer sto = new StringTokenizer(query,",");
		int count = sto.countTokens();
//		System.out.println(count+" no of tokens");
		while(sto.hasMoreTokens()){
			String str = sto.nextToken();
//			System.out.println(str);
			selectquery += "select userdetails_pk,userdetails_firstname,userdetails_lastname,userdetails_picurl,userdetails_friend_count from USERDETAILS where (userdetails_firstname LIKE '%"+str+"%' or userdetails_lastname  LIKE '%"+str+"%') and userdetails_pk IN(select friendlist_friend from FRIENDLIST where friendlist_status='pending' and friendlist_user="+user_id+")";
			count--;
			if(count > 0)
				selectquery += " union ";
		}
//		System.out.println(selectquery);
			rs = dbconn.getStmt().executeQuery(selectquery);
			while(rs.next()){
				friendlist.add(new SearchPeople(Integer.parseInt(rs.getString("userdetails_pk")),rs.getString("userdetails_firstname")+" "+rs.getString("userdetails_lastname"),Integer.parseInt(rs.getString("userdetails_friend_count")),rs.getString("userdetails_picurl"),"Pending"));
			}
		
		dbconn.getConn().close();
		if(friendlist.size()==0)
			return 1;
		return 0;
	}
	
	public int getFriendSuggestion(String user_id , String query , List<SearchPeople> friendlist) throws SQLException{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}
		ResultSet rs;
		String selectquery="";
		StringTokenizer sto = new StringTokenizer(query,",");
		int count = sto.countTokens();
		while(sto.hasMoreTokens()){
			String str = sto.nextToken();
			selectquery += "select userdetails_pk,userdetails_firstname,userdetails_lastname,userdetails_picurl,userdetails_friend_count from USERDETAILS where (userdetails_firstname LIKE '%"+str+"%' or userdetails_lastname  LIKE '%"+str+"%') and userdetails_pk IN(select userdetails_pk as ids from USERDETAILS where userdetails_pk NOT IN(select friendlist_user as ids from FRIENDLIST where friendlist_friend="+user_id+" and friendlist_status in('accepted','pending','blocked') union select friendlist_friend as ids from FRIENDLIST where friendlist_user="+user_id+" and friendlist_status in('accepted','pending','blocked') union SELECT "+user_id+"))";
			count--;
			if(count > 0)
				selectquery += " union ";
			}
			rs = dbconn.getStmt().executeQuery(selectquery);
			while(rs.next()){
				friendlist.add(new SearchPeople(Integer.parseInt(rs.getString("userdetails_pk")),rs.getString("userdetails_firstname")+" "+rs.getString("userdetails_lastname"),Integer.parseInt(rs.getString("userdetails_friend_count")),rs.getString("userdetails_picurl"),"Others"));
			}
		if(friendlist.size()==0)
			return 1;
		return 0;
	}
	
	
	public int getRequestedRequests(String user_id ,String query, List<SearchPeople> friendlist) throws SQLException{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}
		ResultSet rs;
		String selectquery="";
		StringTokenizer sto = new StringTokenizer(query,",");
		int count = sto.countTokens();
		while(sto.hasMoreTokens()){
		String str = sto.nextToken();
		 selectquery += "select userdetails_pk,userdetails_firstname,userdetails_lastname,userdetails_picurl,userdetails_friend_count from USERDETAILS where (userdetails_firstname LIKE '%"+str+"%' or userdetails_lastname  LIKE '%"+str+"%') and userdetails_pk IN(select friendlist_user from FRIENDLIST where friendlist_status='pending' and friendlist_friend="+user_id+")";
		 count--;
			if(count > 0)
				selectquery += " union ";
		}
//		System.out.println(selectquery);
			 rs = dbconn.getStmt().executeQuery(selectquery);
			while(rs.next()){
				friendlist.add(new SearchPeople(Integer.parseInt(rs.getString("userdetails_pk")),rs.getString("userdetails_firstname")+" "+rs.getString("userdetails_lastname"),Integer.parseInt(rs.getString("userdetails_friend_count")),rs.getString("userdetails_picurl"),"Requested"));
			}
		dbconn.getConn().close();
		if(friendlist.size()==0)
			return 1;
		return 0;
	}

}
