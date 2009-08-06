package com.dotspots.rpcplus.client.jsonrpc.impl;

import com.dotspots.rpcplus.client.jsonrpc.CallDecoder;
import com.dotspots.rpcplus.client.jsonrpc.CallResponse;
import com.google.gwt.core.client.JavaScriptObject;

public class StandardCallDecoder implements CallDecoder {
	public enum Types {
		INT, STRING
	}

	public CallResponse decodeCall(JavaScriptObject callResponse) {
		return callResponse.cast();
	}
}
