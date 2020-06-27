package bioskop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bioskop.model.Film;
import bioskop.model.Projekcija;
import bioskop.model.Sala;
import bioskop.model.TipProjekcije;
import bioskop.model.User;

public class ProjekcijaDAO {
	
	public static Projekcija get(String idProjekcija) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT idFilm, idTipProjekcije, idSala, datumPrikazivanja, cena, administratorKorIme FROM projekcija WHERE idProjekcija = ?"; 
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, idProjekcija);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int index = 1;
				String idFilm = rset.getString(index++);
				String idTipProjekcije = rset.getString(index++);
				String idSala = rset.getString(index++);
				String date = rset.getString(index++);
				SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
				Date datumPrikazivanja = formatter1.parse(date);
				double cena = rset.getDouble(index++);
				String administratorKorIme = rset.getString(index++);
				
				Film film = FilmDAO.get(idFilm);
				TipProjekcije tipProjekcije = TipProjekcijeDAO.get(idTipProjekcije);
				Sala sala = SalaDAO.get(idSala);
				User user = UserDAO.get(administratorKorIme);
				
				return new Projekcija(idProjekcija, film, tipProjekcije, sala, datumPrikazivanja, cena, user);
			}
		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
		return null;
	}
	
	public static List<Projekcija> getAllListaProjekcije(String film, String tipProjekcije, String sala, String dateOfRegistration ,int lowCena,int highCena) throws Exception {
		//public static List<Projekcija> getAll() throws Exception {
			List<Projekcija> projekcija = new ArrayList<>();
			
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			try {
				String query = "SELECT * "
						+ " FROM projekcija WHERE idFilm LIKE ? AND idTipProjekcije LIKE ? AND idSala LIKE ? AND datumPrikazivanja LIKE ? AND "
						+ "cena >= ? AND cena <= ?";
				
				pstmt = conn.prepareStatement(query);
				int index = 1;
				pstmt.setString(index++, "%" + film + "%");
				pstmt.setString(index++, "%" + tipProjekcije + "%");
				pstmt.setString(index++, "%" + sala + "%");
				pstmt.setString(index++, "%" + dateOfRegistration + "%");
				pstmt.setInt(index++, lowCena);
				pstmt.setInt(index++, highCena);
				System.out.println(pstmt);
				
				rset = pstmt.executeQuery();
				
				while(rset.next()) {
					index = 1;
					String idProjekcija = rset.getString(index++);
					String idFilm = rset.getString(index++);
					String idTipProjekcije = rset.getString(index++);
					String idSala = rset.getString(index++);
					String date = rset.getString(index++);
					SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Date datumPrikazivanja = formatter1.parse(date);
					//String datumPrikazivanja = rset.getString(index++);
					double cena= rset.getDouble(index++);
					String administratorKorIme = rset.getString(index++);
					
					Film filma = FilmDAO.get(idFilm);
					System.out.println(filma.getNaziv());
					TipProjekcije tipProjekcijee = TipProjekcijeDAO.get(idTipProjekcije);
					System.out.println(tipProjekcijee.getNaziv());
					Sala salaa = SalaDAO.get(idSala);
					System.out.println(salaa.getNaziv());
					User user = UserDAO.get(administratorKorIme);
					System.out.println(user.getUserName());
					
					Projekcija projekcijaa = new Projekcija(idProjekcija, filma, tipProjekcijee, salaa, datumPrikazivanja, cena, user);
					projekcija.add(projekcijaa);
					System.out.println(projekcijaa.getIdProjekcija() + projekcijaa.getFilm().getNaziv() + projekcijaa.getSala().getNaziv() + projekcijaa.getTipProjekcije().getNaziv() + projekcijaa.getCena() + "neki string");
					
					
				}
			}finally {
				try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
				try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
			}
			return projekcija;
		}
	
	public static boolean add(Projekcija projekcija) throws Exception {
		Connection conn = ConnectionManager.getConnection();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO projekcija (idFilm, idTipProjekcije, idSala, datumPrikazivanja, cena, administratorKorIme) " + 
					" VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			int index = 1;
			//pstmt.setString(index++, projekcija.getIdProjekcija());
			pstmt.setString(index++, projekcija.getFilm().getIdFilm());//(index++, projekcija.getFilm());
			pstmt.setString(index++, projekcija.getTipProjekcije().getIdTipProjekcije());
			pstmt.setString(index++, projekcija.getSala().getIdSala());
			pstmt.setString(index++, sdf.format(projekcija.getDatumPrikazivanja()));
			pstmt.setDouble(index++, projekcija.getCena());
			pstmt.setString(index++, projekcija.getUser().getUserName());
			System.out.println(pstmt);
			System.out.println(projekcija.getCena());
			
			return pstmt.executeUpdate() == 1;
		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
	}
	public static boolean update(Projekcija projekcija) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE projekcija SET idFilm = ?,idTipProjekcije = ?,idSala = ?,datumPrikazivanja = ?, "
					+ "cena = ?,administratorKorIme = ? "
					+ "WHERE idProjekcija = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, projekcija.getFilm().getNaziv());
			pstmt.setString(index++, projekcija.getIdProjekcija());
			pstmt.setString(index++, projekcija.getSala().getNaziv());
			pstmt.setString(index++, sdf.format(projekcija.getDatumPrikazivanja()));
			pstmt.setDouble(index++, projekcija.getCena());
			pstmt.setString(index++, projekcija.getUser().getUserName());
			System.out.println(pstmt);
			System.out.println(projekcija.getCena());

			return pstmt.executeUpdate() == 1;
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
	}
	
	public static boolean delete(String idProjekcija) throws Exception{
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "DELETE FROM projekcija WHERE idProjekcija = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, idProjekcija);
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
	}

}
