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

@Path("/friends")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class TimeLineFriendResource {
	
	@POST
	@Path("/getFriendDetails/{userId}")
	public Response getFriendDetails(@PathParam("userId") String userId) throws Exception{
		DatabaseConnection dbconn = new DatabaseConnection();
		ResultSet rs;
		String query = "select * from USERDETAILS where userdetails_pk="+userId;
		rs = dbconn.getStmt().executeQuery(query);
		JSONArray jsonarray = Convertor.convertToJSON(rs);
		return Response.status(201).entity(jsonarray.toString()).build();
	}
}
