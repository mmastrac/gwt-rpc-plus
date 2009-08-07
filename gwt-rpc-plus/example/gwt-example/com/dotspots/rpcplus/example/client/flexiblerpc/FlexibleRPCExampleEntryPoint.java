package com.dotspots.rpcplus.example.client.flexiblerpc;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class FlexibleRPCExampleEntryPoint implements EntryPoint {

	public void onModuleLoad() {
		RootPanel.get().add(new Label("Loaded!"));

		SimpleServiceAsync async = GWT.create(SimpleService.class);
		((ServiceDefTarget) async).setServiceEntryPoint(GWT.getModuleBaseURL() + "api");

		async.add(1, 2, new AsyncCallback<Integer>() {
			public void onSuccess(Integer result) {
				RootPanel.get().add(new Label("Got back result: " + result));
			}

			public void onFailure(Throwable caught) {
				RootPanel.get().add(new Label("Failure: " + caught));
			}
		});
	}
}
