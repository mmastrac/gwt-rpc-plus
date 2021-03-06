package com.dotspots.rpcplus.client.transport.impl;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Represents the event sent via postMessage.
 */
public class PostMessageEvent extends JavaScriptObject {
	protected PostMessageEvent() {
	}

	public final native String getOrigin() /*-{
		return this.origin || '';
	}-*/;

	public final native String getData() /*-{
		return this.data || '';
	}-*/;

	/**
	 * Returns the source window.
	 */
	public final native JavaScriptObject getSource() /*-{
		return this.source;
	}-*/;
}
