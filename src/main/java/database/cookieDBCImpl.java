package database;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class cookieDBCImpl implements cookieDBC{
   
	private static cookieDBCImpl cookie;

	private cookieDBCImpl() {

	}

	public static cookieDBCImpl getInstance() {
		if (cookie == null) {
			cookie = new cookieDBCImpl();
		}
		return cookie;
	}
	
	public int addCookie(String sessionId,String userName) throws SQLException
	{
		 int result = 0;

			String query = "insert into sessions values(?, ?)";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			pstmt.setString(1, sessionId);
			pstmt.setString(2, userName);
			result = pstmt.executeUpdate();
			
		    return result;
	}
	
	public String getUserName(String sessionId) throws SQLException
	{
		
			String query = "select * from sessions where sessionId = ?";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			pstmt.setString(1, sessionId);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				return rs.getString(2);
			}
		return null;
	}
	
	public int deleteCookie(String sessionId) throws SQLException
	{
		 int result = 0;

			String query = "delete from sessions where sessionId = ?";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			pstmt.setString(1, sessionId);
			result = pstmt.executeUpdate();
			
		return result;
	}
}