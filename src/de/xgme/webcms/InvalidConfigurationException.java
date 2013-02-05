package de.xgme.webcms;

public class InvalidConfigurationException extends Exception {
	private static final long serialVersionUID = 6827340533882809428L;

	InvalidConfigurationException(String message) {
		super(message);
	}

	InvalidConfigurationException(Throwable cause) {
		super(cause);
	}

	InvalidConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

}
