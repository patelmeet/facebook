package org.ooad.facebook_v01.resource;

import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.ooad.facebook_v01.database.Convertor;
import org.ooad.facebook_v01.database.DatabaseConnection;

@Path("/EventMemberList")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OnHoverEventMemberList {

	@POST
	@Path("/getEventMemberList/{eventId}/{status}")
	public Response getEventMemberList(@PathParam("eventId") int eventId,@PathParam("status") String status) throws Exception{
//		System.out.println("I came inside new service");
		//memberDetails user =new memberDetails();
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		ResultSet rs;
		String query = "select u.userdetails_firstname, u.userdetails_lastname  from USERDETAILS u,EVENTLIST e  where u.userdetails_pk=e.eventlist_friend and e.eventlist_status='"+status+"' and e.event_pk = " + eventId;
		rs = dbconn.getStmt().executeQuery(query);
		JSONArray jsonarray = Convertor.convertToJSON(rs);
		dbconn.getConn().close();
		return Response.status(201).entity(jsonarray.toString()).build();
	}

	
}
