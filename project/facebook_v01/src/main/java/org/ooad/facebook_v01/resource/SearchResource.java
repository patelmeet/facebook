package org.ooad.facebook_v01.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ooad.facebook_v01.model.SearchPeople;
import org.ooad.facebook_v01.service.SearchService;

@Path("/people")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {
	
	SearchService searchservice=new SearchService();
	@GET
	@Path("/getsearchresults/{userid}/{query}")
	public Response getAllpeopleList(@PathParam("query") String query,@PathParam("userid") String userid) throws Exception{
//		System.out.println("Inside Search");
		List<SearchPeople>  people= new ArrayList<SearchPeople>();
		int returnedval1 = searchservice.getFriendList(userid,query,people);
		int returnedval2 = searchservice.getFriendSuggestion(userid, query, people);
		int returnedval3 = searchservice.getPendingRequests(userid, query, people);
		int returnedval4 = searchservice.getRequestedRequests(userid, query,people);
//		System.out.println("from jersey -- friend list: "+people.toString());
		if(returnedval1!=0 && returnedval2!=0 && returnedval3!=0 && returnedval4!=0 )
		{
			return Response.status(202).entity("Nopeople").build();
		}
		else if(returnedval1==10 || returnedval2==10 || returnedval3==10 || returnedval4==10)
			return Response.status(210).entity("DBError").build();
		else{
			GenericEntity<List<SearchPeople>> entity = new GenericEntity<List<SearchPeople>>(people){};
			return Response.status(201).entity(entity).build();
		}
			
	}

}