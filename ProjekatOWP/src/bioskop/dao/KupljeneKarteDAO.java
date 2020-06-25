package bioskop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import bioskop.model.Karta;
import bioskop.model.User;
import bioskop.dao.ConnectionManager;
import bioskop.model.KupljeneKarte;
import bioskop.model.Projekcija;
import bioskop.model.Sediste;

public class KupljeneKarteDAO {
	public static KupljeneKarte get(User user) throws Exception{
		KupljeneKarte kupljeneKarte = new KupljeneKarte();
		Map<String, Karta> karte = new HashMap<>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT pr.idProjekcija, pr.idFilm, pr.datumPrikazivanja, pr.idTipProjekcije, pr.idSala, pr.cena, k.idSediste, k.idKarta, k.korisnikKorIme FROM projekcija pr JOIN karta k ON pr.idProjekcija = k.idProjekcija WHERE k.korisnikKorIme = ? ORDER BY k.idSediste";
		
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user.getUserName());
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				int index = 1;
				String idProjekcija= rset.getString(index++);
				System.out.println("idProjekcija:" + idProjekcija);
				String idFilm = rset.getString(index++);
				System.out.println("idFilm: " + idFilm);
				String date = rset.getString(index++);
				SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date vremeProdaje = formatter1.parse(date);
				System.out.println("vremeprodaje: " + vremeProdaje);
				String idTipProjekcije = rset.getString(index++);
				System.out.println("idTipProjekcije: " + idTipProjekcije);
				String idSala = rset.getString(index++);
				System.out.println("idSala: " + idSala);
				double cena = rset.getDouble(index++);
				System.out.println("cena: " + cena);
				String idSediste = rset.getString(index++);
				System.out.println("idSediste: " + idSediste);
				String idKarta = rset.getString(index++);
				System.out.println("idKarta: " + idKarta);
				
				//Date vrijemeProdaje = rset.getDate(index++);
				String korisnickoIme = rset.getString(index++);
				System.out.println("korisnickoIme: " + korisnickoIme);
				
				Projekcija projekcija = ProjekcijaDAO.get(idProjekcija);
				Sediste sediste = SedisteDAO.get(idSediste);
				User userr = UserDAO.get(korisnickoIme);
				
				System.out.println(kupljeneKarte);
				
				//Projekcija projekcija = ProjekcijaDAO.get(projekcijaID);
				
				Karta karta = karte.get(idKarta);
				if(karta == null) {
					karta = new Karta(idKarta, projekcija, sediste, vremeProdaje, userr);
					karte.put(karta.getIdKarta(), karta);
				}
				kupljeneKarte.dodajKartu(idKarta, projekcija, sediste, vremeProdaje, userr);
				System.out.println(kupljeneKarte);
			}
		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
		
		return kupljeneKarte;
	}
	
	public static boolean addItem(String idKarta, Projekcija projekcija, Sediste sediste, String korisnikKorIme) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm");
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT COUNT(*) FROM karta WHERE korisnikKorIme = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, korisnikKorIme);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				idKarta = rset.getString(1);
				System.out.println("blablabla");
				System.out.println(idKarta);
				System.out.println("blablabla");
				pstmt.close();
				
				query = "INSERT INTO karta (idKarta, idProjekcija, idSediste, vremeProdaje, korisnikKorIme) "
						+ "VALUES (?, ?, ?, ?, ?)";
				
				pstmt = conn.prepareStatement(query);
				int index = 1;
				pstmt.setString(index++, idKarta);
				pstmt.setString(index++, projekcija.getIdProjekcija());
				pstmt.setString(index++, sediste.getIdSediste());
				pstmt.setString(index++, sdf.format(projekcija.getDatumPrikazivanja()));
				pstmt.setString(index++, korisnikKorIme);
				System.out.println(pstmt);
				
				return pstmt.executeUpdate() == 1;
			}
		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
		return false;
	}
	
	public static int getItemCount(User user) throws Exception{
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT COUNT(*) FROM karta WHERE korisnikKorIme = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user.getUserName());
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			if(rset.next())
				return rset.getInt(1);
		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
		
		return -1;
	}
	
	public static double getTotalPrice(User user) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = 
					"SELECT SUM(p.cena*kar.count) FROM karta kar " + 
					"JOIN projekcija p ON kar.idKarta = p.idProjekcija " + 
					"WHERE kar.korisnikKorIme = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user.getUserName());
			System.out.println(pstmt);

			rset = pstmt.executeQuery();
			
			if (rset.next())
				return rset.getDouble(1);
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}

		return -1;
	}
	
	public static boolean removeItem(User user, int itemIndex) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			
			conn.setAutoCommit(false); 
			conn.commit(); 
			
			String query = "DELETE FROM karta WHERE korisnikKorIme = ? AND idKarta = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, user.getUserName());
			pstmt.setInt(index++, itemIndex);
			System.out.println(pstmt);

			if (pstmt.executeUpdate() == 1) {
				pstmt.close();

				query = "UPDATE karta SET idKarta = idKarta - 1 WHERE korisnikKorIme = ? AND idKarta > ?";

				pstmt = conn.prepareStatement(query);
				index = 1;
				pstmt.setString(index++, user.getUserName());
				pstmt.setInt(index++, itemIndex);
				System.out.println(pstmt);
				
				pstmt.executeUpdate();
				
				conn.commit(); 
				return true;
			}
		} catch (Exception ex) {
			try {conn.rollback();} catch (Exception ex1) {ex1.printStackTrace();} 

			throw ex;		
		} finally {
			try {conn.setAutoCommit(true);} catch (Exception ex1) {ex1.printStackTrace();}
			
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}

		return false;
	}
}
