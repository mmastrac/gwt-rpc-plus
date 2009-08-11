package com.dotspots.rpcplus.client.flexiblerpc.impl;

import com.dotspots.rpcplus.client.transport.impl.HttpTransport;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.impl.Serializer;

/**
 * Transports GWT RPC over HTTP (ie: the same way that GWT does it by default). This is the default flexible RPC
 * transport.
 */
public class HttpRPC extends TextTransportRPC<HttpTransport> {
	public HttpRPC() {
		textTransport = new HttpTransport();
	}

	@Override
	public void initialize(RemoteServiceProxy proxy, Serializer serializer) {
		super.initialize(proxy, serializer);
		textTransport.setUrl(proxy.getServiceEntryPoint());
	}
}
