package org.ooad.facebook_v01.resource;
import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ooad.facebook_v01.database.DatabaseConnection;



@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OnHoverPopOverResource {
	
	
	@GET
	@Path("/getDobEmail/{userId}")
	public Response getDobEmail(@PathParam("userId") int userId) throws Exception{
//		System.out.println("I came inside new service");
		userDobEmail user =new userDobEmail();
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).build();
		}
		ResultSet rs;
		String query = "select userdetails_email, userdetails_dob  from USERDETAILS where userdetails_pk = " + userId;
		rs = dbconn.getStmt().executeQuery(query);
		if (rs.next()) {
			user.setUserdetails_dob(rs.getDate("userdetails_dob").toString());
			user.setUserdetails_email(rs.getString("userdetails_email"));
			
		}	
//		System.out.println("Outside if");
		dbconn.getConn().close();
		return Response.ok().entity(user).build();
	}

}

class userDobEmail{
	private String userdetails_dob;
	private String userdetails_email;
	public String getUserdetails_email() {
		return userdetails_email;
	}
	public void setUserdetails_email(String userdetails_email) {
		this.userdetails_email = userdetails_email;
	}
	public String getUserdetails_dob() {
		return userdetails_dob;
	}
	public void setUserdetails_dob(String userdetails_dob) {
		this.userdetails_dob = userdetails_dob;
	}
	
}