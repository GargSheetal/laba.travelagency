package laba.travelagency.exceptions;

public class InputDoesNotMatchException extends Exception {

	private static final long serialVersionUID = -4898289933714604157L;

	public InputDoesNotMatchException() {
		super();
	}

	public InputDoesNotMatchException(String message) {
		super(message);
	}
	
}
