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
import bioskop.model.Film;
import bioskop.model.User;
import bioskop.model.User.Role;

@SuppressWarnings("serial")
public class FilmServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUserName = (String) request.getSession().getAttribute("loggedInUserName");
//		if (loggedInUserName == null) {
//			response.sendRedirect("./Login.html");
//			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
//			return;
//		}
		try {
			User loggedInUser = UserDAO.get(loggedInUserName);
			if (loggedInUser == null) {
//				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
				return;
			}
	
			String idFilm = request.getParameter("idFilm");
			Film film = FilmDAO.get(idFilm);
	
//			request.setAttribute("loggedInUserRole", loggedInUser.getRole());
//			request.setAttribute("film", film);
//			request.getRequestDispatcher("./Film.jsp").forward(request, response);
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("film", film);
	
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
//			if (loggedInUser == null) {
//				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
//				return;
//			}
			if (loggedInUser.getRole() != Role.ADMIN) {
//				response.sendRedirect("./ListaFilmova.html");
				request.getRequestDispatcher("./UnauthorizedServlet").forward(request, response);
				return;
			}

			String action = request.getParameter("action");
			switch (action) {
				case "add": {
					String naziv = request.getParameter("naziv");
					naziv = (!"".equals(naziv)? naziv: "");
					String reziser = request.getParameter("reziser");
					reziser = (!"".equals(reziser)? reziser: "");
					String glumci = request.getParameter("glumci");
					glumci = (!"".equals(glumci)? glumci: "");
					String zanrovi = request.getParameter("zanrovi");
					zanrovi = (!"".equals(zanrovi)? zanrovi: "");
					int trajanje = Integer.parseInt(request.getParameter("trajanje"));
					trajanje = (trajanje > 0? trajanje: 9999);
					String distributer = request.getParameter("distributer");
					distributer= (!"".equals(distributer)? distributer: "");
					String zemljaPorekla = request.getParameter("zemljaPorekla");
					zemljaPorekla = (!"".equals(zemljaPorekla)? zemljaPorekla: "");
					int godinaProizvodnje = Integer.parseInt(request.getParameter("godinaProizvodnje"));
					godinaProizvodnje = (trajanje > 0? godinaProizvodnje: 9999);
					String opis = request.getParameter("opis");
					opis = (!"".equals(opis)? opis: "");
					
					if ("".equals(naziv))
						throw new Exception("Naziv je prazan!");
					if ("".equals(reziser))
						throw new Exception("Reziser je prazan!");
					if ("".equals(glumci))
						throw new Exception("Glumci su prazni!");
					if ("".equals(zanrovi))
						throw new Exception("Zanrovi su prazni!");
					if ("".equals(trajanje))
						throw new Exception("Trajanje je prazno!");
					if ("".equals(distributer))
						throw new Exception("Distributer je prazan!");
					if ("".equals(zemljaPorekla))
						throw new Exception("Zemlja Porekla je prazna!");
					if ("".equals(godinaProizvodnje))
						throw new Exception("Godina Proizvodnje je prazan!");
					if ("".equals(opis))
						throw new Exception("Opis je prazan!");



					Film film = new Film("", naziv, reziser, glumci, zanrovi,
							trajanje, distributer, zemljaPorekla,
							godinaProizvodnje, opis);
					FilmDAO.add(film);
					break;
				}
				case "update": {
					String idFilm = request.getParameter("idFilm");
					Film film = FilmDAO.get(idFilm);

					String naziv = request.getParameter("naziv");
					naziv = (!"".equals(naziv)? naziv: film.getNaziv());
					String reziser = request.getParameter("reziser");
					reziser = (!"".equals(reziser)? reziser: film.getReziser());
					String glumci = request.getParameter("glumci");
					glumci = (!"".equals(glumci)? glumci: film.getGlumci());
					String zanrovi = request.getParameter("zanrovi");
					zanrovi = (!"".equals(zanrovi)? zanrovi: film.getZanrovi());
					int trajanje = Integer.parseInt(request.getParameter("trajanje"));
					trajanje = (trajanje > 0? trajanje: film.getTrajanje());
					String distributer = request.getParameter("distributer");
					distributer = (!"".equals(distributer)? distributer: film.getDistributer());
					String zemljaPorekla = request.getParameter("zemljaPorekla");
					zemljaPorekla = (!"".equals(zemljaPorekla)? zemljaPorekla: film.getZemljaPorekla());
					int godinaProizvodnje = Integer.parseInt(request.getParameter("godinaProizvodnje"));
					godinaProizvodnje = (godinaProizvodnje > 0? godinaProizvodnje: film.getGodinaProizvodnje());
					String opis = request.getParameter("opis");
					opis = (!"".equals(opis)? opis: film.getOpis());

					film.setNaziv(naziv);
					film.setReziser(reziser);
					film.setGlumci(glumci);
					film.setZanrovi(zanrovi);
					film.setTrajanje(trajanje);
					film.setDistributer(distributer);
					film.setZemljaPorekla(zemljaPorekla);
					film.setGodinaProizvodnje(godinaProizvodnje);
					film.setOpis(opis);
					FilmDAO.update(film);
					break;
				}
				case "delete": {
					String idFilm = request.getParameter("idFilm");
					FilmDAO.delete(idFilm);
					break;
				}
			}request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		} catch (Exception ex) {
			String message = ex.getMessage();
			if (message == null) {
				message = "Nepredvidjena greska!";
				ex.printStackTrace();
			}
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
		}
//		response.sendRedirect("./ListaFilmovaServlet");
	}
}
