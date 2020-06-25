package bioskop;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bioskop.dao.ProjekcijaDAO;
import bioskop.dao.UserDAO;
import bioskop.model.Film;
import bioskop.model.Projekcija;
import bioskop.model.User;

@SuppressWarnings("serial")
public class ListaProjekcijaServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUserName = (String) request.getSession().getAttribute("loggedInUserName");
		try {
			User loggedInUser = UserDAO.get(loggedInUserName);
			
			String film = request.getParameter("filmFilter");
			film = (film != null? film: "");
			String tipProjekcije = request.getParameter("tipProjekcijeFilter");
			tipProjekcije = (tipProjekcije != null?tipProjekcije: "");
			String sala = request.getParameter("salaFilter");
			sala = (sala != null? sala: "");
			
			String dateOfRegistration = request.getParameter("datumPrikazivanjaFilter");
			dateOfRegistration = (dateOfRegistration != null? dateOfRegistration: "");
			
			int lowCena = 0;
			int highCena = Integer.MAX_VALUE;
			try {
				String lowCenaFilter = request.getParameter("lowCenaFilter");
				lowCena= Integer.parseInt(lowCenaFilter);
				lowCena = (lowCena >= 0? lowCena: 0);
			} catch (Exception ex) {}
			try {
				String highCenaFilter = request.getParameter("highCenaFilter");
				highCena= Integer.parseInt(highCenaFilter);
				highCena = (highCena >= 0? highCena: Integer.MAX_VALUE);
			} catch (Exception ex) {}
			
			
//			String lowCena = request.getParameter("lowCena");
//			lowCena = (lowCena != null? lowCena : "");
//			String highCena = request.getParameter("highCena");
//			highCena = (highCena != null? highCena: "");
		
			List<Projekcija> filteredProjekcije = ProjekcijaDAO.getAllListaProjekcije(film,tipProjekcije,sala, dateOfRegistration, lowCena ,highCena);
			
			for (Projekcija projekcija : filteredProjekcije) {
				System.out.println(projekcija);
			}
			
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("filteredProjekcije", filteredProjekcije);
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}