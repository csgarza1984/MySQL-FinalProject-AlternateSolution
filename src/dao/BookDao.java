package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Books;

public class BookDao {

	private Connection connection;
	private final String GET_BOOKS_QUERY = "SELECT * FROM books";
	private final String ADD_NEW_BOOK_QUERY = "INSERT INTO books (title, author, genre, status) VALUES(?, ?, ?, 'available')";
	private final String DELETE_BOOK_BY_ID = "DELETE FROM books WHERE id = ?";
	
	public BookDao() {
		connection = DBConnection.getConnection();
	}
	
	public List<Books> getBooks() throws SQLException {
		ResultSet rs = connection.prepareStatement(GET_BOOKS_QUERY).executeQuery();
		List<Books> books = new ArrayList<Books>();
		
		while (rs.next()) {
			books.add(populateBooks(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
		}
		return books;
	} //getBooks

	private Books populateBooks(int bookID, String title, String author, String genre, String status) {
		return new Books(bookID, title, author, genre, status);
	} //populateBooks
	
	public void createNewBook(String title, String author, String genre) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(ADD_NEW_BOOK_QUERY);
		ps.setString(1,title);
		ps.setString(2, author);
		ps.setString(3, genre);
		ps.executeUpdate();
	} //createNewBook
	
	public void deleteBookByID(int id) throws SQLException {
		//Specific book will be removed from table. Transactions related to it will also be deleted from transactions table
		//Okay to delete book regardless of status because books could have been lost by customer and never returned
		PreparedStatement ps = connection.prepareStatement(DELETE_BOOK_BY_ID);
		ps.setInt(1, id);
		ps.executeUpdate();
	} //deleteBookByID
	
} // class
