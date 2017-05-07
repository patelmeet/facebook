package org.ooad.facebook_v01.resource;

import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ooad.facebook_v01.database.Convertor;
import org.ooad.facebook_v01.database.DatabaseConnection;

@Path("/searchFriends")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)

public class SearchFriendsResource {

	@POST
	@Path("/getSearchFriends/{userId}")
	public Response getSearchFriends(@PathParam("userId") String userId,String searchData) throws Exception{
		
		JSONObject search = new JSONObject(searchData);
		String firstName=search.getString("firstName");
		String lastName=search.getString("lastName");
		String homeTown=search.getString("homeTown");
		String currentCity=search.getString("currentCity");
		String school=search.getString("school");
		String college=search.getString("college");
		String company = search.getString("company");
		
		DatabaseConnection dbconn = new DatabaseConnection();
		if(! dbconn.isStatus()){
			return Response.status(210).entity("DBError").build();
		}
		ResultSet rs;
		String query; 
		int count = 0;
		if(firstName.equals("") && lastName.equals("") && homeTown.equals("") && currentCity.equals("") && school.equals("") && company.equals("") && college.equals(""))
		{
			query = "select * from USERDETAILS where userdetails_pk!="+userId+" and userdetails_pk NOT IN((select friendlist_user from FRIENDLIST where friendlist_friend="+userId+" union select friendlist_friend from FRIENDLIST where friendlist_user="+userId+" ) );";
		}
		else{
				query = "select * from USERDETAILS where userdetails_pk in (";
				if(!company.equals(""))
				{
					query = query + "select userdetails_pk from WORK where work_company = '"+company+"')";
					count++;
				}	
				if(!college.equals(""))
				{
					if(count > 0) query = query + " AND userdetails_pk in (";
					query = query + "select userdetails_pk from COLLEGE where college_name = '"+college+"')";
					count++;
				}
					
				if(!school.equals(""))
				{
					if(count > 0) query = query + " AND userdetails_pk in (";
					query = query + "select userdetails_pk from SCHOOL where school_name = '"+school+"')";
					count++;
				}
					
				if(!currentCity.equals(""))
				{
					if(count > 0) query = query + " AND userdetails_pk in (";
					query = query + "select userdetails_pk from PLACES where places_current_city = '"+currentCity+"')";
					count++;
				}
					
				if(!homeTown.equals(""))
				{
					if(count > 0) query = query + " AND userdetails_pk in (";
					query = query + "select userdetails_pk from PLACES where places_hometown = '"+homeTown+"')";
					count++;
				}
					
				if(!lastName.equals(""))
				{
					if(count > 0) query = query + " AND userdetails_pk in (";
					query = query + "select userdetails_pk from USERDETAILS where userdetails_lastname = '"+lastName+"')";
					count++;
				}
					
				if(!firstName.equals(""))
				{
					if(count > 0) query = query + "  AND userdetails_pk in (";
					query = query + "select userdetails_pk from USERDETAILS where userdetails_firstname = '"+firstName+"')";
					count++;
				}
				
				query = query +"AND userdetails_pk NOT IN (SELECT userdetails_pk FROM USERDETAILS WHERE userdetails_pk IN (SELECT friendlist_friend AS friend_list FROM FRIENDLIST WHERE friendlist_user ="+userId+" AND (friendlist_status='accepted' or friendlist_status='pending') UNION SELECT friendlist_user AS friend_list FROM FRIENDLIST WHERE friendlist_friend ="+userId+" AND (friendlist_status='accepted' or friendlist_status='pending'))) AND userdetails_pk !="+userId;
				System.out.println(query);
		}
		rs = dbconn.getStmt().executeQuery(query);
		JSONArray jsonarray = Convertor.convertToJSON(rs);
		if(jsonarray.length()==0)
		{
			return Response.status(202).build();
		}
		else
			return Response.status(201).entity(jsonarray.toString()).build();
		
		
	
	}
}
