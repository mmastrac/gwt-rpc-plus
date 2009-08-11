package com.dotspots.rpcplus.test.client;

import com.dotspots.rpcplus.client.codec.JsonDecoder;
import com.dotspots.rpcplus.client.codec.JsonEncoder;
import com.dotspots.rpcplus.client.codec.JsonParseException;
import com.dotspots.rpcplus.client.codec.LooseJsonDecoder;
import com.dotspots.rpcplus.client.codec.LooseJsonEncoder;
import com.dotspots.rpcplus.client.codec.impl.EvalJsonDecoder;
import com.dotspots.rpcplus.client.codec.impl.FastJsonDecoder;
import com.dotspots.rpcplus.client.codec.impl.JSONFactory;
import com.dotspots.rpcplus.client.codec.impl.JSONObjectJsonEncoder;
import com.dotspots.rpcplus.client.codec.impl.NativeJson;
import com.dotspots.rpcplus.client.codec.impl.UnevalJsonEncoder;
import com.dotspots.rpcplus.client.jscollections.JsRpcListLong;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;

public class TestJsonTransport extends GWTTestCase {
	/**
	 * Tests the standard encoder/decoder for every platform.
	 */
	public void testJSONFactory() throws JsonParseException {
		JSONFactory factory = new JSONFactory();
		run(factory.createJSONDecoder(), factory.createJSONEncoder());
	}

	/**
	 * Tests the loose encoder/decoder for every platform.
	 */
	public void testJSONFactoryLoose() throws JsonParseException {
		JSONFactory factory = new JSONFactory();
		run(factory.createLooseJSONDecoder(), factory.createLooseJSONEncoder());
	}

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
		testListLong(decoder, encoder);
		testTypes(decoder);
	}

	/**
	 * Ensures that a list of longs is correctly round-tripped.
	 */
	private void testListLong(JsonDecoder decoder, LooseJsonEncoder encoder) throws JsonParseException {
		JsRpcListLong list = JsRpcListLong.create();
		list.add(0);
		list.add(0xffff0000ffff0000L);
		String text = encoder.encode(list);
		JsRpcListLong result = decoder.decode(text).cast();

		assertEquals(2, result.size());
		assertEquals(0L, list.get(0));
		assertEquals(0xffff0000ffff0000L, list.get(1));
	}

	/**
	 * Ensures that the the elements are decoded to the correct types.
	 */
	private void testTypes(JsonDecoder decoder) throws JsonParseException {
		final JavaScriptObject decoded = decoder.decode("[0,0,{}]");
		JavaScriptObject at0 = get(decoded, "0");
		assertEquals("number", typeof(at0).toLowerCase());
		JavaScriptObject at1 = get(decoded, "1");
		assertEquals("number", typeof(at1).toLowerCase());
		JavaScriptObject at2 = get(decoded, "2");
		assertEquals("object", typeof(at2).toLowerCase());
	}

	private native JavaScriptObject get(JavaScriptObject jso, String key) /*-{
		return [jso[key]];
	}-*/;

	private native String typeof(JavaScriptObject jso) /*-{
		return typeof jso[0];
	}-*/;

	private native JavaScriptObject getWindow() /*-{
		return $wnd;
	}-*/;

	@Override
	public String getModuleName() {
		return "com.dotspots.rpcplus.test.Test";
	}
}
