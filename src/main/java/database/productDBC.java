package database;

import java.sql.ResultSet;
import java.sql.SQLException;



public interface productDBC {
 
	ResultSet productDetails() throws SQLException;
	
	int addtoCart(int productId,int userId,String colorName) throws SQLException;
	
	ResultSet allCartProducts(int userId) throws SQLException;
	
	ResultSet productDetails(int productId,int colorId) throws SQLException;
	
	int removefromCart(int productId,int userId,int colorId)throws SQLException;
}
