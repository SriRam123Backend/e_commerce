package com.actions;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.apache.struts2.ServletActionContext;

import database.DbConnection;
import database.cookieDBCImpl;
import models.userDetails;
import service.userServiceImpl;

public class cookies {

  @SuppressWarnings("unchecked")
  public String init() {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    ActionContext context = ActionContext.getContext();
    ServletContext servletContext = (ServletContext) context.get(org.apache.struts2.ServletActionContext.SERVLET_CONTEXT);

    String databaseName = servletContext.getInitParameter("database_name");
    String databaseUserName = servletContext.getInitParameter("database_username");
    String databasePassword = servletContext.getInitParameter("database_password");

    try {
      DbConnection.getDBConnection(databaseName, databaseUserName, databasePassword);
      Cookie[] cookie = request.getCookies();
      if (cookie == null) {
          response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      } else {
        for (int i = 0; i < cookie.length; i++) {
        	System.out.println("console");
          if (cookie[i].getName().equals("sessionId")) {
            String userName = cookieDBCImpl.getInstance().getUserName(cookie[i].getValue());
            System.out.println(userName);
            if (userName != null){
              request.setAttribute("userName", userName);
              userDetails userdata = userServiceImpl.getInstance().verifyValidUser(userName);
              Gson gson = new Gson();
              String obj = gson.toJson(userdata);
              JSONObject json = new JSONObject();
              json.put("user", obj);
              response.getWriter().append(json.toString());
              response.setStatus(HttpServletResponse.SC_ACCEPTED);
            }else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
          }
        }
      }
    } catch (Exception ex) {
      System.out.println(ex);
    }
    if (DbConnection.getDbConnection() == null) {
      System.out.println("FATAL: Please check Data Base Connection Issue");
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      System.exit(0);
    }
    return null;

  }
}