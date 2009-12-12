package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.dom.PostMessageEvent;
import com.dotspots.rpcplus.client.dom.RpcWindow;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Provides the postMessage part of cross-domain frame requests.
 */
public class PostMessageFrameTransportRequest extends CrossDomainFrameTransportRequest {
	private JavaScriptObject handler;
	private RpcWindow receiveWindow;
	private RpcWindow sendWindow;

	public PostMessageFrameTransportRequest(String arguments, AsyncCallback<String> callback, Document document, String url, int timeout) {
		super(arguments, callback, document, url, timeout);
		this.receiveWindow = RpcWindow.fromDocument(document);
	}

	@Override
	protected String getRequestType() {
		return "postmessage";
	}

	@Override
	protected void attachIFrameListeners() {
		handler = addReceivePostMessageHandler(receiveWindow);
	}

	@Override
	protected void detachIFrameListeners() {
		removeReceivePostMessageHandler(receiveWindow, handler);
	}

	@Override
	protected boolean isActiveXSupported() {
		return false;
	}

	protected void onMessage(PostMessageEvent e) {
		this.sendWindow = RpcWindow.fromIFrame(iframe);
		if (equals0(e.getSource(), sendWindow)) {
			final String data = e.getData();
			System.out.println(data);
			callback.onSuccess(data);
		}
	}

	private native boolean equals0(RpcWindow w1, RpcWindow w2) /*-{
		return w1 == w2;
	}-*/;

	protected native JavaScriptObject addReceivePostMessageHandler(RpcWindow receiveWindow) /*-{
		var self = this;
		var handler = function(e) { self.@com.dotspots.rpcplus.client.transport.impl.PostMessageFrameTransportRequest::onMessage(Lcom/dotspots/rpcplus/client/dom/PostMessageEvent;)(e); };

		if (receiveWindow.addEventListener)
		receiveWindow.addEventListener('message', handler, true);
		else
		receiveWindow.attachEvent('onmessage', handler);

		return handler;
	}-*/;

	protected native void removeReceivePostMessageHandler(RpcWindow receiveWindow, JavaScriptObject handler) /*-{
		if (receiveWindow.removeEventListener)
		receiveWindow.removeEventListener('message', handler, true);
		else
		receiveWindow.detachEvent('onmessage', handler);
	}-*/;
}
