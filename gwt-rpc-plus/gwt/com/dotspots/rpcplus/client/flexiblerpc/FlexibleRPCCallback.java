package com.dotspots.rpcplus.client.flexiblerpc;

public interface FlexibleRPCCallback {
	/**
	 * The request completed successfully, returning a String containing the GWT-RPC encoded data. The request may have
	 * failed to complete.
	 * 
	 * @param data
	 *            The encoded RPC data.
	 */
	void onComplete(String data);

	/**
	 * The request failed on the server, returning a text message.
	 * 
	 * @param responseCode
	 *            The response code, or 0 if unavailable.
	 * @param errorMessage
	 *            The error message.
	 */
	void onError(int responseCode, String errorMessage);

	/**
	 * The request failed, throwing an exception.
	 */
	void onError(Throwable t);
}
