package com.dotspots.rpcplus.client.jsonrpc;

import com.google.gwt.core.client.JavaScriptObject;

public class BaseJsRpcObject extends JavaScriptObject {
	protected BaseJsRpcObject() {
	}

	/**
	 * Gets a raw field value.
	 */
	public final native JavaScriptObject getFieldValue(int id) /*-{
		return this[id];
	}-*/;
}
