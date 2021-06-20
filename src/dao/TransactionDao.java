package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class TransactionDao {
	
	private Connection connection;
	private final String CHECKOUT_BOOK_QUERY = "INSERT INTO transactions (book_id, customer_id, action, transaction_date, due_date) VALUES(?, ?, 'CHECKOUT', DATE(NOW()), DATE_ADD(NOW(), INTERVAL +21 DAY))";
	private final String RENEW_BOOK_QUERY = "INSERT INTO transactions (book_id, customer_id, action, transaction_date, due_date) VALUES(?, ?, 'RENEW', DATE(NOW()), DATE_ADD(NOW(), INTERVAL +21 DAY))";
	private final String RETURN_BOOK_QUERY = "INSERT INTO transactions (book_id, customer_id, action, transaction_date, due_date) VALUES(?, ?, 'RETURN', DATE(NOW()), NULL)";
	private final String CHANGE_BOOK_STATUS_QUERY = "UPDATE books SET status = ? WHERE id = ?";
	public TransactionDao() {
		connection = DBConnection.getConnection();
	}


	public void checkoutBook(int bookID, int custID) throws SQLException {
		//create a new entry in the transaction log		
		PreparedStatement ps = connection.prepareStatement(CHECKOUT_BOOK_QUERY);
		ps.setInt(1, bookID);
		ps.setInt(2, custID);
		ps.executeUpdate();
		//change the book status to unavailable
		PreparedStatement ps2 = connection.prepareStatement(CHANGE_BOOK_STATUS_QUERY);
		ps2.setString(1, "unavailable");
		ps2.setInt(2, bookID);
		ps2.executeUpdate();
	} //checkoutBook


	public void renewBook(int bookID, int custID) throws SQLException {
		//create a new entry in the transaction log		
		PreparedStatement ps = connection.prepareStatement(RENEW_BOOK_QUERY);
		ps.setInt(1, bookID);
		ps.setInt(2, custID);
		ps.executeUpdate();
		//change the book status to unavailable
		PreparedStatement ps2 = connection.prepareStatement(CHANGE_BOOK_STATUS_QUERY);
		ps2.setString(1, "unavailable");
		ps2.setInt(2, bookID);
		ps2.executeUpdate();		
	}


	public void returnBook(int bookID, int custID) throws SQLException {
		//create a new entry in the transaction log		
		PreparedStatement ps = connection.prepareStatement(RETURN_BOOK_QUERY);
		ps.setInt(1, bookID);
		ps.setInt(2, custID);
		ps.executeUpdate();
		//change the book status to available
		PreparedStatement ps2 = connection.prepareStatement(CHANGE_BOOK_STATUS_QUERY);
		ps2.setString(1, "available");
		ps2.setInt(2, bookID);
		ps2.executeUpdate();				
		
	}

	
	
	
	
}
