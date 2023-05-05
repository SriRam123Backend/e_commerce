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

		String query = "SELECT p.id,p.productName,p.description,pd.originalPrice,pd.currentPrice,c.id,c.colorName,i.src,f.features FROM productDetails p INNER JOIN priceDetailsOfProduct pd ON p.id = pd.productId INNER JOIN colorOftheProduct c ON p.id = c.productId INNER JOIN imageOftheProduct i ON p.id = i.productId INNER JOIN featuresOfProduct f ON p.id = f.productId";
		PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		rs = pstmt.executeQuery();
		return rs;
	}
	
	@Override
	public ResultSet productDetails(int productId, int colorId) throws SQLException {
		
		ResultSet rs = null;

		String query = "SELECT p.id,p.productName,p.description,pd.originalPrice,pd.currentPrice,c.id,c.colorName,i.src,f.features FROM productDetails p INNER JOIN priceDetailsOfProduct pd ON p.id = pd.productId INNER JOIN colorOftheProduct c ON p.id = c.productId INNER JOIN imageOftheProduct i ON p.id = i.productId INNER JOIN featuresOfProduct f ON p.id = f.productId where p.id = ? and c.id = ?";
		PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		pstmt.setInt(1, productId);
		pstmt.setInt(2, colorId);
		rs = pstmt.executeQuery();
		return rs;
	}
	
	@Override
	public int addtoCart(int productId,int userId,String colorName) throws SQLException {
       
		int result = 0;

		String query = "INSERT INTO cartDetails (productId,userId,colorId) SELECT ?, ? ,? WHERE NOT EXISTS (select * from cartDetails where productId = ? and userId = ? and colorId = ?)";
		String queryOne = "select * from colorOftheProduct where colorName = ?";
		PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		PreparedStatement pstmtOne = DbConnection.dbConnection.prepareStatement(queryOne);
		pstmtOne.setString(1,colorName);
		ResultSet rs = pstmtOne.executeQuery();
		if(rs.next())
		{
			pstmt.setInt(1,productId);
			pstmt.setInt(2,userId);
			pstmt.setInt(3, rs.getInt(1));
			pstmt.setInt(4,productId);
			pstmt.setInt(5,userId);
			pstmt.setInt(6, rs.getInt(1));
		}
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