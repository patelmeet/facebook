package org.ooad.facebook_v01.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import org.ooad.facebook_v01.database.DatabaseConnection;
import org.ooad.facebook_v01.model.Friend;
import org.ooad.facebook_v01.service.ManageFriendService;

@Path("/friends")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class ManageFriendResource {
	
	ManageFriendService friendlistservice=new ManageFriendService();
	
	@POST
	@Path("/getAllFriendsDetail/{userId}")
	public Response getAllFriendsList(@PathParam("userId") String id) throws Exception{
		
		List<Friend> friends= new ArrayList<Friend>();
		int returnedval = friendlistservice.getFriendList(id,friends);
		System.out.println("from jersey -- friend list: "+friends.toString());
		if(returnedval==0){
			GenericEntity<List<Friend>> entity = new GenericEntity<List<Friend>>(friends){};
			return Response.status(201).entity(entity).build();
		}
		else if(returnedval==1)
			return Response.status(202).entity("NoFriends").build();
		else
			return Response.status(210).entity("DBError").build();
	}
	
	@POST
	@Path("/getFriendSuggestion/{userId}")
	public Response getFriendSuggestion(@PathParam("userId") String id) throws Exception{
		List<Friend> friends= new ArrayList<Friend>();
		int returnedval = friendlistservice.getFriendSuggestion(id,friends);
		System.out.println("from jersey -- friend list: "+friends.toString());
		if(returnedval==0){
			GenericEntity<List<Friend>> entity = new GenericEntity<List<Friend>>(friends){};
			return Response.status(201).entity(entity).build();
		}
		else if(returnedval==1)
			return Response.status(202).entity("NoFriends").build();
		else
			return Response.status(210).entity("DBError").build();
	}
	
	@POST
	@Path("/getPendingRequests/{userId}")
	public Response getPendingRequests(@PathParam("userId") String id) throws Exception{
		List<Friend> friends= new ArrayList<Friend>();
		int returnedval = friendlistservice.getPendingRequests(id,friends);
		if(returnedval==0){
			GenericEntity<List<Friend>> entity = new GenericEntity<List<Friend>>(friends){};
			return Response.status(201).entity(entity).build();
		}
		else if(returnedval==1)
			return Response.status(202).entity("NoFriends").build();
		else
			return Response.status(210).entity("DBError").build();
	}
	
	@POST
	@Path("/sendFriendRequest/{userId}/{requestToId}")
	public Response sendFriendRequest(@PathParam("userId") String userId,@PathParam("requestToId") String requestToId) throws Exception{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "insert into FRIENDLIST (friendlist_user,friendlist_friend,friendlist_status) values ("+userId+","+requestToId+",'pending')";
		dbconn.getStmt().executeUpdate(query);
		return Response.status(201).build();
	}
	
	@POST
	@Path("/acceptFriendRequest/{userId}/{requestToId}")
	public Response acceptFriendRequest(@PathParam("userId") String userId,@PathParam("requestToId") String requestToId) throws Exception{
		System.out.println();
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "Update FRIENDLIST set friendlist_status='accepted' where friendlist_user="+userId+" and friendlist_friend="+requestToId ;
		dbconn.getStmt().executeUpdate(query);
		query = "Update FRIENDLIST set friendlist_status='accepted' where friendlist_user="+requestToId+" and friendlist_friend="+userId ;
		dbconn.getStmt().executeUpdate(query);
		query = "Update USERDETAILS set userdetails_friend_count = userdetails_friend_count + 1 where userdetails_pk in("+userId+","+requestToId+")";
		dbconn.getStmt().executeUpdate(query);
		System.out.println("HERE");
		return Response.status(201).build();
	}
	
	@POST
	@Path("/deleteFriendRequest/{userId}/{requestToId}")
	public Response deleteFriendRequest(@PathParam("userId") String userId,@PathParam("requestToId") String requestToId) throws Exception{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "Update FRIENDLIST set friendlist_status='deleted' where friendlist_user="+userId+" and friendlist_friend="+requestToId ;
		dbconn.getStmt().executeUpdate(query);
		query = "Update FRIENDLIST set friendlist_status='deleted' where friendlist_user="+requestToId+" and friendlist_friend="+userId ;
		dbconn.getStmt().executeUpdate(query);
		return Response.status(201).build();
	}
	
	@POST
	@Path("/deleteFriend/{userId}/{requestToId}")
	public Response deleteFriend(@PathParam("userId") String userId,@PathParam("requestToId") String requestToId) throws Exception{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "delete from FRIENDLIST where friendlist_user="+userId+" and friendlist_friend="+requestToId ;
		dbconn.getStmt().executeUpdate(query);
		query = "delete from FRIENDLIST where friendlist_user="+requestToId+" and friendlist_friend="+userId ;
		dbconn.getStmt().executeUpdate(query);
		query = "Update USERDETAILS set userdetails_friend_count = userdetails_friend_count - 1 where userdetails_pk in("+userId+","+requestToId+")";
		dbconn.getStmt().executeUpdate(query);
		return Response.status(201).build();
	}
	
	
}
