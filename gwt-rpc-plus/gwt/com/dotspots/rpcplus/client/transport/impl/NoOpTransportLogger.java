package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.transport.TransportLogger;

/**
 * Sends transport logs to /dev/null.
 */
class NoOpTransportLogger implements TransportLogger {
	public void logSend(String send) {
	}

	public void logReceive(String recv) {
	}
}
