package de.sprinteins.exception;

public class PlayTypeException extends RuntimeException {

	public PlayTypeException() {
		super();
	}

	public PlayTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PlayTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlayTypeException(String message) {
		super(message);
	}

	public PlayTypeException(Throwable cause) {
		super(cause);
	}

}
