package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.transport.TextTransport;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
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
	private String redirect;
	private RedirectType redirectType;

	private enum RedirectType {
		MANUAL, CLEAR_CACHE, FAVICON
	}

	public WindowNameTransport() {
		// By default, assume clear.cache.gif is safe
		redirectType = RedirectType.CLEAR_CACHE;
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
		JavaScriptObject window = getWindowFromDocument(document);
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

	private native JavaScriptObject getWindowFromDocument(Document doc) /*-{
		return doc.defaultView || doc.parentWindow;
	}-*/;

	private native String getWindowLocationPort(JavaScriptObject wnd) /*-{
		return wnd.location.port || null;
	}-*/;

	private native String getWindowLocationProtocol(JavaScriptObject wnd) /*-{
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
			// Try to figure out why this is busted
			System.out.println("favicon redirect: " + redirect);
			break;
		default:
			throw new RuntimeException();
		}

		WindowNameTransportRequest request = new WindowNameTransportRequest(arguments, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}

			public void onSuccess(String result) {
				callback.onSuccess(result);
			}
		}, document, url, timeout, redirect);
		request.start();
	}
}
