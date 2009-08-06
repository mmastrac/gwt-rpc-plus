package com.dotspots.rpcplus.codegen.thrift;

public class RpcTypeStruct extends RpcTypeBase {
	private final RpcStruct struct;

	public RpcTypeStruct(RpcStruct struct) {
		this.struct = struct;
	}

	@Override
	public RpcTypeKey getTypeKey() {
		return RpcTypeKey.STRUCT;
	}

	@Override
	public String toString() {
		return struct.getFullyQualifiedClassName();
	}

	public RpcStruct getStruct() {
		return struct;
	}

	@Override
	public void visit(TypeVisitor typeVisitor) {
		typeVisitor.visitStruct(this);
	}
}
