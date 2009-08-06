package com.dotspots.rpcplus.codegen.thrift;

/**
 * Callback interface to write a service interface.
 */
public interface RpcInterfaceWriter {
	void writePackage(String packageName);

	void startClass(RpcInterface iface);

	void endClass(RpcInterface iface);

	void writeMethod(RpcInterface iface, RpcMethod method);

	void writeType(RpcStruct type);
}
