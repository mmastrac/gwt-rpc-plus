package com.dotspots.rpcplus.codegen.thrift;

/**
 * Callback interface to write a thrift structure.
 */
public interface RpcObjectWriter {
	void writePackage(String packageName);

	void startClass(RpcStruct rpcType, String className);

	void writeConstructor(RpcStruct type);

	void endClass(RpcStruct rpcType, String className);

	void writeGetter(RpcStruct rpcType, RpcField field);

	void writeSetter(RpcStruct rpcType, RpcField field);

	void writeIsSet(RpcStruct rpcType, RpcField field);

	void writeUnset(RpcStruct rpcType, RpcField field);
}
