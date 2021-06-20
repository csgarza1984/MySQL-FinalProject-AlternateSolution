package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Customers;

public class CustomerDao {
	
	private Connection connection;
//	private final String LIST_CUSTOMERS_QUERY = "SELECT * FROM customers";
	private final String CREATE_NEW_CUSTOMER_QUERY = "INSERT INTO customers (first_name, last_name, email, phone, isactive) VALUES(?, ?, ?, ?, true)";
	private final String FIND_CUSTOMERID_BY_PHONE_QUERY = "SELECT * FROM customers where phone = ?";
	private final String DEACTIVATE_CUSTOMER_BY_PHONE = "UPDATE customers SET isactive = false WHERE id = ?";
	
	public CustomerDao() {
		connection = DBConnection.getConnection();
	}

//	public List<Customers> getCustomers() throws SQLException {
//		ResultSet rs = connection.prepareStatement(LIST_CUSTOMERS_QUERY).executeQuery();
//		List<Customers> customers = new ArrayList<Customers>();
//		
//		while (rs.next()) {
//			customers.add(populateCustomers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6)));
//		}
//		return customers;
//	}
//
//	private Customers populateCustomers(int custID, String firstName, String lastName, String phone, String email,
//			Boolean isActive) {
//		return new Customers(custID, firstName, lastName, phone, email, isActive);
//	} //populateCustomers
	
	public List<Customers> getCustomerInfoUsingPhone(String phone) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(FIND_CUSTOMERID_BY_PHONE_QUERY);
		ps.setString(1, phone);
		ResultSet rs = ps.executeQuery();
		 
		List<Customers> customer = new ArrayList<Customers>();
		while(rs.next()) {
			customer.add(new Customers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6)));
		}
		return customer;
	}
	
	public void createNewCustomer(String firstName, String lastName, String phone, String email) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(CREATE_NEW_CUSTOMER_QUERY);
		ps.setString(1, firstName);
		ps.setString(2, lastName);
		ps.setString(3, phone);
		ps.setString(4, email);
		ps.executeUpdate();
	} //createNewCustomer
	
	public void deactivateCustomer(String phone) throws SQLException {
		//Find customer id by given phone
		List<Customers> customer = getCustomerInfoUsingPhone(phone);
		System.out.println("Customer ID:  " + customer.get(0).getCustomerId() + " / First Name:  " + 
				customer.get(0).getFirstName() + " / Phone:  " + customer.get(0).getPhone());
						
		//Change the active status of the customer to inactive, rather than delete
		PreparedStatement ps = connection.prepareStatement(DEACTIVATE_CUSTOMER_BY_PHONE);
		ps.setInt(1, customer.get(0).getCustomerId());
		ps.executeUpdate();
		System.out.println(customer.get(0).getFirstName() + " " + customer.get(0).getLastName() + " deactivated successfully!");
	}
	
	
	
} //class
