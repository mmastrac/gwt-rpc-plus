package com.dotspots.rpcplus.client.codec.impl;

import com.dotspots.rpcplus.client.codec.JsonEncoder;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONValue;

/**
 * Uses GWT's native JSON support to encode proper JSON.
 */
public class JSONObjectJsonEncoder implements JsonEncoder {
	public String encode(JavaScriptObject jso) {
		return getValue(jso).toString();
	}

	private native JSONValue getValue(JavaScriptObject v) /*-{
		var func = @com.google.gwt.json.client.JSONParser::typeMap[typeof v];
		return func ? func(v) : @com.google.gwt.json.client.JSONParser::throwUnknownTypeException(Ljava/lang/String;)(typeof v);
	}-*/;
}
