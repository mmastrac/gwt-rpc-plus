package com.dotspots.rpcplus.codegen.thrift;

public class RpcTypeNative extends RpcTypeBase {
	private final RpcTypeKey key;

	public RpcTypeNative(RpcTypeKey key) {
		this.key = key;
	}

	@Override
	public RpcTypeKey getTypeKey() {
		return key;
	}

	@Override
	public void visit(TypeVisitor typeVisitor) {
		typeVisitor.visitNative(this);
	}

	@Override
	public String toString() {
		return key.toString();
	}
}
