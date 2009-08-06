package com.dotspots.rpcplus.client.codec.impl;

import com.dotspots.rpcplus.client.codec.LooseJsonEncoder;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Creates loose JSON encoder through a mozilla-specific uneval() method.
 */
public class UnevalJsonEncoder implements LooseJsonEncoder {
	private JavaScriptObject wnd;

	public static native boolean isSupported(JavaScriptObject window) /*-{
		return "uneval" in window;
	}-*/;

	public UnevalJsonEncoder(JavaScriptObject wnd) {
		this.wnd = wnd;
	}

	public String encode(JavaScriptObject jso) {
		return uneval(wnd, jso);
	}

	private static native String uneval(JavaScriptObject wnd, JavaScriptObject jso) /*-{
		return wnd.uneval(jso);
	}-*/;
}
