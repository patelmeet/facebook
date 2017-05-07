package org.ooad.facebook_v01.servlets;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ooad.facebook_v01.database.DatabaseConnection;

/**
 * Servlet implementation class UpdateSessionServlet
 */
public class UpdateSessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateSessionServlet() {
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
//		System.out.println("Inside UPDATE SESSION");
		HttpSession session = request.getSession();		//Get Session
		String userdetails_pk = (String)session.getAttribute("userdetails_pk");
		if(userdetails_pk == null)
			response.setHeader("mystatus","206");	//User is not logged in so send status=206 and do accordingly on javascript 
		else{
			DatabaseConnection dbconn = new DatabaseConnection();
			ResultSet rs;
			String query = "select * from USERDETAILS where userdetails_pk="+userdetails_pk;
			try{
				rs = dbconn.getStmt().executeQuery(query);
				if(rs.next()){
				//User is logged in. send user details in response header and read it on javascript
		        	session.setAttribute("userdetails_username", rs.getString("userdetails_username"));
		        	session.setAttribute("userdetails_firstname", rs.getString("userdetails_firstname"));
		        	session.setAttribute("userdetails_lastname", rs.getString("userdetails_lastname"));
		        	session.setAttribute("userdetails_profile_pic_url", rs.getString("userdetails_picurl"));
		        	session.setAttribute("userdetails_cover_pic_url", rs.getString("userdetails_coverpicurl"));
//		        	System.out.println(session.getAttribute("userdetails_cover_pic_url"));
				}
			}
			catch(Exception e){
				
			}
		}
	}

}
