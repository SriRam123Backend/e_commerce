package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import models.userDetails;

public interface userDBC {
    
	int newuserRegistration(userDetails user) throws SQLException;
	
	ResultSet verifyValidUser(String user) throws SQLException;
	
	ResultSet verifyEmailId(String emailId) throws SQLException;
	
}
