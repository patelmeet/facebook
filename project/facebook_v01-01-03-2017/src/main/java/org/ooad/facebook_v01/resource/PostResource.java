package org.ooad.facebook_v01.resource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.ooad.facebook_v01.model.Post;
import org.ooad.facebook_v01.service.PostService;

@Path("/post")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {
	private PostService postService = new PostService();
	
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
		System.out.println("This is in post resource: " + post.getPostText() + ", " + post.getSenderId() + ", " + post.getReceiverId());
		postService.addPost(post);
		System.out.println("Post Created Successfully!");
		
	}
	
	@POST
	@Path("/postToGroup/{userId}/{GroupId}")
	public void addPostToGroup(Post post) throws Exception{
		//Remaining
		System.out.println("This is in post resource: " + post.getPostText() + ", " + post.getSenderId() + ", " + post.getReceiverId());
		postService.addPost(post);
		System.out.println("Post Created Successfully!");
		
	}
	
	@POST
	@Path("/postToFriendTimeline/{userId}/{FriendId}")
	public void addPostToFriendTimeline(Post post) throws Exception{
		//Remaining
		System.out.println("This is in post resource: " + post.getPostText() + ", " + post.getSenderId() + ", " + post.getReceiverId());
		postService.addPost(post);
		System.out.println("Post Created Successfully!");
		
	}
}
