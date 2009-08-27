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
import com.dotspots.rpcplus.client.jscollections.JsRpcListString;
import com.dotspots.rpcplus.client.transport.impl.HttpTransport;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

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

	/**
	 * Tests the standard encoder/decoder for every platform, only allowing safe decoders.
	 */
	public void testJSONFactoryOnlySafe() throws JsonParseException {
		JSONFactory factory = new JSONFactory(false);
		run(factory.createJSONDecoder(), factory.createJSONEncoder());
	}

	/**
	 * Tests the loose encoder/decoder for every platform, only allowing safe decoders.
	 */
	public void testJSONFactoryLooseOnlySafe() throws JsonParseException {
		JSONFactory factory = new JSONFactory(false);
		try {
			factory.createLooseJSONDecoder();
			fail("This should have failed");
		} catch (Throwable t) {
			// Expected
		}

		// Not really worth testing anything at this point
	}

	public void testFastJsonDecoder() throws JsonParseException {
		FastJsonDecoder decoder = new FastJsonDecoder();
		JSONObjectJsonEncoder encoder = new JSONObjectJsonEncoder();

		run(decoder, encoder);
	}

	public void testEvalJsonDecoder() throws JsonParseException {
		EvalJsonDecoder decoder = new EvalJsonDecoder();
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
			EvalJsonDecoder decoder = new EvalJsonDecoder();

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
		testStringRoundtrip(decoder, encoder);
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

	/**
	 * Ensures that strings roundtrip properly with this pair of decoder/encoder.
	 */
	private void testStringRoundtrip(final JsonDecoder decoder, LooseJsonEncoder encoder) throws JsonParseException {
		HttpTransport transport = new HttpTransport();
		transport.setContentType("text/x-gwt-rpc; charset=utf-8");
		transport.setUrl(GWT.getModuleBaseURL() + "echo");

		String allCharacters = EveryCharacterStringUtility.getAllCharacterString();

		JsRpcListString list = JsRpcListString.create();
		list.add(allCharacters);

		final String encoded = encoder.encode(list);

		// for (char c : encoded.toCharArray()) {
		// System.out.print((int) c + " (0x" + Integer.toHexString(c) + ", '" + c + "')  ");
		// }
		// System.out.println();

		// First, check a round-trip client-side only
		System.out.println(encoded);
		JsRpcListString outputClientSide = decoder.decode(encoded).cast();
		EveryCharacterStringUtility.checkString(outputClientSide.get(0));

		// Now run it through the server and check that round-trip
		transport.call(encoded, new AsyncCallback<String>() {
			public void onSuccess(String result) {
				try {
					System.out.println(result);
					JsRpcListString output = decoder.decode(result).cast();
					EveryCharacterStringUtility.checkString(output.get(0));
					finishTest();
				} catch (JsonParseException e) {
					fail(e.toString());
					finishTest();
				}
			}

			public void onFailure(Throwable caught) {
				fail(caught.toString());
				finishTest();
			}
		});

		delayTestFinish(150000);
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
