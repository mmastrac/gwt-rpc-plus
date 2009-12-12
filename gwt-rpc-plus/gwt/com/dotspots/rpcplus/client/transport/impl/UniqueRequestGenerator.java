package com.dotspots.rpcplus.client.transport.impl;

import com.google.gwt.core.client.Duration;

/**
 * Globally-unique request ID generator.
 */
public class UniqueRequestGenerator {
	// Globally unique ID
	private static int nameSerial;

	public static String createUniqueId() {
		return nameSerial++ + "-" + (Duration.currentTimeMillis() % 0xffffff);
	}
}
