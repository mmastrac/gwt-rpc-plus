package com.dotspots.rpcplus.test.client;

import com.dotspots.rpcplus.client.jscollections.JsRpcSetString;
import com.dotspots.rpcplus.client.jsonrpc.thrift.CallInterceptor;
import com.dotspots.rpcplus.example.torturetest.client.ContextIn;
import com.dotspots.rpcplus.example.torturetest.client.ContextOut;
import com.dotspots.rpcplus.example.torturetest.client.SimpleException;
import com.dotspots.rpcplus.example.torturetest.client.TortureTestApi;
import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class TestThriftRPC extends GWTTestCase {
	private TortureTestApi api;

	@Override
	protected void gwtSetUp() throws Exception {
		api = new TortureTestApi();
		api.setServiceEntryPoint(GWT.getModuleBaseURL() + "api");
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

	public void testPositionalArguments() {
		delayTestFinish(1500000);
		api.testPositionalArguments(123, "somestring", new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				fail(caught.toString());
			}

			public void onSuccess(String result) {
				assertEquals("123somestring", result);
				finishTest();
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

	public void testOnBeforeAndAfterCall() {
		delayTestFinish(15000);

		api.addCallInterceptor(new CallInterceptor<TortureTestApi>() {
			public void onAfterCall(TortureTestApi api) {
				ContextOut responseContext = api.popResponseContext();
				assertEquals(">>>datafrominterceptor", responseContext.getData());
			}

			public void onBeforeCall(TortureTestApi i) {
				api.setRequestContext(ContextIn.create("token", "datafrominterceptor"));
			}
		});

		api.testSetString(new AsyncCallback<JsRpcSetString>() {
			public void onFailure(Throwable caught) {
				fail(caught.toString());
			}

			public void onSuccess(JsRpcSetString result) {
				finishTest();
			}
		});
	}

	@Override
	public String getModuleName() {
		return "com.dotspots.rpcplus.test.TestWithServer";
	}
}
