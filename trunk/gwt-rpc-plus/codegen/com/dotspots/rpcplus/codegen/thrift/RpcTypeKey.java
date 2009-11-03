package com.dotspots.rpcplus.codegen.thrift;

import org.apache.thrift.protocol.TType;

public enum RpcTypeKey {
	VOID, BINARY, BOOL, BYTE, DOUBLE, I16, I32, I64, LIST, MAP, SET, STRING, STRUCT, ENUM;

	public String getThriftTypeString() {
		switch (this) {
		case VOID:
			return "Void";
		case BINARY:
			return "Binary";
		case BOOL:
			return "Bool";
		case BYTE:
			return "Byte";
		case DOUBLE:
			return "Double";
		case I16:
			return "I16";
		case I32:
			return "I32";
		case I64:
			return "I64";
		case STRING:
			return "String";
		case STRUCT:
			return "Struct";
		case MAP:
			return "Map";
		case SET:
			return "Set";
		case LIST:
			return "List";
		case ENUM:
			return "I32";
		}

		throw new RuntimeException("Unexpected type: " + this);
	}

	public byte getThriftType() {
		switch (this) {
		case VOID:
			return TType.VOID;
		case BINARY:
			return TType.STRING;
		case BOOL:
			return TType.BOOL;
		case BYTE:
			return TType.BYTE;
		case DOUBLE:
			return TType.DOUBLE;
		case I16:
			return TType.I16;
		case I32:
			return TType.I32;
		case I64:
			return TType.I64;
		case STRING:
			return TType.STRING;
		case STRUCT:
			return TType.STRUCT;
		case MAP:
			return TType.MAP;
		case SET:
			return TType.SET;
		case LIST:
			return TType.LIST;
		case ENUM:
			return TType.I32;
		}

		throw new RuntimeException("Unexpected type: " + this);
	}
}
