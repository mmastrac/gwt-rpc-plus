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
	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();
		addObjectPrototypeBadness(getWindow());
	}

	@Override
	protected void gwtTearDown() throws Exception {
		super.gwtTearDown();
		removeObjectPrototypeBadness(getWindow());
	}

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

	/**
	 * Some sites use an ancient version of Crockford's JSON script that breaks on [1,,1].
	 */
	public void testNativeJsonWithBustedStringifier() throws JsonParseException {
		try {
			addBustedJsonArrayStringifier();

			if (NativeJson.isSupported(getWindow())) {
				NativeJson decoder = new NativeJson(getWindow());
				NativeJson encoder = new NativeJson(getWindow());

				run(decoder, encoder);
			}
		} finally {
			removeBustedJsonArrayStringifier();
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

		// Test a list with a hole in it
		list.set(3, 2);
		text = encoder.encode(list);
		result = decoder.decode(text).cast();

		assertEquals(4, result.size());
		assertEquals(0L, list.get(0));
		assertEquals(0xffff0000ffff0000L, list.get(1));
		assertEquals(0L, list.get(2));
		assertEquals(2, list.get(3));
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
		JsRpcListString outputClientSide = decoder.decode(encoded).cast();
		EveryCharacterStringUtility.checkString(outputClientSide.get(0));

		// Now run it through the server and check that round-trip
		transport.call(encoded, new AsyncCallback<String>() {
			public void onSuccess(String result) {
				try {
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

	private native void removeBustedJsonArrayStringifier() /*-{
		if ($wnd.__oldJSON) {
		delete $wnd.__oldJSON;
		} else {
		$wnd.JSON = $wnd.__oldJSON;
		}
	}-*/;

	// JSON code from http://i.usatoday.net/_common/_scripts/_community/directapi/json.js
	// Google Plugin For Eclipse is eating the formatting here.
	private native void addBustedJsonArrayStringifier() /*-{
		$wnd.__oldJSON = $wnd.JSON;
		$wnd.JSON = function () {
		var m = {
		'\b': '\\b',
		'\t': '\\t',
		'\n': '\\n',
		'\f': '\\f',
		'\r': '\\r',
		'"' : '\\"',
		'\\': '\\\\'
		},
		s = {
		'boolean': function (x) {
		return String(x);
		},
		number: function (x) {
		return isFinite(x) ? String(x) : 'null';
		},
		string: function (x) {
		if (/["\\\x00-\x1f]/.test(x)) {
		x = x.replace(/([\x00-\x1f\\"])/g, function(a, b) {
		var c = m[b];
		if (c) {
		return c;
		}
		c = b.charCodeAt();
		return '\\u00' +
		Math.floor(c / 16).toString(16) +
		(c % 16).toString(16);
		});
		}
		return '"' + x + '"';
		},
		object: function (x) {
		if (x) {
		var a = [], b, f, i, l, v;
		if (x instanceof Array) {
		a[0] = '[';
		l = x.length;
		for (i = 0; i < l; i += 1) {
		v = x[i];
		f = s[typeof v];
		if (f) {
		v = f(v);
		if (typeof v == 'string') {
		if (b) {
		a[a.length] = ',';
		}
		a[a.length] = v;
		b = true;
		}
		}
		}
		a[a.length] = ']';
		} else if (x instanceof Object) {
		a[0] = '{';
		for (i in x) {
		v = x[i];
		f = s[typeof v];
		if (f) {
		v = f(v);
		if (typeof v == 'string') {
		if (b) {
		a[a.length] = ',';
		}
		a.push(s.string(i), ':', v);
		b = true;
		}
		}
		}
		a[a.length] = '}';
		} else {
		return;
		}
		return a.join('');
		}
		return 'null';
		}
		};
		return {
		copyright: '(c)2005 JSON.org',
		license: 'http://www.crockford.com/JSON/license.html',
		stringify: function (v) {
		var f = s[typeof v];
		if (f) {
		v = f(v);
		if (typeof v == 'string') {
		return v;
		}
		}
		return null;
		},
		eval: function (text) {
		try {
		return !(/[^,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]/.test(
		text.replace(/"(\\.|[^"\\])*"/g, ''))) &&
		eval('(' + text + ')');
		} catch (e) {
		return false;
		}
		},

		parse: function (text) {
		var at = 0;
		var ch = ' ';

		function error(m) {
		throw {
		name: 'JSONError',
		message: m,
		at: at - 1,
		text: text
		};
		}

		function next() {
		ch = text.charAt(at);
		at += 1;
		return ch;
		}

		function white() {
		while (ch) {
		if (ch <= ' ') {
		next();
		} else if (ch == '/') {
		switch (next()) {
		case '/':
		while (next() && ch != '\n' && ch != '\r') {}
		break;
		case '*':
		next();
		for (;;) {
		if (ch) {
		if (ch == '*') {
		if (next() == '/') {
		next();
		break;
		}
		} else {
		next();
		}
		} else {
		error("Unterminated comment");
		}
		}
		break;
		default:
		error("Syntax error");
		}
		} else {
		break;
		}
		}
		}

		function string() {
		var i, s = '', t, u;

		if (ch == '"') {
		outer:          while (next()) {
		if (ch == '"') {
		next();
		return s;
		} else if (ch == '\\') {
		switch (next()) {
		case 'b':
		s += '\b';
		break;
		case 'f':
		s += '\f';
		break;
		case 'n':
		s += '\n';
		break;
		case 'r':
		s += '\r';
		break;
		case 't':
		s += '\t';
		break;
		case 'u':
		u = 0;
		for (i = 0; i < 4; i += 1) {
		t = parseInt(next(), 16);
		if (!isFinite(t)) {
		break outer;
		}
		u = u * 16 + t;
		}
		s += String.fromCharCode(u);
		break;
		default:
		s += ch;
		}
		} else {
		s += ch;
		}
		}
		}
		error("Bad string");
		}

		function array() {
		var a = [];

		if (ch == '[') {
		next();
		white();
		if (ch == ']') {
		next();
		return a;
		}
		while (ch) {
		a.push(value());
		white();
		if (ch == ']') {
		next();
		return a;
		} else if (ch != ',') {
		break;
		}
		next();
		white();
		}
		}
		error("Bad array");
		}

		function object() {
		var k, o = {};

		if (ch == '{') {
		next();
		white();
		if (ch == '}') {
		next();
		return o;
		}
		while (ch) {
		k = string();
		white();
		if (ch != ':') {
		break;
		}
		next();
		o[k] = value();
		white();
		if (ch == '}') {
		next();
		return o;
		} else if (ch != ',') {
		break;
		}
		next();
		white();
		}
		}
		error("Bad object");
		}

		function number() {
		var n = '', v;
		if (ch == '-') {
		n = '-';
		next();
		}
		while (ch >= '0' && ch <= '9') {
		n += ch;
		next();
		}
		if (ch == '.') {
		n += '.';
		while (next() && ch >= '0' && ch <= '9') {
		n += ch;
		}
		}
		if (ch == 'e' || ch == 'E') {
		n += 'e';
		next();
		if (ch == '-' || ch == '+') {
		n += ch;
		next();
		}
		while (ch >= '0' && ch <= '9') {
		n += ch;
		next();
		}
		}
		v = +n;
		if (!isFinite(v)) {
		////error("Bad number");
		} else {
		return v;
		}
		}

		function word() {
		switch (ch) {
		case 't':
		if (next() == 'r' && next() == 'u' && next() == 'e') {
		next();
		return true;
		}
		break;
		case 'f':
		if (next() == 'a' && next() == 'l' && next() == 's' &&
		next() == 'e') {
		next();
		return false;
		}
		break;
		case 'n':
		if (next() == 'u' && next() == 'l' && next() == 'l') {
		next();
		return null;
		}
		break;
		}
		error("Syntax error");
		}

		function value() {
		white();
		switch (ch) {
		case '{':
		return object();
		case '[':
		return array();
		case '"':
		return string();
		case '-':
		return number();
		default:
		return ch >= '0' && ch <= '9' ? number() : word();
		}
		}

		return value();
		}
		};
		}();
	}-*/;

	private native void removeObjectPrototypeBadness(JavaScriptObject window) /*-{
		delete window.Array.prototype.toJSON;
		delete window.Object.prototype.toJSON;
	}-*/;

	private native void addObjectPrototypeBadness(JavaScriptObject window) /*-{
		window.Array.prototype.toJSON = function() { return "bleh"; };
		window.Object.prototype.toJSON = function() { return "bleh"; };
	}-*/;
}
