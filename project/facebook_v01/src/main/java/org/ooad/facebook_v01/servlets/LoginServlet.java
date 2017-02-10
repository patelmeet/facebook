package org.ooad.facebook_v01.servlets;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Inside Servlet");
		PrintWriter out=response.getWriter();
		
		String input_data = request.getParameter("msg");
		
		
		Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/facebook_v01/webapi").path("login");

//        Login login_details = new Login();
//        login_details.setLogin_Id("13");
//        login_details.setLogin_Password("nnssd");
        
        ObjectMapper mapper = new ObjectMapper();
//        String str = mapper.writeValueAsString(login_details);

        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response returned_data = invocationBuilder.post(Entity.entity(input_data, MediaType.APPLICATION_JSON));

        if(returned_data.getStatus() == 201){
        	//Login successful
        	
        	User usr = returned_data.readEntity(User.class);		//Get User Details
        	out.println(mapper.writeValueAsString(usr));
        	HttpSession session = request.getSession();
        	session.setAttribute("userdetails_pk", usr.getUserDetails_pk());
        	
//        	request.getRequestDispatcher("ViewPersonalInfo.html").forward(request, response);
//        	response.sendRedirect("ViewPersonalInfo.html");
        	
        }
        else if(returned_data.getStatus() == 404){
        	//Login is NOT successful
        	response.setStatus(404,"Invalid");
        }
        else{
        	//OTHER ERROR
        	response.setStatus(404,"OtherIssue");
        }
		
		
	}

}
