package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.common.RPCPlusService;
import com.dotspots.rpcplus.client.transport.HasTransport;
import com.dotspots.rpcplus.client.transport.TextTransport;

/**
 * Initializes an object with a given transport.
 */
public class StandardTransportFactory extends AbstractTransportFactory {
	@Override
	protected TextTransport createTransport(RPCPlusService service, HasTransport<?> hasTransport) {
		HttpTransport transport = new HttpTransport();
		return transport;
	}
}
