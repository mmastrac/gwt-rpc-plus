package com.dotspots.rpcplus.client.transport;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TextTransport {
	<T> void call(String arguments, AsyncCallback<String> callback);
}
