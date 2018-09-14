package app.exceptions;

public class InsufficientSeatsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InsufficientSeatsException() {
		super("Not enough seats");
	}

}
