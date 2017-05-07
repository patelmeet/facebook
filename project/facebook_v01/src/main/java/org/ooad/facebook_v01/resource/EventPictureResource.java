package org.ooad.facebook_v01.resource;

import java.io.IOException;
import java.io.InputStream;

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
import org.ooad.facebook_v01.service.EventPictureService;

@Path("/events")
public class EventPictureResource{
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/demo")
	public String getTest(){
		return "gunjan";
	}
	
	
	@SuppressWarnings("finally")
	@POST
	@Path("/setEventPicture/{filename}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadImage(@PathParam("filename") String fileName,
			@FormDataParam("imagefile") InputStream fileInputStream, 
			@FormDataParam("imagefile") FormDataContentDisposition fileFormDataContentDisposition) {
		EventPictureService eventPictureService = new EventPictureService();
		// local variables
		int returnvalue=1;
		
		try {
			returnvalue = eventPictureService.storeImageService(fileInputStream,fileName);
		} 
		catch(IOException ioe){
			ioe.printStackTrace();
//			System.out.println(ioe.getMessage());
		}
		finally{
			// release resources, if any
		
		
		
		
		if(returnvalue == 0){
			//Login successful
			return Response.status(201).entity("valid").build();
		}
		else if(returnvalue == 1){
			//Login is NOT successful
			return Response.status(404).entity("Invalid").build();
		}
		else{
			//DB ERROR
			return Response.status(410).entity("OtherIssue").build();
		}
		}
	}

	
	

}



