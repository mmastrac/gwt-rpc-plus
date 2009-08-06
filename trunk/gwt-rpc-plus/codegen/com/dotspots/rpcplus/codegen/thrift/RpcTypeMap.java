package com.dotspots.rpcplus.codegen.thrift;

public class RpcTypeMap extends RpcTypeBase {
	private RpcTypeBase keyType;
	private RpcTypeBase valueType;

	public RpcTypeMap(RpcTypeBase keyType, RpcTypeBase valueType) {
		this.keyType = keyType;
		this.valueType = valueType;
	}

	@Override
	public RpcTypeKey getTypeKey() {
		return RpcTypeKey.MAP;
	}

	public RpcTypeBase getKeyType() {
		return keyType;
	}

	public RpcTypeBase getValueType() {
		return valueType;
	}

	@Override
	public void visit(TypeVisitor typeVisitor) {
		if (typeVisitor.visitMap(this, keyType, valueType)) {
			keyType.visit(typeVisitor);
			valueType.visit(typeVisitor);
			typeVisitor.endVisitMap(this, keyType, valueType);
		}
	}

	@Override
	public String toString() {
		return "map<" + keyType + ", " + valueType + ">";
	}

}
