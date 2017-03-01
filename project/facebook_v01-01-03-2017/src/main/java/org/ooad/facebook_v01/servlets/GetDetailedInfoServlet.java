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

import org.ooad.facebook_v01.model.PictureModel;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class GetDetailedInfoServlet
 */
public class GetDetailedInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDetailedInfoServlet() {
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
		ObjectMapper mapper = new ObjectMapper();
		HttpSession session = request.getSession();
		String userdetails_pk = (String)session.getAttribute("userdetails_pk");
		
		String picture_data = request.getParameter("msg");
//		System.out.println(input_data);
		
		PictureModel picmodel = new PictureModel();
		picmodel.setUserdetails_pk(userdetails_pk);
		picmodel.setPicture_data(picture_data);
		
		Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/facebook_v01/webapi").path("picture");
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        ////
        Response returned_data = invocationBuilder.post(Entity.entity(mapper.writeValueAsString(picmodel), MediaType.APPLICATION_JSON));
        
        if(returned_data.getStatus() == 201){
        	//Signup successful
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
