package com.dotspots.rpcplus.test.client;

import com.dotspots.rpcplus.client.codec.impl.EvalJsonDecoder;
import com.dotspots.rpcplus.client.codec.impl.JSONObjectJsonEncoder;
import com.dotspots.rpcplus.client.jscollections.JsRpcSetString;
import com.dotspots.rpcplus.client.jsonrpc.RpcException;
import com.dotspots.rpcplus.client.transport.HasWrappedTransport;
import com.dotspots.rpcplus.client.transport.impl.JsonOverTextTransport;
import com.dotspots.rpcplus.client.transport.impl.WindowNameTransport;
import com.dotspots.rpcplus.example.torturetest.client.ContextIn;
import com.dotspots.rpcplus.example.torturetest.client.ContextOut;
import com.dotspots.rpcplus.example.torturetest.client.TortureTestApi;
import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class TestWindowNameThriftRPC extends GWTTestCase {
	private TortureTestApi api;

	@Override
	protected void gwtSetUp() throws Exception {
		api = new TortureTestApi();
		api.setServiceEntryPoint(GWT.getModuleBaseURL() + "api");
	}

	public void testWindowNameTransport() {
		delayTestFinish(15000);

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

		WindowNameTransport transport = (WindowNameTransport) ((HasWrappedTransport) api.getJsonTransport()).getWrappedTransport();
		transport.setRedirectFavicon();

		api.setJsonTransport(new JsonOverTextTransport(transport, new EvalJsonDecoder(), new JSONObjectJsonEncoder()));
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

		WindowNameTransport transport = (WindowNameTransport) ((HasWrappedTransport) api.getJsonTransport()).getWrappedTransport();
		transport.setUrl(getCrossSiteModuleBaseUrl() + "/apidoesntexist");
		transport.setTimeout(2000);

		api.setJsonTransport(new JsonOverTextTransport(transport, new EvalJsonDecoder(), new JSONObjectJsonEncoder()));
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

	public void testReallyLongRequestAndResponse() {
		delayTestFinish(15000);

		// 1310720 bytes long
		final StringBuilder stringBuilder = new StringBuilder("0123456789");
		for (int i = 0; i < 17; i++) {
			stringBuilder.append(stringBuilder.toString());
		}

		finishTest();

		api.testPassthru(stringBuilder.toString(), new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				fail(caught.toString());
				finishTest();
			}

			public void onSuccess(String result) {
				assertEquals(stringBuilder.toString(), result);

				finishTest();
			}
		});
	}

	public void testPassThruAllChars() {
		delayTestFinish(15000);

		api.testPassthru(EveryCharacterStringUtility.getAllCharacterString(), new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				fail(caught.toString());
				finishTest();
			}

			public void onSuccess(String result) {
				EveryCharacterStringUtility.checkString(result);
				finishTest();
			}
		});
	}

	private String getCrossSiteModuleBaseUrl() {
		return GWT.getModuleBaseURL().replaceAll("localhost", "127.0.0.1");
	}

	@Override
	public String getModuleName() {
		return "com.dotspots.rpcplus.test.TestWithServerCrossDomain";
	}
}
