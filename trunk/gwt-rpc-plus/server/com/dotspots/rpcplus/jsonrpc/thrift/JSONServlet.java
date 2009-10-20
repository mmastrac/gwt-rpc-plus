package com.dotspots.rpcplus.jsonrpc.thrift;

import org.apache.thrift.TException;

public interface JSONServlet {
	public void processRequest(TBaseJSONProtocol in, TJSONNativeProtocol out) throws TException;
}
