package com.dotspots.rpcplus.codegen.thrift;

public class RpcTypeSet extends RpcTypeBase {
	private RpcTypeBase elementType;

	public RpcTypeSet(RpcTypeBase elementType) {
		this.elementType = elementType;
	}

	@Override
	public RpcTypeKey getTypeKey() {
		return RpcTypeKey.SET;
	}

	public RpcTypeBase getElementType() {
		return elementType;
	}

	@Override
	public void visit(TypeVisitor typeVisitor) {
		if (typeVisitor.visitSet(this, elementType)) {
			elementType.visit(typeVisitor);
			typeVisitor.endVisitSet(this, elementType);
		}
	}

	@Override
	public String toString() {
		return "set<" + elementType + ">";
	}

}
