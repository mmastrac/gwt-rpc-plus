package com.dotspots.rpcplus.codegen.thrift;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.hadoop.hive.serde2.dynamic_type.DynamicSerDeField;
import org.apache.hadoop.hive.serde2.dynamic_type.DynamicSerDeFunction;

/**
 * Represents a service method from a service interface in a thrift IDL file.
 */
public class RpcMethod {
	private RpcTypeBase result;
	private RpcStruct resultStruct;
	private RpcStruct argType;
	private List<RpcStruct> exceptionTypes;
	private int methodId;
	private final DynamicSerDeFunction func;

	public RpcMethod(int methodId, RpcInterface iface, RpcTypeFactory typeFactory, DynamicSerDeFunction func) throws RpcParseException {
		this.methodId = methodId;
		this.func = func;

		if (func.getReturnType().getType() == null) {
			result = new RpcTypeNative(RpcTypeKey.VOID);
		} else {
			result = typeFactory.get(func.getReturnType().getType().getMyType());
		}

		argType = new RpcStruct(typeFactory, iface.getClassName() + "$" + func.getName() + "_args", func.getNamespace(), false,
				func.getFieldList().getChildren());

		exceptionTypes = new ArrayList<RpcStruct>();
		if (func.getExceptions() != null) {
			for (DynamicSerDeField field : func.getExceptions().getChildren()) {
				// Assume exception types have to be structures
				RpcTypeStruct struct = (RpcTypeStruct) typeFactory.get(field.getFieldType().getMyType());
				exceptionTypes.add(struct.getStruct());
			}
		}

		final boolean isVoid = result.getTypeKey() == RpcTypeKey.VOID;

		RpcField[] resultFields = new RpcField[isVoid ? 0 : 1];
		if (!isVoid) {
			resultFields[0] = new RpcField(0, 0, "success", result);
		}

		resultStruct = new RpcStruct(typeFactory, iface.getClassName() + "$" + func.getName() + "_result", func.getNamespace(), false,
				resultFields);

	}

	public int getMethodId() {
		return methodId;
	}

	public String getName() {
		return func.getName();
	}

	public RpcTypeBase getResult() {
		return result;
	}

	public List<RpcField> getArgs() {
		return argType.getOrderedFields();
	}

	public List<RpcField> getArgsInDeclaredOrder() {
		return argType.getDeclaredOrderedFields();
	}

	public List<RpcStruct> getExceptionTypes() {
		return exceptionTypes;
	}

	public Collection<RpcStruct> getSerializationTypes() {
		HashSet<RpcStruct> types = new HashSet<RpcStruct>();

		types.addAll(resultStruct.getSerializationTypes());
		types.addAll(result.getSerializationTypes());
		types.addAll(argType.getSerializationTypes());
		for (RpcStruct type : exceptionTypes) {
			types.addAll(type.getSerializationTypes());
		}

		return types;
	}
}
