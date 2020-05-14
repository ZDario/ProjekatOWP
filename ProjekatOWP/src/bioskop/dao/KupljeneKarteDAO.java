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
			String query = "SELECT pr.idProjekcija, pr.idFilm, pr.datumPrikazivanja, pr.idTipProjekcije, pr.idSala, pr.cena, k.sedisteRbr, k.idKarta, k.userName FROM projekcije pr JOIN karta k ON pr.idProjekcija = k.idProjekcija WHERE k.userName = ? ORDER BY k.sedisteRbr";
		
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
				SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
				Date vremeProdaje = formatter1.parse(date);
				System.out.println("vremeprodaje: " + vremeProdaje);
				String idTipProjekcije = rset.getString(index++);
				System.out.println("idTipProjekcije: " + idTipProjekcije);
				String idSala = rset.getString(index++);
				System.out.println("idSala: " + idSala);
				double cena = rset.getDouble(index++);
				System.out.println("cena: " + cena);
				String sedisteRbr = rset.getString(index++);
				System.out.println("sedisteRbr: " + sedisteRbr);
				String idKarta = rset.getString(index++);
				System.out.println("idKarta: " + idKarta);
				
				//Date vrijemeProdaje = rset.getDate(index++);
				String korisnickoIme = rset.getString(index++);
				System.out.println("korisnickoIme: " + korisnickoIme);
				
				Projekcija projekcija = ProjekcijaDAO.get(idProjekcija);
				Sediste sediste = SedisteDAO.get(sedisteRbr);
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm:ss");
		
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
				
				query = "INSERT INTO karta (idKarta, idProjekcija, sedisteRbr, vremeProdaje, korisnikKorIme) "
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
}
