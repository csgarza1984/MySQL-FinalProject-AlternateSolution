package application;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import dao.BookDao;
import dao.CustomerDao;
import dao.TransactionDao;
import entity.Books;

public class Menu {
	
	private BookDao bookDao = new BookDao();
	private CustomerDao customerDao = new CustomerDao();
	private TransactionDao transactionDao = new TransactionDao();
	private Scanner scanner = new Scanner(System.in);
	
	private List<String> options = Arrays.asList(
			"Display Books",
			"Add a Book",
			"Delete a Book",
			"Add a Customer",
			"Deactivate a Customer",
			"Check Out a Book",
			"Renew a Book",
			"Return a Book");
	
	public void start() {
		
		String selection = "";
		
		do {
			printMenu();
			selection = scanner.nextLine();
			if (selection.equals("-1")) {
				System.out.println("Application stopped.");
			} else {
				System.out.println("You chose " + selection);
			}
			
			try {
				if (selection.equals("1")) {
					displayBooks();
				} else if (selection.equals("2")) {
					addBook();
				} else if (selection.equals("3")) {
					deleteBook();
				} else if (selection.equals("4")) {
					addCustomer();
				} else if (selection.equals("5")) {
					deactivateCustomer();
				} else if (selection.equals("6")) {
					checkoutBook();
				} else if (selection.equals("7")) {
					renewBook();
				} else if (selection.equals("8")) {
					returnBook();
				} else if (selection.equals("-1")) {
					break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			System.out.println();
			System.out.println("PRESS ENTER TO DISPLAY MENU OR -1 TO STOP.");
			scanner.nextLine();
			
		} while (!selection.equals("-1"));

	} //start
	
	private void printMenu() {
		System.out.println("Enter the number of your selection OR PRESS -1 TO STOP:\n--------------------------");
		for (int i=0; i < options.size(); i++) {
			System.out.println(i + 1 + ") " + options.get(i));
		}
	} //printMenu
		
	private void displayBooks() throws SQLException {
		List<Books> books = bookDao.getBooks();
		for (Books book : books) {
			System.out.println("Book ID:  " + book.getBookID() + " / Title:  " + book.getTitle() + 
					" / Author:  " + book.getAuthor() + " / Genre:  " + book.getGenre() + " / Status:  " + book.getStatus());
		}
		
	} //displayBooks
	
	private void addBook() throws SQLException {
		System.out.print("Enter title:  ");
		String title = scanner.nextLine();
		System.out.print("Enter author's full name:  ");
		String author = scanner.nextLine();
		System.out.print("Enter genre:  ");
		String genre = scanner.nextLine();
		bookDao.createNewBook(title, author, genre);
	} //addBook
	
	private void deleteBook() throws SQLException {
		System.out.print("Enter the book ID to be deleted:  ");
		int id = Integer.parseInt(scanner.nextLine());
		bookDao.deleteBookByID(id);
	} //deleteBook
	
	private void addCustomer() throws SQLException {
//		customerDao.getCustomers();
		System.out.println("Enter first name:  ");
		String firstName = scanner.nextLine();
		System.out.println("Enter last name:  ");
		String lastName = scanner.nextLine();
		System.out.println("Enter phone as XXX-XXX-XXXX:  ");
		String phone = scanner.nextLine();
		System.out.println("Enter email addres:  ");
		String email = scanner.nextLine();
		customerDao.createNewCustomer(firstName, lastName, phone, email);
	} //addCustomer
	
	private void deactivateCustomer() throws SQLException {
		System.out.print("Enter phone number as XXX-XXX-XXXX");
		String phone = scanner.nextLine();
		customerDao.deactivateCustomer(phone);
	} //deactivateCustomer
	
	private void checkoutBook() throws SQLException {
		System.out.print("Enter the book ID to be checked out:  ");
		int bookID = Integer.parseInt(scanner.nextLine());
		System.out.print("Enter customer ID:  ");
		int custID = Integer.parseInt(scanner.nextLine());
		transactionDao.checkoutBook(bookID, custID);
	} //checkoutBook
	
	private void renewBook() throws SQLException {
		System.out.print("Enter the book ID to be renewed:  ");
		int bookID = Integer.parseInt(scanner.nextLine());
		System.out.print("Enter customer ID:  ");
		int custID = Integer.parseInt(scanner.nextLine());
		transactionDao.renewBook(bookID, custID);		
	} //renewBook

	private void returnBook() throws SQLException {
		System.out.print("Enter the book ID to be returned:  ");
		int bookID = Integer.parseInt(scanner.nextLine());
		System.out.print("Enter customer ID:  ");
		int custID = Integer.parseInt(scanner.nextLine());
		transactionDao.returnBook(bookID, custID);		
	} //returnBook

} //class
