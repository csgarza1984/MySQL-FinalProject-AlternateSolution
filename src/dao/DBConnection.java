package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DBConnection {
	
	private final static String URL = "jdbc:mysql://localhost:3306/library";
	private static String USERNAME;
	private static String PASSWORD;
	
	private static Connection connection;
	private static DBConnection instance;
	
	private static Scanner scanner = new Scanner(System.in);
	
	private DBConnection(Connection connection) {
		this.connection = connection;
	}
	
	public static Connection getConnection() {
		if (instance == null) {
			try {
				System.out.print("Enter user name:  ");
				USERNAME = scanner.nextLine();
				System.out.println("Enter password:  ");
				PASSWORD = scanner.nextLine();
				connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				instance = new DBConnection(connection);
				System.out.println("Connection successful.");
		} catch (SQLException e) {
				System.out.println("Error connecting to the database.");
				e.printStackTrace();
			}
		}
		return DBConnection.connection;
	}
	
}
