package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.jscollections.JsRpcListString;
import com.dotspots.rpcplus.client.transport.HasWrappedTransport;
import com.dotspots.rpcplus.client.transport.JsonTransport;
import com.dotspots.rpcplus.client.transport.TextTransport;
import com.dotspots.rpcplus.client.transport.Transport;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class TextOverJsonTransport implements TextTransport, HasWrappedTransport {
	private JsonTransport jsonTransport;

	public TextOverJsonTransport() {
	}

	public TextOverJsonTransport(JsonTransport jsonTransport) {
		this.jsonTransport = jsonTransport;
	}

	public Transport getWrappedTransport() {
		return jsonTransport;
	}

	public void setJsonTransport(JsonTransport jsonTransport) {
		this.jsonTransport = jsonTransport;
	}

	public JsonTransport getJsonTransport() {
		return jsonTransport;
	}

	public void call(String arguments, final AsyncCallback<String> callback) {
		JsRpcListString call = JsRpcListString.create();
		call.add(arguments);
		jsonTransport.call(call, new AsyncCallback<JavaScriptObject>() {
			public void onSuccess(JavaScriptObject result) {
				callback.onSuccess(result.<JsRpcListString> cast().get(0));
			}

			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}
		});
	}
}
