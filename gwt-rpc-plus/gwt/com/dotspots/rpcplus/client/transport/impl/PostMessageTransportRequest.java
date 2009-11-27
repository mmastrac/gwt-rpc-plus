package com.dotspots.rpcplus.client.transport.impl;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class PostMessageTransportRequest {
	private static final String MESSAGE_SEND_ID_PREFIX = "pmts:";
	private static final String MESSAGE_RECEIVE_ID_PREFIX = "pmtr:";

	private String name;

	private final PostMessageTransport postMessageTransport;

	private JavaScriptObject receiveHandle;

	private final AsyncCallback<String> callback;

	private final String arguments;

	public PostMessageTransportRequest(PostMessageTransport postMessageTransport, String arguments, AsyncCallback<String> callback) {
		this.postMessageTransport = postMessageTransport;
		this.arguments = arguments;
		this.callback = callback;
		name = UniqueRequestGenerator.createUniqueId();
	}

	public void start() {
		// Keep track of this request
		postMessageTransport.inflightRequests.set(name, this);

		receiveHandle = addReceivePostMessageHandler(postMessageTransport.receiveWindow);
		postMessage(postMessageTransport.sendWindow, MESSAGE_SEND_ID_PREFIX + name + ":" + arguments);
	}

	public void cancel() {
		removeReceivePostMessageHandler(postMessageTransport.receiveWindow, receiveHandle);
		postMessageTransport.inflightRequests.remove(name);
	}

	protected void onPostMessage(PostMessageEvent e) {
		if (isSameWindow(e.getSource(), postMessageTransport.sendWindow)) {
			String data = e.getData();
			if (data.startsWith(MESSAGE_RECEIVE_ID_PREFIX + name + ":")) {
				cancel();

				callback.onSuccess(data.substring(name.length()));
			}
		}
	}

	/**
	 * Compare two window references using == (instead of the default GWT ===), since some windows might be wrapped.
	 */
	private native boolean isSameWindow(JavaScriptObject window1, JavaScriptObject window2) /*-{
		return window1 == window2;
	}-*/;

	protected native JavaScriptObject postMessage(JavaScriptObject window, String message) /*-{
		window.postMessage(message, '*');
	}-*/;

	protected native JavaScriptObject addReceivePostMessageHandler(JavaScriptObject receiveWindow) /*-{
		var self = this;
		var handler = function(e) { self.@com.dotspots.rpcplus.client.transport.impl.PostMessageTransportRequest::onPostMessage(Lcom/dotspots/rpcplus/client/transport/impl/PostMessageEvent;)(e); };

		if (receiveWindow.addEventHandler)
		receiveWindow.addEventHandler('postmessage', handler, true);
		else
		receiveWindow.attachEvent('onpostmessage', handler);

		return handler;
	}-*/;

	protected native void removeReceivePostMessageHandler(JavaScriptObject receiveWindow, JavaScriptObject receiveHandle) /*-{
		if (receiveWindow.removeEventHandler)
		receiveWindow.removeEventHandler('postmessage', handler, true);
		else
		receiveWindow.detachEvent('onpostmessage', handler);

		receiveWindow.removeEventHandler('postmessage', receiveHandle);
	}-*/;
}
