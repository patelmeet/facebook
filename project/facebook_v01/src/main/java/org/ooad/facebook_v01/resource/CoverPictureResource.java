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

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.ooad.facebook_v01.service.CoverPictureService;


@Path("/setcoverpicture")
public class CoverPictureResource {
	

	CoverPictureService coverPictureService = new CoverPictureService();
	
	
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

//		System.out.println("gunjan I am here");
		// local variables
		int returnvalue=1;
		String filename = "abc.jpg";

		try {
//			System.out.println(fileFormDataContentDisposition.getFileName());
			filename = fileFormDataContentDisposition.getFileName();
			returnvalue = coverPictureService.storeImageService(fileInputStream,user_id,filename);
		} 
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		finally{
			// release resources, if any
		}
	
	}
	
	

}
