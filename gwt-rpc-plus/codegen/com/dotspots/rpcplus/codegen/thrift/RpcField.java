package com.dotspots.rpcplus.codegen.thrift;

import org.apache.thrift.protocol.TType;

/**
 * Represents a field from a Thrift IDL file.
 */
public class RpcField {
	private final int key;
	private final int declaredOrder;
	private final RpcTypeBase fieldType;
	private final String name;
	private final RpcStruct structType;

	public RpcField(int key, int declaredOrder, String name, RpcTypeBase fieldType) {
		this.structType = (fieldType instanceof RpcTypeStruct) ? ((RpcTypeStruct) fieldType).getStruct() : null;
		this.key = key;
		this.declaredOrder = declaredOrder;
		this.name = name;
		this.fieldType = fieldType;
	}

	/**
	 * If this field is a direct struct type, returns it. Otherwise returns null (even if this is a collection of struct
	 * types, etc)
	 */
	public RpcStruct getStructType() {
		return structType;
	}

	public String getGetterName() {
		if ((fieldType instanceof RpcTypeNative) && ((RpcTypeNative) fieldType).getTypeKey().getThriftType() == TType.BOOL) {
			return "is" + getCamelCaseName();
		}
		return "get" + getCamelCaseName();
	}

	public String getSetterName() {
		return "set" + getCamelCaseName();
	}

	public String getIsSetName() {
		return "isSet" + getCamelCaseName();
	}

	public String getUnsetName() {
		return "unset" + getCamelCaseName();
	}

	private String getCamelCaseName() {
		return Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}

	public int getKey() {
		return key;
	}

	public int getDeclaredOrder() {
		return declaredOrder;
	}

	public String getName() {
		return name;
	}

	public String getThriftType() {
		return getThriftType(fieldType.getTypeKey().getThriftType());
	}

	public static String getThriftType(byte type) {
		switch (type) {
		case TType.VOID:
			return "Void";
		case TType.BOOL:
			return "Bool";
		case TType.BYTE:
			return "Byte";
		case TType.DOUBLE:
			return "Double";
		case TType.I16:
			return "I16";
		case TType.I32:
			return "I32";
		case TType.I64:
			return "I64";
		case TType.STRING:
			return "String";
		case TType.STRUCT:
			return "Struct";
		case TType.MAP:
			return "Map";
		case TType.SET:
			return "Set";
		case TType.LIST:
			return "List";
		}

		throw new RuntimeException("Unexpected type: " + type);
	}

	public RpcTypeBase getType() {
		return fieldType;
	}
}
