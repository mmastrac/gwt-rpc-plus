package com.dotspots.rpcplus.client.dom;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.IFrameElement;

/**
 * Helper JSO for representing the DOM window.
 * 
 * TODO: When GWT gets a JSO window, the superclass of this object will change.
 */
public final class RpcWindow extends JavaScriptObject {
	protected RpcWindow() {
	}

	/**
	 * Gets the current GWT window, aka $wnd.
	 */
	public static native RpcWindow get() /*-{
		return $wnd;
	}-*/;

	/**
	 * Gets a window from a given document.
	 */
	public static native RpcWindow fromDocument(Document document) /*-{
		return document.defaultView || document.parentWindow;
	}-*/;

	/**
	 * Gets a window from a given iframe.
	 */
	public static native RpcWindow fromIFrame(IFrameElement iframe) /*-{
		return iframe.contentWindow;
	}-*/;

	public native boolean isPostMessageSupported() /*-{
		return "postMessage" in this;
	}-*/;

	public native void postMessage(String message, String origin) /*-{
		this.postMessage(message, origin);
	}-*/;
}
