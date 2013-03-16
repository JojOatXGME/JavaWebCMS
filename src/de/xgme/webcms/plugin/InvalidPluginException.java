package de.xgme.webcms.plugin;

/**
 * Thrown to indicate that the description file of a plug-in is not valid.
 */
public class InvalidPluginException extends Exception {
	private static final long serialVersionUID = 1632846825655943080L;

	/**
	 * Creates a new InvalidPluginException with null as its detail message.
	 */
	public InvalidPluginException() {
		
	}

	/**
	 * Creates a new InvalidPluginException with the specified detail message.
	 * @param message The detail message
	 */
	public InvalidPluginException(final String message) {
		super(message);
	}

	/**
	 * Creates a new InvalidPluginException with the specified cause and a detail message of cause.toString().
	 * @param cause The cause why the exception is thrown
	 */
	public InvalidPluginException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new InvalidPluginException with the specified detail message and cause.
	 * @param message The detail message
	 * @param cause The cause why the exception is thrown
	 */
	public InvalidPluginException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
