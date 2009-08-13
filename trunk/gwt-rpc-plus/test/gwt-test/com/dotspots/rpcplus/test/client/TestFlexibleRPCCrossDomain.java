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
public class TestFlexibleRPCCrossDomain extends GWTTestCase {
	public void testRPCOverCrossDomainRPC() {
		delayTestFinish(15000);

		SimpleServiceAsync api = GWT.create(SimpleService.class);
		((ServiceDefTarget) api).setServiceEntryPoint(getCrossSiteModuleBaseUrl() + "/api");
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

	public String getCrossSiteModuleBaseUrl() {
		return GWT.getModuleBaseURL().replaceAll("localhost", "127.0.0.1");
	}

	@Override
	public String getModuleName() {
		return "com.dotspots.rpcplus.test.TestFlexibleRPCCrossDomain";
	}
}
