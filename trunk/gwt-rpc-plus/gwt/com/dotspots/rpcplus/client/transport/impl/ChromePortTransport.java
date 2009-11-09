package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.transport.HasName;
import com.dotspots.rpcplus.client.transport.JsonTransport;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Implements a JSON transport over Chrome extension ports.
 */
public class ChromePortTransport implements JsonTransport, HasName {
	private String extensionId;
	private String name;

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}

	public String getExtensionId() {
		return extensionId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	private static final class Port extends JavaScriptObject {
		protected Port() {
		}

		public native void postMessage(JavaScriptObject msg) /*-{
			this.postMessage(msg);
		}-*/;

		public native void setOnMessageListener(AsyncCallback<JavaScriptObject> callback) /*-{
			var port = this;
			this.onMessage.addListener(function(response) {
			callback.@com.google.gwt.user.client.rpc.AsyncCallback::onSuccess(Ljava/lang/Object;)(response);
			port.disconnect();
			});
		}-*/;
	}

	public void call(JavaScriptObject arguments, AsyncCallback<JavaScriptObject> callback) {
		Port port = connect0(extensionId, name);
		port.setOnMessageListener(callback);
		port.postMessage(arguments);
	}

	private native Port connect0(String extensionId, String name) /*-{
		var args = [];
		if (extensionId) {
		args.push(extensionId);
		}
		if (name) {
		args.push({name:name});
		}
		return chrome.extension.connect.apply(null, args);
	}-*/;
}
