package org.ooad.facebook_v01.resource;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.ooad.facebook_v01.model.Login;
import org.ooad.facebook_v01.model.User;
import org.ooad.facebook_v01.service.LoginService;


@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {
	
	LoginService loginservice = new LoginService();
	
	@GET
	public Login GetTest(){
//			System.out.println("Get is working");
			return new Login();
		}

	@POST
	public Response loginUser(Login login) throws SQLException{
		
		User user = new User();
		
		int returnvalue = loginservice.validateLogin(login,user);
		if(returnvalue == 0){
			//Login successful
//			System.out.println("Inside service");
			return Response.status(201).entity(user).build();
		}
		else if(returnvalue == 1){
			//Login is NOT successful
			return Response.status(204).build();
		}
		else{
			//DB ERROR
			return Response.status(500).build();
		}
	}
}
