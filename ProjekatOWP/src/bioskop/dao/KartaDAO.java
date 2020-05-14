package bioskop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bioskop.model.Karta;
import bioskop.model.User;
import bioskop.model.Projekcija;
import bioskop.model.Sediste;

public class KartaDAO {
	
	public static Karta get(String idKarta) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT idProjekcija, sedisteRbr, vremeProdaje, korisnikKorIme FROM karta WHERE idKarta = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, idKarta);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int index = 1;
				String idProjekcija = rset.getString(index++);
				String sedisteRbr = rset.getString(index++);
				String date = rset.getString(index++);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
				Date vremeProdaje = sdf.parse(date);
				String korisnikKorIme = rset.getString(index++);
				
				Projekcija projekcija = ProjekcijaDAO.get(idProjekcija);
				User user = UserDAO.get(korisnikKorIme);
				Sediste sediste = SedisteDAO.get(sedisteRbr);
				
				return new Karta(idKarta, projekcija, sediste, vremeProdaje, user);
			}
		}finally{
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
		return null;
	}
	
	public static List<Karta> getAll() throws Exception {
		List<Karta> karte = new ArrayList<>();
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM karta";
			pstmt = conn.prepareStatement(query);
			int index = 1;
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				index = 1;
				String idKarta = rset.getString(index++);
				String idProjekcija = rset.getString(index++);
				String sedisteRbr = rset.getString(index++);
				String date = rset.getString(index++);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
				Date vremeProdaje = sdf.parse(date);
				String korisnikKorIme = rset.getString(index++);
				
				Projekcija projekcija = ProjekcijaDAO.get(idProjekcija);
				User user = UserDAO.get(korisnikKorIme);
				Sediste sediste = SedisteDAO.get(sedisteRbr);
			
				Karta karta = new Karta(idKarta, projekcija, sediste, vremeProdaje, user);
				karte.add(karta);
			}
		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
		return karte;
	}
	
	public static boolean add(Karta karta) throws Exception{
		Connection conn = ConnectionManager.getConnection();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO karta(idKarta, idProjekcija, sedisteRbr, vremeProdaje, korisnikKorIme) " + 
					"VALUES(?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, karta.getIdKarta());
			pstmt.setString(index++, karta.getProjekcija().getIdProjekcija());
			pstmt.setString(index++, karta.getSediste().getIdSediste());
			pstmt.setString(index++, sdf.format(karta.getVremeProdaje()));
			pstmt.setString(index++, karta.getUser().getUserName());
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
	}
	
	public static boolean delete(String idKarta) throws Exception{
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "DELETE FROM karta WHERE idKarta = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,idKarta);
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
	}

}
