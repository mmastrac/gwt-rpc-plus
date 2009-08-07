package com.dotspots.rpcplus.client.jsonrpc.thrift;

import com.dotspots.rpcplus.client.jsonrpc.CallDecoder;
import com.dotspots.rpcplus.client.jsonrpc.CallEncoder;
import com.dotspots.rpcplus.client.jsonrpc.CallResponseProcessor;
import com.dotspots.rpcplus.client.transport.JsonTransport;
import com.dotspots.rpcplus.example.torturetest.client.TortureTestApi;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class ThriftClientStub implements CallResponseProcessor {
	private JsonTransport transport;
	private CallEncoder callEncoder;
	protected CallDecoder callDecoder;

	public void setTransport(JsonTransport transport) {
		this.transport = transport;
	}

	public void setCallEncoder(CallEncoder callEncoder) {
		this.callEncoder = callEncoder;
	}

	public void setCallDecoder(CallDecoder callDecoder) {
		this.callDecoder = callDecoder;
	}

	protected <T> void call(final int methodId, String call, JavaScriptObject args, JavaScriptObject requestContext,
			final AsyncCallback<T> callback, TortureTestApi tortureTestApi) {
		transport.call(callEncoder.encodeCall(call, args, requestContext), new AsyncCallback<JavaScriptObject>() {
			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}

			public void onSuccess(JavaScriptObject result) {
				onResponse(methodId, callback, result);
			}
		});
	}
}
