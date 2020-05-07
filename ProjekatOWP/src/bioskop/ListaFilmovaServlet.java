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

@SuppressWarnings("serial")
public class ListaFilmovaServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUserName = (String) request.getSession().getAttribute("loggedInUserName");
//		if (loggedInUserName == null) {
////			response.sendRedirect("./Login.html");
//			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
//			return;
//		}
		try {
			User loggedInUser = UserDAO.get(loggedInUserName);
//			if (loggedInUser == null) {
//				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
//				return;
//			}
			int lowDuration = 0;
			int lowYearOfProduction = 0;
			int highDuration = Integer.MAX_VALUE;
			int highYearOfProduction = Integer.MAX_VALUE;
			
			String naziv = request.getParameter("nazivFilter");
			naziv = (naziv != null? naziv: "");
			
			String zanrovi = request.getParameter("zanroviFilter");
			zanrovi = (zanrovi != null? zanrovi: "");
			
			try {
				String lowDurationFilter = request.getParameter("lowDurationFilter");
				lowDuration = Integer.parseInt(lowDurationFilter);
				lowDuration = (lowDuration >= 0? lowDuration: 0);
			} catch (Exception ex) {}
			try {
				String highDurationFilter = request.getParameter("highDurationFilter");
				highDuration = Integer.parseInt(highDurationFilter);
				highDuration = (highDuration >= 0? highDuration: Integer.MAX_VALUE);
			} catch (Exception ex) {}
			
			
			String distributer = request.getParameter("distributerFilter");
			distributer = (distributer != null? distributer: "");
			
			String zemljaPorekla = request.getParameter("zemljaPoreklaFilter");
			zemljaPorekla = (zemljaPorekla != null? zemljaPorekla: "");
			

			try {
				String lowYearOfProductionFilter = request.getParameter("lowYearOfProductionFilter");
				lowYearOfProduction = Integer.parseInt(lowYearOfProductionFilter);
				lowYearOfProduction = (lowYearOfProduction >= 0? lowYearOfProduction: 0);
			} catch (Exception ex) {}
			
			try {
				String highYearOfProductionFilter = request.getParameter("highYearOfProductionFilter");
				highYearOfProduction = Integer.parseInt(highYearOfProductionFilter);
				highYearOfProduction = (highYearOfProduction >= 0? highYearOfProduction: Integer.MAX_VALUE);
			} catch (Exception ex) {}
	
			List<Film> filteredFilms = FilmDAO.getAll(naziv, zanrovi, lowDuration, highDuration, distributer, zemljaPorekla,
					 lowYearOfProduction,highYearOfProduction);
			for (Film film : filteredFilms) {
				System.out.println(film);
				
			}
			
//			request.setAttribute("loggedInUserRole", loggedInUser.getRole());
//			...
//			request.setAttribute("filteredFilms", filteredFilms);
//			request.getRequestDispatcher("./ListaFilmova.jsp").forward(request, response);
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("filteredFilms", filteredFilms);
			
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
