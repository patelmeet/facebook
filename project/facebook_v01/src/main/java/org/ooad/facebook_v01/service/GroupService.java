package org.ooad.facebook_v01.service;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.ooad.facebook_v01.database.DatabaseConnection;
import org.ooad.facebook_v01.model.Friend;
import org.ooad.facebook_v01.model.GroupDetailsModel;
import org.ooad.facebook_v01.model.GroupJoinRequestList;
import org.ooad.facebook_v01.model.Post;

import com.mysql.jdbc.Statement;

public class GroupService {
	
//=============== Added on 1st april By Nitish ===============
		
	//For Getting members that could be added by admin
	
	public int getMembersToAdd(String userId , String group_id, List<GroupJoinRequestList> grouplist) throws SQLException{ 
		
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}
		
		ResultSet rs;
			
//		System.out.println("Here--userId = "+userId+" groupId = "+group_id);
		String query = "select userdetails_pk,userdetails_username,userdetails_firstname,userdetails_lastname,userdetails_picurl from USERDETAILS where userdetails_pk in(select * from (select friendlist_friend as friend_list from FRIENDLIST where friendlist_user = "+userId+" and friendlist_status='accepted' union select friendlist_user as friend_list from FRIENDLIST where friendlist_friend = "+userId+" and friendlist_status='accepted')as t1 where friend_list not in (select groupmembers_memberid from GROUPMEMBERS where groupmembers_groupid = "+group_id+"))";
//		System.out.println(query);
		
		rs = dbconn.getStmt().executeQuery(query);
		
		while(rs.next()){
			
			// Using GroupJoinRequestList model for retireving all group members as we need limited details 
			
			GroupJoinRequestList retrieved = new  GroupJoinRequestList();
			
			retrieved.setUserDetails_pk(Integer.parseInt(rs.getString(1)));
			retrieved.setUserDetails_userName(rs.getString(2));
			retrieved.setUserDetails_firstName(rs.getString(3));
			retrieved.setUserDetails_lastName(rs.getString(4));
			retrieved.setUserdetails_picurl(rs.getString(5));
			
			grouplist.add(retrieved);
						
		}
		if(grouplist.size()==0)
			return 1;
		return 0;
	}
	
	
////=============== Added on 1st april Ends : By Nitish===============
	
	
public int getGroupYouManageList(String user_id , List<GroupDetailsModel> grouplist) throws SQLException{ 
		
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return 10;
		}
		
		ResultSet rs;
		//String query = "select * from GROUPDETAILS where groupdetails_id in(select groupmembers_groupid from GROUPMEMBERS where groupmembers_memberid ="+user_id+" and groupmembers_role=0);";
		String query = "select * from GROUPDETAILS where groupdetails_createdby = "+ user_id +";";
		rs = dbconn.getStmt().executeQuery(query);
		
		
		
//		System.out.println("Inside service First");
		
		while(rs.next()){
			
//			System.out.println("Inside service Second");
			
//			System.out.println(rs.getString("groupdetails_name"));
			
			GroupDetailsModel retrieved = new GroupDetailsModel ();
			
			retrieved.setGroupdetails_id(rs.getInt("groupdetails_id"));
			retrieved.setGroupdetails_name(rs.getString("groupdetails_name"));
			retrieved.setGroupdetails_createdby(rs.getInt("groupdetails_createdby"));
			retrieved.setGroupdetails_privacy(rs.getInt("groupdetails_privacy"));
			retrieved.setGroupdetails_membercount(rs.getInt("groupdetails_membercount"));
			//retrieved.setGroupdetails_datecreated(rs.getDate("groupdetails_datecreated"));
			retrieved.setGroupdetails_description(rs.getString("groupdetails_description"));
			retrieved.setGroupdetails_web_email(rs.getString("groupdetails_web_email"));
			retrieved.setGroupdetails_imageurl(rs.getString("groupdetails_imageurl"));	
			
			grouplist.add(retrieved);
		
		}
		if(grouplist.size()==0)
			return 1;
		return 0;
	}




