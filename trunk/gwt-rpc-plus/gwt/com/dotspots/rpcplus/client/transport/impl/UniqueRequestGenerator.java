package com.dotspots.rpcplus.client.transport.impl;

import com.google.gwt.core.client.Duration;

public class UniqueRequestGenerator {
	// Globally unique ID
	private static int nameSerial;

	public static String createUniqueId() {
		return nameSerial++ + "-" + Duration.currentTimeMillis();
	}

}
