package bioskop;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bioskop.dao.UserDAO;
import bioskop.model.User;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			if ("".equals(userName) && "".equals(password))
				throw new Exception("Niste popunili polja!");
			if ("".equals(userName))
				throw new Exception("Korisnicko ime je prazno!");
			if ("".equals(password))
				throw new Exception("Lozinka je prazna!");
			
			User user = UserDAO.get(userName, password);
			if (user == null) {
//				response.sendRedirect("./Login.html");
				request.getRequestDispatcher("./FailureServlet").forward(request, response);
				return;
			}
	
			request.getSession().setAttribute("loggedInUserName", user.getUserName());
			
//			response.sendRedirect("./ListaFilmovaServlet");
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		} catch (Exception ex) {
			String message = ex.getMessage();
			if (message == null) {
				message = "Nepredvidjena greska!";
				ex.printStackTrace();
			}
			
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("message", message);

			request.setAttribute("data", data);
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
		}
	}

}
