package com.dotspots.rpcplus.client.transport.impl;

/**
 * The window.name transport supports postMessage now, so it's been renamed. Using this subclass will disable
 * postMessage.
 * 
 * This class will likely be removed early 2010.
 */
@Deprecated
public class WindowNameTransport extends CrossDomainFrameTransport {
	public WindowNameTransport() {
		// Don't allow postMessage, since we don't know if the server supports it
		setAllowPostMessage(false);
	}
}
