package bioskop.dao;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
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
			String query = "SELECT dateOfRegistration, role FROM users WHERE userName = ? AND password = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, userName);
			pstmt.setString(index++, password);
			System.out.println(pstmt);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				String dateOfRegistration = rset.getString("dateOfRegistration");
				Role role = Role.valueOf(rset.getString("role"));

				return new User(userName, password,dateOfRegistration, role);
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
			String query = "SELECT password, dateOfRegistration, role FROM users WHERE userName = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			System.out.println(pstmt);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 1;
				String password = rset.getString(index++);
				String dateOfRegistration = rset.getString(index++);
				Role role = Role.valueOf(rset.getString(index++));

				return new User(userName, password, dateOfRegistration, role);
			}
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}

		return null;
	}

	public static List<User> getAll(String userName,String password, String dateOfRegistration, String role) throws Exception {
		List<User> users = new ArrayList<>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT userName,password,dateOfRegistration,role "
					+ " FROM users WHERE userName LIKE ? AND "
					+ "password LIKE ? AND dateOfRegistration LIKE ? AND role LIKE ?";
					
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, "%"+userName+"%");
			pstmt.setString(index++, "%"+password+"%");
			pstmt.setString(index++, "%"+dateOfRegistration+"%");
			pstmt.setString(index++, "%"+role+"%");
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				index = 1;
				String useruserName = rset.getString(index++);
				String userpassword = rset.getString(index++);
				String userdateOfRegistration = rset.getString(index++);
				Role userrole = Role.valueOf(rset.getString(index++));
				
				User user = new User(useruserName,userpassword,userdateOfRegistration ,userrole);
				users.add(user);
			}
		}
		finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
		
		return users;
	}

	public static boolean add(User user) throws Exception {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO users (userName, password, dateOfRegistration, role) "
					+ "VALUES (?, ?, ? , ?)";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date parsed = sqlDateFormat.parse(user.getDateOfRegistration());
			java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());
			pstmt.setString(index++, user.getUserName());
			pstmt.setString(index++, user.getPassword());
			pstmt.setString(index++, sqlDate.toString());
			pstmt.setString(index++, user.getRole().toString());
			System.out.println(pstmt);

			return pstmt.executeUpdate() == 1;
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
		}
	}

	public static boolean update(User user) throws Exception {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE users SET userName = ?,password = ? "
					+ "WHERE userName = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, user.getUserName());
			pstmt.setString(index++, user.getPassword());

			System.out.println(pstmt);

			return pstmt.executeUpdate() == 1;
		} finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();}
		}
	}

	public static boolean delete(String userName) throws Exception {
		Connection conn = ConnectionManager.getConnection();

	PreparedStatement pstmt = null;
	try {
		String query = "DELETE FROM users WHERE userName = ?";

		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, userName);
		System.out.println(pstmt);

		return pstmt.executeUpdate() == 1;
	} finally {
		try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
		try {conn.close();} catch (Exception ex1) {ex1.printStackTrace();} // ako se koristi DBCP2, konekcija se mora vratiti u pool
	}
}

}
