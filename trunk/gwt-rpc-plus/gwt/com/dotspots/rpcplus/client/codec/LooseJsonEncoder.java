package com.dotspots.rpcplus.client.codec;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Encodes a JavaScriptObject using "loose" JSON (ie: numbers as keys of objects
 * are not required to be quoted).
 */
public interface LooseJsonEncoder {
	String encode(JavaScriptObject jso);
}
