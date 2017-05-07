package org.ooad.facebook_v01.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CheckSessionServlet
 */
public class CheckSessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckSessionServlet() {
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

		HttpSession session = request.getSession();		//Get Session
		String userdetails_pk = (String)session.getAttribute("userdetails_pk");
		if(userdetails_pk == null)
			response.setHeader("mystatus","206");	//User is not logged in so send status=206 and do accordingly on javascript 
		else{
			//User is logged in. send user details in response header and read it on javascript
			response.setHeader("mystatus","205");
			response.setHeader("userdetails_pk", (String)session.getAttribute("userdetails_pk"));
			response.setHeader("userdetails_username", (String)session.getAttribute("userdetails_username"));
			response.setHeader("userdetails_firstname", (String)session.getAttribute("userdetails_firstname"));
			response.setHeader("userdetails_lastname", (String)session.getAttribute("userdetails_lastname"));
			response.setHeader("userdetails_profile_pic_url", (String)session.getAttribute("userdetails_profile_pic_url"));
			response.setHeader("userdetails_cover_pic_url", (String)session.getAttribute("userdetails_cover_pic_url"));
		}
	}

}
