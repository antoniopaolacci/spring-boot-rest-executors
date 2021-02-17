package it.example.app.rest.exceptions;

public class RestServiceCallException extends Exception {

	private static final long serialVersionUID = 7718828512143293558L;

	public RestServiceCallException() {
		super();
	}

	public RestServiceCallException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RestServiceCallException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestServiceCallException(String message) {
		super(message);
	}

	public RestServiceCallException(Throwable cause) {
		super(cause);
	}
	
}
