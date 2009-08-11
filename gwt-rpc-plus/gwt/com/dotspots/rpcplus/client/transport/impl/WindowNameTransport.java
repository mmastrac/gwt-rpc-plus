package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.transport.TextTransport;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Assumes that someone is waiting on the end of the URL for a form post and will return JSON by assigning it to
 * window.name and redirecting back to us.
 * 
 * Supports navigation-click-sound-free operation on IE through ActiveXObject("htmlfile").
 */
public class WindowNameTransport implements TextTransport {
	private String url;
	private Document document;
	private int timeout = 30000;

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public void call(String arguments, final AsyncCallback<String> callback) {
		WindowNameTransportRequest request = new WindowNameTransportRequest(arguments, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}

			public void onSuccess(String result) {
				callback.onSuccess(result);
			}
		}, document, url, timeout);
		request.start();
	}
}
