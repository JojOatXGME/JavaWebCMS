package de.xgme.webcms;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class PostResponse extends Response {

	PostResponse(HttpServletResponse servlResponse) {
		super(servlResponse);
	}

	/**
	 * Send the information to the client.
	 * This method should be called after handling the request.
	 * 
	 * @param status
	 *            The HTTP status.
	 *            In this situation you can use for example:
	 *            Success:
	 *            {@link HttpStatus#CREATED}
	 *            {@link HttpStatus#ACCEPTED}
	 *            {@link HttpStatus#NO_RESPONSE}
	 * @param url
	 *            The URL to the target page.
	 *            You should use the full URL like http://www.domain.org/.
	 * @throws IOException if an IO error occurred
	 */
	public void commit(HttpStatus status, String url) throws IOException {
		// TODO handle HttpStatus.CREATED in a special way
		// TODO commit informations
		resp.flushBuffer();
	}

}
