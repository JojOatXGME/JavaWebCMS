package de.xgme.webcms;

import javax.servlet.http.HttpServletResponse;

public abstract class Response {
	final HttpServletResponse resp;

	Response(HttpServletResponse servlResponse) {
		this.resp = servlResponse;
	}

	/**
	 * Gets whether the response is already committed or not.
	 * A committed response have already send the status and header.
	 * @return true if the response is committed, false otherwise
	 */
	public boolean isCommitted() {
		return resp.isCommitted();
	}

}
