package bioskop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

}