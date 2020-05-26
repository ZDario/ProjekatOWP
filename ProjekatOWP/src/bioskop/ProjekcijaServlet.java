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
			data.put("loggedInUserRole", loggedInUser.getRole());
			
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
					Date datumPrikazivanja = formatter1.parse(datumivremee);
					System.out.println(datumivremee);
					System.out.println("datumPrikazivanja" + datumPrikazivanja);
					
					double cenazakartu = Double.parseDouble(request.getParameter("cenakartezapr"));
					cenazakartu = (cenazakartu > 0? cenazakartu: 300);
					System.out.println(cenazakartu);
					Film film = FilmDAO.getNaziv(nazivFilma);
					TipProjekcije tipProjekcijee = TipProjekcijeDAO.getNaziv(tipProjek);
					Sala sala = SalaDAO.getNaziv(salaPr);
					User user = UserDAO.get(loggedInUserName);
					
					if ("".equals(nazivFilma))
						throw new Exception("Naziv je prazan!");
					if ("".equals(tipProjek))
						throw new Exception("Tip Projekcije je prazno!");
					if ("".equals(salaPr))
						throw new Exception("Sala je prazni!");
					if ("".equals(datumivremee))
						throw new Exception("Datum je prazni!");
					if ("".equals(cenazakartu))
						throw new Exception("Cena je prazna!");
					if ("".equals(user))
						throw new Exception("Korisnik je prazan!");
					
					Projekcija proj = new Projekcija("", film, tipProjekcijee, sala, datumPrikazivanja, cenazakartu, user);
					ProjekcijaDAO.add(proj);
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
