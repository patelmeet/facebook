package org.ooad.facebook_v01.servlets;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

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

import org.json.JSONObject;
import org.ooad.facebook_v01.model.Login;
import org.ooad.facebook_v01.model.User;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class LoginServlet
 */

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginServlet() {
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
		
		String input_data = request.getParameter("msg");	//Data from jquery
		
		
		
		//JAX-RX client to call REST Service
		Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/facebook_v01/webapi").path("login");
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response returned_data = invocationBuilder.post(Entity.entity(input_data, MediaType.APPLICATION_JSON));
        
        if(returned_data.getStatus() == 201){
        	//Login successful
        	
        	User usr = returned_data.readEntity(User.class);		//Get User Details
        	HttpSession session = request.getSession();            //Get Session from request
            //Set Attributes to session
        	session.setAttribute("userdetails_pk", usr.getUserDetails_pk());
        	session.setAttribute("userdetails_username", usr.getUserDetails_userName());
        	session.setAttribute("userdetails_firstname", usr.getUserDetails_firstName());
        	session.setAttribute("userdetails_lastname", usr.getUserDetails_lastName());
        	session.setAttribute("userdetails_profile_pic_url", usr.getUserdetails_picurl());
        	session.setAttribute("userdetails_cover_pic_url", usr.getUserdetails_coverpicurl());
        	
        	response.setHeader("mystatus","205");  //Set response status=205, which we can access in javascript.
//        	System.out.println("205 setted");
        }
        else if(returned_data.getStatus() == 204){
        	//Login is NOT successful
        	response.setHeader("mystatus","206");  //Set response status=206
//        	System.out.println("206 setted");
        }
        else{
        	//OTHER ERROR
        	response.setHeader("mystatus","500");
        }
	}

}
