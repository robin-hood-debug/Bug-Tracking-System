
import java.io.IOException;

/*import javax.servlet.*;
import javax.servlet.http.*;*/

import connect.ConnectionProvider;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import connect.ConnectionProvider;

import java.sql.*;
public class Change extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session=request.getSession();
		String currentpass=request.getParameter("currentpass");
		String newpass=request.getParameter("newpass");
		ServletContext ctx=getServletContext();
		String loginid=session.getAttribute("loginid").toString();
		
		
		try{
		

		    Connection con=ConnectionProvider.getConnection();
		    
		    	System.out.println("Connection is got  "+con);
		    		Statement stmt = con.createStatement();
		    
			ResultSet rs = stmt.executeQuery("select * from login where loginid ='" + loginid + "' and type='Admin'");
		    while(rs.next()) {
				String pass= rs.getString("password");
				System.out.println(pass);
			 	
				if(currentpass.equals(pass)) {
			 	
					
					Statement stmt1=con.createStatement();
					stmt1.executeUpdate("update login set password='"+newpass+"'");
					RequestDispatcher rd=request.getRequestDispatcher("admin.jsp?msg=success");
					rd.forward(request, response);
				}	
			 	
				else {
					RequestDispatcher rd=request.getRequestDispatcher("changepass.jsp?msg=passerror");
					rd.forward(request, response);
			}	
			
			
		}
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
