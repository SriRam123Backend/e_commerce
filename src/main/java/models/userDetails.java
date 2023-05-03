package models;

import java.util.Date;

public class userDetails {
    
	public int id;
	public String userName;
	public String password;
	public String emailId;
	public Date dateOfBirth;
	
	public userDetails(int id, String userName, String password, String emailId, Date dateOfBirth) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.emailId = emailId;
		this.dateOfBirth = dateOfBirth;
	}
	
	public userDetails(String userName, String password, String emailId, Date dateOfBirth) {
		this.userName = userName;
		this.password = password;
		this.emailId = emailId;
		this.dateOfBirth = dateOfBirth;
	}
	
	public userDetails(String userName, String password)
	{
		this.userName = userName;
		this.password = password;
	}
	
	public int getUserId() {
		return id;
	}
	public void setUserId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailId() {
		return emailId;
	}
	public void getEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	@Override
	public String toString()
	{
		return this.userName+" "+this.password+" "+this.emailId+" "+this.dateOfBirth+".";
	}
	
}
