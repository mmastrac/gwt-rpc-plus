package com.dotspots.rpcplus.client.jsonrpc;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Encapsules the two parts of the response we need: the response code and the response object.
 */
public class CallResponse<T extends BaseJsRpcObject> extends JavaScriptObject {
	protected CallResponse() {
	}

	public final native int getResponseVersion() /*-{
		return this[0];
	}-*/;

	public final native int getResponseCode() /*-{
		return this[1];
	}-*/;

	public final native T getResponseObject() /*-{
		return this[2];
	}-*/;

	public final native BaseJsRpcObject getResponseContext() /*-{
		return this[3];
	}-*/;
}
