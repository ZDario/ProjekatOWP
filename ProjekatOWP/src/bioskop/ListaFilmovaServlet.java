package bioskop;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bioskop.dao.FilmDAO;
import bioskop.dao.UserDAO;
import bioskop.model.Film;
import bioskop.model.User;


public class ListaFilmovaServlet extends HttpServlet {
	
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
			String naziv = request.getParameter("nazivFilter");
			naziv = (naziv != null? naziv: "");
			
			String zanrovi = request.getParameter("zanroviFilter");
			zanrovi = (zanrovi != null? zanrovi: "");
			
			int trajanje = request.getContentLength();
			trajanje = (trajanje != 0? trajanje: 0);
			
			String distributer = request.getParameter("distributerFilter");
			distributer = (distributer != null? distributer: "");
			
			String zemljaPorekla = request.getParameter("zemljaPoreklaFilter");
			zemljaPorekla = (zemljaPorekla != null? zemljaPorekla: "");
			
			int godinaProizvodnje = request.getContentLength();
			godinaProizvodnje = (godinaProizvodnje != 0? godinaProizvodnje: 0);
			
			double lowDuration = 0.0;
			double lowYearOfProduction = 0.0;
			try {
				String lowDurationFilter = request.getParameter("lowDurationFilter");
				lowDuration = Double.parseDouble(lowDurationFilter);
				lowDuration = (lowDuration >= 0.0? lowDuration: 0.0);
			} catch (Exception ex) {}
			try {
				String lowYearOfProductionFilter = request.getParameter("lowYearOfProductionFilter");
				lowYearOfProduction = Double.parseDouble(lowYearOfProductionFilter);
				lowYearOfProduction = (lowYearOfProduction >= 0.0? lowYearOfProduction: 0.0);
			} catch (Exception ex) {}
			
			double highDuration = Double.MAX_VALUE;
			double highYearOfProduction = Double.MAX_VALUE;
			try {
				String highDurationFilter = request.getParameter("highDurationFilter");
				highDuration = Double.parseDouble(highDurationFilter);
				highDuration = (highDuration >= 0.0? highDuration: Double.MAX_VALUE);
			} catch (Exception ex) {}
			try {
				String highYearOfProductionFilter = request.getParameter("highYearOfProductionFilter");
				highYearOfProduction = Double.parseDouble(highYearOfProductionFilter);
				highYearOfProduction = (highYearOfProduction >= 0.0? highYearOfProduction: Double.MAX_VALUE);
			} catch (Exception ex) {}
	
			List<Film> filteredFilms = FilmDAO.getAll(naziv, zanrovi, trajanje, distributer, zemljaPorekla,
					godinaProizvodnje,lowDuration, highDuration, lowYearOfProduction,highYearOfProduction);
	
//			request.setAttribute("loggedInUserRole", loggedInUser.getRole());
//			request.setAttribute("name", name);
//			request.setAttribute("lowPrice", lowPrice);
//			request.setAttribute("highPrice", highPrice);
//			request.setAttribute("filteredProducts", filteredProducts);
//			request.getRequestDispatcher("./WebShop.jsp").forward(request, response);
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("filteredProducts", filteredFilms);
	
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
