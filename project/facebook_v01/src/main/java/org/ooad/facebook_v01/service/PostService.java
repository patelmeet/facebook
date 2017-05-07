package org.ooad.facebook_v01.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.ooad.facebook_v01.database.Convertor;
import org.ooad.facebook_v01.database.DatabaseConnection;
import org.ooad.facebook_v01.model.Post;

public class PostService {
	private ResultSet myResult;
	private DatabaseConnection myConn;
	
	private void estabilishConnection(){
		myConn = new DatabaseConnection();
	}
	
	private void closeConnection() throws SQLException{
		myConn.getConn().close();
	}
	
	// This method returns only your posts.
	public String getTimelinePosts(int usedId) throws Exception{
		estabilishConnection();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String query = "select post_pk,post_senderId,post_statusText,post_date,post_like_count,userdetails_firstname,userdetails_lastname,userdetails_picurl from POST inner join USERDETAILS on post_senderId=userdetails_pk where post_receiverId="+usedId + " and post_groupId is NULL and post_eventId is NULL order by post_date DESC";
			myResult = myConn.getStmt().executeQuery(query);
			/*while (myResult.next()){
				System.out.println(myResult.getString("post_pk") + " " + myResult.getString("post_statusText") + " " + myResult.getString("post_senderId"));
			}*/
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
		}		
		JSONArray str = Convertor.convertToJSON(myResult);
		closeConnection();
		//System.out.println(str.toString());
		return str.toString();
	}
	
	// This method returns the mix of your as well as your friends' posts.
	public String getNewsfeedPosts(int usedId) throws Exception{
		estabilishConnection();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String query = "select post_pk,post_statusText,post_senderId,post_date,post_like_count,post_img_url,userdetails_firstname,userdetails_lastname,userdetails_picurl from POST inner join USERDETAILS on post_senderId=userdetails_pk where (post_senderId IN(select friendlist_friend as friend_list from FRIENDLIST where friendlist_user = "+usedId+" and friendlist_status='accepted' union select friendlist_user as friend_list from FRIENDLIST where friendlist_friend = "+usedId+" and friendlist_status='accepted' union SELECT "+usedId+") or post_receiverId IN(select friendlist_friend as friend_list from FRIENDLIST where friendlist_user = "+usedId+" and friendlist_status='accepted' union select friendlist_user as friend_list from FRIENDLIST where friendlist_friend = "+usedId+" and friendlist_status='accepted' union SELECT "+usedId+")) and post_groupId is NULL and post_eventId is NULL order by post_date DESC";
			myResult = myConn.getStmt().executeQuery(query);
			/*while (myResult.next()){
				System.out.println(myResult.getString("post_pk") + " " + myResult.getString("post_statusText") + " " + myResult.getString("post_senderId"));
			}*/
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
		}		
		JSONArray str = Convertor.convertToJSON(myResult);
		closeConnection();
		return str.toString();
	}
	
	
	// This method adds a post into the database table.
	public void addPost(Post post) throws SQLException{
		estabilishConnection();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String query = "insert into POST values(default, " + "\'" + post.getPostText() + "\'" + ", default," + post.getSenderId()+","+post.getReceiverId()+",NULL,0,'"+post.getPost_img_url()+"', default)";
			//System.out.println(query);
			myConn.getStmt().executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
		}		
		closeConnection();
	}
	
	
	
	
	
	
	
////============ For Group Part

// This method adds a new post into the database for a group.
	public void addGroupPost(Post post) throws SQLException{
		estabilishConnection();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String query = "insert into POST values(default, " + "\'" + post.getPostText() + "\'" + ", default," + post.getSenderId()+","+post.getSenderId()+","+post.getReceiverId()+", 0, default, default)";
			
			System.out.println("Query: " + query);
			myConn.getStmt().executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
		}		
		closeConnection();
	}
	
	
// This method fetches the posts of a group from the database.	
	public String getGroupPosts(int groupId) throws Exception{
		estabilishConnection();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			String query = "select post_pk,post_statusText,post_senderId,post_date,post_like_count,userdetails_firstname,userdetails_lastname,userdetails_picurl from POST inner join USERDETAILS on post_senderId=userdetails_pk where post_senderId IN(select post_senderId from POST where post_groupId = "
							+ groupId
							+") and post_groupId ="
							+ groupId
							+" order by post_date DESC";
			
			myResult = myConn.getStmt().executeQuery(query);
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
		}		
		JSONArray str = Convertor.convertToJSON(myResult);
		closeConnection();
		return str.toString();
	}
	
	
////============ Group Part Ends here
	
	
	
	
	
	
/*****  Code for event posts starts here	*****/
	
	// This method adds a new post for an event in the database.
	public void addEventPost(Post post) throws SQLException{
		estabilishConnection();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String query = "insert into POST values(default, " + "\'" + post.getPostText() + "\'" + ", default," + post.getSenderId()+","+post.getReceiverId()+", default" + ", 0, default, "+ post.getEventId()+")";
			myConn.getStmt().executeUpdate(query);
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
		}		
		closeConnection();
	}
	
	
// This method fetches the posts of an event from the database.	
	public String getEventPosts(int eventId) throws Exception{
		estabilishConnection();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String query = "Select u.userdetails_picurl, u.userdetails_firstname, u.userdetails_lastname, p.post_date, p.post_statusText from facebook_v01.POST p JOIN USERDETAILS u ON p.post_senderId = u.userdetails_pk where p.post_eventId = " + eventId + " order by p.post_date desc";
			myResult = myConn.getStmt().executeQuery(query);
		}
		catch(SQLException e){
			System.out.println(""+e.getMessage());
		}
		catch(ClassNotFoundException e){
			System.out.println(""+e.getMessage());
		}		
		JSONArray str = Convertor.convertToJSON(myResult);
		closeConnection();
		return str.toString();
	}
/*****  Code for event posts ends here	*****/	
	
}
