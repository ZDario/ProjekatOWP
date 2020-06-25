package bioskop;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bioskop.dao.KartaDAO;
import bioskop.dao.KupljeneKarteDAO;
import bioskop.dao.UserDAO;
import bioskop.model.Karta;
import bioskop.model.KupljeneKarte;
import bioskop.model.User;

@SuppressWarnings("serial")
public class KupljenaKartaServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			
			Map<String, Object> data = new LinkedHashMap<>();
			
			String action = request.getParameter("action");
			switch(action) {
				case "all": {
					KupljeneKarte kupljeneKarte = KupljeneKarteDAO.get(loggedInUser);
					data.put("kupljeneKarte", kupljeneKarte);
				}
				case "size": {
					int size = KupljeneKarteDAO.getItemCount(loggedInUser);
					data.put("size", size);
					break;
				}
				case "totalPrice": {
					double totalPrice = KupljeneKarteDAO.getTotalPrice(loggedInUser);
					data.put("totalPrice", totalPrice);
					break;
				}
			}
			request.setAttribute("data", data);
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			System.out.println("kupljenekartendata:" + data.toString());
		}catch(Exception ex) {
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
			if (loggedInUser == null) {
				request.getRequestDispatcher("./LogoutServlet").forward(request, response);
				return;
			}

			String action = request.getParameter("action");
			switch (action) {
				case "add": {
					String idKarta = request.getParameter("idKarta");
					Karta karta = KartaDAO.get(idKarta);
					if (karta == null)
						break;

					int itemCount = Integer.parseInt(request.getParameter("itemCount"));
					//KupljeneKarteDAO.addItem(loggedInUser, idKarta, itemCount);
					break;
				}
				case "remove": {
					int index = Integer.parseInt(request.getParameter("index"));
					KupljeneKarteDAO.removeItem(loggedInUser, index);
					break;
				}
			}	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
//		response.sendRedirect("./ShoppingCartServlet");
		request.getRequestDispatcher("./SuccessServlet").forward(request, response);
	}
}
