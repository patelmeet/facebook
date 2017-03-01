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
			String query = "select post_pk,post_statusText,post_date,userdetails_firstname,userdetails_lastname,userdetails_picurl from POST inner join userdetails on post_senderId=userdetails_pk where post_receiverId="+usedId + " order by post_date DESC";
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
			String query = "select post_pk,post_statusText,post_date,userdetails_firstname,userdetails_lastname,userdetails_picurl from POST inner join USERDETAILS on post_senderId=userdetails_pk where post_senderId IN(select friendlist_friend as friend_list from FRIENDLIST where friendlist_user = "+usedId+" and friendlist_status='accepted' union select friendlist_user as friend_list from FRIENDLIST where friendlist_friend = "+usedId+" and friendlist_status='accepted' union SELECT "+usedId+") or post_receiverId IN(select friendlist_friend as friend_list from FRIENDLIST where friendlist_user = "+usedId+" and friendlist_status='accepted' union select friendlist_user as friend_list from FRIENDLIST where friendlist_friend = "+usedId+" and friendlist_status='accepted' union SELECT "+usedId+") order by post_date DESC";
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
			String query = "insert into POST values(default, " + "\'" + post.getPostText() + "\'" + ", default," + "1, 1)";
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
}
