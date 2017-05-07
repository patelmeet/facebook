package org.ooad.facebook_v01.resource;

import java.sql.ResultSet;
import java.sql.SQLException;

//@Author : Nitish
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import org.ooad.facebook_v01.model.GroupDetailsModel;
import org.ooad.facebook_v01.model.GroupJoinRequestList;
import org.ooad.facebook_v01.service.GroupService;

@Path("/groupdetails")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class GroupResource {
	
	// ==================== Added on 1st April BY Nitish Starts=========================
	
	
	@POST
	@Path("/deleteGroup/{groupId}")
	public Response deleteGroup(@PathParam("groupId") String groupId) throws Exception{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
//		System.out.println("Inside delete Group");
				
		String query = "delete from GROUPDETAILS where groupdetails_id ="+ groupId;
		
		dbconn.getStmt().executeUpdate(query);
		return Response.status(201).build();
	}
	
	// Called when admin add a Member
	
	@POST
	@Path("/addMemberToGroup/{groupId}/{userId}")
	public Response AddMemberToGroup(@PathParam("groupId") String groupId,@PathParam("userId") String userId) throws Exception{
		
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		
		//System.out.println("Inside addMemberToGroup April 1");
		// id,memid,role,status
		String query = "insert into GROUPMEMBERS values("+groupId+","+userId+",2,1)";
		dbconn.getStmt().executeUpdate(query);
		return Response.status(201).build();
	}
	
	
	/// For Getting all User Details who could be added by admin
		
		@GET
		@Path("/getMembersToAddDetails/{group_Id}/{userId}")
		public Response getAllProbableMembers(@PathParam("group_Id") String group_Id,@PathParam("userId") String userId) throws Exception{
					
			// In this GroupJoinRequestModel is used , the same was used while join request. 
			
			List<GroupJoinRequestList> groupmemberlist = new ArrayList<GroupJoinRequestList>();
			
			GroupService gs = new GroupService();
			
			int returnedval = gs.getMembersToAdd(userId ,group_Id, groupmemberlist );
						
			if(returnedval==0){
				
				GenericEntity<List<GroupJoinRequestList>> entity = new GenericEntity<List<GroupJoinRequestList>>(groupmemberlist){};
				return Response.status(201).entity(entity).build();
			}
			
			else if(returnedval==1)
				return Response.status(202).entity("NoGroups").build();
			else
				return Response.status(210).entity("DBError").build();
			
		}
		
// ==================== Added on 1st April BY Nitish ends =========================
		
		
	
	@GET
	@Path("/getAllGroupsDetailYouManage/{userId}")
	public Response getAllGroupYouManageList(@PathParam("userId") String id) throws Exception{
		
//		System.out.println("Inside resource ");
		
		List<GroupDetailsModel> grouplist = new ArrayList<GroupDetailsModel>();
		
		GroupService gs = new GroupService();
		
		int returnedval = gs.getGroupYouManageList(id, grouplist);
		
//		System.out.println("from jersey -- friend list: "+grouplist.toString());
		
		if(returnedval==0){
			GenericEntity<List<GroupDetailsModel>> entity = new GenericEntity<List<GroupDetailsModel>>(grouplist){};
			return Response.status(201).entity(entity).build();
		}
		
		else if(returnedval==1)
			return Response.status(202).entity("NoGroups").build();
		else
			return Response.status(210).entity("DBError").build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getMemberList/{group_Id}")
	public Response getMemberList(@PathParam("group_Id") String group_Id) throws Exception{

		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		String query = "select userdetails_firstname,userdetails_lastname from USERDETAILS where userdetails_pk in (select groupmembers_memberid from GROUPMEMBERS where groupmembers_groupid = " + group_Id + " and groupmembers_status=1);";
		ResultSet rs;
		rs = dbconn.getStmt().executeQuery(query);
		JSONArray jsonarray = Convertor.convertToJSON(rs);
		dbconn.getConn().close();
		return Response.status(201).entity(jsonarray.toString()).build();
		
	}
	
	
	@GET
	@Path("/getAllGroupsYourGroup/{userId}")
	public Response getAllGroupYourGroupList(@PathParam("userId") String id) throws Exception{
		
//		System.out.println("Inside resource ");
		
		List<GroupDetailsModel> gplist = new ArrayList<GroupDetailsModel>();
		
		GroupService gs = new GroupService();
		
		int returnedval = gs.getAllYourGroupList(id, gplist);
		
//		System.out.println("from jersey -- friend list: "+gplist.toString());
		
		if(returnedval==0){
			GenericEntity<List<GroupDetailsModel>> entity = new GenericEntity<List<GroupDetailsModel>>(gplist){};
			return Response.status(201).entity(entity).build();
		}
		
		else if(returnedval==1)
			return Response.status(202).entity("NoGroups").build();
		else
			return Response.status(210).entity("DBError").build();
	}
	
	
	/// For getting a particular Group Details , when user click a group from Group Page	
	@GET
	@Path("/getGroupDetails/{userId}/{group_Id}")
	public Response getAllGroupYourGroupList(@PathParam("userId") String user_Id,@PathParam("group_Id") String group_Id) throws Exception{
		
//		System.out.println("GetGroupDetails 13 march " + user_Id + " " + group_Id);
		
		GroupDetailsModel gpModel = new GroupDetailsModel();
		
		GroupService gs = new GroupService();
	
		int returnedval = gs.getParticularGroupList(user_Id, group_Id, gpModel);
		
//		System.out.println(gpModel.getGroupdetails_name());
		
		//System.out.println("from jersey -- Nitish Vashisth ");
		
		if(returnedval==0){
//			System.out.println("getGroupDetails 13th march first");
			GenericEntity<GroupDetailsModel> entity = new GenericEntity<GroupDetailsModel>(gpModel){};
			return Response.status(201).entity(entity).build();
		}
		
		else if(returnedval==1){
//			System.out.println("getGroupDetails 13th march second");
			return Response.status(202).entity("NoGroupExists").build();}
		else{
//			System.out.println("getGroupDetails 13th march third");
			return Response.status(210).entity("DBError").build();}
	}
	
	
	
	
	/// FOr Checking the group join Request
	
	@GET
	@Path("/getGroupRequestDetails/{group_Id}")
	public Response getAllGroupJoinRequest(@PathParam("group_Id") String group_Id) throws Exception{
		
//		System.out.println("Inside resource " + group_Id);
		
		List<GroupJoinRequestList> grouplist = new ArrayList<GroupJoinRequestList>();
		
		GroupService gs = new GroupService();
		
		int returnedval = gs.getGroupJoinRequestList(group_Id, grouplist);
		
//		System.out.println("from jersey -- GroupJoinRequest : "+grouplist.toString());
		
		if(returnedval==0){
			GenericEntity<List<GroupJoinRequestList>> entity = new GenericEntity<List<GroupJoinRequestList>>(grouplist){};
			return Response.status(201).entity(entity).build();
		}
		
		else if(returnedval==1)
			return Response.status(202).entity("NoGroups").build();
		else
			return Response.status(210).entity("DBError").build();
		
	}
	
	
	
	/// For Getting all Group Members Details : will be rendered in GroupMembers.html
	/// Added after 13th march
	
	@GET
	@Path("/getGroupMembersDetails/{group_Id}")
	public Response getAllGroupMembers(@PathParam("group_Id") String group_Id) throws Exception{
		
//		System.out.println("Inside getGroupMembersDetails model " + group_Id);
		
		// In this GroupJoinRequestModel is used , the same was used while join request. 
		
		List<GroupJoinRequestList> groupmemberlist = new ArrayList<GroupJoinRequestList>();
		
		GroupService gs = new GroupService();
		
		int returnedval = gs.getGroupMembersList(group_Id, groupmemberlist );
		
//		System.out.println("March 14 "+ groupmemberlist.toString());
		
		if(returnedval==0){
//			System.out.println("Success inside resource  march 14 ");
			GenericEntity<List<GroupJoinRequestList>> entity = new GenericEntity<List<GroupJoinRequestList>>(groupmemberlist){};
			return Response.status(201).entity(entity).build();
		}
		
		else if(returnedval==1)
			return Response.status(202).entity("NoGroups").build();
		else
			return Response.status(210).entity("DBError").build();
		
	}
	
	
	
	
	/// For Join Group	
	@POST
	@Path("/sendJoinGroupRequest/{groupId}/{userId}")
	public Response sendFriendRequest(@PathParam("groupId") String groupId,@PathParam("userId") String userId) throws Exception{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
//		System.out.println("Inside sebdJoinGroupRequestResource");
		// id,memid,role,status
		String query = "insert into GROUPMEMBERS values("+groupId+","+userId+",2,0)";
		dbconn.getStmt().executeUpdate(query);
		return Response.status(201).build();
	}
	
	///For confirming Join GRoup Request
	@POST
	@Path("/confirmJoinGroupRequest/{groupId}/{userId}")
	public Response confirmJoinGroupRequest(@PathParam("groupId") String groupId,@PathParam("userId") String userId) throws Exception{
		
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
//		System.out.println("Inside JoinGroupRequest Resource April 1" + groupId + " " + userId);
		// id,memid,role,status
		//String query = "insert into GROUPMEMBERS values("+groupId+","+userId+",2,0)";
		
		String query = "UPDATE GROUPMEMBERS SET groupmembers_status =1 WHERE groupmembers_groupid="+groupId+" and groupmembers_memberid =" + userId;
		
		dbconn.getStmt().executeUpdate(query);
		
		//UPDATE GROUPDETAILS SET groupdetails_membercount = groupdetails_membercount+1 WHERE groupdetails_id=3 ;
		String query2 = "UPDATE GROUPDETAILS SET groupdetails_membercount = groupdetails_membercount+1 WHERE groupdetails_id="+ groupId +";";
		dbconn.getStmt().executeUpdate(query2);
		
		return Response.status(201).build();
	}
	
	
	/// FOr Declining Group Request
	@POST
	@Path("/declineJoinGroupRequest/{groupId}/{userId}")
	public Response declineJoinGroupRequest(@PathParam("groupId") String groupId,@PathParam("userId") String userId) throws Exception{
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
//		System.out.println("Inside decline GroupRequest Resource");
		// id,memid,role,status
		//String query = "insert into GROUPMEMBERS values("+groupId+","+userId+",2,0)";
		
		String query = "DELETE from GROUPMEMBERS WHERE groupmembers_groupid="+groupId+" and groupmembers_memberid =" + userId;
		
		dbconn.getStmt().executeUpdate(query);
		return Response.status(201).build();
	}
	
	
	/// For Creating Group
	
	@POST
	@Path("/createNewGroup/{user_id}/{group_Name}/{group_People}/{group_Privacy}")
	public Response CreateGroup(@PathParam("user_id") String user_id,@PathParam("group_Name") String group_Name,@PathParam("group_People") String group_People,@PathParam("group_Privacy") String group_Privacy) throws SQLException{
		
		
		GroupService gs = new GroupService();
		
		
//		System.out.println("Inside CreateGroup Resource");
		
//		System.out.println("Inside Create Group");
//		System.out.println("GroupName : " + group_Name);
//		System.out.println("GroupPeople : "+group_People);
		
		int returnvalue = gs.createNewGroup(user_id, group_Name, group_People, group_Privacy);
		
		
		if(returnvalue == 0){
			return Response.status(201).entity("Success").build();
		}
		else if(returnvalue == 1){
			//DB ERROR
			return Response.status(404).entity("Invalid").build();
		}
		else
			return Response.status(410).entity("OtherIssue").build();
			

	}	
	
	/// For Discover Group
	
	@GET
	@Path("/discoverGroups/{userId}")
	public Response discoverGroups(@PathParam("userId") String id) throws Exception{
		
//		System.out.println("INside Discover Group Resource");
		
		List<GroupDetailsModel> grouplist = new ArrayList<GroupDetailsModel>();
		
		GroupService gs = new GroupService();
		
		int returnedval = gs.getDiscoveredGroup(id, grouplist);
		
//		System.out.println("from jersey -- friend list: "+grouplist.toString());
		
		if(returnedval==0){
			GenericEntity<List<GroupDetailsModel>> entity = new GenericEntity<List<GroupDetailsModel>>(grouplist){};
			return Response.status(201).entity(entity).build();
		}
		
		else if(returnedval==1)
			return Response.status(202).entity("NoGroups").build();
		else
			return Response.status(210).entity("DBError").build();
	}
	
	//Added on 14 th march
	/// For post in a group
	/*
	@POST
	@Path("/postStatus/{userId}")
	public void addPostStatus(Post post) throws Exception{
		//Remaining
		GroupService gs = new GroupService();
		
		System.out.println("This is in post resource: " + post.getPostText() + ", " + post.getSenderId() + ", " + post.getReceiverId());
		//postService.addPost(post);
		gs.addGroupPost(post);
		
		System.out.println("Post Created Successfully!");
		
	}*/
	
	//=======================Rachana 17/03/2017 19:50===================================//
		///Remove group member
			@DELETE
			@Consumes(MediaType.TEXT_PLAIN)
			@Produces(MediaType.TEXT_PLAIN)
			@Path("/removeGroupMember/{groupId}/{userId}")
			public Response removeGroupMember(@PathParam("groupId") String groupId,@PathParam("userId") String userId) throws Exception{
				DatabaseConnection dbconn = new DatabaseConnection();
				if(! dbconn.isStatus()){
					return Response.status(210).entity("DBError").build();
				}
//				System.out.println("Inside sebdJoinGroupRequestResource");

				String query = "delete from facebook_v01.GROUPMEMBERS where groupmembers_groupid="+
								groupId+
								" and groupmembers_memberid="+userId+" and groupmembers_role!=0;";
				
				int ret=dbconn.getStmt().executeUpdate(query);
//				System.out.println(ret);
				if(ret==1)
					return Response.status(201).entity("member deleted").build();
				else if(ret==0)
					return Response.status(204).entity("admin").build();
				else
					return Response.status(210).entity("error").build();
			}
		//=======================End Rachana 17/03/2017 19:50===================================//	
	
		
}
