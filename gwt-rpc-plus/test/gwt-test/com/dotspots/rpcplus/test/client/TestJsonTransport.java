package com.dotspots.rpcplus.test.client;

import com.dotspots.rpcplus.client.codec.JsonDecoder;
import com.dotspots.rpcplus.client.codec.JsonEncoder;
import com.dotspots.rpcplus.client.codec.JsonParseException;
import com.dotspots.rpcplus.client.codec.LooseJsonDecoder;
import com.dotspots.rpcplus.client.codec.LooseJsonEncoder;
import com.dotspots.rpcplus.client.codec.impl.EvalJsonDecoder;
import com.dotspots.rpcplus.client.codec.impl.FastJsonDecoder;
import com.dotspots.rpcplus.client.codec.impl.JSONObjectJsonEncoder;
import com.dotspots.rpcplus.client.codec.impl.NativeJson;
import com.dotspots.rpcplus.client.codec.impl.UnevalJsonEncoder;
import com.dotspots.rpcplus.client.jscollections.JsRpcListLong;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;

public class TestJsonTransport extends GWTTestCase {
	public void testFastJsonDecoder() throws JsonParseException {
		FastJsonDecoder decoder = new FastJsonDecoder();
		JSONObjectJsonEncoder encoder = new JSONObjectJsonEncoder();

		run(decoder, encoder);
	}

	public void testEvalJsonDecoder() throws JsonParseException {
		EvalJsonDecoder decoder = new EvalJsonDecoder(getWindow());
		JSONObjectJsonEncoder encoder = new JSONObjectJsonEncoder();

		run((JsonDecoder) decoder, encoder);
	}

	public void testNativeJson() throws JsonParseException {
		if (NativeJson.isSupported(getWindow())) {
			NativeJson decoder = new NativeJson(getWindow());
			NativeJson encoder = new NativeJson(getWindow());

			run(decoder, encoder);
		}
	}

	public void testUnevalJsonEncoder() throws JsonParseException {
		if (UnevalJsonEncoder.isSupported(getWindow())) {
			UnevalJsonEncoder encoder = new UnevalJsonEncoder(getWindow());
			EvalJsonDecoder decoder = new EvalJsonDecoder(getWindow());

			run(decoder, encoder);
		}
	}

	private void run(LooseJsonDecoder decoder, LooseJsonEncoder encoder) throws JsonParseException {
		runInternal(decoder, encoder);
	}

	private void run(JsonDecoder decoder, JsonEncoder encoder) throws JsonParseException {
		runInternal(decoder, encoder);
	}

	private void runInternal(JsonDecoder decoder, LooseJsonEncoder encoder) throws JsonParseException {
		JsRpcListLong list = JsRpcListLong.create();
		list.add(0);
		list.add(0xffff0000ffff0000L);
		String text = encoder.encode(list);
		System.out.println(text);
		JsRpcListLong result = decoder.decode(text).cast();

		assertEquals(2, result.size());
		assertEquals(0L, list.get(0));
		assertEquals(0xffff0000ffff0000L, list.get(1));
	}

	private native JavaScriptObject getWindow() /*-{
		return $wnd;
	}-*/;

	@Override
	public String getModuleName() {
		return "com.dotspots.rpcplus.test.Test";
	}
}
