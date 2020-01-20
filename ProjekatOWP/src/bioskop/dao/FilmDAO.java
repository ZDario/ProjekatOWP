package bioskop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bioskop.model.Film;

public class FilmDAO {
	
	public static Film get(String idFilm) throws Exception {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT naziv,reziser,glumci,zanrovi, "
					+ "trajanje,distributer,zemljaPorekla,godinaProizvodnje,opis "
					+ " FROM films WHERE idFilm = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, idFilm);
			System.out.println(pstmt);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 1;
				String naziv = rset.getString(index++);
				String reziser = rset.getString(index++);
				String glumci = rset.getString(index++);
				String zanrovi = rset.getString(index++);
				int trajanje = rset.getInt(index++);
				String distributer = rset.getString(index++);
				String zemljaPorekla = rset.getString(index++);
				int godinaProizvodnje = rset.getInt(index++);
				String opis = rset.getString(index++);

				return new Film(idFilm, naziv, reziser, glumci, zanrovi,
						trajanje, distributer, zemljaPorekla,
						godinaProizvodnje, opis);
			}
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
		
		return null;
	}
	
	public static List<Film> getAll(String naziv,
			String zanrovi, int lowDuration, int highDuration, String distributer, String zemljaPorekla, 
			int lowYearOfProduction, int highYearOfProduction) throws Exception {
		List<Film> films = new ArrayList<>();

		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT idFilm,naziv,reziser,glumci, zanrovi, "
					+ "trajanje,distributer,zemljaPorekla,godinaProizvodnje, opis FROM films WHERE "
					+ "naziv LIKE ? AND zanrovi LIKE ? AND trajanje >= ? AND trajanje <= ? "
					+ "AND distributer LIKE ? AND zemljaPorekla LIKE ? "
					+ "AND godinaProizvodnje >= ? AND godinaProizvodnje <= ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, "%" + naziv + "%");
			pstmt.setString(index++, "%" + zanrovi + "%");
			pstmt.setInt(index++, lowDuration);
			pstmt.setInt(index++, highDuration);
			pstmt.setString(index++, "%" + distributer + "%");
			pstmt.setString(index++, "%" + zemljaPorekla + "%");
			pstmt.setInt(index++, lowYearOfProduction);
			pstmt.setInt(index++, highYearOfProduction);
			System.out.println(pstmt);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				index = 1;
				String filmId = rset.getString(index++);
				String filmNaziv = rset.getString(index++);
				String filmReziser = rset.getString(index++);
				String filmGlumci = rset.getString(index++);
				String filmZanrovi = rset.getString(index++);
				int filmTrajanje = rset.getInt(index++);
				String filmDistributer = rset.getString(index++);
				String filmZemljaPorekla = rset.getString(index++);
				int filmGodinaProizvodnje = rset.getInt(index++);
				String filmOpis = rset.getString(index++);

				Film film = new Film(filmId, filmNaziv, filmReziser, filmGlumci, filmZanrovi, filmTrajanje,
						filmDistributer, filmZemljaPorekla, filmGodinaProizvodnje, filmOpis);
				films.add(film);
			}
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
		
		return films;
	}

	public static boolean add(Film film) throws Exception {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO films (naziv,reziser,glumci,zanrovi, "
					+ "trajanje,distributer,zemljaPorekla,godinaProizvodnje,opis) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, film.getNaziv());
			pstmt.setString(index++, film.getReziser());
			pstmt.setString(index++, film.getGlumci());
			pstmt.setString(index++, film.getZanrovi());
			pstmt.setInt(index++, film.getTrajanje());
			pstmt.setString(index++, film.getDistributer());
			pstmt.setString(index++, film.getZemljaPorekla());
			pstmt.setInt(index++, film.getGodinaProizvodnje());
			pstmt.setString(index++, film.getOpis());
			System.out.println(pstmt);

			return pstmt.executeUpdate() == 1;
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
	}
	
	public static boolean update(Film film) throws Exception {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE films SET naziv = ?,reziser = ?,glumci = ?,zanrovi = ?, "
					+ "trajanje = ?,distributer = ?,zemljaPorekla = ?,godinaProizvodnje = ?,opis = ? "
					+ "WHERE idFilm = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, film.getNaziv());
			pstmt.setString(index++, film.getReziser());
			pstmt.setString(index++, film.getGlumci());
			pstmt.setString(index++, film.getZanrovi());
			pstmt.setInt(index++, film.getTrajanje());
			pstmt.setString(index++, film.getDistributer());
			pstmt.setString(index++, film.getZemljaPorekla());
			pstmt.setInt(index++, film.getGodinaProizvodnje());
			pstmt.setString(index++, film.getOpis());
			pstmt.setString(index++, film.getIdFilm());
			System.out.println(pstmt);

			return pstmt.executeUpdate() == 1;
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
	}
	
	public static boolean delete(String idFilm) throws Exception {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "DELETE FROM films WHERE idFilm = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, idFilm);
			System.out.println(pstmt);

			return pstmt.executeUpdate() == 1;
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
	}
}
