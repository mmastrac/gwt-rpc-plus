package com.dotspots.rpcplus.codegen.thrift;

public class RpcTypeList extends RpcTypeBase {
	private RpcTypeBase elementType;

	public RpcTypeList(RpcTypeBase elementType) {
		this.elementType = elementType;
	}

	@Override
	public RpcTypeKey getTypeKey() {
		return RpcTypeKey.LIST;
	}

	public RpcTypeBase getElementType() {
		return elementType;
	}

	@Override
	public void visit(TypeVisitor typeVisitor) {
		if (typeVisitor.visitList(this, elementType)) {
			elementType.visit(typeVisitor);
			typeVisitor.endVisitList(this, elementType);
		}
	}

	@Override
	public String toString() {
		return "list<" + elementType + ">";
	}
}
