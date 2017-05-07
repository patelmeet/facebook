package org.ooad.facebook_v01.resource;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.ooad.facebook_v01.database.Convertor;
import org.ooad.facebook_v01.database.DatabaseConnection;
import org.ooad.facebook_v01.model.Friend;
import org.ooad.facebook_v01.model.SearchPeople;
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
//		System.out.println("from jersey -- friend list: "+friends.toString());
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
	@Path("/getOtherFriendsDetail/{userId}/{friendId}")
	public Response getOtherFriendsList(@PathParam("userId") String id,@PathParam("friendId") String fid) throws Exception{
		
		List<SearchPeople> friends= new ArrayList<SearchPeople>();
		int returnedval = friendlistservice.getOthersFriendList(id,fid,friends);
//		System.out.println(id+" "+fid);
//		System.out.println("from jersey -- friend list: "+friends.toString());
		if(returnedval==0){
			GenericEntity<List<SearchPeople>> entity = new GenericEntity<List<SearchPeople>>(friends){};
			return Response.status(201).entity(entity).build();
		}
		else if(returnedval==1)
			return Response.status(202).entity("NoFriends").build();
		else
			return Response.status(210).entity("DBError").build();
	}
	
							//For Suggesting Friends to My Friends - Chetan Sharma - 14/03/2017
	
	@GET
	@Path("/getUncommonFriends/{suggestedBy}/{suggestedTo}")
	public Response getUncommonFriendsList(@PathParam("suggestedBy") String suggestedBy,@PathParam("suggestedTo") String suggestedTo) throws Exception{
	//	System.out.println("Came in getUncommonFriends service");
		List<Friend> uncommonFriends= new ArrayList<Friend>();
		int returnedval = friendlistservice.getUncommonFriendsList(suggestedBy,suggestedTo,uncommonFriends);
	//	System.out.println("from jersey -- friend list: "+uncommonFriends.toString());
		if(returnedval==0){
			GenericEntity<List<Friend>> entity = new GenericEntity<List<Friend>>(uncommonFriends){};
			return Response.status(201).entity(entity).build();
		}
		else if(returnedval==1)
			return Response.status(202).entity("No Uncommon Friends").build();
		else
			return Response.status(210).entity("DBError").build();
	}
	
	@POST
	@Path("/addSuggestion/{suggestedBy}/{suggestedTo}/{suggestedWhom}")
	public Response addSuggestion(@PathParam("suggestedBy") String suggestedBy,@PathParam("suggestedTo") String suggestedTo,@PathParam("suggestedWhom") String suggestedWhom) throws Exception{
		
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "INSERT INTO SUGGESTION (suggestion_suggested_by,suggestion_suggested_to,suggestion_suggested_whom) VALUES ("+suggestedBy+","+suggestedTo+","+suggestedWhom+")";
		dbconn.getStmt().executeUpdate(query);
		return Response.status(201).build();
	}
	
	@POST
	@Path("/removeSuggestion/{suggestion_pk}")
	public Response removeSuggestion(@PathParam("suggestion_pk") String suggestion_pk) throws Exception{
		
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "DELETE FROM SUGGESTION WHERE suggestion_pk="+suggestion_pk;
		dbconn.getStmt().executeUpdate(query);
		return Response.status(201).build();
	}
	
	@POST
	@Path("/fetchSuggestions/{userId}")
	public Response fetchSuggestions(@PathParam("userId") String userId) throws Exception{
		DatabaseConnection dbconn = new DatabaseConnection();
		ResultSet rs;
		String query = "SELECT suggestion_pk,suggestion_suggested_whom,suggestion_suggested_by FROM facebook_v01.SUGGESTION WHERE suggestion_suggested_to="+userId;
		rs = dbconn.getStmt().executeQuery(query);
		JSONArray jsonarray = Convertor.convertToJSON(rs);
		return Response.status(201).entity(jsonarray.toString()).build();
	}
	
						// Ends Here ----For Suggesting Friends to My Friends - Chetan Sharma - 14/03/2017 
	
	
	
	
	
	//To get People You May Know -- Friends of Friends
	@POST
	@Path("/getFriendSuggestion/{userId}")
	public Response getFriendSuggestion(@PathParam("userId") String id) throws Exception{
		List<Friend> friends= new ArrayList<Friend>();
		int returnedval = friendlistservice.getFriendSuggestion(id,friends);
		
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
	//	System.out.println();
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
	//	System.out.println("HERE");
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
		query = "Update USERDETAILS SET userdetails_friend_count = userdetails_friend_count - 1 where userdetails_pk in("+userId+","+requestToId+")";
	//	System.out.println(query);
		dbconn.getStmt().executeUpdate(query);
		return Response.status(201).build();
	}
	
	@POST
	@Path("/getDetailsOnHover//{userId}")
	public Response getDetailsOnHover(@PathParam("userId") String userId) throws Exception{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "";
		ResultSet rs = dbconn.getStmt().executeQuery(query);
		return Response.status(201).build();
	}
}