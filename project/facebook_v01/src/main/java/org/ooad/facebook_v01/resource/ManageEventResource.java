package org.ooad.facebook_v01.resource;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
import org.ooad.facebook_v01.model.Events;
import org.ooad.facebook_v01.model.Friend;
import org.ooad.facebook_v01.model.Post;
import org.ooad.facebook_v01.service.ManageEventService;
import org.ooad.facebook_v01.service.PostService;

@Path("/eventList")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ManageEventResource {

	ManageEventService manageEventService=new ManageEventService();
	private PostService postService = new PostService();
	
	@POST
	@Path("/new/{userId}")
	public Response createEvent(Events events,@PathParam("userId") String id) throws Exception{
//		System.out.println("in event");
		int uid=Integer.parseInt(id);
		int retval=manageEventService.newEvent(uid, events);
//		System.out.println("in event");
		if(retval>=0){
			return Response.ok().build();
		}
		else{
			return Response.status(204).entity("DB ERROR").build();
		}
	}
	
	
	@POST
	@Path("/getAllEventDetail/{userId}")
	public Response getAllEventList(@PathParam("userId") String id) throws Exception{
		
		List<Events> events= new ArrayList<Events>();
		int returnedval = manageEventService.getEventList(id,events);
//		System.out.println("from jersey -- event list: "+events.toString());
		if(returnedval==0){
			GenericEntity<List<Events>> entity = new GenericEntity<List<Events>>(events){};
			return Response.status(201).entity(entity).build();
		}
		else if(returnedval==1)
			return Response.status(202).entity("NoEvents").build();
		else
			return Response.status(210).entity("DBError").build();
	}
	
	@POST
	@Path("/getPastEventDetail/{userId}")
	public Response getPastEventList(@PathParam("userId") String id) throws Exception{
		
		List<Events> events= new ArrayList<Events>();
		int returnedval = manageEventService.getPastEventList(id,events);
//		System.out.println("from jersey -- event list: "+events.toString());
		if(returnedval==0){
			GenericEntity<List<Events>> entity = new GenericEntity<List<Events>>(events){};
			return Response.status(201).entity(entity).build();
		}
		else if(returnedval==1)
			return Response.status(202).entity("NoEvents").build();
		else
			return Response.status(210).entity("DBError").build();
	}
	
	@POST
	@Path("/getFriendsForInvites/{eventId}/{userId}")
	public Response getAllFriendsList(@PathParam("userId") String id,@PathParam("eventId") String eventId) throws Exception{
		
		List<Friend> friends= new ArrayList<Friend>();
		int returnedval = manageEventService.getFriendList(id,eventId,friends);
//		System.out.println("from jersey -- friend list: "+friends.toString());
		if(returnedval==0){
			GenericEntity<List<Friend>> entity = new GenericEntity<List<Friend>>(friends){};
			return Response.status(201).entity(entity).build();
		}
		else if(returnedval==1)
			return Response.status(202).entity("NoFriendsForInvites").build();
		else
			return Response.status(210).entity("DBError").build();
	}
	//Event service for all pending , accepted events
	@POST
	@Path("/getPendingInvites/{userId}")
		public Response getPendingInvites(@PathParam("userId") String id) throws Exception
		{
			DatabaseConnection dbconn = new DatabaseConnection();
			if(! dbconn.isStatus()){
				return Response.status(210).entity("DBError").build();
			}
			ResultSet rs;
			String query = "Select u.userdetails_firstname , u.userdetails_lastname , el.eventlist_pk ,el.eventlist_status , e.event_pk, e.event_name , e.event_location, e.event_start_date , e.event_start_time , e.event_end_date , e.event_end_time , e.event_description , e.event_going , e.event_maybe , e.event_cant_go , e.event_photo , el.eventlist_status from EVENT e, EVENTLIST el , USERDETAILS u where u.userdetails_pk = e.userdetails_pk and e.event_pk = el.event_pk and el.eventlist_friend="+id+" and (e.event_end_date > curdate() OR (e.event_end_date = curdate() and e.event_end_time > curtime()) )";
			//String query = "Select u.userdetails_firstname , u.userdetails_lastname , el.eventlist_pk ,el.eventlist_status , e.event_name , e.event_location, e.event_start_date , e.event_start_time , e.event_end_date , e.event_end_time , e.event_description , e.event_going , e.event_maybe , e.event_cant_go , e.event_photo , el.eventlist_status from EVENT e, EVENTLIST el , USERDETAILS u where u.userdetails_pk = e.userdetails_pk and e.event_pk = el.event_pk and el.eventlist_friend="+id;
			rs = dbconn.getStmt().executeQuery(query);
			JSONArray jsonarray = Convertor.convertToJSON(rs);
			return Response.status(201).entity(jsonarray.toString()).build();
		}
	
	@POST
	@Path("/getEventDetails/{userId}/{eventId}")
		public Response getEventDetails(@PathParam("userId") String id,@PathParam("eventId") String eventId) throws Exception
		{
			DatabaseConnection dbconn = new DatabaseConnection();
			if(! dbconn.isStatus()){
				return Response.status(210).entity("DBError").build();
			}
			ResultSet rs;
			String query = "Select * from EVENT where event_pk ="+eventId;
			rs = dbconn.getStmt().executeQuery(query);
			JSONArray jsonarray = Convertor.convertToJSON(rs);
			return Response.status(201).entity(jsonarray.toString()).build();
		}
	
	@POST
	@Path("/sendEventInvite/{eventId}/{userId}/{requestToId}")
	public Response sendEventInvite(@PathParam("eventId") String eventId,@PathParam("userId") String userId,@PathParam("requestToId") String requestToId) throws Exception{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String tempquery = new String(requestToId);
//		System.out.println(tempquery);
		StringTokenizer sto = new StringTokenizer(tempquery,",");
//		System.out.println(count+" no of tokens");
//		System.out.println();
		
		while(sto.hasMoreTokens()){
				String str = sto.nextToken();
//				System.out.println(str);
				String query = "insert into EVENTLIST (eventlist_user,eventlist_friend,eventlist_status,event_pk) values ("+userId+","+str+",'Invited',"+eventId+")";
				dbconn.getStmt().executeUpdate(query);
		}
		
		return Response.status(201).build();
	}
	
	@POST
	@Path("/acceptEventInvite/{eventListId}/{status}")
	public Response acceptEventInvite(@PathParam("status") String status, @PathParam("eventListId") String eventListId) throws Exception{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
//		System.out.println(eventListId);
//		System.out.println(status);
		String previousStatus = null;
		String query = "select eventlist_status from EVENTLIST where eventlist_pk="+eventListId;
		ResultSet rs = dbconn.getStmt().executeQuery(query);
		if(rs.next()){
			previousStatus = rs.getString(1);
		}
//		System.out.println(previousStatus);
		
		query ="Update EVENTLIST set eventlist_status='"+status+"' where eventlist_pk="+eventListId;
		dbconn.getStmt().executeUpdate(query);
//		System.out.println("hello");
		
		//Increment count
		if(status.equals("Going"))
			query = "Update EVENT set event_going = event_going + 1 where event_pk= (Select event_pk from EVENTLIST where eventlist_pk IN ("+eventListId+"))"; 
		else if(status.equals("Maybe"))
			query = "Update EVENT set event_maybe = event_maybe + 1 where event_pk= (Select event_pk from EVENTLIST where eventlist_pk="+eventListId+")"; 
		else if(status.equals("Cant_go"))
			query = "Update EVENT set event_cant_go = event_cant_go + 1 where event_pk= (Select event_pk from EVENTLIST where eventlist_pk="+eventListId+")"; 
		else{}
		dbconn.getStmt().executeUpdate(query);
		
		//Decrement only if it is not invited
		if(previousStatus.equals("Going")){
			query = "Update EVENT set event_going = event_going - 1 where event_pk= (Select event_pk from EVENTLIST where eventlist_pk IN ("+eventListId+"))";
			dbconn.getStmt().executeUpdate(query);
		}
		else if(previousStatus.equals("Maybe")){
			query = "Update EVENT set event_maybe = event_maybe - 1 where event_pk= (Select event_pk from EVENTLIST where eventlist_pk="+eventListId+")";
			dbconn.getStmt().executeUpdate(query);
		}
		else if(previousStatus.equals("Cant_go")){
			query = "Update EVENT set event_cant_go = event_cant_go - 1 where event_pk= (Select event_pk from EVENTLIST where eventlist_pk="+eventListId+")";
			dbconn.getStmt().executeUpdate(query);
		}
		
		return Response.status(201).build();
	}
	
	
	
	
	/***** START: Methods added for posts on event page *****/ 
	@GET
	@Path("/fetchEventUpdates/{eventid}")
	public String fetchEventUpdates(@PathParam("eventid") int eventId) throws Exception{
		return postService.getEventPosts(eventId);
	}
	
	
	@POST
	@Path("/addEventUpdates/{eventid}/{userid}")
	public void addEventUpdates(@PathParam("eventid") int eventId, @PathParam("userid") int userId, Post post) throws Exception{
		postService.addEventPost(post);
	}	
	/***** END: Methods added for posts on event page *****/
	
	
	
	
	
	
	
	
	
	
	
	
}






