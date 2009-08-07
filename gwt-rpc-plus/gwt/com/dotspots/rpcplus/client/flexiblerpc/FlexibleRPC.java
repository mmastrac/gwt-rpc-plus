package com.dotspots.rpcplus.client.flexiblerpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.impl.Serializer;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;

/**
 * Invokes a remote service.
 * 
 * @return A valid {@link FlexibleRPCRequest} if applicable, otherwise null.
 */
public interface FlexibleRPC {
	/**
	 * Initializes the RPC provider with the correct proxy and internal serializer.
	 */
	public void initialize(RemoteServiceProxy proxy, Serializer serializer);

	/**
	 * Mirrors the internal doInvoke function from RemoteServiceProxy.
	 */
	public <T> FlexibleRPCRequest doInvoke(String url, ResponseReader responseReader, String methodName, int requestId, String requestData,
			AsyncCallback<T> callback);
}
