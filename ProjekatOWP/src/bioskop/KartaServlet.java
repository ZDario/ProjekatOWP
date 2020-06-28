package bioskop;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bioskop.dao.FilmDAO;
import bioskop.dao.KartaDAO;
import bioskop.dao.KupljeneKarteDAO;
import bioskop.dao.ProjekcijaDAO;
import bioskop.dao.SalaDAO;
import bioskop.dao.TipProjekcijeDAO;
import bioskop.dao.UserDAO;
import bioskop.model.Film;
import bioskop.model.Karta;
import bioskop.model.Projekcija;
import bioskop.model.Sala;
import bioskop.model.TipProjekcije;
import bioskop.model.User;
import bioskop.model.User.Role;

@SuppressWarnings("serial")
public class KartaServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUserName = (String) request.getSession().getAttribute("loggedInUserName");
//		if(loggedInUserName == null) {
//			request.getRequestDispatcher("./LogoutServlet");
//			return;
//		}
		try {
			User loggedInUser = UserDAO.get(loggedInUserName);
//			if(loggedInUser == null) {
//				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
//				return;
//			}
			
			String idKarta = request.getParameter("idKarta");
			Karta karta = KartaDAO.getAdmin(idKarta);
			
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("karta", karta);
//			data.put("loggedInUserRole", loggedInUser.getRole());
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUserName = (String) request.getSession().getAttribute("loggedInUserName");
//		if(loggedInUserName == null) {
//			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
//			return;
//		}
		try {
			User loggedInUser = UserDAO.get(loggedInUserName);
//			if(loggedInUser == null) {
//				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
//				return;
//			}
			if(loggedInUser.getRole() != Role.ADMIN) {
				request.getRequestDispatcher("./UnauthorizedServlet").forward(request, response);
				return;
			}
			String action = request.getParameter("action");
			switch(action) {
				case "delete": {
					String idKarta = request.getParameter("idKarta");
					KartaDAO.delete(idKarta);
					System.out.println("dsadsasdsdadsasdadsa");
					break;
				}
			}
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		}catch(Exception ex) {
			String message = ex.getMessage();
			if (message == null) {
				message = "Nepredvidjena greska!";
				ex.printStackTrace();
			}
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
		}
	}

}
