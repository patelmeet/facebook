package org.ooad.facebook_v01.resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ooad.facebook_v01.database.DatabaseConnection;
import org.ooad.facebook_v01.model.College;
import org.ooad.facebook_v01.model.School;
import org.ooad.facebook_v01.model.Work;
import org.ooad.facebook_v01.service.ManageAboutPageService;


@Path("/profile")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ManageAboutPageResource {

	@POST
	@Path("/work/{userid}/set")
	public Response setprofileWork(@PathParam("userid") String userid ,Work work) throws SQLException{
		
		work.setUserdetails_pk(Integer.parseInt(userid));
		int returnvalue = new ManageAboutPageService().setProfileWorkService(work);
		
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
	
	@POST
	@Path("/work/{userid}/edit/{workid}")
	public Response editprofileWork(@PathParam("userid") String userid ,@PathParam("workid") String workid ,Work work) throws SQLException{
		
		work.setUserdetails_pk(Integer.parseInt(userid));
		int returnvalue = new ManageAboutPageService().editProfileWorkService(work,workid);
		
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
	
	@POST
	@Path("/work/{userid}/get")
	public Response getprofileWork(@PathParam("userid") String userid) throws SQLException{
		ArrayList<Work> work =  new ArrayList<>();
		int returnvalue = new ManageAboutPageService().getProfileWorkService(work,userid);
		
		
		if(returnvalue == 0){
			GenericEntity<ArrayList<Work>> entity = new GenericEntity<ArrayList<Work>>(work){};
			return Response.status(201).entity(entity).build();
		}
		else if(returnvalue == 1){
			return Response.status(404).entity("Invalid").build();
		}
		else{
			return Response.status(410).entity("OtherIssue").build();
		}
	}
	
	@POST
	@Path("/college/edit/{userId}")
	public Response profileCollegeEdit(College clg,@PathParam("userId") String id) throws Exception{
		int uid=Integer.parseInt(id);
		int retval=new ManageAboutPageService().editCollege(clg);
		if(retval==0){
			return Response.status(201).entity("UPDATED SUCCESSFULLY").build();
		}
		else{
			return Response.status(204).entity("DB ERROR").build();
		}
	}
	
	@POST
	@Path("/college/new/{userId}")
	public Response profileCollegeNew(College clg,@PathParam("userId") String id) throws Exception{
		int uid=Integer.parseInt(id);
		int retval=new ManageAboutPageService().newCollege(uid, clg);
		if(retval==0){
			return Response.status(201).entity("INSERTED SUCCESSFULLY").build();
		}
		else{
			return Response.status(204).entity("DB ERROR").build();
		}
	}
	
	@GET
	@Path("/college/{userId}")
	public Response profileCollege(@PathParam("userId") String id) throws Exception{
		ArrayList<College> colleges=new ArrayList<>();
		
		int uid=Integer.parseInt(id);
		int retvalue=new ManageAboutPageService().getCollege(uid, colleges);
		System.out.println(colleges);
		if(retvalue==0){
			GenericEntity<ArrayList<College>> entity=new GenericEntity<ArrayList<College>>(colleges){};
			return Response.status(201).entity(entity).build();
		}
		else if(retvalue==1){
			return Response.status(202).entity("NO COLLEGE FOUND").build();
		}
		else{
			return Response.status(204).entity("DB ERROR").build();
		}
	}
	
	@POST
	@Path("/school/edit/{userId}")
	public Response profileSchoolEdit(School school,@PathParam("userId") String id) throws Exception{
		int uid=Integer.parseInt(id);
		int retval=new ManageAboutPageService().editSchool(school);
		if(retval==0){
			return Response.status(201).entity("UPDATED_SUCCESSFULLY").build();
		}
		else{
			return Response.status(204).entity("DBERROR").build();
		}
	}
	
	@POST
	@Path("/school/new/{userId}")
	public Response profileSchoolNew(School school,@PathParam("userId") String id) throws Exception{
		int uid=Integer.parseInt(id);
		int retval=new ManageAboutPageService().newSchool(uid, school);
		if(retval==0){
			return Response.status(201).entity("INSERTED_SUCCESSFULLY").build();
		}
		else{
			return Response.status(204).entity("DBERROR").build();
		}
	}
	
	@GET
	@Path("/school/{userId}")
	public Response profileSchool(@PathParam("userId") String id) throws Exception{
		ArrayList<School> schoollist=new ArrayList<>();
		
		int uid=Integer.parseInt(id);
		int retvalue=new ManageAboutPageService().getSchool(uid, schoollist);
		if(retvalue==0){
			GenericEntity<ArrayList<School>> entity=new GenericEntity<ArrayList<School>>(schoollist){};
			return Response.status(201).entity(entity).build();
		}
		else if(retvalue==1){
			return Response.status(202).entity("NO_SCHOOL_FOUND").build();
		}
		else{
			return Response.status(204).entity("DBERROR").build();
		}
	}

	@POST
	@Path("/getPlaces/{userId}")
	public Response getPlaces(@PathParam("userId") String id) throws Exception{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).build();
		}
		ResultSet rs;
		String query = "";
		rs = dbconn.getStmt().executeQuery(query);
		if(rs.next()){
			
			return Response.status(201).build();
		}
		else{
			
			return Response.status(202).build();
		}
	}

	@POST
	@Path("/editPlaces/{userId}")
	public Response editPlaces(@PathParam("userId") String id) throws Exception{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).build();
		}
		ResultSet rs;
		String query = "";
		rs = dbconn.getStmt().executeQuery(query);
		if(rs.next()){
			
			return Response.status(210).build();
		}
		else{
			
			return Response.status(210).build();
		}
	}

}
