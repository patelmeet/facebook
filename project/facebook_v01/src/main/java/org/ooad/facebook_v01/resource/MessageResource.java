/**** Resource File for Messenger *****/


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

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class MessageResource {

	@POST
	@Path("/addMessage/{sendBy}/{receiveBy}")
	public Response addMessage(@PathParam("sendBy") String sender,@PathParam("receiveBy") String receiver,String messageData) throws Exception{
		System.out.println(messageData);
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "INSERT INTO MESSAGES (messages_sender_id,messages_receiver_id,messages_message) VALUES ("+sender+","+receiver+",'"+messageData+"')";
		dbconn.getStmt().executeUpdate(query);
		dbconn.getConn().close();
		return Response.status(201).build();
	}
	
	

	@POST
	@Path("/fetchMessage/{senderId}/{receiverId}")
	public Response fetchMesseges(@PathParam("senderId") String senderId,@PathParam("receiverId") String receiverId) throws Exception{
		DatabaseConnection dbconn = new DatabaseConnection();
		ResultSet rs;
		String query = "SELECT * FROM MESSAGES WHERE (messages_receiver_id ="+receiverId+" and messages_sender_id="+senderId+") or (messages_receiver_id ="+senderId+" and messages_sender_id="+receiverId+") order by messages_time_stamp;";
		rs = dbconn.getStmt().executeQuery(query);
		JSONArray jsonarray = Convertor.convertToJSON(rs);
		dbconn.getConn().close();
		return Response.status(201).entity(jsonarray.toString()).build();
	}
	
}
