package bioskop;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bioskop.dao.KartaDAO;
import bioskop.dao.UserDAO;
import bioskop.model.Karta;
import bioskop.model.User;

@SuppressWarnings("serial")
public class ListaKarataServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String loggedInUserName = (String) request.getSession().getAttribute("loggedInUserName");
		try {
			User loggedInUser = UserDAO.get(loggedInUserName);
			System.out.println("-----------------------------------------");
			
			String projekcijak = request.getParameter("projekcijaFilter");
			projekcijak = (projekcijak != null? projekcijak: "");
			String sedistek = request.getParameter("sedisteFilter");
			sedistek = (sedistek != null? sedistek: "");
			
			String vremeProdajek = request.getParameter("vremeProdajeFilter");
			vremeProdajek = (vremeProdajek != null? vremeProdajek: "");
			
			String korisnikk = request.getParameter("korisnikFilter");
			korisnikk = (korisnikk != null? korisnikk: "");
			
		
			List<Karta> filteredKarte = KartaDAO.getAll(projekcijak,sedistek,vremeProdajek, korisnikk);
			
			for (Karta karta : filteredKarte) {
				System.out.println(karta);
			}
			
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("filteredKarte", filteredKarte);
			
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
