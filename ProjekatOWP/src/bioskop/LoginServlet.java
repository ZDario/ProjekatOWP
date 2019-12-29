package bioskop;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bioskop.dao.UserDAO;
import bioskop.model.User;


public class LoginServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		try {
			User user = UserDAO.get(userName, password);
			if (user == null) {
//				response.sendRedirect("./Login.html");
				request.getRequestDispatcher("./FailureServlet").forward(request, response);
				return;
			}
	
			request.getSession().setAttribute("loggedInUserName", user.getUserName());
			
//			response.sendRedirect("./WebShopServlet");
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
