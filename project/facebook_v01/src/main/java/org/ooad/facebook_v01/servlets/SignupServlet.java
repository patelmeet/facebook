package org.ooad.facebook_v01.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ooad.facebook_v01.model.User;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class SignupServlet
 */
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
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
		
//		System.out.println("SignupServlet");
		String input_data = request.getParameter("msg");
//		System.out.println(input_data);
		
		Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/facebook_v01/webapi").path("signup");
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response returned_data = invocationBuilder.post(Entity.entity(input_data, MediaType.APPLICATION_JSON));
        
        if(returned_data.getStatus() == 201){
        	//Signup successful
        	response.setHeader("mystatus","201");
        }
        else if(returned_data.getStatus() == 404){
        	//Signup is NOT successful
//        	System.out.println("Inside Invalid");
        	response.setHeader("mystatus","205");
        	return;
        }
        else{
        	//OTHER ERROR
        	response.setHeader("mystatus","404");
        }
        
	}

}