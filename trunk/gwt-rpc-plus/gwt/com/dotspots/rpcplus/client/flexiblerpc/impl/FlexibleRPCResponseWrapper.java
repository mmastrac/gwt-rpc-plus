package com.dotspots.rpcplus.client.flexiblerpc.impl;

import com.google.gwt.http.client.Header;
import com.google.gwt.http.client.Response;

public class FlexibleRPCResponseWrapper extends Response {
	protected final int statusCode;
	protected final String text;

	public FlexibleRPCResponseWrapper(int statusCode, String text) {
		this.statusCode = statusCode;
		this.text = text;
	}

	@Override
	public String getHeader(String header) {
		return null;
	}

	@Override
	public Header[] getHeaders() {
		return null;
	}

	@Override
	public String getHeadersAsString() {
		return null;
	}

	@Override
	public int getStatusCode() {
		return statusCode;
	}

	@Override
	public String getStatusText() {
		return null;
	}

	@Override
	public String getText() {
		return text;
	}

}
