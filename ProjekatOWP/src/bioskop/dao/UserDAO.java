package bioskop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bioskop.model.User;
import bioskop.model.User.Role;

public class UserDAO {
	
	public static User get(String userName, String password) throws Exception {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT role FROM users WHERE userName = ? AND password = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, userName);
			pstmt.setString(index++, password);
			System.out.println(pstmt);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				Role role = Role.valueOf(rset.getString(1));

				return new User(userName, password, role);
			}
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}

		return null;
	}
	
	public static User get(String userName) throws Exception {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT password, role FROM users WHERE userName = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			System.out.println(pstmt);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 1;
				String password = rset.getString(index++);
				Role role = Role.valueOf(rset.getString(index++));

				return new User(userName, password, role);
			}
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}

		return null;
	}

	public static List<User> getAll(String name, Role role) throws Exception {
		return new ArrayList<>();
	}

	public static boolean add(User user) throws Exception {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO users (userName, password, role) "
					+ "VALUES (?, ?, ?)";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, user.getUserName());
			pstmt.setString(index++, user.getPassword());
			pstmt.setString(index++, user.getRole().toString());
			System.out.println(pstmt);

			return pstmt.executeUpdate() == 1;
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
	}

	public static boolean update(User user) throws Exception {
		return false;
	}

	public static boolean delete(String userName) throws Exception {
		return false;
	}

}
