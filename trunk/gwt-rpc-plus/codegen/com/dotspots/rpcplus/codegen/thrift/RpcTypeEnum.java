package com.dotspots.rpcplus.codegen.thrift;

public class RpcTypeEnum extends RpcTypeBase {
	private final RpcStruct struct;

	public RpcTypeEnum(RpcStruct struct) {
		this.struct = struct;
	}

	@Override
	public RpcTypeKey getTypeKey() {
		return RpcTypeKey.ENUM;
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
		typeVisitor.visitEnum(this);
	}
}
