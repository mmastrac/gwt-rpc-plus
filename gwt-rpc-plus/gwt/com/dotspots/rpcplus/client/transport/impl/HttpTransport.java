package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.transport.TextTransport;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class HttpTransport implements TextTransport {
	private String url;
	private String mimeType = DEFAULT_MIME_TYPE;

	public static final String GWT_MIME_TYPE = "text/x-gwt-rpc; charset=utf-8";
	public static final String JSON_MIME_TYPE = "application/json; charset=utf-8";
	public static final String DEFAULT_MIME_TYPE = "text/plain; charset=utf-8";

	public void setUrl(String url) {
		this.url = url;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void call(String arguments, AsyncCallback<String> callback) {
		RequestBuilder builder = new RequestBuilder(getMethod(), url);
		builder.setCallback(getCallback(callback));
		builder.setRequestData(arguments);
		TransportLogger.logSend(arguments);
		build(builder);

		try {
			builder.send();
		} catch (Throwable t) {
			callback.onFailure(t);
		}
	}

	protected <T> RequestCallback getCallback(final AsyncCallback<String> callback) {
		return new RequestCallback() {
			public void onError(Request request, Throwable exception) {
				callback.onFailure(exception);
			}

			public void onResponseReceived(Request request, Response response) {
				TransportLogger.logReceive(response.getText());
				callback.onSuccess(response.getText());
			}
		};
	}

	protected void build(RequestBuilder builder) {
		builder.setHeader("Content-Type", mimeType);
	}

	public Method getMethod() {
		return RequestBuilder.POST;
	}
}
