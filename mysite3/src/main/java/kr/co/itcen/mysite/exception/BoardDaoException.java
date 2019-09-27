package kr.co.itcen.mysite.exception;

public class BoardDaoException extends RuntimeException {
	public BoardDaoException() {
		super("GuestBookDaoException Occurs");
	}
	
	public BoardDaoException(String message) {
		super(message);
	}

}
