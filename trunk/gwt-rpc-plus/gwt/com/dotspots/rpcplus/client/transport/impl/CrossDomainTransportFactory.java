package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.common.RPCPlusService;
import com.dotspots.rpcplus.client.transport.HasTransport;
import com.dotspots.rpcplus.client.transport.TextTransport;

/**
 * A transport factory that creates cross-domain transports.
 */
public class CrossDomainTransportFactory extends AbstractTransportFactory {
	@Override
	protected TextTransport createTransport(RPCPlusService service, HasTransport<?> hasTransport) {
		WindowNameTransport transport = new WindowNameTransport();
		return transport;
	}
}