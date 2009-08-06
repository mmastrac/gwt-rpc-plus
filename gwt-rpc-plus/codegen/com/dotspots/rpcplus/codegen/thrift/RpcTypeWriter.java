package com.dotspots.rpcplus.codegen.thrift;

import org.apache.thrift.protocol.TList;
import org.apache.thrift.protocol.TMap;
import org.apache.thrift.protocol.TSet;

/**
 * Callback interface to write a service interface.
 */
public interface RpcTypeWriter {
	void writeMapBegin(TMap map);

	void writeMapEnd();

	void writeListBegin(TList list);

	void writeListEnd();

	void writeSetBegin(TSet set);

	void writeSetEnd();

	void writeBool();

	void writeByte();

	void writeI16();

	void writeI32();

	void writeI64();

	void writeDouble();

	void writeString();

	void writeBinary();
}
