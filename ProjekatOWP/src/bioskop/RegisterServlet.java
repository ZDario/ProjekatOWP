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
import bioskop.model.User.Role;


@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String userName = request.getParameter("userName");
			if (UserDAO.get(userName) != null)
				throw new Exception("Korisnicko ime vec postoji!");
			if ("".equals(userName))
				throw new Exception("Korisnicko ime je prazno!");

			String password = request.getParameter("password");
			if ("".equals(password))
				throw new Exception("Lozinka je prazna!");
			
			User user = new User(userName, password, Role.USER);
			UserDAO.add(user);

//			response.sendRedirect("./Login.html");
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		} catch (Exception ex) {
			String message = ex.getMessage();
			if (message == null) {
				message = "Nepredvidjena greska!";
				ex.printStackTrace();
			}

//			request.setAttribute("message", message);
//			request.getRequestDispatcher("./Message.jsp").forward(request, response);
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("message", message);

			request.setAttribute("data", data);
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
		}
	}

}
