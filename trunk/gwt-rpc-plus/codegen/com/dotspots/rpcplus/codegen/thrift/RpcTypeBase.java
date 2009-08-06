package com.dotspots.rpcplus.codegen.thrift;

import java.util.Collection;
import java.util.HashSet;

public abstract class RpcTypeBase {
	public Collection<? extends RpcStruct> getSerializationTypes() {
		final HashSet<RpcStruct> types = new HashSet<RpcStruct>();
		visit(new TypeVisitorAdapter() {
			@Override
			public void visitStruct(RpcTypeStruct rpcTypeStruct) {
				types.addAll(rpcTypeStruct.getStruct().getSerializationTypes());
			}
		});

		return types;
	}

	public abstract RpcTypeKey getTypeKey();

	public abstract void visit(TypeVisitor typeVisitor);
}
