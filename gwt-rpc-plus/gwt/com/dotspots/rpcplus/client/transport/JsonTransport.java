package com.dotspots.rpcplus.client.transport;

import com.dotspots.rpcplus.client.jsonrpc.CallResponseProcessor;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface JsonTransport {
	<T> void call(int methodId, JavaScriptObject arguments, AsyncCallback<?> callback, CallResponseProcessor callResponseProcessor);
}
