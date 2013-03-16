package de.xgme.webcms.plugin;

/**
 * Thrown when attempting to load an plug-in file which need a unknown plug-in.
 */
public class UnknownDependencyException extends Exception {
	private static final long serialVersionUID = 4289808296778849745L;

	/**
	 * Creates a new UnknownDependencyException.
	 */
	public UnknownDependencyException() {
		
	}

	/**
	 * Creates a new UnknownDependencyException with the specified detail message.
	 * @param message The detail message
	 */
	public UnknownDependencyException(final String message) {
		super(message);
	}

	/**
	 * Creates a new UnknownDependencyException with the specified cause.
	 * The detail message will be cause.toString().
	 * @param cause The cause of the exception
	 */
	public UnknownDependencyException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new UnknownDependencyException with the specified detail message and cause.
	 * @param message The detail message
	 * @param cause The cause of the exception
	 */
	public UnknownDependencyException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
