package com.dotspots.rpcplus.client.flexiblerpc.impl;

import com.dotspots.rpcplus.client.flexiblerpc.FlexibleRPCRequest;
import com.dotspots.rpcplus.client.transport.impl.WindowNameTransport;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;

public class CrossDomainRPC extends TextTransportRPC<WindowNameTransport> {
	public CrossDomainRPC() {
		textTransport = new WindowNameTransport();
		textTransport.setDocument(Document.get());
	}

	@Override
	public <T> FlexibleRPCRequest doInvoke(String url, ResponseReader responseReader, String methodName, int requestId, String requestData,
			AsyncCallback<T> callback) {
		textTransport.setUrl(url);
		return super.doInvoke(url, responseReader, methodName, requestId, requestData, callback);
	}
}
