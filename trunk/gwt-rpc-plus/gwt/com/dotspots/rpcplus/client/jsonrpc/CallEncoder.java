package com.dotspots.rpcplus.client.jsonrpc;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Encodes a method call to the server into a JSON object that is suitable for sending over the transport.
 */
public interface CallEncoder {
	JavaScriptObject encodeCall(String call, JavaScriptObject args, JavaScriptObject context);
}
