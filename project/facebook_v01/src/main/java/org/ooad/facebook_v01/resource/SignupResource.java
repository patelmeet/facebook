package org.ooad.facebook_v01.resource;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ooad.facebook_v01.model.User;
import org.ooad.facebook_v01.service.SignupService;


@Path("/signup")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SignupResource {
	
	SignupService userLoginService = new SignupService();
	/*
	
	@GET
	public User GetTest(){
			System.out.println("Get is working");
			return new User();
		}
		
	*/

	@POST
	public Response setUserPersonalInfo(User user) throws SQLException{
		
		int returnvalue = userLoginService.setUserPersonalInfo(user);
		
		if(returnvalue == 0){
			return Response.status(201).entity("SignupSuccessful").build();
		}
		else if(returnvalue == 1){
			//DB ERROR
			return Response.status(404).entity("Invalid").build();
		}
		else
			return Response.status(410).entity("OtherIssue").build();
//		System.out.println(returnvalue);
		
		
//		System.out.println(user.getUserDetails_userName());
//		System.out.println(user.getUserDetails_firstName());
//		System.out.println(user.getUserDetails_lastName());
//		System.out.println(user.getUserDetails_email());
//		System.out.println(user.getUserDetails_password());
//		System.out.println(user.getUserDetails_day());
//		System.out.println(user.getUserDetails_month());
//		System.out.println(user.getUserDetails_year());
//		System.out.println(user.getUserDetails_gender());
		//userLoginService.setUserPersonalInfo(user);
	}	
}
