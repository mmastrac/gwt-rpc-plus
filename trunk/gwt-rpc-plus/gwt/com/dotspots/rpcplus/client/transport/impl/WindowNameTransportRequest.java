package com.dotspots.rpcplus.client.transport.impl;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Provides the window.name transport part of cross-domain frame requests.
 */
class WindowNameTransportRequest extends CrossDomainFrameTransportRequest {
	private final String redirect;

	public WindowNameTransportRequest(String arguments, final AsyncCallback<String> callback, Document document, String url, int timeout,
			String redirect) {
		super(arguments, callback, document, url, timeout);
		this.redirect = redirect;
	}

	@Override
	protected String getRequestType() {
		return "window.name";
	}

	@Override
	protected void populateForm() {
		super.populateForm();
		appendHiddenInput("redirect", redirect);
	}

	@Override
	protected void attachIFrameListeners() {
		attachIFrameListener(iframe, this);
	}

	@Override
	protected void detachIFrameListeners() {
		detachIFrameListener(iframe);
	}

	@SuppressWarnings("unused")
	private void check() {
		if (!running) {
			return;
		}

		String currentIframeName = getIFrameContentWindowName(iframe);
		if (currentIframeName != null && !currentIframeName.equals(iframeName) && currentIframeName.startsWith(responseName)) {
			currentIframeName = currentIframeName.substring(responseName.length());
			receive(currentIframeName);
		}
	}

	private static native void attachIFrameListener(IFrameElement iframe, WindowNameTransportRequest self) /*-{
		iframe.onload = function() { self.@com.dotspots.rpcplus.client.transport.impl.WindowNameTransportRequest::check()(); };
		iframe.onerror = function() { self.@com.dotspots.rpcplus.client.transport.impl.WindowNameTransportRequest::error()(); };
		iframe.onreadystatechange = function() { self.@com.dotspots.rpcplus.client.transport.impl.WindowNameTransportRequest::check()(); };
	}-*/;

	private static native void detachIFrameListener(IFrameElement iframe) /*-{
		iframe.onload = function() {};
		iframe.onerror = function() {};
		iframe.onreadystatechange = function() {};
	}-*/;

	private static native String getIFrameContentWindowName(IFrameElement iframe) /*-{
		try {
		return iframe.contentWindow.name || null;
		} catch (e) {
		// Probably a permission error
		return null;
		}
	}-*/;
}
