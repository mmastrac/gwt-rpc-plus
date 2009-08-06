package com.dotspots.rpcplus.client.codec;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Decodes a string to a JavaScriptObject using strict JSON rules.
 */
public interface JsonDecoder {
	JavaScriptObject decode(String json) throws JsonParseException;
}
