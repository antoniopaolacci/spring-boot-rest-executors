package it.example.app.exceptions;

public class RestResponseException extends Exception {

	private static final long serialVersionUID = 7718828512143293558L;

	public RestResponseException() {
		super();
	}

	public RestResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RestResponseException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestResponseException(String message) {
		super(message);
	}

	public RestResponseException(Throwable cause) {
		super(cause);
	}
	
}
