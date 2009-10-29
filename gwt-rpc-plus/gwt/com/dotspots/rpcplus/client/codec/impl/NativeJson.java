package com.dotspots.rpcplus.client.codec.impl;

import com.dotspots.rpcplus.client.codec.JsonDecoder;
import com.dotspots.rpcplus.client.codec.JsonEncoder;
import com.dotspots.rpcplus.client.codec.JsonParseException;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Encodes and decodes JSON using window.JSON methods. Requires a native window to hook into.
 */
public class NativeJson implements JsonDecoder, JsonEncoder {
	private final JavaScriptObject wnd;

	public static native boolean isSupported(JavaScriptObject window) /*-{
		return ("JSON" in window) && ("stringify" in window.JSON) && ("parse" in window.JSON);
	}-*/;

	public NativeJson(JavaScriptObject wnd) {
		this.wnd = wnd;
	}

	public JavaScriptObject decode(String json) throws JsonParseException {
		try {
			return decodeNative(wnd, json);
		} catch (Throwable t) {
			throw new JsonParseException(t);
		}
	}

	private native void restoreToJSONMethods(JavaScriptObject window, JavaScriptObject savedToJSON) /*-{
		window.Object.prototype.toJSON = savedToJSON[0];
		window.Array.prototype.toJSON = savedToJSON[1];
		window.Number.prototype.toJSON = savedToJSON[2];
		window.Boolean.prototype.toJSON = savedToJSON[3];
		window.String.prototype.toJSON = savedToJSON[4];
	}-*/;

	/**
	 * Workaround prototype.js' incompatible toJSON method on Array.
	 * 
	 * http://yuilibrary.com/projects/yui2/ticket/2528561
	 */
	private native JavaScriptObject saveToJSONMethods(JavaScriptObject window) /*-{
		var saved = [window.Object.prototype.toJSON, window.Array.prototype.toJSON, window.Number.prototype.toJSON, window.Boolean.prototype.toJSON, window.String.prototype.toJSON];
		delete window.Object.prototype.toJSON;
		delete window.Array.prototype.toJSON;
		delete window.Number.prototype.toJSON;
		delete window.Boolean.prototype.toJSON;
		delete window.String.prototype.toJSON;
		return saved;
	}-*/;

	public String encode(JavaScriptObject jso) {
		JavaScriptObject savedToJSON = saveToJSONMethods(wnd);
		try {
			return TransportUnicodeCleaner.cleanUnicodeForTransport(encodeNative(wnd, jso));
		} finally {
			restoreToJSONMethods(wnd, savedToJSON);
		}
	}

	private static native String encodeNative(JavaScriptObject wnd, JavaScriptObject jso) /*-{
		return wnd.JSON.stringify(jso);
	}-*/;

	private static native JavaScriptObject decodeNative(JavaScriptObject wnd, String json) /*-{
		return wnd.JSON.parse(json);
	}-*/;
}
