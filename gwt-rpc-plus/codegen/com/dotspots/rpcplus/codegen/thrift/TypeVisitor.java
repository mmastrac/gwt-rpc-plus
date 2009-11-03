package com.dotspots.rpcplus.codegen.thrift;

public interface TypeVisitor {
	void visitNative(RpcTypeNative type);

	void visitStruct(RpcTypeStruct rpcTypeStruct);

	void visitEnum(RpcTypeEnum rpcTypeEnum);

	boolean visitMap(RpcTypeMap map, RpcTypeBase keyType, RpcTypeBase valueType);

	void endVisitMap(RpcTypeMap map, RpcTypeBase keyType, RpcTypeBase valueType);

	boolean visitSet(RpcTypeSet set, RpcTypeBase elementType);

	void endVisitSet(RpcTypeSet set, RpcTypeBase elementType);

	boolean visitList(RpcTypeList list, RpcTypeBase elementType);

	void endVisitList(RpcTypeList list, RpcTypeBase elementType);
}
