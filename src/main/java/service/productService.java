package service;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import models.cart;
import models.product;

public interface productService {
   
	 ArrayList<product> productDetails();
	 
	 cart allCartProducts(int userId);
	 
	 int addtoCart(JSONObject cartData);
}
