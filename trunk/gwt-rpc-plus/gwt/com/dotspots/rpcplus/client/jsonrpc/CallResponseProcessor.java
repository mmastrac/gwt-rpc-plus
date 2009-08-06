package com.dotspots.rpcplus.client.jsonrpc;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CallResponseProcessor {
	void onResponse(int methodId, AsyncCallback<?> callback, JavaScriptObject response);
}
