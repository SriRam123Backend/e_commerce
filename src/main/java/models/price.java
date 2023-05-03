package models;

public class price{
   
	public float original;
	public float current;
	
	public price(float original, float current) {
		super();
		this.original = original;
		this.current = current;
	}
	
	public float getOriginal() {
		return original;
	}
	public void setOriginal(float original) {
		this.original = original;
	}
	public float getCurrent() {
		return current;
	}
	public void setCurrent(float current) {
		this.current = current;
	}
	
    
	
	
}
