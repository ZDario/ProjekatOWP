package bioskop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bioskop.model.Sala;
import bioskop.model.Sediste;

public class SedisteDAO {
	public static Sediste get(String idSediste) throws Exception {
		
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT sala, zauzeto FROM sediste WHERE idSediste = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, idSediste);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 1;
				String idSala = rset.getString(index++);
				boolean zauzeto = rset.getBoolean(index++);
				
				Sala sala = SalaDAO.get(idSala);
				
				return new Sediste(idSediste, sala, zauzeto);
			}
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}

		return null;
	}
	
	public static List<Sediste> getAll() throws Exception {
		List<Sediste> sedista = new ArrayList<>();
		
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELET * FROM sediste";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				index = 1;
				String idSediste = rset.getString(index++);
				String idSala = rset.getString(index++);
				boolean zauzeto = rset.getBoolean(index++);
				
				Sala sala = SalaDAO.get(idSala);
				
				Sediste sediste = new Sediste(idSediste, sala, zauzeto);
				sedista.add(sediste);
			}
		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
		return sedista;
	}
}
