package com.dotspots.rpcplus.client.transport;

/**
 * A set of common MIME types.
 */
public interface CommonMimeTypes {
	/**
	 * GWT's standard MIME type.
	 */
	public static final String GWT_MIME_TYPE = "text/x-gwt-rpc; charset=utf-8";

	/**
	 * The standard MIME type for JSON requests.
	 */
	public static final String JSON_MIME_TYPE = "application/json; charset=utf-8";

	/**
	 * The default, safe MIME type.
	 */
	public static final String DEFAULT_MIME_TYPE = "text/plain; charset=utf-8";
}
