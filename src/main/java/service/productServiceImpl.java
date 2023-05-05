package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import database.productDBC;
import database.productDBCImpl;
import models.cart;
import models.color;
import models.price;
import models.product;

public class productServiceImpl implements productService{
	
	private static productServiceImpl product = null;

	private productServiceImpl() {

	}

	public static productServiceImpl getInstance() {
		if (product == null) {
			product = new productServiceImpl();
		}

		return product;
	}

	@Override
	public ArrayList<product> productDetails() {
        
		ArrayList<product> productDetail = new ArrayList<product>();
		productDBC dao = productDBCImpl.getInstance();
		try {
			ResultSet rs = dao.productDetails();     
			while(rs.next())
			{   
			    product newProduct = this.productDetails(rs);
			    productDetail.add(newProduct);
			}
			return productDetail;
		} catch (SQLException er) {
			System.out.println(er.getMessage());
		}
		return null;
	}

	@Override
	public cart allCartProducts(int userId) {
		
		ArrayList<product> cartProducts = new ArrayList<product>();
		productDBC dao = productDBCImpl.getInstance();
		int cartId = 0;
		try {
			ResultSet rs = dao.allCartProducts(userId);
			while(rs.next())
			{
				ResultSet rs1 = dao.productDetails(rs.getInt(2),rs.getInt(4)); 
				
				while(rs1.next())
				{   
				    product cartProduct = this.productDetails(rs1);
				    cartProducts.add(cartProduct);
				}
				cartId = rs.getInt(1);
			}
			return new cart(cartId,cartProducts,userId);
		} catch (SQLException err)
		{
			System.out.println("3"+err.getMessage());
		}
		
		return null;
	}

	@Override
	public int addtoCart(JSONObject cartData) {
		
		int result = 0;
		productDBC dao = productDBCImpl.getInstance();
		
		int productId = Integer.parseInt(cartData.get("productId").toString());
		int userId= Integer.parseInt(cartData.get("userId").toString());
		String colorName = cartData.get("color").toString();
		
		try {
			int rs = dao.addtoCart(productId,userId,colorName);  
			if(rs != 0)
			{
				result = userId;
			}
		} catch (SQLException err)
		{
			System.out.println("2"+ err.getMessage());
		}
		
		return result;
	}
	
	public product productDetails(ResultSet rs)
	{
		product newProduct = null;
		try {
			int productId = rs.getInt(1);
			String productName = rs.getString(2);
			String description = rs.getString(3);
		    float originalPrice = rs.getFloat(4);
		    float currentPrice =rs.getFloat(5);
		    int colorId =  rs.getInt(6);
		    String[] color = {rs.getString(7)};
		    String imgSrc = rs.getString(8);
		    String[] features = rs.getString(9).split("(?<=[.?!])\\s+");
            
		    price priceDetails = new price(originalPrice,currentPrice);
		    List<color> colorDetails = new ArrayList<color>();
		    for(String colorName : color)
		    {
		      color colorData = new color(colorId,colorName,imgSrc.replaceAll("colorName",colorName));
		      colorDetails.add(colorData);
		    }
		    
		     newProduct = new product(productId,productName,description,priceDetails,features,colorDetails);
		} catch (SQLException er)
		{
			System.out.println(er.getMessage());
		}
	 	
		return newProduct;
	}
	
}
