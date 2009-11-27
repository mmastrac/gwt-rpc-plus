package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.jscollections.JsRpcMapString;
import com.dotspots.rpcplus.client.transport.TextTransport;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Sends a message to another window using the HTML5 postMessage method. Uses a map of inflight requests to coordinate
 * requests with responses.
 */
public class PostMessageTransport implements TextTransport {
	protected JsRpcMapString<PostMessageTransportRequest> inflightRequests = JsRpcMapString.create();

	protected JavaScriptObject sendWindow;
	protected JavaScriptObject receiveWindow;

	public static boolean isSupported(JavaScriptObject window) {
		return isSupported0(window);
	}

	private static native boolean isSupported0(JavaScriptObject window) /*-{
		return "postMessage" in window;
	}-*/;

	public void setSendWindow(JavaScriptObject sendWindow) {
		this.sendWindow = sendWindow;
	}

	public void setReceiveWindow(JavaScriptObject receiveWindow) {
		this.receiveWindow = receiveWindow;
	}

	public void call(String arguments, AsyncCallback<String> callback) {
		PostMessageTransportRequest transportRequest = new PostMessageTransportRequest(this, arguments, callback);
		transportRequest.start();
	}

}
