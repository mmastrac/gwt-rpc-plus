package com.dotspots.rpcplus.codegen.thrift;

public class TypeVisitorAdapter implements TypeVisitor {
	public void endVisitList(RpcTypeList list, RpcTypeBase elementType) {
	}

	public void endVisitMap(RpcTypeMap map, RpcTypeBase keyType, RpcTypeBase valueType) {
	}

	public void endVisitSet(RpcTypeSet set, RpcTypeBase elementType) {
	}

	public boolean visitList(RpcTypeList list, RpcTypeBase elementType) {
		return true;
	}

	public boolean visitMap(RpcTypeMap map, RpcTypeBase keyType, RpcTypeBase valueType) {
		return true;
	}

	public void visitNative(RpcTypeNative type) {
	}

	public boolean visitSet(RpcTypeSet set, RpcTypeBase elementType) {
		return true;
	}

	public void visitStruct(RpcTypeStruct rpcTypeStruct) {
	}

	public void visitEnum(RpcTypeEnum rpcTypeEnum) {
	}
}
