package database;

import java.sql.SQLException;

public interface cookieDBC {
	
	int addCookie(String sessionId,String userName) throws SQLException;
	 
	int deleteCookie(String sessionId) throws SQLException;
	
	String getUserName(String sessionId) throws SQLException;
}
