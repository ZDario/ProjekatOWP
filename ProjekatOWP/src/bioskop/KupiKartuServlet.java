package bioskop;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bioskop.dao.KartaDAO;
import bioskop.dao.ProjekcijaDAO;
import bioskop.dao.SedisteDAO;
import bioskop.dao.UserDAO;
import bioskop.model.Karta;
import bioskop.model.Projekcija;
import bioskop.model.Sediste;
import bioskop.model.User;

@SuppressWarnings("serial")
public class KupiKartuServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUserName = (String) request.getSession().getAttribute("loggedInUserName");
		if(loggedInUserName == null) {
			request.getRequestDispatcher("./LogoutServlet");
			return;
		}
		try {
			User loggedInUser =UserDAO.get(loggedInUserName);
			if(loggedInUser == null) {
				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
				return;
			}
			
			String idProjekcija = request.getParameter("idProjekcija");
			Projekcija projekcija = ProjekcijaDAO.get(idProjekcija);
			
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("projekcija", projekcija);
			data.put("loggedInUserRole", loggedInUser.getRole());
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUserName = (String) request.getSession().getAttribute("loggedInUserName");
		if(loggedInUserName == null) {
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		try {
			User loggedInUser = UserDAO.get(loggedInUserName);
			if(loggedInUser == null) {
				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
				return;
			}
			String action = request.getParameter("action");
			switch(action) {
				case "add":{
					String idProjekcija = request.getParameter("idProjekcija");
					System.out.println("idProjekcija: " + idProjekcija);
					String nazivFilmaa = request.getParameter("naziv");
					nazivFilmaa = (!"".equals(nazivFilmaa)?nazivFilmaa: "");
					System.out.println("nazifvilmaa: " + nazivFilmaa);
					String tipProjekcijee = request.getParameter("tipProjekcije");
					tipProjekcijee = (!"".equals(tipProjekcijee)?tipProjekcijee:"");
					System.out.println("tipProjekcijee: "+ tipProjekcijee);
					String salaaa = request.getParameter("sala");
					salaaa = (!"".equals(salaaa)?salaaa:"");
					System.out.println("salaaa:" + salaaa);
					String date = request.getParameter("datVremee");
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//					Date datVremee = sdf.parse(date);
//					System.out.println(datVremee);
					double cenaKartee = Double.parseDouble(request.getParameter("cenaKartee"));
					cenaKartee = (cenaKartee > 0?cenaKartee: 300);
					System.out.println(cenaKartee);
					String sedisteKartee = request.getParameter("sedisteKartee");
					sedisteKartee = (!"".equals(sedisteKartee)?sedisteKartee:"");
					System.out.println("sedisteKartee:" + sedisteKartee);
					Random rand = new Random();
					int idKarte = rand.nextInt();
					String idk = String.valueOf(idKarte);
					Projekcija projekcija = ProjekcijaDAO.get(idProjekcija);
					Sediste sediste = SedisteDAO.get(sedisteKartee);
					Karta karta = new Karta(idk, projekcija, sediste, new Date(), loggedInUser);
					KartaDAO.add(karta);
					//KupljeneKarteDAO.addItem("4", projekcija, sjediste, loggedInUserName);
					break;
				}
			}
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		}catch(Exception ex) {
			ex.printStackTrace();
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
		}
	}
}
