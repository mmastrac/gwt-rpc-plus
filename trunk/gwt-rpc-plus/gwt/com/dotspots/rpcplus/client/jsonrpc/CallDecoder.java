package com.dotspots.rpcplus.client.jsonrpc;

import com.google.gwt.core.client.JavaScriptObject;

public interface CallDecoder {
	public CallResponse<?> decodeCall(JavaScriptObject response);
}
