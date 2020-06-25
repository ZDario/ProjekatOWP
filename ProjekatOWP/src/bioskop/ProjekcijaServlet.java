package bioskop;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bioskop.dao.FilmDAO;
import bioskop.dao.ProjekcijaDAO;
import bioskop.dao.SalaDAO;
import bioskop.dao.TipProjekcijeDAO;
import bioskop.dao.UserDAO;
import bioskop.model.Film;
import bioskop.model.Projekcija;
import bioskop.model.Sala;
import bioskop.model.TipProjekcije;
import bioskop.model.User;
import bioskop.model.User.Role;

@SuppressWarnings("serial")
public class ProjekcijaServlet extends HttpServlet {
	
   
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
			
			String idProjekcija = request.getParameter("idProjekcija");
			Projekcija projekcija = ProjekcijaDAO.get(idProjekcija);
			
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("projekcija", projekcija);
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
				case "add":{
					String nazivFilma = request.getParameter("nazivFilma");
					nazivFilma = (!"".equals(nazivFilma)? nazivFilma: "");
					System.out.println(nazivFilma);
					String tipProjek = request.getParameter("tipProjek");
					tipProjek = (!"".equals(tipProjek)? tipProjek: "");
					System.out.println(tipProjek);
					String salaPr = request.getParameter("salaPr");
					salaPr = (!"".equals(salaPr)? salaPr: "");
					System.out.println(salaPr);
					String datumivremee = request.getParameter("datumvreme");
					SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					//Date datumPrikazivanja = formatter1.parse(datumivremee);
					java.sql.Date datumPrikazivanja = new java.sql.Date(formatter1.parse(datumivremee).getTime());
					System.out.println(datumivremee);
					System.out.println("datumPrikazivanja: " + datumPrikazivanja);
					
					double cenazakartu = Double.parseDouble(request.getParameter("cenazakartu"));
					cenazakartu = (cenazakartu > 0? cenazakartu: 3000);
					System.out.println(cenazakartu);
					Film film = FilmDAO.get(nazivFilma);
					TipProjekcije tipProjekcijee = TipProjekcijeDAO.get(tipProjek);
					Sala sala = SalaDAO.get(salaPr);
					User user = UserDAO.get(loggedInUserName);
						
					Projekcija proj = new Projekcija("", film, tipProjekcijee, sala, datumPrikazivanja, cenazakartu, user);
					ProjekcijaDAO.add(proj);
					break;
				}
				case "update": {
					String idProjekcija = request.getParameter("idProjekcija");
					Projekcija projekcija = ProjekcijaDAO.get(idProjekcija);
					
					String film = request.getParameter("film");
					film = (!"".equals(film)? film: film);
					String tipProjekcije = request.getParameter("tipProjekcije");
					tipProjekcije = (!"".equals(tipProjekcije)? tipProjekcije: tipProjekcije);
					String sala = request.getParameter("sala");
					sala = (!"".equals(sala)? sala: sala);
					
					String datumivremee = request.getParameter("datum");
					SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					//Date datum = formatter1.parse(datumivremee);
					
					//String datumPrikazivanjaString = request.getParameter("datumPrikazivanja");
					//SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
					java.sql.Date datum = new java.sql.Date(formatter1.parse(datumivremee).getTime());
					
					double cenazakartu = Double.parseDouble(request.getParameter("cenazakartu"));
					cenazakartu = (cenazakartu > 0? cenazakartu: 3000);
					
					Film film1 = FilmDAO.get(film);
					TipProjekcije tipProjekcije1 = TipProjekcijeDAO.get(tipProjekcije);
					Sala sala1 = SalaDAO.get(sala);
					User user = UserDAO.get(loggedInUserName);
					
					projekcija.setIdProjekcija(idProjekcija);
					projekcija.setFilm(film1);
					projekcija.setTip(tipProjekcije1);
					projekcija.setSala(sala1);;
					projekcija.setDatumPrikazivanja(datum);
					projekcija.setCena(cenazakartu);
					projekcija.setUser(user);
					ProjekcijaDAO.update(projekcija);
					break;
				}
				case "delete": {
					String idProjekcija = request.getParameter("idProjekcija");
					ProjekcijaDAO.delete(idProjekcija);
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
