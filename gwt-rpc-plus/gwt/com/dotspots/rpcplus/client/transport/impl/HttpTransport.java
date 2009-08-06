package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.codec.JsonDecoder;
import com.dotspots.rpcplus.client.codec.LooseJsonEncoder;
import com.dotspots.rpcplus.client.jsonrpc.CallResponseProcessor;
import com.dotspots.rpcplus.client.transport.JsonTransport;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class HttpTransport implements JsonTransport {
	private JsonDecoder decoder;
	private LooseJsonEncoder encoder;
	private String url;

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDecoder(JsonDecoder decoder) {
		this.decoder = decoder;
	}

	public void setEncoder(LooseJsonEncoder encoder) {
		this.encoder = encoder;
	}

	public <T> void call(int methodId, JavaScriptObject arguments, AsyncCallback<?> callback, CallResponseProcessor callResponseProcessor) {
		RequestBuilder builder = new RequestBuilder(getMethod(), url);
		builder.setCallback(getCallback(methodId, callback, callResponseProcessor));
		builder.setRequestData(encoder.encode(arguments));
		builder.setHeader("Content-Type", "application/json");
		GWT.log("SEND: " + builder.getRequestData(), null);
		build(builder);

		try {
			builder.send();
		} catch (Throwable t) {
			callback.onFailure(t);
		}
	}

	protected <T> RequestCallback getCallback(final int methodId, final AsyncCallback<?> callback,
			final CallResponseProcessor callResponseProcessor) {
		return new RequestCallback() {
			public void onError(Request request, Throwable exception) {
				callback.onFailure(exception);
			}

			public void onResponseReceived(Request request, Response response) {
				final String text = response.getText();
				GWT.log("RECV: " + text, null);

				try {
					callResponseProcessor.onResponse(methodId, callback, decoder.decode(text));
				} catch (Throwable t) {
					callback.onFailure(t);
				}
			}
		};
	}

	protected void build(RequestBuilder builder) {
	}

	public Method getMethod() {
		return RequestBuilder.POST;
	}
}
