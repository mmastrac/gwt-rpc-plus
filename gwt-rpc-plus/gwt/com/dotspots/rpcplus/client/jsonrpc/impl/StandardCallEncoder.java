package com.dotspots.rpcplus.client.jsonrpc.impl;

import com.dotspots.rpcplus.client.jsonrpc.CallEncoder;
import com.google.gwt.core.client.JavaScriptObject;

public class StandardCallEncoder implements CallEncoder {
	public native JavaScriptObject encodeCall(String call, JavaScriptObject args, JavaScriptObject context) /*-{
		return [0, call, context, args];
	}-*/;
}
