package bioskop;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bioskop.dao.FilmDAO;
import bioskop.dao.UserDAO;
import bioskop.model.User;
import bioskop.model.User.Role;

@SuppressWarnings("serial")
public class UserServlet extends HttpServlet { 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUserName = (String) request.getSession().getAttribute("loggedInUserName");
		if (loggedInUserName == null) {
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		try {
			User loggedInUser = UserDAO.get(loggedInUserName);
			if (loggedInUser == null) {
				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
				return;
			}
	
			Map<String, Object> data = new LinkedHashMap<>();
	
			String action = request.getParameter("action");
			switch (action) {
				case "loggedInUserRole": {
					data.put("loggedInUserRole", loggedInUser.getRole());
					break;
				}
			}
	
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUserName = (String) request.getSession().getAttribute("loggedInUserName");
		if (loggedInUserName == null) {
//			response.sendRedirect("./Login.html");
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		try {
			User loggedInUser = UserDAO.get(loggedInUserName);
			if (loggedInUser == null) {
				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
				return;
			}
			if (loggedInUser.getRole() != Role.ADMIN) {
//				response.sendRedirect("./ListaFilmova.html");
				request.getRequestDispatcher("./UnauthorizedServlet").forward(request, response);
				return;
			}
			
			String action = request.getParameter("action");
			switch (action) {
			case "add": {
					String userName = request.getParameter("userName");
					userName = (!"".equals(userName)? userName: "<prazan userName>");
					String password = request.getParameter("password");
					password = (!"".equals(password)? password: "<prazan password>");
					
					User user = new User(userName,password,Role.USER);
					UserDAO.add(user);
					
			}
			case "update": {
				String userName = request.getParameter("userName");
				User user = UserDAO.get(userName);
				
				String userName1 = request.getParameter("userName1");
				userName1 = (!"".equals(userName1)? userName1: user.getUserName());
				String password = request.getParameter("password");
				password = (!"".equals(password)? password: user.getPassword());
				
				user.setUserName(userName1);
				user.setPassword(password);
				UserDAO.update(user);
				break;
			}
			case "delete": {
				String userName = request.getParameter("userName");
				FilmDAO.delete(userName);
				break;
			}
			}request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
		}
//		response.sendRedirect("./ListaUseraServlet");
	}
}

