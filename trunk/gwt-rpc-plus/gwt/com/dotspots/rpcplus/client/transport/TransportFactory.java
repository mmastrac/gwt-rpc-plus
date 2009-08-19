package com.dotspots.rpcplus.client.transport;

import com.dotspots.rpcplus.client.common.RPCPlusService;

public interface TransportFactory {
	public Transport initialize(String contentType, RPCPlusService service, HasTransport<?> hasTransport);
}
