package com.dotspots.rpcplus.client.flexiblerpc.impl;

import com.dotspots.rpcplus.client.transport.impl.WindowNameTransport;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.impl.Serializer;

public class CrossDomainRPC extends TextTransportRPC<WindowNameTransport> {
	public CrossDomainRPC() {
		textTransport = new WindowNameTransport();
		textTransport.setDocument(Document.get());
	}

	@Override
	public void initialize(RemoteServiceProxy proxy, Serializer serializer) {
		super.initialize(proxy, serializer);
		textTransport.setUrl(proxy.getServiceEntryPoint());
	}
}
