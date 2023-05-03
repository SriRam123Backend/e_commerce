package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.userDetails;


public class userDBCImpl implements userDBC {

	private static userDBCImpl User = null;

	private userDBCImpl() {

	}

	public static userDBCImpl getInstance() {
		if (User == null) {
			User = new userDBCImpl();
		}

		return User;
	}
	@Override

	public int newuserRegistration(userDetails user) throws SQLException {

		int result = 0;
		String query = "insert into userDetails (userName,dateOfBirth,emailId,password) values(?, ?, ?, ?)";
		PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		System.out.println(pstmt);
		pstmt.setString(1, user.getUserName());
		pstmt.setLong(2, user.getDateOfBirth().getTime());
		pstmt.setString(3, user.getEmailId());
		pstmt.setString(4, user.getPassword());
		System.out.println(pstmt);
		ResultSet rs = User.verifyValidUser(user.getUserName());
		ResultSet rs1 = User.verifyEmailId(user.getEmailId());
		if(!rs.next() && !rs1.next())
		{
			pstmt.executeUpdate();
			ResultSet rs2 = User.verifyEmailId(user.getEmailId());
			if(rs2.next())
			{   
				result = rs2.getInt(1);
			}
		}
		return result;
	}

	@Override

	public ResultSet verifyValidUser(String userName) throws SQLException {

		ResultSet rs = null;

		String query = "select * from userDetails where userName=?";
		PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		pstmt.setString(1,userName);
		rs = pstmt.executeQuery();
		return rs;
	}

	@Override
	public ResultSet verifyEmailId(String emailId) throws SQLException {
		
		ResultSet rs = null;

		String query = "select * from userDetails where emailId = ?";
		PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		pstmt.setString(1, emailId);
		System.out.println(pstmt);
		rs = pstmt.executeQuery();

		return rs;
	}

}