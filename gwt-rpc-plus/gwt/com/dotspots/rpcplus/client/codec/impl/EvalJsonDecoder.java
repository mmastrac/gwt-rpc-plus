package com.dotspots.rpcplus.client.codec.impl;

import com.dotspots.rpcplus.client.codec.JsonParseException;
import com.dotspots.rpcplus.client.codec.LooseJsonDecoder;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Parses loose JSON using window.eval().
 */
public class EvalJsonDecoder implements LooseJsonDecoder {
	public EvalJsonDecoder() {
	}

	public JavaScriptObject decode(String json) throws JsonParseException {
		try {
			return eval0(json);
		} catch (Throwable t) {
			throw new JsonParseException(t);
		}
	}

	private static native JavaScriptObject eval0(String json) /*-{
		return eval(json);
	}-*/;
}
