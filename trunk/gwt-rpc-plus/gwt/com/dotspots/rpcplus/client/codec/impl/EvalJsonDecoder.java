package com.dotspots.rpcplus.client.codec.impl;

import com.dotspots.rpcplus.client.codec.JsonParseException;
import com.dotspots.rpcplus.client.codec.LooseJsonDecoder;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Parses loose JSON using window.eval().
 */
public class EvalJsonDecoder implements LooseJsonDecoder {
	private final JavaScriptObject wnd;

	public EvalJsonDecoder(JavaScriptObject wnd) {
		this.wnd = wnd;
	}

	public JavaScriptObject decode(String json) throws JsonParseException {
		try {
			return eval(wnd, json);
		} catch (Throwable t) {
			throw new JsonParseException(t);
		}
	}

	private static native JavaScriptObject eval(JavaScriptObject wnd, String json) /*-{
		return wnd.eval(json);
	}-*/;
}
