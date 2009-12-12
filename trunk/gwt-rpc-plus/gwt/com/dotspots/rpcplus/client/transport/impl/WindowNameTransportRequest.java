package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.transport.TransportLogger;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Provides the window.name transport part of cross-domain frame requests.
 */
class WindowNameTransportRequest extends CrossDomainFrameTransportRequest {
	private final String redirect;

	private String responseName;

	public WindowNameTransportRequest(String arguments, final AsyncCallback<String> callback, Document document, String url, int timeout,
			String redirect) {
		super(arguments, callback, document, url, timeout);
		this.redirect = redirect;
	}

	@Override
	protected String getRequestType() {
		return "crossdomainframe";
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
			/*
			 * Defer removing the iframe - works around a Firefox bug where the throbber keeps spinning, similar to this
			 * GWT bug: (http://code.google.com/p/google-web-toolkit/issues/detail?id=916). The running flag is set to
			 * false to make sure we don't try to re-send the response.
			 */
			running = false;
			DeferredCommand.addCommand(new Command() {
				public void execute() {
					cancel();
				}
			});

			currentIframeName = currentIframeName.substring(responseName.length());
			TransportLogger.INSTANCE.logReceive(currentIframeName);

			try {
				callback.onSuccess(currentIframeName);
			} catch (Throwable t) {
				callback.onFailure(t);
			}
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