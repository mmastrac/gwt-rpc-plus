package com.dotspots.rpcplus.client.common;

/**
 * Marker interface for any service: enhanced GWT-RPC or thrift. Mimics ServiceDefTarget from GWT RPC.
 */
public interface RPCPlusService {
	/**
	 * Gets the URL of a service implementation.
	 * 
	 * @return the last value passed to {@link #setServiceEntryPoint(String)}
	 */
	String getServiceEntryPoint();

	/**
	 * Sets the URL of a service implementation.
	 * 
	 * @param address
	 *            a URL that designates the service implementation to call
	 */
	void setServiceEntryPoint(String address);
}
