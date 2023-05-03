package models;

import java.util.ArrayList;

public class cart {
   
	public int id;
	public ArrayList<product> products;
	public int userId;
	
	public cart(int id, ArrayList<product> products, int userId) {
		super();
		this.id = id;
		this.products = products;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<product> products) {
		this.products = products;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	
	
}
