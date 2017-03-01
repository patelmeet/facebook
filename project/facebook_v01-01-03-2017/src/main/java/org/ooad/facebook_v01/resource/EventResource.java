package org.ooad.facebook_v01.resource;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.ooad.facebook_v01.model.Events;
import org.ooad.facebook_v01.service.EventService;
@Path("/event")
public class EventResource {
/*
	@GET
	public String sample()
	{
		return "gunja_harami";
	}
*/	
	@POST
	@Path("/new/{userId}")
	public Response profileCollegeNew(Events events,@PathParam("userId") String id) throws Exception{
		int uid=Integer.parseInt(id);
		int retval=new EventService().newEvent(uid, events);
		if(retval==0){
			return Response.status(201).entity("INSERTED SUCCESSFULLY").build();
		}
		else{
			return Response.status(204).entity("DB ERROR").build();
		}
	}
	
	@GET
	@Path("/event/{userId}")
	public Response profileEvent(@PathParam("userId") String id) throws Exception{
		ArrayList<Events> events=new ArrayList<>();
		
		int uid=Integer.parseInt(id);
		int retvalue=new EventService().getEvent(uid, events);
		System.out.println(events);
		if(retvalue==0){
			GenericEntity<ArrayList<Events>> entity=new GenericEntity<ArrayList<Events>>(events){};
			return Response.status(201).entity(entity).build();
		}
		else if(retvalue==1){
			return Response.status(202).entity("NO EVENT FOUND").build();
		}
		else{
			return Response.status(204).entity("DB ERROR").build();
		}
	}
}
