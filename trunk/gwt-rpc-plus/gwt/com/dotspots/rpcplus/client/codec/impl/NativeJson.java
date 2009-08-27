package com.dotspots.rpcplus.client.codec.impl;

import com.dotspots.rpcplus.client.codec.JsonDecoder;
import com.dotspots.rpcplus.client.codec.JsonEncoder;
import com.dotspots.rpcplus.client.codec.JsonParseException;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Encodes and decodes JSON using window.JSON methods.
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

	public String encode(JavaScriptObject jso) {
		return TransportUnicodeCleaner.cleanUnicodeForTransport(encodeNative(wnd, jso));
	}

	public static native String encodeNative(JavaScriptObject wnd, JavaScriptObject jso) /*-{
		return wnd.JSON.stringify(jso);
	}-*/;

	private static native JavaScriptObject decodeNative(JavaScriptObject wnd, String json) /*-{
		return wnd.JSON.parse(json);
	}-*/;
}
