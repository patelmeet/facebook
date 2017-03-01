package org.ooad.facebook_v01.servlets;

import java.io.IOException;
import java.io.PrintWriter;

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

/**
 * Servlet implementation class GetPictureServlet
 */
public class GetPictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPictureServlet() {
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
		
		PrintWriter out=response.getWriter();
		HttpSession session = request.getSession();
		String userdetails_pk = (String)session.getAttribute("userdetails_pk");
		
		Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/facebook_v01/webapi").path("picture").path(userdetails_pk);
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        ////
        Response returned_data = invocationBuilder.post(null);
        
        if(returned_data.getStatus() == 201){
        	//Signup successful
        	String picdata = returned_data.readEntity(String.class);
        	System.out.println("--HERE--" + picdata);
        	out.write("data:image/png;base64,"+picdata);
        }
        else if(returned_data.getStatus() == 404){
        	//Signup is NOT successful
        	response.setStatus(404,"Invalid");
        }
        else{
        	//OTHER ERROR
        	response.setStatus(404,"OtherIssue");
        }
		
		
	}

}
