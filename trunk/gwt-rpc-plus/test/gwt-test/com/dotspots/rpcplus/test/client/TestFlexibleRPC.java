package com.dotspots.rpcplus.test.client;

import com.dotspots.rpcplus.example.client.flexiblerpc.SimpleService;
import com.dotspots.rpcplus.example.client.flexiblerpc.SimpleServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Tests flexible RPC with the default transport.
 */
public class TestFlexibleRPC extends GWTTestCase {
	public void testRPCOverDefaultFlexibleRPC() {
		delayTestFinish(15000);

		SimpleServiceAsync api = GWT.create(SimpleService.class);
		((ServiceDefTarget) api).setServiceEntryPoint(GWT.getModuleBaseURL() + "/api");
		api.add(1, 2, new AsyncCallback<Integer>() {
			public void onFailure(Throwable caught) {
				fail(caught.toString());
			}

			public void onSuccess(Integer result) {
				assertEquals(3, (int) result);
				finishTest();
			}

		});
	}

	@Override
	public String getModuleName() {
		return "com.dotspots.rpcplus.test.TestFlexibleRPC";
	}
}
