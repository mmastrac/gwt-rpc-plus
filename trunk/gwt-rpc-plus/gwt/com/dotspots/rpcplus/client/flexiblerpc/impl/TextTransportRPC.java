package com.dotspots.rpcplus.client.flexiblerpc.impl;

import com.dotspots.rpcplus.client.common.RPCPlusService;
import com.dotspots.rpcplus.client.flexiblerpc.FlexibleRPCRequest;
import com.dotspots.rpcplus.client.transport.TextTransport;
import com.dotspots.rpcplus.client.transport.TransportFactory;
import com.dotspots.rpcplus.client.transport.impl.HttpTransport;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter;
import com.google.gwt.user.client.rpc.impl.Serializer;

public class TextTransportRPC extends AbstractFlexibleRPC {
	protected TextTransport textTransport;

	public TextTransportRPC() {
	}

	public void setTextTransport(TextTransport transport) {
		textTransport = transport;
	}

	public TextTransport getTextTransport() {
		return textTransport;
	}

	@Override
	public void initialize(RemoteServiceProxy proxy, Serializer serializer) {
		super.initialize(proxy, serializer);

		TransportFactory transportFactory = GWT.create(TransportFactory.class);
		transportFactory.initialize(HttpTransport.GWT_MIME_TYPE, (RPCPlusService) proxy, this);
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
