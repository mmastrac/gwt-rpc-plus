package com.dotspots.rpcplus.jsonrpc.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;

import com.dotspots.rpcplus.jsonrpc.thrift.TBaseJSONProtocol;

public interface JSONServlet {
	public void processRequest(TBaseJSONProtocol in, TProtocol out) throws TException;
}
