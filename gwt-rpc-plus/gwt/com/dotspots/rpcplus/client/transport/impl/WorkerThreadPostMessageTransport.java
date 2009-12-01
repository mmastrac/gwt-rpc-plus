package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.jscollections.JsRpcMapString;
import com.dotspots.rpcplus.client.transport.TextTransport;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Creates a transport that sends message from a host page to a web worker, or from a web worker to a host page
 * (http://www.whatwg.org/specs/web-workers/current-work/).
 */
public class WorkerThreadPostMessageTransport implements TextTransport {
	protected JavaScriptObject worker;
	protected JsRpcMapString<WorkerThreadPostMessageTransportRequest> inflightRequests = JsRpcMapString.create();

	/**
	 * Creates a transport against 'self' (from Worker to host)
	 */
	public WorkerThreadPostMessageTransport() {
		this(getSelf0());
	}

	/**
	 * Creates a transport against a given worker.
	 */
	public WorkerThreadPostMessageTransport(JavaScriptObject worker) {
		this.worker = worker;
	}

	public void call(String arguments, AsyncCallback<String> callback) {
		WorkerThreadPostMessageTransportRequest transportRequest = new WorkerThreadPostMessageTransportRequest(this, arguments, callback);
		transportRequest.start();
	}

	/**
	 * Self is the global object in a worker context.
	 * 
	 * @return
	 */
	private static native JavaScriptObject getSelf0() /*-{
		return self;
	}-*/;

}
