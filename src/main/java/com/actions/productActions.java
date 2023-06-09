package com.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;

import models.color;
import models.price;
import models.product;
import models.cart;
import service.productServiceImpl;

public class productActions {

  @SuppressWarnings("unchecked")
  public String productDetails() {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    try {
      if (request.getMethod().equals("POST")) {
        ArrayList < product > products = productServiceImpl.getInstance().productDetails();
        int userid = Integer.parseInt(request.getParameter("userID").toString());
        JSONObject productObject = new JSONObject();
        if(userid != 0)
        {
            JSONObject cart = new JSONObject();
      	    JSONObject cart2 = new JSONObject();
      	    cart cartProducts = productServiceImpl.getInstance().allCartProducts(userid);  
    		cart2.put("id",1);
    		ArrayList<product> cartThings = cartProducts.getProducts();
    		JSONObject cartproduct = productObject(cartThings,cartProducts.getId());
    		cart.put("products",cartproduct);
    		cart2.put("userId",cartProducts.getUserId());
    		cart.put("cart",cart2);
            productObject.put("cart",cart);
        }
        JSONObject productsArray = productObject(products,0); 
        productObject.put("products",productsArray.get("products"));
        productObject.put("colors",productsArray.get("colors"));
        response.getWriter().append(productObject.toJSONString());
        response.setStatus(HttpServletResponse.SC_OK);
      }
    } catch (IOException er) {
      System.out.println(er.getMessage());
    }
    return null;
  }
  
  @SuppressWarnings("unchecked")
public String addToCart()
  {
	    HttpServletResponse response = ServletActionContext.getResponse();
	    HttpServletRequest request = ServletActionContext.getRequest();
	   try{ 
	    if(request.getMethod().equals("POST"))
	    {
	    	JSONObject cartDetails = userActions.response();
	    	JSONObject cart = new JSONObject();
	    	
	    	int result = productServiceImpl.getInstance().addtoCart(cartDetails);
	    	
	    	if(result != 0)
	    	{
	    	  JSONObject cart2 = new JSONObject();
	    	  cart cartProducts = productServiceImpl.getInstance().allCartProducts(result);  
			  cart2.put("id",1);
			  ArrayList<product> cartThings = cartProducts.getProducts();
			  JSONObject cartproduct = productObject(cartThings,cartProducts.getId());
			  cart.put("products",cartproduct);
			  cart2.put("userId",cartProducts.getUserId());
			  cart.put("cart",cart2);
			  response.getWriter().append(cart.toJSONString());
			  response.setStatus(HttpServletResponse.SC_OK);
	    	}
	    	else
	    	{
	    		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	    	}
	     }
	   }catch (IOException er) {
	        System.out.println(er.getMessage());
	    }
	    return null;	  
  }
  
public String removeFromtheCart()
  {
	    HttpServletResponse response = ServletActionContext.getResponse();
	    HttpServletRequest request = ServletActionContext.getRequest();
	    if(request.getMethod().equals("PATCH"))
	    {   
//    		JSONObject cart = new JSONObject();
    		
	    	int userid = Integer.parseInt(request.getParameter("userId").toString());
	    	int productId = Integer.parseInt(request.getParameter("productId").toString());
	    	int colorId = Integer.parseInt(request.getParameter("id").toString());
	    	
	    	int result = productServiceImpl.getInstance().removeFromCart(colorId,productId,userid);
	    	
	    	if(result != 0)
	    	{
				response.setStatus(HttpServletResponse.SC_OK);
	    	}
	    	else
	    	{
	    		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    	}
	    }
	    return null;
  }
  
  @SuppressWarnings("unchecked")
  public static JSONObject productObject(ArrayList<product> products,int cartId)
  {
      JSONArray productsArray = new JSONArray();
      JSONArray colorArray = new JSONArray();
      JSONObject main = new JSONObject();
      for (product productDetails: products) {
      	JSONObject pro = new JSONObject();
      	if(cartId != 0)
      	{
      		pro.put("cart",1);
      	}
          pro.put("id",productDetails.getId());
          pro.put("name", productDetails.getName());
          pro.put("description", productDetails.getDescription());
          price pr = productDetails.getPrice();
          JSONObject pro1 = new JSONObject();
          pro1.put("original",pr.getOriginal());
          pro1.put("current",pr.getCurrent());
          pro.put("price", pro1);
          JSONArray feature = new JSONArray();
          for(String eachFeature: productDetails.getFeatures())
          {
          	feature.add(eachFeature);
          }
          pro.put("features",feature); 
            List<color> col = productDetails.getColors();
            JSONArray col2 = new JSONArray();
            
            for(color colour : col)
            {   
              JSONObject pro2 = new JSONObject();
              pro2.put("id",colour.getId());
           	  if(cartId != 0)
           	  {
           		 pro2.put("cart",1);
              }
           	 
              pro2.put("product",productDetails.getId()); 
              pro2.put("color",colour.getColor());
              pro2.put("image",colour.getImage());	
              col2.add(pro2);
            }
            
            colorArray.add(col2);
          if(!productsArray.contains(pro))
          {
        	  productsArray.add(pro);  
          }
          main.put("colors",colorArray);
          main.put("products",productsArray);
      }
      return main;
  }
}

 