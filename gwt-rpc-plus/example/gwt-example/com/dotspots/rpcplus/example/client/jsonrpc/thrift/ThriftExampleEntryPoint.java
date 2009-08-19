package com.dotspots.rpcplus.example.client.jsonrpc.thrift;

import com.dotspots.rpcplus.client.jscollections.JsRpcSetString;
import com.dotspots.rpcplus.example.torturetest.client.TortureTestApi;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class ThriftExampleEntryPoint implements EntryPoint {

	public void onModuleLoad() {
		RootPanel.get().add(new Label("Loaded!"));

		TortureTestApi api = new TortureTestApi();
		api.setServiceEntryPoint(GWT.getModuleBaseURL() + "api");

		api.testSetString(new AsyncCallback<JsRpcSetString>() {
			public void onFailure(Throwable caught) {
				RootPanel.get().add(new Label("Failure: " + caught));
			}

			public void onSuccess(JsRpcSetString result) {
				RootPanel.get().add(new Label("Got back results: "));
				for (String returnValue : result.iterable()) {
					RootPanel.get().add(new Label(returnValue));
				}
			}
		});
	}

	public static native JavaScriptObject getWindow() /*-{
		return $wnd;
	}-*/;
}
