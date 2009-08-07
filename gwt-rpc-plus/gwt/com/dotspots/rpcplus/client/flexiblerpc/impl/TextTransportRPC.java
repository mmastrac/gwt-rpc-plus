package com.dotspots.rpcplus.client.flexiblerpc.impl;

import com.dotspots.rpcplus.client.flexiblerpc.FlexibleRPCRequest;
import com.dotspots.rpcplus.client.transport.TextTransport;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter;

public class TextTransportRPC<TRANSPORT extends TextTransport> extends AbstractFlexibleRPC {
	protected TRANSPORT textTransport;

	public TextTransportRPC() {
	}

	@Override
	protected <T> FlexibleRPCRequest doInvoke(String methodName, int requestId, String requestData,
			final RequestCallbackAdapter<T> requestCallbackAdapter) {
		textTransport.call(requestData, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				onResponseError(requestCallbackAdapter, caught);
			}

			public void onSuccess(String result) {
				onResponseSuccess(requestCallbackAdapter, result);
			}
		});

		return null;
	}
}
