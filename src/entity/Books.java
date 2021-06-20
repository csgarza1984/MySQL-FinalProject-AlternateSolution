package entity;

public class Books {
	
	private int bookID;
	private String title;
	private String author;
	private String genre;
	private String status;
	
	public Books(int bookID, String title, String author, String genre, String status) {
		this.bookID = bookID;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.setStatus(status);
	} //Books constructor

	public int getBookID() {
		return bookID;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getGenre() {
		return genre;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
} // class
