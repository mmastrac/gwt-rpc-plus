package com.dotspots.rpcplus.client.transport;

/**
 * Represents a transport that has a MIME content type.
 */
public interface HasContentType {
	public void setContentType(String contentType);

	public String getContentType();
}
