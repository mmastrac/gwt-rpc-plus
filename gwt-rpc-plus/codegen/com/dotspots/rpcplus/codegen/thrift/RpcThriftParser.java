package com.dotspots.rpcplus.codegen.thrift;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hive.serde2.dynamic_type.DynamicSerDeService;
import org.apache.hadoop.hive.serde2.dynamic_type.DynamicSerDeSimpleNode;
import org.apache.hadoop.hive.serde2.dynamic_type.DynamicSerDeStructBase;
import org.apache.hadoop.hive.serde2.dynamic_type.ParseException;
import org.apache.hadoop.hive.serde2.dynamic_type.thrift_grammar;

public class RpcThriftParser {
	private ThriftGrammar parseTree;
	private DynamicSerDeStructBase bt;

	private static class ThriftGrammar extends thrift_grammar {
		public ThriftGrammar(InputStream is, List<String> includePath, String lang) {
			super(is, includePath, lang);
		}

		public Map<String, DynamicSerDeSimpleNode> getTypes() {
			return types;
		}

		public Map<String, DynamicSerDeSimpleNode> getTables() {
			return tables;
		}

		public Map<String, DynamicSerDeService> getServices() {
			return services;
		}

	}

	public RpcThriftParser(InputStream is, List<String> includePath) throws RpcParseException {
		this.parseTree = new ThriftGrammar(is, includePath, "java");
		try {
			this.parseTree.Start();
		} catch (ParseException e) {
			throw new RpcParseException(e);
		}
	}

	public List<RpcInterface> computeInterfaces(RpcTypeFactory typeFactory) throws RpcParseException {
		List<RpcInterface> interfaces = new ArrayList<RpcInterface>();

		for (Map.Entry<String, DynamicSerDeService> entry : parseTree.getServices().entrySet()) {
			final DynamicSerDeService node = entry.getValue();

			RpcInterface iface = new RpcInterface(typeFactory, node);
			interfaces.add(iface);
		}

		return interfaces;
	}
}
