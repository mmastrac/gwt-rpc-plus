package com.dotspots.rpcplus.codegen.thrift;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hive.serde2.dynamic_type.DynamicSerDeEnum;
import org.apache.hadoop.hive.serde2.dynamic_type.DynamicSerDeStructBase;
import org.apache.hadoop.hive.serde2.dynamic_type.DynamicSerDeTypeBase;
import org.apache.hadoop.hive.serde2.dynamic_type.DynamicSerDeTypeBinary;
import org.apache.hadoop.hive.serde2.dynamic_type.DynamicSerDeTypeList;
import org.apache.hadoop.hive.serde2.dynamic_type.DynamicSerDeTypeMap;
import org.apache.hadoop.hive.serde2.dynamic_type.DynamicSerDeTypeSet;
import org.apache.hadoop.hive.serde2.dynamic_type.DynamicSerDeXception;
import org.apache.thrift.protocol.TType;

public class RpcTypeFactory {
	private final Map<DynamicSerDeTypeBase, RpcTypeBase> types = new HashMap<DynamicSerDeTypeBase, RpcTypeBase>();
	private final String suffixPackage;

	public RpcTypeFactory() {
		this(null);
	}

	public RpcTypeFactory(String suffixPackage) {
		this.suffixPackage = suffixPackage == null ? "" : suffixPackage;
	}

	public String getSuffixPackage() {
		return suffixPackage;
	}

	public RpcTypeBase get(DynamicSerDeTypeBase type) throws RpcParseException {
		if (types.containsKey(type)) {
			return types.get(type);
		}

		if (type instanceof DynamicSerDeTypeSet) {
			return new RpcTypeSet(get(((DynamicSerDeTypeSet) type).getElementType()));
		}

		if (type instanceof DynamicSerDeTypeList) {
			return new RpcTypeList(get(((DynamicSerDeTypeList) type).getElementType()));
		}

		if (type instanceof DynamicSerDeTypeMap) {
			return new RpcTypeMap(get(((DynamicSerDeTypeMap) type).getKeyType()), get(((DynamicSerDeTypeMap) type).getValueType()));
		}

		if (type instanceof DynamicSerDeStructBase) {
			final DynamicSerDeStructBase structBase = (DynamicSerDeStructBase) type;
			RpcStruct struct = new RpcStruct(this, structBase.getName(), structBase.getNamespace(),
					structBase instanceof DynamicSerDeXception, structBase.getFieldList().getChildren());
			RpcTypeStruct structType = new RpcTypeStruct(struct);

			types.put(structBase, structType);
			return structType;
		}

		if (type instanceof DynamicSerDeEnum) {
			DynamicSerDeEnum rawEnumType = (DynamicSerDeEnum) type;
			RpcStruct struct = new RpcStruct(this, rawEnumType.getName(), rawEnumType.getNamespace(), rawEnumType.getFieldList()
					.getChildren());
			RpcTypeEnum enumType = new RpcTypeEnum(struct);

			types.put(rawEnumType, enumType);
			return enumType;
		}

		DynamicSerDeTypeBase typeBase = type;
		switch (typeBase.getType()) {
		case TType.BOOL:
			return new RpcTypeNative(RpcTypeKey.BOOL);
		case TType.I16:
			return new RpcTypeNative(RpcTypeKey.I16);
		case TType.I32:
			return new RpcTypeNative(RpcTypeKey.I32);
		case TType.I64:
			return new RpcTypeNative(RpcTypeKey.I64);
		case TType.STRING:
			if (typeBase instanceof DynamicSerDeTypeBinary) {
				return new RpcTypeNative(RpcTypeKey.BINARY);
			}
			return new RpcTypeNative(RpcTypeKey.STRING);
		case TType.DOUBLE:
			return new RpcTypeNative(RpcTypeKey.DOUBLE);
		}

		throw new RpcParseException("Unknown type: " + type);
	}

}
