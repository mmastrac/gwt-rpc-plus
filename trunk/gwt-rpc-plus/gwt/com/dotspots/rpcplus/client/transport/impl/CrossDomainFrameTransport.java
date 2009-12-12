package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.dom.RpcWindow;
import com.dotspots.rpcplus.client.transport.HasDocument;
import com.dotspots.rpcplus.client.transport.HasUrlEndpoint;
import com.dotspots.rpcplus.client.transport.TextTransport;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Assumes that someone is waiting on the end of the URL for a form post and will return JSON by either assigning it to
 * window.name and redirecting back to us or sending us a postMessage.
 * 
 * Supports navigation-click-sound-free operation on IE through ActiveXObject("htmlfile").
 */
public class CrossDomainFrameTransport implements TextTransport, HasUrlEndpoint, HasDocument {
	private String url;
	private Document document;
	private int timeout = 30000;
	private String redirect;
	private RedirectType redirectType;
	private RpcWindow window;
	private boolean allowPostMessage = true;

	private enum RedirectType {
		MANUAL, CLEAR_CACHE, FAVICON
	}

	public CrossDomainFrameTransport() {
		// Favicon redirect is the most stable right now
		redirectType = RedirectType.CLEAR_CACHE;
	}

	protected void setAllowPostMessage(boolean allowPostMessage) {
		this.allowPostMessage = allowPostMessage;
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
		this.window = RpcWindow.fromDocument(document);
	}

	/**
	 * Sets the redirect URL to use the clear.cache.gif (found in most GWT applications).
	 */
	private String getRedirectToClearCacheGif() {
		return GWT.getModuleBaseURL() + "clear.cache.gif";
	}

	/**
	 * Sets the redirect URL to use a favicon based on document.domain.
	 */
	private String getRedirectToFavicon() {
		String url = getWindowLocationProtocol(window);

		url += "//" + document.getDomain();
		String port = getWindowLocationPort(window);
		if (port != null) {
			url += ":" + port;
		}

		url += "/favicon.ico";

		return url;
	}

	/**
	 * Manually set the redirect URL.
	 */
	public void setRedirect(String redirect) {
		this.redirect = redirect;
		redirectType = RedirectType.MANUAL;
	}

	/**
	 * Redirect to GWT's clear.cache.gif (the default)
	 */
	public void setRedirectClearCacheGif() {
		redirectType = RedirectType.CLEAR_CACHE;
	}

	/**
	 * Redirect to the domain's favicon.
	 */
	public void setRedirectFavicon() {
		redirectType = RedirectType.FAVICON;
	}

	private native String getWindowLocationPort(RpcWindow wnd) /*-{
		return wnd.location.port || null;
	}-*/;

	private native String getWindowLocationProtocol(RpcWindow wnd) /*-{
		return wnd.location.protocol;
	}-*/;

	public void call(String arguments, final AsyncCallback<String> callback) {
		String redirect;

		switch (redirectType) {
		case MANUAL:
			redirect = this.redirect;
			break;
		case CLEAR_CACHE:
			redirect = getRedirectToClearCacheGif();
			break;
		case FAVICON:
			redirect = getRedirectToFavicon();
			break;
		default:
			throw new RuntimeException();
		}

		CrossDomainFrameTransportRequest request;
		if (allowPostMessage && window.isPostMessageSupported()) {
			request = new PostMessageFrameTransportRequest(arguments, callback, document, url, timeout);
		} else {
			request = new WindowNameTransportRequest(arguments, callback, document, url, timeout, redirect);
		}

		request.start();
	}
}
