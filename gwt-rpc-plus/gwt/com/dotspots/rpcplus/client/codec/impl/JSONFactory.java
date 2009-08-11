package com.dotspots.rpcplus.client.codec.impl;

import com.dotspots.rpcplus.client.codec.JsonDecoder;
import com.dotspots.rpcplus.client.codec.JsonEncoder;
import com.dotspots.rpcplus.client.codec.LooseJsonDecoder;
import com.dotspots.rpcplus.client.codec.LooseJsonEncoder;
import com.google.gwt.core.client.JavaScriptObject;

public class JSONFactory {
	private final JavaScriptObject wnd;
	private final boolean allowUnsafe;

	public JSONFactory() {
		this(getWindow());
	}

	public JSONFactory(JavaScriptObject wnd) {
		this(wnd, true);
	}

	public JSONFactory(boolean allowUnsafe) {
		this(getWindow(), allowUnsafe);
	}

	public JSONFactory(JavaScriptObject wnd, boolean allowUnsafe) {
		this.wnd = wnd;
		this.allowUnsafe = allowUnsafe;
	}

	public JsonDecoder createJSONDecoder() {
		if (NativeJson.isSupported(wnd)) {
			return new NativeJson(wnd);
		}

		if (allowUnsafe) {
			return new EvalJsonDecoder(wnd);
		} else {
			return new FastJsonDecoder();
		}
	}

	public LooseJsonDecoder createLooseJSONDecoder() {
		if (!allowUnsafe) {
			throw new RuntimeException("Unable to create a safe LooseJsonDecoder");
		}

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
