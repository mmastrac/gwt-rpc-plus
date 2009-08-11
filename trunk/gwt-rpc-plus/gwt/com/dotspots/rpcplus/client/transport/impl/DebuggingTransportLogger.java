package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.transport.TransportLogger;
import com.google.gwt.core.client.GWT;

/**
 * Dumps transport data to the GWT and stdout consoles.
 */
class DebuggingTransportLogger implements TransportLogger {
	public void logSend(String send) {
		GWT.log("SEND: " + send, null);
		System.out.println("SEND: " + send);
	}

	public void logReceive(String recv) {
		GWT.log("RECV: " + recv, null);
		System.out.println("RECV: " + recv);
	}
}
