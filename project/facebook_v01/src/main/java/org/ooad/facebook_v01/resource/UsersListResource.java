package org.ooad.facebook_v01.resource;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ooad.facebook_v01.database.DatabaseConnection;

@Path("/getAllUsers")
@Produces(MediaType.APPLICATION_JSON)
public class UsersListResource {
	@GET
	public Response getAllFriendsList(@PathParam("userId") String id) throws Exception{
		
		List<User> users= new ArrayList<User>();
		User newUser = null;
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).build();
		}
		ResultSet rs;
		String query = "SELECT userdetails_username, userdetails_password,userdetails_firstname FROM facebook_v01.USERDETAILS";
		rs = dbconn.getStmt().executeQuery(query);
		while (rs.next()) {
			newUser = new User();
			newUser.setUserName(rs.getString("userdetails_username"));
			newUser.setFirstName(rs.getString("userdetails_firstname"));
			newUser.setPassword(rs.getString("userdetails_password"));
			users.add(newUser);
		}	
		dbconn.getConn().close();
		GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users){};
		return Response.status(201).entity(entity).build();
	}
}

class User{
	private String userName;
	private String firstName;
	private String password;
	public User(){
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}