package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;

import org.json.simple.JSONObject;

import models.userDetails;
import database.userDBC;
import database.userDBCImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class userServiceImpl implements userService {

	private static userServiceImpl User = null;

	private userServiceImpl() {

	}

	public static userServiceImpl getInstance() {
		if (User == null) {
			User = new userServiceImpl();
		}

		return User;
	}

	@Override

	public userDetails newuserRegistration(JSONObject userData) {
         
		userDBC dao = userDBCImpl.getInstance();
		userDetails user = null;
		int result = 0;
		try {
			String userName = userData.get("userName").toString();
			String encodedPassword = new String(Base64.getEncoder().encode((userData.get("password").toString()).getBytes()));
			String emailId = userData.get("emailId").toString();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

			Date dob = null;
			try {
				dob = dateFormat.parse(userData.get("dob").toString());
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			}

			user = new userDetails(userName, encodedPassword, emailId, dob);
			System.out.println(user.toString());
			result = dao.newuserRegistration(user);

			if (result != 0) {
				user = new userDetails(result ,userName, encodedPassword, emailId, dob);
				return user;
			}
		} catch (SQLException er) {
			System.out.println(er.getMessage());

		}

		return null;
	}

	@Override

	public userDetails verifyValidUser(JSONObject customer) {
        
		userDBC dao = userDBCImpl.getInstance();
		userDetails user = null;
		try {
			String userName = customer.get("userName").toString();
			String password = customer.get("password").toString();

			user = new userDetails(userName,password);
			ResultSet rs = dao.verifyValidUser(user.getUserName());
            
			if(rs.next())
			{
				String decodedPassword = new String(Base64.getDecoder().decode(rs.getString(5)));
				if(password.equals(decodedPassword))
				{   
					user = new userDetails(rs.getInt(1),rs.getString(2),rs.getString(5),rs.getString(4),new Date(rs.getLong(3)));
					return user;
				}
			}
		} catch (SQLException er) {
			System.out.println(er.getMessage());

		}
		return null;
	}
   
	public userDetails verifyValidUser(String userName) {
        
		userDBC dao = userDBCImpl.getInstance();
		userDetails user = null;
		try {
			ResultSet rs = dao.verifyValidUser(userName);
            
			if(rs.next())
			{
					user = new userDetails(rs.getInt(1),rs.getString(2),rs.getString(5),rs.getString(4),new Date(rs.getLong(3)));
					return user;
			}
		} catch (SQLException er) {
			System.out.println(er.getMessage());

		}
		return null;
	}
}