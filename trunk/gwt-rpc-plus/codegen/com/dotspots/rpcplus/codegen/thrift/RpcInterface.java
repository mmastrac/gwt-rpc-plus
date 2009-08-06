package com.dotspots.rpcplus.codegen.thrift;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.hadoop.hive.serde2.dynamic_type.DynamicSerDeFunction;
import org.apache.hadoop.hive.serde2.dynamic_type.DynamicSerDeService;

/**
 * Represents a service interface from a Thrift IDL file.
 */
public class RpcInterface {
	private List<RpcMethod> methods = new ArrayList<RpcMethod>();
	private final RpcTypeFactory typeFactory;
	private RpcStruct requestContext;
	private RpcStruct responseContext;
	private final DynamicSerDeService svc;

	public RpcInterface(RpcTypeFactory typeFactory, DynamicSerDeService svc) throws RpcParseException {
		this.typeFactory = typeFactory;
		this.svc = svc;
		int methodId = 0;
		for (DynamicSerDeFunction func : svc.getFunctions()) {
			// __context is a magic method that tells us what the out-of-band context objects are for an interface
			if (func.getName().equals("__getContext")) {
				RpcMethod contextMethod = new RpcMethod(-1, this, typeFactory, func);

				if (!(contextMethod.getResult() instanceof RpcTypeStruct)) {
					throw new RpcParseException("__getContext must return a structure");
				}

				responseContext = ((RpcTypeStruct) contextMethod.getResult()).getStruct();
			} else if (func.getName().equals("__setContext")) {
				RpcMethod contextMethod = new RpcMethod(-1, this, typeFactory, func);
				if (contextMethod.getArgs().size() != 1) {
					throw new RpcParseException("One (and only one) context parameter must be provided");
				}

				requestContext = contextMethod.getArgs().get(0).getStructType();
				if (requestContext == null) {
					throw new RpcParseException("__setContext parameter must be a structure");
				}
			} else {
				methods.add(new RpcMethod(methodId++, this, typeFactory, func));
			}
		}
	}

	/**
	 * Gets the request context, or null if none.
	 */
	public RpcStruct getRequestContext() {
		return requestContext;
	}

	/**
	 * Gets the response context, or null if none.
	 */
	public RpcStruct getResponseContext() {
		return responseContext;
	}

	public RpcTypeFactory getTypeFactory() {
		return typeFactory;
	}

	public String getFilename() {
		return getFilename("");
	}

	public String getFilename(String suffix) {
		return getFullyQualifiedClassName().replace('.', '/') + suffix + ".java";
	}

	public String getPackageName() {
		return svc.getNamespace() + typeFactory.getSuffixPackage();
	}

	public String getClassName() {
		return svc.getName();
	}

	public String getFullyQualifiedClassName() {
		return getPackageName() + "." + getClassName();
	}

	public List<RpcMethod> getMethods() {
		return methods;
	}

	public Collection<RpcStruct> getSerializationTypes() {
		HashSet<RpcStruct> types = new HashSet<RpcStruct>();
		for (RpcMethod method : methods) {
			types.addAll(method.getSerializationTypes());
		}

		if (requestContext != null) {
			types.add(requestContext);
		}
		if (responseContext != null) {
			types.add(responseContext);
		}

		return types;
	}

	public void write(RpcInterfaceWriter writer) {
		writer.writePackage(getPackageName());

		writer.startClass(this);

		for (RpcMethod method : methods) {
			writer.writeMethod(this, method);
		}

		for (RpcStruct type : getSerializationTypes()) {
			writer.writeType(type);
		}

		writer.endClass(this);
	}
}
