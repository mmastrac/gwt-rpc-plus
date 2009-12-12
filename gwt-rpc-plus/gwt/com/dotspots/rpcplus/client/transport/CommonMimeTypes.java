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
	 * The standard MIME type for JSON requests/responses.
	 */
	public static final String JSON_MIME_TYPE = "application/json; charset=utf-8";

	/**
	 * The standard MIME type for HTML responses.
	 */
	public static final String HTML_MIME_TYPE = "text/html; charset=utf-8";

	/**
	 * The default, safe MIME type.
	 */
	public static final String DEFAULT_MIME_TYPE = "text/plain; charset=utf-8";

	/**
	 * The URL-encoded MIME type for form posts.
	 */
	public static final String URL_ENCODED_MIME_TYPE = "application/x-www-form-urlencoded; charset=utf-8";
}
