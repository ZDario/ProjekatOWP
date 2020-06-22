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
import bioskop.model.Projekcija;
import bioskop.model.User;

@SuppressWarnings("serial")
public class GlavnaStranicaServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUserName = (String) request.getSession().getAttribute("loggedInUserName");
		try {
			User loggedInUser = UserDAO.get(loggedInUserName);
			
			String film = request.getParameter("film");
			film = (film != null?film: "");
			String tipProjekcije = request.getParameter("tipProjekcije");
			tipProjekcije = (tipProjekcije != null?tipProjekcije: "");
			String sala = request.getParameter("sala");
			sala = (sala != null?sala: "");
			
			/*Date minDatumRegistracije = new Date();
			String mindatumRegistracije = minDatumRegistracije.toString();
			mindatumRegistracije = (mindatumRegistracije != null?mindatumRegistracije: "");*/
			int mindatVr = 0;
			try {
				String mindatvr = request.getParameter("mindatvr");
				mindatVr = Integer.parseInt(mindatvr);
				mindatVr = (mindatVr >= 0? mindatVr: 0);
			} catch(Exception ex) {}
			/*Date maxDatumRegistracije = new Date();
			String maxdatumRegistracije = maxDatumRegistracije.toString();
			maxdatumRegistracije = (maxdatumRegistracije != null?maxdatumRegistracije: "");*/
			
			int maxdatVr = 0;
			try {
				String maxdatvr = request.getParameter("maxdatvr");
				maxdatVr = Integer.parseInt(maxdatvr);
				maxdatVr = (maxdatVr >= 0? maxdatVr: 0);
			} catch(Exception ex) {}
			
			int lowCena1 = 0;
			int highCena1 = Integer.MAX_VALUE;
			try {
				String lowCena = request.getParameter("lowCena");
				lowCena1= Integer.parseInt(lowCena );
				lowCena1 = (lowCena1 >= 0? lowCena1: 0);
			} catch (Exception ex) {}
			try {
				String highCena = request.getParameter("highCena");
				highCena1= Integer.parseInt(highCena );
				highCena1 = (highCena1 >= 0? highCena1: 0);
			} catch (Exception ex) {}
		
			List<Projekcija> filteredProjekcije = ProjekcijaDAO.getAllListaProjekcije(film, tipProjekcije, sala, mindatVr, maxdatVr, lowCena1, highCena1);
			List<Projekcija> danasnjeProjekcije = new ArrayList<>();
			
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("filteredProjekcije", filteredProjekcije);
			data.put("danasnjeProjekcije", danasnjeProjekcije);
			System.out.println(data + "data");
			System.out.println(filteredProjekcije + "filteredproj");
			
			System.out.println("datumdanas");
			Date danas = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(sdf.format(danas));
			System.out.println("datumdanas");
			
			
			for(Projekcija projekcija: filteredProjekcije) {
				if(sdf.format(projekcija.getDatumPrikazivanja()).toString().equals((sdf.format(danas)).toString())) {
					danasnjeProjekcije.add(projekcija);
				}
				System.out.println(sdf.format(projekcija.getDatumPrikazivanja()).toString());
				System.out.println(sdf.format(danas).toString() + "danasdanas");
				System.out.println(danasnjeProjekcije + "danasnjeproj");
			}
			data.put("danasnjeProjekcije", danasnjeProjekcije);
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
