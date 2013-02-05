package de.xgme.webcms;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PostProcessingBase extends QueryProcessingBase {
	private final Request request;
	private final PostResponse response;

	PostProcessingBase(HttpServletRequest servlRequest, HttpServletResponse servlResponse, WebCMS cms) {
		super(cms);
		request = new Request(servlRequest);
		response = new PostResponse(servlResponse);
	}

	@Override
	public Request getRequest() {
		return request;
	}

	@Override
	public PostResponse getResponse() {
		return response;
	}

	@Override
	void finish() throws IOException {
		if (!response.isCommitted()) {
			// TODO write a warning in log
			getResponse().commit(HttpStatus.FOUND, "http://"); // TODO use an working path and sc
		}
		
		super.finish();
	}

}
