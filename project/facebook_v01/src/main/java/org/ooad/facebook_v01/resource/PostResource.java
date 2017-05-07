package org.ooad.facebook_v01.resource;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ooad.facebook_v01.database.Convertor;
import org.ooad.facebook_v01.database.DatabaseConnection;
import org.ooad.facebook_v01.model.Post;
import org.ooad.facebook_v01.service.PostService;

@Path("/post")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {
	private PostService postService = new PostService();
	
	//====================================================
		// for post into Group 
		@POST
		@Path("/postToGroup/{userId}")
		public void addPostToGroup(Post post) throws Exception{
			//Remaining
//			System.out.println("for post in Group Resource");
			
//			System.out.println("This is in post resource: " + post.getPostText() + ", " + post.getSenderId() + ", " + post.getReceiverId());
			postService.addGroupPost(post);
			
		}
		
		//=========== For getting group post 
		
		@GET
		@Path("/getGroupPosts/{groupId}")
		public String getGroupPosts(@PathParam("groupId") int groupId) throws Exception{
//			System.out.println("march 14 indise get group post");
			return postService.getGroupPosts(groupId);
		}
		
		//================== Group part ends here
		
	@GET
	@Path("/getTimelinePosts/{userId}")
	public String getTimelinePosts(@PathParam("userId") int userId) throws Exception{
		return postService.getTimelinePosts(userId);
	}
	
	@GET
	@Path("/getNewsfeedPosts/{userId}")
	public String getNewsfeedPosts(@PathParam("userId") int userId) throws Exception{
		return postService.getNewsfeedPosts(userId);
	}
	
	@POST
	@Path("/postStatus/{userId}")
	public void addPostStatus(Post post) throws Exception{
		//Remaining
		//System.out.println("This is in post resource: " + post.getPostText() + ", " + post.getSenderId() + ", " + post.getReceiverId());
		postService.addPost(post);
//		System.out.println("Post Created Successfully!");
		
	}
	
	
	@POST
	@Path("/postToFriendTimeline/{userId}/{FriendId}")
	public void addPostToFriendTimeline(Post post) throws Exception{
		//Remaining
//		System.out.println("This is in post resource: " + post.getPostText() + ", " + post.getSenderId() + ", " + post.getReceiverId());
		postService.addPost(post);
//		System.out.println("Post Created Successfully!");
		
	}
	
	//---------------- Like Section Starts -----------------------
	
	@POST
	@Path("/addPostLike/{postId}/{userId}")
	public Response addPostLike(@PathParam("postId") int postId , @PathParam("userId") int userId) throws SQLException
	{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "insert into POST_LIKE (post_like_user_id,post_like_post_pk) values ("+userId+","+postId+")";
		dbconn.getStmt().executeUpdate(query);
		query = "Update POST set post_like_count = post_like_count + 1 where post_pk = " + postId;
		dbconn.getStmt().executeUpdate(query);
		dbconn.getConn().close();
		return Response.status(201).build();
	}
	
	@POST
	@Path("/removePostLike/{postId}/{userId}")
	public Response removePostLike(@PathParam("postId") int postId , @PathParam("userId") int userId) throws SQLException
	{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "delete from POST_LIKE where post_like_user_id = "+userId+" and post_like_post_pk = "+postId+" ";
		dbconn.getStmt().executeUpdate(query);
		query = "Update POST set post_like_count = post_like_count - 1 where post_pk = " + postId;
		dbconn.getStmt().executeUpdate(query);
		dbconn.getConn().close();
		return Response.status(201).build();
	}
	
	@POST
	@Path("/getPostLikeList/{postId}")
	public Response getPostLikeList(@PathParam("postId") int postId) throws Exception
	{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "select userdetails_firstname,userdetails_lastname from USERDETAILS inner join POST_LIKE on userdetails_pk = post_like_user_id  where post_like_post_pk="+postId+" ";
		ResultSet rs;
		rs = dbconn.getStmt().executeQuery(query);
		JSONArray jsonarray = Convertor.convertToJSON(rs);
		dbconn.getConn().close();
		return Response.status(201).entity(jsonarray.toString()).build();
	}
	
	@POST
	@Path("/isPostLiked/{userId}/{postId}")
	public Response getPostLikeList(@PathParam("postId") int postId , @PathParam("userId") int userId) throws Exception
	{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "select post_like_pk from POST_LIKE where post_like_user_id="+userId+" and post_like_post_pk="+postId;
		ResultSet rs;
		rs = dbconn.getStmt().executeQuery(query);
		if(rs.next()){
			dbconn.getConn().close();
			return Response.status(201).entity("True").build();
		}
		dbconn.getConn().close();
		return Response.status(202).entity("False").build();
	}
	
	//-------------- Like Section Ends ------------------------------
	
	
	//-------------- Comment Like Starts ----------------------------
	@POST
	@Path("/addCommentLike/{commentId}/{userId}")
	public Response addCommentLike(@PathParam("commentId") int commentId , @PathParam("userId") int userId) throws SQLException
	{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "insert into COMMENT_LIKE (comment_like_user_id,comment_like_comment_id,comment_like_time) values ("+userId+","+commentId+",default)";
		dbconn.getStmt().executeUpdate(query);
		query = "Update COMMENT set comment_like_count = comment_like_count + 1 where comment_pk = " + commentId;
		dbconn.getStmt().executeUpdate(query);
		dbconn.getConn().close();
		return Response.status(201).build();
	}
	
	@POST
	@Path("/removeCommentLike/{commentId}/{userId}")
	public Response removeCommentLike(@PathParam("commentId") int commentId , @PathParam("userId") int userId) throws SQLException
	{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "delete from COMMENT_LIKE where comment_like_user_id = "+userId+" and comment_like_comment_id = "+commentId+" ";
		dbconn.getStmt().executeUpdate(query);
		query = "Update COMMENT set comment_like_count = comment_like_count - 1 where comment_pk = " + commentId;
		dbconn.getStmt().executeUpdate(query);
		dbconn.getConn().close();
		return Response.status(201).build();
	}
	
	@POST
	@Path("/getCommentLikeList/{commentId}")
	public Response getCommentLikeList(@PathParam("commentId") int commentId) throws Exception
	{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "select userdetails_firstname,userdetails_lastname from USERDETAILS inner join on COMMENT_LIKE userdetails_pk = comment_like_user_id  where comment_like_comment_id="+commentId+" ";
		ResultSet rs;
		rs = dbconn.getStmt().executeQuery(query);
		JSONArray jsonarray = Convertor.convertToJSON(rs);
		dbconn.getConn().close();
		return Response.status(201).entity(jsonarray.toString()).build();
	}
	
	//-------------- Comment Like Ends -------------------------------
	
	
	//-------------- Post Comment starts ----------------------------- 
	@POST
	@Path("/addPostComment/{postId}/{userId}")
	public Response addPostComment(@PathParam("postId") String postId , @PathParam("userId") String userId , String comment) throws SQLException
	{
//		System.out.println(comment);
		JSONObject jobj = new JSONObject(comment);
		String commentmsg = jobj.getString("comment");
//		System.out.println(ourdata);
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "insert into COMMENT (comment_text,comment_by,comment_post_id) values ('"+commentmsg+"',"+userId+","+postId+")";
		dbconn.getStmt().executeUpdate(query);
		dbconn.getConn().close();
		return Response.status(201).build();
		
	}
	
	@POST
	@Path("/getCommentList/{postId}")
	public Response getCommentList(@PathParam("postId") int postId) throws Exception
	{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "select userdetails_username,userdetails_firstname,userdetails_lastname,userdetails_picurl,comment_pk,comment_text,comment_created,comment_like_count from USERDETAILS inner join COMMENT on comment_by=userdetails_pk where comment_post_id="+postId;
		ResultSet rs;
		rs = dbconn.getStmt().executeQuery(query);
		JSONArray jsonarray = Convertor.convertToJSON(rs);
		dbconn.getConn().close();
		return Response.status(201).entity(jsonarray.toString()).build();
	}
	
	//---------------- Post Comment Ends ---------------------------
}
