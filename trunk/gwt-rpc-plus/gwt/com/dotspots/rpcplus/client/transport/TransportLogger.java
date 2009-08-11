package com.dotspots.rpcplus.client.transport;

import com.google.gwt.core.client.GWT;

public interface TransportLogger {
	public static TransportLogger INSTANCE = GWT.create(TransportLogger.class);

	public void logSend(String send);

	public void logReceive(String recv);
}
