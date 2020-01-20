package bioskop;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bioskop.dao.UserDAO;
import bioskop.model.User;

@SuppressWarnings("serial")
public class ListaUseraServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		String userName = request.getParameter("userNameFilter");
		userName = (userName != null? userName: "");
		String password = request.getParameter("passwordFilter");
		password = (password != null? password: "");
		String role = request.getParameter("roleFilter");
		role = (role != null? role: "");
		
		List<User> filteredUsers = UserDAO.getAll(userName, password, role);
		for (User user : filteredUsers) {
			System.out.println(user);
		}
		
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("filteredUsers", filteredUsers);
		
		request.setAttribute("data", data);
		request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
