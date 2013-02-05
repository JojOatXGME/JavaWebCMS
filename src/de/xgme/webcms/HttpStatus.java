package de.xgme.webcms;

public enum HttpStatus {

	// --- Status Success (2xx) ---

	OK(200),

	CREATED(201),

	ACCEPTED(202),

	PARTIAL_INFORMATION(203),

	NO_RESPONSE(204),

	// --- Status Redirection (3xx) ---

	MOVED(301),

	FOUND(302),

	METHOD(303),

	NOT_MODIDIED(304),

	// --- Status Error (4xx, 5xx) ---

	BAD_REQUEST(400),

	UNAUTHORIZED(401),

	PAYMENT_REQUIRED(402),

	FORBIDDEN(403),

	NOT_FOUND(404),

	INTERNAL_ERROR(500),

	NOT_IMPLEMENTED(501),

	GATEWAY_TIMEOUT(503);

	public final int code;
	private HttpStatus(int sc) {
		this.code = sc;
	}
}
