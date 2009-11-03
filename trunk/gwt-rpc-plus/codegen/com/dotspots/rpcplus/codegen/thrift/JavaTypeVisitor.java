package com.dotspots.rpcplus.codegen.thrift;

public class JavaTypeVisitor implements TypeVisitor {
	private StringBuilder builder = new StringBuilder();
	private final boolean boxed;
	private int level = 0;
	private final boolean binaryIsString;

	public JavaTypeVisitor(boolean boxed, boolean binaryIsString) {
		this.boxed = boxed;
		this.binaryIsString = binaryIsString;
	}

	public void endVisitList(RpcTypeList list, RpcTypeBase elementType) {
		level--;
		builder.append(">");
	}

	public void endVisitMap(RpcTypeMap map, RpcTypeBase keyType, RpcTypeBase valueType) {
		level--;
		builder.append(">");
	}

	public void endVisitSet(RpcTypeSet set, RpcTypeBase elementType) {
		level--;
		builder.append(">");
	}

	public boolean visitList(RpcTypeList list, RpcTypeBase elementType) {
		level++;
		builder.append("List<");
		return true;
	}

	public boolean visitMap(RpcTypeMap map, RpcTypeBase keyType, RpcTypeBase valueType) {
		level++;
		builder.append("Map<");
		keyType.visit(this);
		builder.append(", ");
		valueType.visit(this);

		endVisitMap(map, keyType, valueType);
		return false;
	}

	public boolean visitSet(RpcTypeSet set, RpcTypeBase elementType) {
		level++;
		builder.append("Set<");
		return true;
	}

	public void visitNative(RpcTypeNative type) {
		boolean boxed = this.boxed || level > 0;

		switch (type.getTypeKey()) {
		case VOID:
			builder.append(boxed ? "Void" : "void");
			return;
		case BINARY:
			builder.append(binaryIsString ? "String" : "byte[]");
			return;
		case BOOL:
			builder.append(boxed ? "Boolean" : "boolean");
			return;
		case DOUBLE:
			builder.append(boxed ? "Double" : "double");
			return;
		case BYTE:
			builder.append(boxed ? "Byte" : "byte");
			return;
		case I16:
			builder.append(boxed ? "Short" : "short");
			return;
		case I32:
			builder.append(boxed ? "Integer" : "int");
			return;
		case I64:
			builder.append(boxed ? "Long" : "long");
			return;
		case STRING:
			builder.append("String");
			return;
		}

		throw new RuntimeException("Unavailable type: " + type);
	}

	public void visitStruct(RpcTypeStruct struct) {
		builder.append(struct.getStruct().getFullyQualifiedClassName());
	}

	public void visitEnum(RpcTypeEnum struct) {
		builder.append(boxed ? "Integer" : "int");
	}

	@Override
	public String toString() {
		return builder.toString();
	}
}
