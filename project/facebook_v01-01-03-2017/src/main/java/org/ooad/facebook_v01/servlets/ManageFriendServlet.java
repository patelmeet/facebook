package org.ooad.facebook_v01.servlets;

import java.awt.List;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.persistence.sessions.Session;
import org.ooad.facebook_v01.model.Friend;

/**
 * Servlet implementation class FriendListServlet
 */
public class ManageFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageFriendServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Inside FriendList Servlet..");
		HttpSession session = request.getSession();
		String uid=(String) session.getAttribute("userdetails_pk");
		int current_user=Integer.parseInt(uid);
		
		Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/facebook_v01/webapi").path("friends").path(current_user+"");
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.TEXT_PLAIN);
        Response returned_data = invocationBuilder.post(null);
        System.out.println("returned_data.getStatus(): "+returned_data.getStatus());
        if(returned_data.getStatus() == 201){
        	//Got some friends
        	List friendlist = returned_data.readEntity(List.class);
        	System.out.println("--HERE--" + friendlist);
        	
        }
        else if(returned_data.getStatus() == 202){
        	//NO FRIENDS
        	response.setStatus(202,"Invalid");		//CHECK
        }
        else{
        	//OTHER ERROR
        	response.setStatus(404,"DBIssue");
        }
		
	}

}
