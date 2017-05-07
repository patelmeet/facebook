package org.ooad.facebook_v01.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.ooad.facebook_v01.service.ProfilePictureService;
@Path("/setprofilepicture")
public class ProfilePictureResource {
	
	
	ProfilePictureService profilePictureService = new ProfilePictureService();
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/demo")
	public String getTest(){
		return "Hello";
	}
	@POST
	@Path("/picture/{userid}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void uploadImage(@PathParam("userid") String user_id, @FormDataParam("uploadFile") InputStream fileInputStream
			,@FormDataParam("uploadFile") FormDataContentDisposition fileFormDataContentDisposition) {

		// local variables
		int returnvalue=1;
		String filename = "abc.jpg";

		try {
//			System.out.println(fileFormDataContentDisposition.getFileName());
			filename = fileFormDataContentDisposition.getFileName();
			returnvalue = profilePictureService.storeImageService(fileInputStream,user_id,filename);
		} 
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		finally{
			// release resources, if any
		}
		
		
		
//		if(returnvalue == 0){
//			//Login successful
//			return Response.status(201).build();
//		}
//		else if(returnvalue == 1){
//			//Login is NOT successful
//			return Response.status(404).build();
//		}
//		else{
//			//DB ERROR
//			return Response.status(410).build();
//		}
	}
	
	
	

}
