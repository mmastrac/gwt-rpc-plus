package com.dotspots.rpcplus.client.flexiblerpc.impl;

import com.dotspots.rpcplus.client.flexiblerpc.FlexibleRPCRequest;
import com.dotspots.rpcplus.client.transport.HasContentType;
import com.dotspots.rpcplus.client.transport.TextTransport;
import com.dotspots.rpcplus.client.transport.impl.HttpTransport;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter;
import com.google.gwt.user.client.rpc.impl.Serializer;

public class TextTransportRPC<TRANSPORT extends TextTransport> extends AbstractFlexibleRPC {
	protected TRANSPORT textTransport;

	public TextTransportRPC() {
	}

	@Override
	public void initialize(RemoteServiceProxy proxy, Serializer serializer) {
		super.initialize(proxy, serializer);

		// Set the appropriate MIME type on the underlying transport
		if (textTransport instanceof HasContentType) {
			((HasContentType) textTransport).setContentType(HttpTransport.GWT_MIME_TYPE);
		}
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
