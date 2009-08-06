package com.dotspots.rpcplus.test.client;

import com.dotspots.rpcplus.client.codec.impl.EvalJsonDecoder;
import com.dotspots.rpcplus.client.codec.impl.JSONObjectJsonEncoder;
import com.dotspots.rpcplus.client.jscollections.JsRpcSetString;
import com.dotspots.rpcplus.client.jsonrpc.RpcException;
import com.dotspots.rpcplus.client.jsonrpc.impl.StandardCallDecoder;
import com.dotspots.rpcplus.client.jsonrpc.impl.StandardCallEncoder;
import com.dotspots.rpcplus.client.transport.impl.HttpTransport;
import com.dotspots.rpcplus.client.transport.impl.WindowNameTransport;
import com.dotspots.rpcplus.example.torturetest.client.ContextIn;
import com.dotspots.rpcplus.example.torturetest.client.ContextOut;
import com.dotspots.rpcplus.example.torturetest.client.SimpleException;
import com.dotspots.rpcplus.example.torturetest.client.TortureTestApi;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class TestRpc extends GWTTestCase {
	private HttpTransport transport;
	private TortureTestApi api;

	@Override
	protected void gwtSetUp() throws Exception {
		transport = new HttpTransport();
		transport.setDecoder(new EvalJsonDecoder(getWindow()));
		transport.setEncoder(new JSONObjectJsonEncoder());
		transport.setUrl(GWT.getModuleBaseURL() + "/api");

		api = new TortureTestApi();
		api.setTransport(transport);
		api.setCallEncoder(new StandardCallEncoder());
		api.setCallDecoder(new StandardCallDecoder());
	}

	public void testSetString() {
		delayTestFinish(15000);

		api.testSetString(new AsyncCallback<JsRpcSetString>() {
			public void onFailure(Throwable caught) {
				fail(caught.toString());
			}

			public void onSuccess(JsRpcSetString result) {
				assertEquals(4, result.countSize());
				assertTrue(result.contains("hi0"));
				assertTrue(result.contains("hi1"));
				assertTrue(result.contains("hi2"));
				assertTrue(result.contains("hi3"));
				assertFalse(result.contains("hi4"));

				finishTest();
			}
		});
	}

	public void testWindowNameTransport() {
		delayTestFinish(15000);
		WindowNameTransport transport = new WindowNameTransport();
		transport.setDecoder(new EvalJsonDecoder(getWindow()));
		transport.setEncoder(new JSONObjectJsonEncoder());
		transport.setUrl(GWT.getModuleBaseURL() + "/api");
		transport.setDocument(Document.get());

		api.setTransport(transport);
		api.setRequestContext(ContextIn.create("token", "\"'*(,.&^<>!@#5$%()^\\\n"));

		api.testSetString(new AsyncCallback<JsRpcSetString>() {
			public void onFailure(Throwable caught) {
				fail(caught.toString());
				finishTest();
			}

			public void onSuccess(JsRpcSetString result) {
				ContextOut responseContext = api.popResponseContext();
				assertEquals(">>>\"'*(,.&^<>!@#5$%()^\\\n", responseContext.getData());

				assertEquals(4, result.countSize());
				assertTrue(result.contains("hi0"));
				assertTrue(result.contains("hi1"));
				assertTrue(result.contains("hi2"));
				assertTrue(result.contains("hi3"));
				assertFalse(result.contains("hi4"));

				finishTest();
			}
		});
	}

	public void testWindowNameTransportError() {
		delayTestFinish(15000);
		WindowNameTransport transport = new WindowNameTransport();
		transport.setDecoder(new EvalJsonDecoder(getWindow()));
		transport.setEncoder(new JSONObjectJsonEncoder());
		transport.setUrl(GWT.getModuleBaseURL() + "/apidoesntexist");
		transport.setDocument(Document.get());
		transport.setTimeout(2000);

		api.setTransport(transport);
		api.setRequestContext(ContextIn.create("token", "\"'*(,.&^<>!@#5$%()^\\\n"));

		api.testSetString(new AsyncCallback<JsRpcSetString>() {
			public void onFailure(Throwable caught) {
				assertTrue(caught instanceof RpcException);
				finishTest();
			}

			public void onSuccess(JsRpcSetString result) {
				fail("Should not have succeeded");
			}
		});
	}

	public void testThrowsAnException() {
		delayTestFinish(15000);

		api.testThrowsAnException(new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				assertTrue("Wrong exception type: " + caught.getClass(), caught instanceof SimpleException);
				finishTest();
			}

			public void onSuccess(String result) {
				fail("Shouldn't have succeeded");
			}
		});
	}

	public void testContext() {
		delayTestFinish(15000);

		api.setRequestContext(ContextIn.create("token", "data"));
		api.testSetString(new AsyncCallback<JsRpcSetString>() {
			public void onFailure(Throwable caught) {
				fail(caught.toString());
			}

			public void onSuccess(JsRpcSetString result) {
				ContextOut responseContext = api.popResponseContext();
				assertEquals(">>>data", responseContext.getData());
				assertNull(api.popResponseContext());
				finishTest();
			}
		});
	}

	public static native JavaScriptObject getWindow() /*-{
		return $wnd;
	}-*/;

	@Override
	public String getModuleName() {
		return "com.dotspots.rpcplus.test.TestWithServer";
	}
}
