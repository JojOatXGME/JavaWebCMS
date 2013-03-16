package de.xgme.webcms.plugin;

/**
 * Thrown to indicate that the description file of a plug-in is not valid.
 */
public class InvalidDescriptionException extends InvalidPluginException {
	private static final long serialVersionUID = 581395412612679190L;

	/**
	 * Constructs a new InvalidDescriptionException with null as its detail message.
	 */
	public InvalidDescriptionException() {
		
	}

	/**
	 * Constructs a new InvalidPluginException with the specified detail message.
	 * @param message The detail message
	 */
	public InvalidDescriptionException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new InvalidPluginException with the specified detail message and cause.
	 * @param message The detail message
	 * @param cause The cause why the exception is thrown
	 */
	public InvalidDescriptionException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new InvalidPluginException with the specified cause and a detail message of cause.toString().
	 * @param cause The cause why the exception is thrown
	 */
	public InvalidDescriptionException(final Throwable cause) {
		super(cause);
	}

}
