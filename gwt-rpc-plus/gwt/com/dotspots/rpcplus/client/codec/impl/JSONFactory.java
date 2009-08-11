package com.dotspots.rpcplus.client.codec.impl;

import com.dotspots.rpcplus.client.codec.JsonDecoder;
import com.dotspots.rpcplus.client.codec.JsonEncoder;
import com.dotspots.rpcplus.client.codec.LooseJsonDecoder;
import com.dotspots.rpcplus.client.codec.LooseJsonEncoder;
import com.google.gwt.core.client.JavaScriptObject;

public class JSONFactory {
	private final JavaScriptObject wnd;

	public JSONFactory() {
		this(getWindow());
	}

	public JSONFactory(JavaScriptObject wnd) {
		this.wnd = wnd;
	}

	public JsonDecoder createJSONDecoder() {
		if (NativeJson.isSupported(wnd)) {
			return new NativeJson(wnd);
		}

		return new FastJsonDecoder();
	}

	public LooseJsonDecoder createLooseJSONDecoder() {
		return new EvalJsonDecoder(wnd);
	}

	public JsonEncoder createJSONEncoder() {
		if (NativeJson.isSupported(wnd)) {
			return new NativeJson(wnd);
		}

		return new JSONObjectJsonEncoder();
	}

	public LooseJsonEncoder createLooseJSONEncoder() {
		if (NativeJson.isSupported(wnd)) {
			return new NativeJson(wnd);
		}
		if (UnevalJsonEncoder.isSupported(wnd)) {
			return new UnevalJsonEncoder(wnd);
		}

		return new JSONObjectJsonEncoder();
	}

	private native static JavaScriptObject getWindow() /*-{
		return $wnd;
	}-*/;
}
