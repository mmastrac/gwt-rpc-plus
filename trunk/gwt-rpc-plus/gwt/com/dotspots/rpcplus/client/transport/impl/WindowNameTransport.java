package com.dotspots.rpcplus.client.transport.impl;

import java.util.ArrayList;

import com.dotspots.rpcplus.client.transport.TextTransport;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Timer;
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

	private int callsInProgress = 0;

	private ArrayList<QueuedCall> queuedCalls = new ArrayList<QueuedCall>();

	private class QueuedCall {
		public QueuedCall(String arguments, AsyncCallback<String> callback) {
			this.arguments = arguments;
			this.callback = callback;
		}

		private String arguments;
		private AsyncCallback<String> callback;
	}

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

	private boolean canMakeThisCall() {
		// if (!isActiveXSupported()) {
		// return true;
		// }

		// return callsInProgress < 2;
		return true;
	}

	public void call(String arguments, final AsyncCallback<String> callback) {
		if (!canMakeThisCall()) {
			queuedCalls.add(new QueuedCall(arguments, callback));
		}

		WindowNameTransportRequest request = new WindowNameTransportRequest(arguments, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				cleanup();
				callback.onFailure(caught);
			}

			public void onSuccess(String result) {
				cleanup();
				callback.onSuccess(result);
			}
		}, document, url, timeout);
		request.start();
	}

	void cleanup() {
		callsInProgress--;

		if (queuedCalls.size() > 0) {
			final QueuedCall call = queuedCalls.remove(0);
			new Timer() {
				@Override
				public void run() {
					call(call.arguments, call.callback);
				}
			}.schedule(1);
		}
	}
}