public int getAllYourGroupList(String user_id , List<GroupDetailsModel> grouplist) throws SQLException{ 
	
	DatabaseConnection dbconn = new DatabaseConnection();
	if(! dbconn.isStatus()){
		return 10;
	}
	
	ResultSet rs;
	
	// Status 1 - joined
	// Role !=0 means , either Moderator or Member
	
	String query = "select * from GROUPDETAILS where groupdetails_id in(select groupmembers_groupid from GROUPMEMBERS where groupmembers_memberid ="+user_id+" and groupmembers_role!=0 and groupmembers_status=1);";
	rs = dbconn.getStmt().executeQuery(query);
	
	
	
//	System.out.println("Inside service First");
	
	while(rs.next()){
		
//		System.out.println("Inside service Second");
		
//		System.out.println(rs.getString("groupdetails_name"));
		
		GroupDetailsModel retrieved = new GroupDetailsModel ();
		
		retrieved.setGroupdetails_id(rs.getInt("groupdetails_id"));
		retrieved.setGroupdetails_name(rs.getString("groupdetails_name"));
		retrieved.setGroupdetails_createdby(rs.getInt("groupdetails_createdby"));
		retrieved.setGroupdetails_privacy(rs.getInt("groupdetails_privacy"));
		retrieved.setGroupdetails_membercount(rs.getInt("groupdetails_membercount"));
		//retrieved.setGroupdetails_datecreated(rs.getDate("groupdetails_datecreated"));
		retrieved.setGroupdetails_description(rs.getString("groupdetails_description"));
		retrieved.setGroupdetails_web_email(rs.getString("groupdetails_web_email"));
		retrieved.setGroupdetails_imageurl(rs.getString("groupdetails_imageurl"));	
		
		grouplist.add(retrieved);
	
	}
	if(grouplist.size()==0)
		return 1;
	return 0;
}

//// For Getting a Particular Group Details

public int getParticularGroupList(String user_id ,String group_id, GroupDetailsModel retrieved) throws SQLException{ 
	
	DatabaseConnection dbconn = new DatabaseConnection();
	if(! dbconn.isStatus()){
		return 10;
	}
	
	ResultSet rs;
	
	// Status 1 - joined
	// Role !=0 means , either Moderator or Member
	//select * from GROUPDETAILS where groupdetails_id = 1 and groupdetails_createdby = 1;
	
//	System.out.println(group_id + " indise 13 march " + user_id); 
	
	String query = "select * from GROUPDETAILS where groupdetails_id ="+ group_id;
	rs = dbconn.getStmt().executeQuery(query);

//	System.out.println("Inside service First");
	
	while(rs.next()){
		
//		System.out.println("Inside service Second");
		
//		System.out.println("hello " + rs.getString("groupdetails_name"));
		
		retrieved.setGroupdetails_id(rs.getInt("groupdetails_id"));
		retrieved.setGroupdetails_name(rs.getString("groupdetails_name"));
		retrieved.setGroupdetails_createdby(rs.getInt("groupdetails_createdby"));
		retrieved.setGroupdetails_privacy(rs.getInt("groupdetails_privacy"));
		retrieved.setGroupdetails_membercount(rs.getInt("groupdetails_membercount"));
		//retrieved.setGroupdetails_datecreated(rs.getDate("groupdetails_datecreated"));
		retrieved.setGroupdetails_description(rs.getString("groupdetails_description"));
		retrieved.setGroupdetails_web_email(rs.getString("groupdetails_web_email"));
		retrieved.setGroupdetails_imageurl(rs.getString("groupdetails_imageurl"));	
	
	}
	if( retrieved.getGroupdetails_id()!= Integer.parseInt(group_id) )
		return 1;
	return 0;
}

//// For GroupJoinRequestList

public int getGroupJoinRequestList(String group_id, List<GroupJoinRequestList> grouplist) throws SQLException{ 
	
	DatabaseConnection dbconn = new DatabaseConnection();
	if(! dbconn.isStatus()){
		return 10;
	}
	
//	System.out.println("Inside Join Request service");
	
	ResultSet rs;
	
	// Status 1 - joined
	// Role !=0 means , either Moderator or Member
	//select * from GROUPDETAILS where groupdetails_id = 1 and groupdetails_createdby = 1;
	
	String query = "select userdetails_pk,userdetails_username,userdetails_firstname,userdetails_lastname,userdetails_picurl  from USERDETAILS where userdetails_pk in(Select groupmembers_memberid from GROUPMEMBERS where groupmembers_groupid="+group_id +" and groupmembers_status=0)";
	rs = dbconn.getStmt().executeQuery(query);

	//System.out.println("Inside service First");
	
	while(rs.next()){
		
		GroupJoinRequestList retrieved = new GroupJoinRequestList();
		
		retrieved.setUserDetails_pk(rs.getInt(1));
		retrieved.setUserDetails_userName(rs.getString(2));
		retrieved.setUserDetails_firstName(rs.getString(3));
		retrieved.setUserDetails_lastName(rs.getString(4));	
		retrieved.setUserdetails_picurl(rs.getString(5));
		
//		System.out.println(rs.getInt(1) + " " +rs.getString(2));
		
		grouplist.add(retrieved);
		
		//System.out.println("Inside service Second Hello");
		
	}
	if( grouplist.size()==0 )
		return 1;
	return 0;
}


/// For getting all GroupMembers List
/// Added after 13th march

