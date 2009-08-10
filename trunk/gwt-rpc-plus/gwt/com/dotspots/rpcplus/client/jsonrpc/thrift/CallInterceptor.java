package com.dotspots.rpcplus.client.jsonrpc.thrift;

public interface CallInterceptor<I extends ThriftClientStub<I>> {
	public void onBeforeCall(I api);

	public void onAfterCall(I api);
}
