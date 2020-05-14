package bioskop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bioskop.model.TipProjekcije;

public class TipProjekcijeDAO {
	public static TipProjekcije get(String idTipProjekcije) throws Exception {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT naziv FROM tipProjekcije WHERE idTipProjekcije = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, idTipProjekcije);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 1;
				String naziv = rset.getString(index++);
				
				return new TipProjekcije(idTipProjekcije, naziv);
			}
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}

		return null;
	}
	
	public static TipProjekcije getNaziv(String naziv) throws Exception{
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT idTipProjekcije FROM tipProjekcije WHERE idTipProjekcije = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, naziv);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int index = 1;
				String idTipProjekcije = rset.getString(index++);
				
				return new TipProjekcije(idTipProjekcije, naziv);
			}
		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
		return null;
	}
	
	public static List<TipProjekcije> getAll() throws Exception{
		List<TipProjekcije> tipProjekcije = new ArrayList<>(); 
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT * FROM tipProjekcije";
			pstmt = conn.prepareStatement(query);
			int index = 1;
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				index = 1;
				String idTipProjekcije = rset.getString(index++);
				String naziv = rset.getString(index++);
				
				TipProjekcije tipproj = new TipProjekcije(idTipProjekcije, naziv);
				tipProjekcije.add(tipproj);
			}
		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
		return tipProjekcije;
	}

}