public int getGroupMembersList(String group_id, List<GroupJoinRequestList> grouplist) throws SQLException{ 
	
	DatabaseConnection dbconn = new DatabaseConnection();
	if(! dbconn.isStatus()){
		return 10;
	}
	
	ResultSet rs;
	
//	System.out.println("Inside getGroupMembersList service zero");
		 
	String query = "select userdetails_pk,userdetails_username,userdetails_firstname,userdetails_lastname,userdetails_picurl from USERDETAILS where userdetails_pk in (select groupmembers_memberid from GROUPMEMBERS where groupmembers_groupid = " + group_id + " and groupmembers_status=1)";
	rs = dbconn.getStmt().executeQuery(query);

//	System.out.println("Inside getGroupMembersList service First");
	
	while(rs.next()){
		
		// Using GroupJoinRequestList model for retireving all group members as we need limited details 
		
		GroupJoinRequestList retrieved = new  GroupJoinRequestList();
		
		retrieved.setUserDetails_pk(Integer.parseInt(rs.getString(1)));
		retrieved.setUserDetails_userName(rs.getString(2));
		retrieved.setUserDetails_firstName(rs.getString(3));
		retrieved.setUserDetails_lastName(rs.getString(4));
		retrieved.setUserdetails_picurl(rs.getString(5));
		
		grouplist.add(retrieved);
		
//		System.out.println("Inside service Second");
		
	
	}
	if(grouplist.size()==0)
		return 1;
	return 0;
}



//// For Discover Group

public int getDiscoveredGroup(String user_id , List<GroupDetailsModel> grouplist) throws SQLException{ 
	
//	System.out.println("INside Discover Group Service");
	
	DatabaseConnection dbconn = new DatabaseConnection();
	if(! dbconn.isStatus()){
		return 10;
	}
	
	ResultSet rs;
	String query = "select * from GROUPDETAILS where groupdetails_id NOT IN(select groupmembers_groupid from GROUPMEMBERS where groupmembers_memberid ="+user_id+");";
	//String query = "select * from GROUPDETAILS where groupdetails_createdby = "+ user_id +";";
	rs = dbconn.getStmt().executeQuery(query);
	
//	System.out.println("Inside service First");
	
	while(rs.next()){
		
//		System.out.println("Inside service Second");
//		
//		System.out.println(rs.getString("groupdetails_name"));
		
		GroupDetailsModel retrieved = new GroupDetailsModel ();
		
		retrieved.setGroupdetails_id(rs.getInt("groupdetails_id"));
		retrieved.setGroupdetails_name(rs.getString("groupdetails_name"));
		retrieved.setGroupdetails_createdby(rs.getInt("groupdetails_createdby"));
		retrieved.setGroupdetails_privacy(rs.getInt("groupdetails_privacy"));
		retrieved.setGroupdetails_membercount(rs.getInt("groupdetails_membercount"));
		//retrieved.setGroupdetails_datecreated(rs.getDate("groupdetails_datecreated"));
		retrieved.setGroupdetails_description(rs.getString("groupdetails_description"));
		retrieved.setGroupdetails_web_email(rs.getString("groupdetails_web_email"));
		retrieved.setGroupdetails_imageurl(rs.getString("groupdetails_imageurl"));	
		
		grouplist.add(retrieved);
	
	}
	if(grouplist.size()==0)
		return 1;
	return 0;
}


/// For creating new Group

public int createNewGroup(String user_id , String group_Name,String group_People,String group_Privacy) throws SQLException{ 
	
//	System.out.println("Indside Service");
	
	DatabaseConnection dbconn = new DatabaseConnection();
	if(! dbconn.isStatus()){
		return 10;
	}
	
	ResultSet rs1,rs2;

//	System.out.println("Inside service First:");
	
	
	String query2 = "insert into GROUPDETAILS(groupdetails_name,groupdetails_createdby,groupdetails_privacy) values('" + group_Name + "','" + user_id +"','" + group_Privacy +"')";
//	System.out.println(query2);

	 dbconn.getStmt().executeUpdate(query2);
//	System.out.println("Inside service Second ");
	
	
	String key=null;
	
	
	String query3="select groupdetails_id from GROUPDETAILS where groupdetails_name = '"+ group_Name + "'and groupdetails_createdby = '" + user_id + "'";
	//String query3 = "select groupdetails_id from GROUPDETAILS where groupdetails_name ="+ group_Name + " and groupdetails_createdby = "+ ; 
	rs2 = dbconn.getStmt().executeQuery(query3);
	while(rs2.next()){
//		System.out.println("inside while");
		key = rs2.getString(1);
				//("groupdetails_id");
	}	
//	System.out.println("Inside service third: " + key );
	
	
	String query4 = "insert into GROUPMEMBERS(groupmembers_groupid,groupmembers_memberid,groupmembers_role,groupmembers_status) values (" +key+","+user_id+","+0+","+1+")";
	dbconn.getStmt().executeUpdate(query4);
//	System.out.println("Inside service Fourth");
	
	dbconn.getConn().close();
	
	return 0;
	
	
	

}


}