package com.dotspots.rpcplus.client.transport;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface JsonTransport {
	void call(JavaScriptObject arguments, AsyncCallback<JavaScriptObject> callback);
}
