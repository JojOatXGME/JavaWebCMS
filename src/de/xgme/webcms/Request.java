package de.xgme.webcms;

import javax.servlet.http.HttpServletRequest;

public class Request {
	private final HttpServletRequest req;

	Request(HttpServletRequest servlRequest) {
		this.req = servlRequest;
	}

}
