package service;

import org.json.simple.JSONObject;

import models.userDetails;

public interface userService {
   
	userDetails newuserRegistration(JSONObject user);
	
	userDetails verifyValidUser(JSONObject user);
}
