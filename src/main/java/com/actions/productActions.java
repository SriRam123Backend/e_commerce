package com.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.google.gson.Gson;

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
        System.out.println(products);
        JSONObject productObject = new JSONObject();
        JSONArray productsArray = productObject(products,0); 

        productObject.put("products", productsArray);
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
		        Gson gson = new Gson();
		        String json = gson.toJson(cartProducts);
		        System.out.println(json);
			  cart2.put("id",cartProducts.getId());
			  ArrayList<product> cartThings = cartProducts.getProducts();
			  JSONArray cartproduct = productObject(cartThings,cartProducts.getId());
			  cart2.put("products",cartproduct);
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
  
  @SuppressWarnings("unchecked")
  public static JSONArray productObject(ArrayList<product> products,int cartId)
  {
      JSONArray productsArray = new JSONArray();
      for (product productDetails: products) {
      	JSONObject pro = new JSONObject();
      	if(cartId != 0)
      	{
      		pro.put("cart",cartId);
      	}
          pro.put("id", productDetails.getId());
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
              pro2.put("color",colour.getColor());
              pro2.put("image",colour.getImage());	
              col2.add(pro2);
          }
          pro.put("colors",col2);
          productsArray.add(pro);
      }
      return productsArray;
  }
}

 