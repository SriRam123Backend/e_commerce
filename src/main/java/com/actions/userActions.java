package com.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import database.cookieDBCImpl;
import service.userServiceImpl;
import models.userDetails;


public class userActions {

	public static JSONObject response() {
		HttpServletRequest request = ServletActionContext.getRequest();

		JSONObject userDetails = null;
		try {
			BufferedReader reader = request.getReader();
			String response_Object = reader.readLine();
			JSONParser parser = new JSONParser();

			try {
				userDetails = (JSONObject) parser.parse(response_Object);

			} catch (ParseException e1) {
				e1.printStackTrace();;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return userDetails;

	}
	@SuppressWarnings({ "unchecked"})

	public String signUp() {

		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();

		try {
			if (request.getMethod().equals("POST")) {

				JSONObject userDetails = response();
				JSONObject user = new JSONObject();
				userDetails userdata = userServiceImpl.getInstance().newuserRegistration(userDetails);
		        
				System.out.println("result of this " + userdata);

				if (userdata != null) {
					UUID uuid = UUID.randomUUID();
					String sessionId = uuid.toString();
					int result = cookieDBCImpl.getInstance().addCookie(sessionId, userdata.getUserName());

					if (result != 0) {
				        Gson gson = new Gson();
				        String json = gson.toJson(userdata);
						user.put("Message", "success");
						user.put("user", json);
						response.getWriter().append(user.toString());
				        Cookie cookie = new Cookie("sessionId", sessionId);
				        cookie.setPath("/");
				        cookie.setDomain("localhost");
				        cookie.setMaxAge(60 * 60 * 24 * 30);

				        // add the cookie to the HTTP response
				        response.addCookie(cookie);
						response.setStatus(HttpServletResponse.SC_CREATED);
					} else {
						response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
					}
				} else {
					response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (SQLException err) {
			System.out.println(err.getMessage());
		}

		return null;
	}

	@SuppressWarnings("unchecked")

	public String signIn() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			if (request.getMethod().equals("POST")) {

				JSONObject userDetails = response();
				JSONObject user = new JSONObject();
				userDetails userdata = userServiceImpl.getInstance().verifyValidUser(userDetails);
		        
				System.out.println("result of this " + userdata);

				if (userdata != null) {
					UUID uuid = UUID.randomUUID();
					String sessionId = uuid.toString();
					int result = cookieDBCImpl.getInstance().addCookie(sessionId, userdata.getUserName());

					if (result != 0) {
				        Gson gson = new Gson();
				        String json = gson.toJson(userdata);
						user.put("Message", "success");
						user.put("user", json);
						response.getWriter().append(user.toString());
				        Cookie cookie = new Cookie("sessionId", sessionId);
				        cookie.setPath("/");
				        cookie.setDomain("localhost");
				        cookie.setMaxAge(60 * 60 * 24 * 30);

				        // add the cookie to the HTTP response
				        response.addCookie(cookie);
						response.setStatus(HttpServletResponse.SC_CREATED);
					} else {
						response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
					}
				} else {
					response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (SQLException err) {
			System.out.println(err.getMessage());
		}

		return null;
	}

}