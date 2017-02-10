package org.ooad.facebook_v01.resource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ooad.facebook_v01.database.DatabaseConnection;
import org.ooad.facebook_v01.model.PictureModel;

@Path("/picture")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PictureResource {

	public static final String UPLOAD_FILE_SERVER = "C:\\Users\\Meet\\Desktop\\OOAD\\workspace\\facebook_v01\\Images\\";
	
	@POST
	public Response UploadPicture(PictureModel picmodel) throws Exception
	{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(404).entity("Error").build();
		}
		ResultSet rs;
		String filename = picmodel.getUserdetails_pk()+".txt";
		writeToFileServer(filename,picmodel);
		
		String query = "update userdetails set userdetails_picurl = '"+filename+"' where userdetails_pk="+picmodel.getUserdetails_pk();
		dbconn.getStmt().executeUpdate(query);
		
		return Response.status(201).entity("Uploaded").build();
		
	}
	
	
	@POST
	@Path("/{userId}")
	public Response GetPicture(@PathParam("userId") String id) throws Exception
	{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(404).entity("Error").build();
		}
		ResultSet rs;
		String query = "select userdetails_picurl from userdetails where userdetails_pk="+id;
		rs = dbconn.getStmt().executeQuery(query);
		String url = UPLOAD_FILE_SERVER;
		if(rs.next()){
			url = url + rs.getString(1);
			
			FileReader fileReader = new FileReader(url);
			BufferedReader br = new BufferedReader(fileReader);
			String picdata = br.readLine();
			return Response.status(201).entity(picdata).build();
		}
		return Response.status(404).entity("Error").build();
	}
	
	
	private void writeToFileServer(String fileName,PictureModel o1) throws IOException {

		String qualifiedUploadFilePath = UPLOAD_FILE_SERVER + fileName;
			 try (FileWriter file = new FileWriter(qualifiedUploadFilePath)) {

		            file.write(o1.getPicture_data());
		            file.flush();
		            file.close();

		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			 
	}
}
