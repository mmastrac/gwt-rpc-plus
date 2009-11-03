package com.dotspots.rpcplus.codegen.thrift;

import java.util.HashMap;
import java.util.Map;

public class GwtCodeGenBase {
	protected String getType(RpcField field) {
		return getType(field.getType(), false);
	}

	protected String getType(RpcTypeBase type, final boolean boxed) {
		final Map<RpcTypeKey, String> typeMap = new HashMap<RpcTypeKey, String>();
		typeMap.put(RpcTypeKey.I32, "Int");
		typeMap.put(RpcTypeKey.BOOL, "Bool");
		typeMap.put(RpcTypeKey.DOUBLE, "Double");
		typeMap.put(RpcTypeKey.I64, "Long");
		typeMap.put(RpcTypeKey.ENUM, "Int");

		final Map<RpcTypeKey, String> keyTypeMap = new HashMap<RpcTypeKey, String>();
		keyTypeMap.put(RpcTypeKey.I32, "Int");
		keyTypeMap.put(RpcTypeKey.ENUM, "Int");
		keyTypeMap.put(RpcTypeKey.STRING, "String");

		final StringBuilder builder = new StringBuilder();

		type.visit(new TypeVisitor() {
			public void endVisitList(RpcTypeList list, RpcTypeBase elementType) {
				builder.append(">");
			}

			public void endVisitMap(RpcTypeMap map, RpcTypeBase keyType, RpcTypeBase valueType) {
				builder.append(">");
			}

			public void endVisitSet(RpcTypeSet set, RpcTypeBase elementType) {
				builder.append(">");
			}

			public boolean visitList(RpcTypeList list, RpcTypeBase elementType) {
				if (typeMap.containsKey(elementType.getTypeKey())) {
					builder.append("JsRpcList" + typeMap.get(elementType.getTypeKey()));
					return false;
				}

				builder.append("JsRpcList<");
				return true;
			}

			public boolean visitMap(RpcTypeMap map, RpcTypeBase keyType, RpcTypeBase valueType) {
				if (keyTypeMap.containsKey(keyType.getTypeKey())) {
					builder.append("JsRpcMap").append(keyTypeMap.get(keyType.getTypeKey()));

					if (typeMap.containsKey(valueType.getTypeKey())) {
						builder.append(typeMap.get(valueType.getTypeKey()));
						return false;
					}

					builder.append("<");
					valueType.visit(this);
					builder.append(">");

					return false;
				}

				throw new RuntimeException("Unavailable type: Map from " + keyType + " to " + valueType);
			}

			public void visitNative(RpcTypeNative type) {
				switch (type.getTypeKey()) {
				case VOID:
					builder.append(boxed ? "Void" : "void");
					return;
				case BOOL:
					builder.append(boxed ? "Boolean" : "boolean");
					return;
				case DOUBLE:
					builder.append(boxed ? "Double" : "double");
					return;
				case BYTE:
				case I16:
				case I32:
					builder.append(boxed ? "Integer" : "int");
					return;
				case I64:
					builder.append(boxed ? "Long" : "long");
					return;
				case STRING:
				case BINARY:
					builder.append("String");
					return;
				}

				throw new RuntimeException("Unavailable type: " + type);
			}

			public boolean visitSet(RpcTypeSet set, RpcTypeBase elementType) {
				if (keyTypeMap.containsKey(elementType.getTypeKey())) {
					builder.append("JsRpcSet" + keyTypeMap.get(elementType.getTypeKey()));
					return false;
				}

				throw new RuntimeException("Unavailable type: Set of " + elementType);
			}

			public void visitEnum(RpcTypeEnum rpcTypeEnum) {
				builder.append(boxed ? "Integer" : "int");
			}

			public void visitStruct(RpcTypeStruct struct) {
				builder.append(struct.getStruct().getFullyQualifiedClassName());
			}
		});

		return builder.toString();
	}
}
