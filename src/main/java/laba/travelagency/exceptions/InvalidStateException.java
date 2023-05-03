package laba.travelagency.exceptions;

public class InvalidStateException extends Exception {

	private static final long serialVersionUID = 7011369845429801723L;

	public InvalidStateException() {
		super();
	}

	public InvalidStateException(String message) {
		super(message);
	}
	
}