package com.dotspots.rpcplus.client.transport.impl;

import com.google.gwt.core.client.GWT;

/**
 * TODO: Make this pluggable.
 */
class TransportLogger {
	public static void logSend(String send) {
		GWT.log("SEND: " + send, null);
	}

	public static void logReceive(String recv) {
		GWT.log("RECV: " + recv, null);
	}
}
