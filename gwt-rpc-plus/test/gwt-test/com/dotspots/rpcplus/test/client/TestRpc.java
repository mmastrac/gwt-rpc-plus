package com.dotspots.rpcplus.test.client;

import com.dotspots.rpcplus.client.codec.impl.EvalJsonDecoder;
import com.dotspots.rpcplus.client.codec.impl.JSONFactory;
import com.dotspots.rpcplus.client.codec.impl.JSONObjectJsonEncoder;
import com.dotspots.rpcplus.client.jscollections.JsRpcSetString;
import com.dotspots.rpcplus.client.jsonrpc.RpcException;
import com.dotspots.rpcplus.client.jsonrpc.impl.StandardCallDecoder;
import com.dotspots.rpcplus.client.jsonrpc.impl.StandardCallEncoder;
import com.dotspots.rpcplus.client.jsonrpc.thrift.CallInterceptor;
import com.dotspots.rpcplus.client.transport.impl.HttpTransport;
import com.dotspots.rpcplus.client.transport.impl.JsonOverTextTransport;
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
		transport.setUrl(GWT.getModuleBaseURL() + "/api");

		JSONFactory factory = new JSONFactory();

		api = new TortureTestApi();
		api.setTransport(new JsonOverTextTransport(transport, factory));
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

	public void testWindowNameTransport() {
		delayTestFinish(15000);
		WindowNameTransport transport = new WindowNameTransport();
		transport.setUrl(getCrossSiteModuleBaseUrl() + "/api");
		transport.setDocument(Document.get());

		api.setTransport(new JsonOverTextTransport(transport, new EvalJsonDecoder(), new JSONObjectJsonEncoder()));
		api.setRequestContext(ContextIn.create("token", "\"'*(,.&^<>!@#5$%()^\\\n"));

		api.testSetString(new AsyncCallback<JsRpcSetString>() {
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
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

	public void testWindowNameTransportWithFaviconRedirect() {
		delayTestFinish(15000);
		WindowNameTransport transport = new WindowNameTransport();
		transport.setUrl(getCrossSiteModuleBaseUrl() + "/api");
		transport.setDocument(Document.get());
		transport.setRedirectFavicon();

		api.setTransport(new JsonOverTextTransport(transport, new EvalJsonDecoder(), new JSONObjectJsonEncoder()));
		api.setRequestContext(ContextIn.create("token", "\"'*(,.&^<>!@#5$%()^\\\n"));

		api.testSetString(new AsyncCallback<JsRpcSetString>() {
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				fail(caught.toString());
				finishTest();
			}

			public void onSuccess(JsRpcSetString result) {
				finishTest();
			}
		});
	}

	/**
	 * Temporary test hoping to shake out IE7 bugs.
	 */
	public void testLotsOfWindowNameTransports() {
		delayTestFinish(120000);
		WindowNameTransport transport = new WindowNameTransport();
		transport.setUrl(getCrossSiteModuleBaseUrl() + "/api");
		transport.setDocument(Document.get());

		api.setTransport(new JsonOverTextTransport(transport, new EvalJsonDecoder(), new JSONObjectJsonEncoder()));

		final int[] total = new int[] { 0 };

		for (int i = 0; i < 10; i++) {
			api.setRequestContext(ContextIn.create("token", "\"'*(,.&^<>!@#5$%()^\\\n"));
			api.testSetString(new AsyncCallback<JsRpcSetString>() {
				public void onFailure(Throwable caught) {
					fail(caught.toString());
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

					for (int j = 0; j < 5; j++) {
						api.testSetString(new AsyncCallback<JsRpcSetString>() {
							public void onFailure(Throwable caught) {
								fail(caught.toString());
							}

							public void onSuccess(JsRpcSetString result) {
								total[0]++;

								System.out.println("Completed: " + total[0]);

								if (total[0] == 50) {
									finishTest();
								}
							}
						});
					}
				}
			});
		}
	}

	public void testWindowNameTransportError() {
		delayTestFinish(15000);
		WindowNameTransport transport = new WindowNameTransport();
		transport.setUrl(getCrossSiteModuleBaseUrl() + "/apidoesntexist");
		transport.setDocument(Document.get());
		transport.setTimeout(2000);

		api.setTransport(new JsonOverTextTransport(transport, new EvalJsonDecoder(), new JSONObjectJsonEncoder()));
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

	public static native JavaScriptObject getWindow() /*-{
		return $wnd;
	}-*/;

	public String getCrossSiteModuleBaseUrl() {
		return GWT.getModuleBaseURL().replaceAll("localhost", "127.0.0.1");
	}

	@Override
	public String getModuleName() {
		return "com.dotspots.rpcplus.test.TestWithServer";
	}
}
