package org.ooad.facebook_v01.resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.ooad.facebook_v01.database.Convertor;
import org.ooad.facebook_v01.database.DatabaseConnection;
import org.ooad.facebook_v01.model.College;
import org.ooad.facebook_v01.model.ContactAndBasicInfo;
import org.ooad.facebook_v01.model.Places;
import org.ooad.facebook_v01.model.School;
import org.ooad.facebook_v01.model.Work;
import org.ooad.facebook_v01.service.ContactAndBasicInfoService;
import org.ooad.facebook_v01.service.ManageAboutPageService;


@Path("/profile")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ManageAboutPageResource {	
	
	@POST
	@Path("/getbook/{userid}")
	public Response getbook(@PathParam("userid") String userid) throws Exception
	{
		DatabaseConnection dbconn = new DatabaseConnection();
		ResultSet rs;
		String query = "SELECT userdetails_pk,userdetails_book FROM userdetails WHERE userdetails_pk="+userid;
		rs = dbconn.getStmt().executeQuery(query);
		JSONArray jsonarray = Convertor.convertToJSON(rs);
		return Response.status(201).entity(jsonarray.toString()).build();
//		return Response.ok().build();
	}
	
	@POST
	@Path("/updatebook/{userid}/{book}")
	public Response updatebook(@PathParam("userid") String userid, @PathParam("book") String book) throws Exception
	{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "Update userdetails SET userdetails_book='"+book+"' WHERE userdetails_pk="+userid;
		dbconn.getStmt().executeUpdate(query);
		return Response.status(201).build();
	}
	
	
	
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
	
	
	@GET
	@Path("/work/remove/{workid}")
	public Response removeprofileWork(@PathParam("workid") int workid) throws SQLException{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).build();
		}
		String query = "DELETE FROM WORK WHERE work_pk = " + workid;
		dbconn.getStmt().executeUpdate(query);
		dbconn.getConn().close();
		return Response.status(201).build();
	}
	
	
	
	
	
	
	@GET
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
	
	@GET
	@Path("/college/remove/{college_pk}")
	public Response removeCollegeDetails(@PathParam("college_pk") int college_pk) throws SQLException{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(!dbconn.isStatus()){
			return Response.status(210).build();
		}
		String query = "DELETE FROM COLLEGE where college_pk = " + college_pk;
		dbconn.getStmt().executeUpdate(query);
		dbconn.getConn().close();
		return Response.status(201).build();
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
	
	@GET
	@Path("/school/remove/{school_pk}")
	public Response removeSchoolDetails(@PathParam("school_pk") int school_pk) throws SQLException{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).build();
		}
		String query = "DELETE FROM SCHOOL where school_pk = " + school_pk;
		dbconn.getStmt().executeUpdate(query);
		dbconn.getConn().close();
		return Response.status(201).build();
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
	@Path("/places/new/{userId}")
	public Response newPlaces(Places place, @PathParam("userId") int id) throws Exception{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).build();
		}
		String query = "insert into PLACES values(default, "+ "'" + place.getPlaces_current_city()+"', '" + place.getPlaces_hometown()+"', "+ id +")";
		
		dbconn.getStmt().executeUpdate(query);
		return Response.status(201).build();
	}

	@GET
	@Path("/getPlaces/{userId}")
	public Response getPlaces(@PathParam("userId") String id) throws Exception{
		ArrayList<Places> placesList = new ArrayList<Places>();
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).build();
		}
		ResultSet rs;
		String query = "select * from PLACES where userdetails_pk = " + id;
		rs = dbconn.getStmt().executeQuery(query);
		while (rs.next()) {
			Places place=new Places();
			place.setPlaces_current_city(rs.getString("places_current_city"));
			place.setPlaces_hometown(rs.getString("places_hometown"));
			place.setPlaces_pk(Integer.parseInt(rs.getString("places_pk")));
			placesList.add(place);
		}
		GenericEntity<ArrayList<Places>> entity=new GenericEntity<ArrayList<Places>>(placesList){};
		return Response.status(201).entity(entity).build();
	}

	@POST
	@Path("/editPlaces/{placesId}")
	public Response editPlaces(@PathParam("placesId") int placeid, Places place) throws Exception{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).build();
		}

		String query = "update PLACES set places_current_city='"+place.getPlaces_current_city()+"' , places_hometown='"+place.getPlaces_hometown()+"'where places_pk="+ placeid ;
		//System.out.println(query);
		dbconn.getStmt().executeUpdate(query);
		
		dbconn.getConn().close();
			return Response.status(201).entity("EDITED SUCCESSFULLY").build();
		
	}
	
	//================================Rachana==================================//
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/contactandbasicinfo/{userId}")
	public Response editContactAndBasicInfo(@PathParam("userId") String id,ContactAndBasicInfo cbi) throws Exception{
		ContactAndBasicInfoService cbis=new ContactAndBasicInfoService();
		int uid=Integer.parseInt(id);
		
		int res=cbis.checkExistance(uid);
//		System.out.println("res"+res);
		if(res==0){
			cbis.insertContactAndBasicInfo(uid, cbi);
		}
		
		
		int r1=1,r2=1,r3=1,r4=1;
		if(!cbi.getUserdetails_mobile().equals(""))
			r1=cbis.editPhoneNumber(uid, cbi.getUserdetails_mobile());
		if(!cbi.getUserdetails_email().equals(""))
			r2=cbis.editEmailAddress(uid, cbi.getUserdetails_email());
		if(!cbi.getUserdetails_dob().equals(""))
			r3=cbis.editDOB(uid, cbi.getUserdetails_dob());
		if(!(cbi.getUserdetails_gender()=='\0'))
			r4=cbis.editGender(uid, cbi.getUserdetails_gender());
		
		int retvalue=r1&r2&r3&r4;
		
		if(retvalue==1){
			return Response.status(201).entity("Success").build();
		}
		else if(retvalue==-1){
			return Response.status(401).entity("SQLException").build();
		}
		else if(retvalue==-2){
			return Response.status(402).entity("ClassNotFoundException").build();
		}
		else{
			return Response.status(403).entity("Exception").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/contactandbasicinfo/{userId}")
	public Response getContactAndBasicInfo(@PathParam("userId") String id) throws Exception{
		int uid=Integer.parseInt(id);
		ContactAndBasicInfo cbi=new ContactAndBasicInfo();
		ContactAndBasicInfoService cbis=new ContactAndBasicInfoService();
		int retvalue=cbis.getContactAndBasicInfo(uid, cbi);
		if(retvalue==1){
			return Response.status(201).entity(cbi).build();
		}
		else if(retvalue==-1){
			return Response.status(401).entity("SQLException").build();
		}
		else if(retvalue==-2){
			return Response.status(402).entity("ClassNotFoundException").build();
		}
		else{
			return Response.status(403).entity("Exception").build();
		}
	}
	//===============================End Rachana===============================//
	// Services to fetch and update password - Chetan Sharma @14-04-2017-------------------------------------------------
	
		@GET
		@Path("/fetchOldPassword/{userId}")
		public Response fetchOldPassword(@PathParam("userId") String userid) throws Exception{
			
			DatabaseConnection dbconn = new DatabaseConnection();
			ResultSet rs;
			String query = "SELECT userdetails_password FROM USERDETAILS WHERE userdetails_pk="+userid;
			rs = dbconn.getStmt().executeQuery(query);
			JSONArray jsonarray = Convertor.convertToJSON(rs);
			return Response.status(201).entity(jsonarray.toString()).build();
		}
		
		@GET
		@Path("/setNewPassword/{userId}/{newPassword}")
		public Response setNewPassword(@PathParam("userId") String userId,@PathParam("newPassword") String newPassword) throws Exception{
			DatabaseConnection dbconn = new DatabaseConnection();
			if(! dbconn.isStatus()){
				return Response.status(210).build();
			}
			String query = "UPDATE USERDETAILS SET userdetails_password='"+newPassword+"' WHERE userdetails_pk="+userId;
			dbconn.getStmt().executeUpdate(query);
			return Response.status(201).build();
		}
		
		
		// Services to fetch and update password - Chetan Sharma @14-04-2017 - Ends here-------------------------------------

}
