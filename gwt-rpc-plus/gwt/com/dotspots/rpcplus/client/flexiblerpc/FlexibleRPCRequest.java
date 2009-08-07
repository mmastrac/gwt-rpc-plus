package com.dotspots.rpcplus.client.flexiblerpc;

public interface FlexibleRPCRequest {
	/**
	 * Cancels a pending request, ensuring it will not complete.
	 */
	void cancel();
}
