package models;

import java.util.List;

public class product {
  
	public int id;
	public String name;
	public String description;
	public price price;
	public String[] features;
	public List<color> colors;
	
	public product(int id, String name, String description, models.price price, String[] features, List<color> colors) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.features = features;
		this.colors = colors;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public price getPrice() {
		return price;
	}

	public void setPrice(price price) {
		this.price = price;
	}

	public String[] getFeatures() {
		return features;
	}

	public void setFeatures(String[] features) {
		this.features = features;
	}

	public List<color> getColors() {
		return colors;
	}

	public void setColors(List<color> colors) {
		this.colors = colors;
	}
	
    
	
	
}
