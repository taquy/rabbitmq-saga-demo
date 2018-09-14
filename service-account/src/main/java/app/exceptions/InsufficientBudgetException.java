package app.exceptions;

public class InsufficientBudgetException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public InsufficientBudgetException() {
		super("Not enough money to make payment");
	}
	
}
