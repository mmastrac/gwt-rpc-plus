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

	public void setUrl(String url) {
		this.url = url;
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
		builder.setHeader("Content-Type", "application/json");
	}

	public Method getMethod() {
		return RequestBuilder.POST;
	}
}
