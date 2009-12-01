package com.dotspots.rpcplus.client.transport.impl;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class WorkerThreadPostMessageTransportRequest {
	private static final String MESSAGE_SEND_ID_PREFIX = "wpmts:";
	private static final String MESSAGE_RECEIVE_ID_PREFIX = "wpmtr:";

	private String name;

	private final WorkerThreadPostMessageTransport postMessageTransport;

	private final AsyncCallback<String> callback;

	private final String arguments;

	public WorkerThreadPostMessageTransportRequest(WorkerThreadPostMessageTransport postMessageTransport, String arguments,
			AsyncCallback<String> callback) {
		this.postMessageTransport = postMessageTransport;
		this.arguments = arguments;
		this.callback = callback;
		name = UniqueRequestGenerator.createUniqueId();
	}

	public void start() {
		// Keep track of this request
		postMessageTransport.inflightRequests.set(name, this);

		addReceivePostMessageHandler(postMessageTransport.worker);
		postMessage(postMessageTransport.worker, MESSAGE_SEND_ID_PREFIX + name + ":" + arguments);
	}

	public void cancel() {
		removeReceivePostMessageHandler(postMessageTransport.worker);
		postMessageTransport.inflightRequests.remove(name);
	}

	protected void onPostMessage(PostMessageEvent e) {
		String data = e.getData();
		if (data.startsWith(MESSAGE_RECEIVE_ID_PREFIX + name + ":")) {
			cancel();

			callback.onSuccess(data.substring(name.length()));
		}
	}

	protected native JavaScriptObject postMessage(JavaScriptObject worker, String message) /*-{
		worker.postMessage(message);
	}-*/;

	protected native void addReceivePostMessageHandler(JavaScriptObject worker) /*-{
		var self = this;
		worker.onmessage = function(e) { self.@com.dotspots.rpcplus.client.transport.impl.PostMessageTransportRequest::onPostMessage(Lcom/dotspots/rpcplus/client/transport/impl/PostMessageEvent;)(e); };
	}-*/;

	protected native void removeReceivePostMessageHandler(JavaScriptObject worker) /*-{
		delete worker.onmessage;
	}-*/;
}
