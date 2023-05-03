package database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;

public class productDBCImpl implements productDBC{
  
	private static productDBCImpl product = null;

	private productDBCImpl() {

	}

	public static productDBCImpl getInstance() {
		if (product == null) {
			product = new productDBCImpl();
		}

		return product;
	}

	@Override
	public ResultSet productDetails() throws SQLException {
		
		ResultSet rs = null;

		String query = "SELECT p.id,p.productName,p.description,pd.originalPrice,pd.currentPrice,c.colorName,i.src,f.features FROM productDetails p INNER JOIN priceDetailsOfProduct pd ON p.id = pd.productId INNER JOIN colorOftheProduct c ON p.id = c.productId INNER JOIN imageOftheProduct i ON c.id = i.colorId INNER JOIN featuresOfProduct f ON p.id = f.productId";
		PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		rs = pstmt.executeQuery();
		return rs;
	}
	
	@Override
	public ResultSet productDetails(int productId) throws SQLException {
		
		ResultSet rs = null;

		String query = "SELECT p.id,p.productName,p.description,pd.originalPrice,pd.currentPrice,c.colorName,i.src,f.features FROM productDetails p INNER JOIN priceDetailsOfProduct pd ON p.id = pd.productId INNER JOIN colorOftheProduct c ON p.id = c.productId INNER JOIN imageOftheProduct i ON c.id = i.colorId INNER JOIN featuresOfProduct f ON p.id = f.productId where p.id = ?";
		PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		pstmt.setInt(1, productId);
		rs = pstmt.executeQuery();
		return rs;
	}
	
	@Override
	public int addtoCart(int productId,int userId) throws SQLException {
       
		int result = 0;

		String query = "insert into cartDetails(productId,userId) values (? ,?)";
		PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		pstmt.setInt(1,productId);
		pstmt.setInt(2,userId);
		result = pstmt.executeUpdate();
		
		return result;
	}

	@Override
	public ResultSet allCartProducts(int userId) throws SQLException {

		ResultSet rs = null;

		String query = "select * from cartDetails where userId = ?";
		PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		pstmt.setInt(1,userId);
		rs = pstmt.executeQuery();
		
		return rs;
	}
}