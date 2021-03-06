/**
 * 
 */
package com.dotspots.rpcplus.codegen.thrift;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

final class ServerCodeGenRpcInterfaceWriter implements RpcInterfaceWriter {
	private final IndentingPrintWriter printWriter;

	ServerCodeGenRpcInterfaceWriter(PrintWriter printWriter) {
		this.printWriter = new IndentingPrintWriter(printWriter);
	}

	public void endClass(RpcInterface iface) {
		printWriter.unindent();
		printWriter.println("}");
		printWriter.endBlock();
	}

	public void startClass(RpcInterface iface) {
		printWriter.startBlock();

		printWriter.println("@SuppressWarnings(\"unused\")");
		printWriter.println("public final class " + iface.getClassName() + "Json implements JSONServlet {");
		printWriter.indent();
		printWriter.println(iface.getFullyQualifiedClassName() + ".Iface service;");
		printWriter.println();
		printWriter.println("public void processRequest(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {");
		printWriter.indent();
		printWriter.println("in.readListBegin();");
		printWriter.println("in.hasNext();");
		printWriter.println("int version = in.readI32();");
		printWriter.println("in.hasNext();");
		printWriter.println("String call = in.readString();");
		printWriter.println("in.hasNext();");
		if (iface.getRequestContext() == null) {
			printWriter.println("// No context");
			printWriter.println("in.readString();");
			printWriter.println("in.hasNext();");
		} else {
			printWriter.println("// Read request context");
			printWriter.println("service.__setContext(read" + iface.getRequestContext().getClassName() + "(in));");
			printWriter.println("in.hasNext();");
		}
		for (RpcMethod method : iface.getMethods()) {
			printWriter.println("if (call.equals(\"" + method.getName() + "\")) {");
			printWriter.indent();
			printWriter.println(method.getName() + "(in, out);");
			printWriter.println("return;");
			printWriter.unindent();
			printWriter.println("}");
		}

		printWriter.unindent();
		printWriter.println("}");
		printWriter.println();

		printWriter.println("public void setService(" + iface.getFullyQualifiedClassName() + ".Iface service) {");
		printWriter.indent();
		printWriter.println("this.service = service;");
		printWriter.unindent();
		printWriter.println("}");
		printWriter.println();
	}

	public void writePackage(String packageName) {
		printWriter.startBlock();

		printWriter.println("package " + packageName + ";");
		printWriter.println();
		printWriter.println("import java.util.ArrayList;");
		printWriter.println("import java.util.List;");
		printWriter.println("import java.util.HashMap;");
		printWriter.println("import java.util.Map;");
		printWriter.println("import java.util.HashSet;");
		printWriter.println("import java.util.Set;");
		printWriter.println("import java.util.Iterator;");
		printWriter.println();
		printWriter.println("import org.apache.thrift.TException;");
		printWriter.println("import org.apache.thrift.protocol.TProtocolUtil;");
		printWriter.println("import org.apache.thrift.protocol.TField;");
		printWriter.println("import org.apache.thrift.protocol.TType;");
		printWriter.println("import org.apache.thrift.protocol.TProtocol;");
		printWriter.println();
		printWriter.println("import com.dotspots.rpcplus.jsonrpc.thrift.*;");
		printWriter.println();

		printWriter.endBlock();
	}

	public void writeMethod(RpcInterface iface, RpcMethod method) {
		printWriter.startBlock();

		List<String> args = new ArrayList<String>();

		for (RpcField field : method.getArgsInDeclaredOrder()) {
			args.add("args." + field.getGetterName() + "()");
		}

		printWriter.println("private final void " + method.getName()
				+ "(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {");
		printWriter.indent();
		printWriter.println(iface.getClassName() + "." + method.getName() + "_args args = read" + iface.getClassName() + "_"
				+ method.getName() + "_args(in);");
		printWriter.println(iface.getClassName() + "." + method.getName() + "_result result = new " + iface.getClassName() + "."
				+ method.getName() + "_result();");
		printWriter.println("try {");
		printWriter.indent();
		if (method.getResult().getTypeKey() == RpcTypeKey.VOID) {
			printWriter.println("service." + method.getName() + "(" + StringUtils.join(args, ", ") + ");");
		} else {
			printWriter.println(getType(method.getResult(), false) + " methodResult = service." + method.getName() + "("
					+ StringUtils.join(args, ", ") + ");");
			printWriter.println("result.setSuccess(methodResult);");
		}
		printWriter.println("out.writeListBegin(null);");
		printWriter.println("out.writeI32(0); // Version");
		printWriter.println("out.writeI32(0); // Success");
		printWriter.println("write" + iface.getClassName() + "_" + method.getName() + "_result(out, result);");
		writeResponseContext(iface);
		printWriter.println("out.writeListEnd();");

		int index = 1;
		for (RpcStruct type : method.getExceptionTypes()) {
			printWriter.unindent();
			printWriter.println("} catch (" + type.getFullyQualifiedClassName() + " e) {");
			printWriter.indent();
			printWriter.println("out.writeListBegin(null);");
			printWriter.println("out.writeI32(0); // Version");
			printWriter.println("out.writeI32(" + index + "); // Failure");
			printWriter.println("result.setFieldValue(" + index + ", e);");
			printWriter.println("write" + iface.getClassName() + "_" + method.getName() + "_result(out, result);");
			writeResponseContext(iface);
			printWriter.println("out.writeListEnd();");

			index++;
		}

		printWriter.unindent();
		printWriter.println("} catch (Throwable t) {");
		printWriter.indent();
		printWriter.println("// Log unexpected exceptions");
		printWriter.println("java.util.logging.Logger.getLogger(\"" + iface.getFullyQualifiedClassName()
				+ "\").log(java.util.logging.Level.SEVERE, \"Unexpected error\", t);");
		printWriter.unindent();
		printWriter.println("}");
		printWriter.unindent();
		printWriter.println("};");
		printWriter.println();

		printWriter.endBlock();
	}

	private void writeResponseContext(RpcInterface iface) {
		printWriter.startBlock();

		if (iface.getResponseContext() != null) {
			printWriter.println(iface.getResponseContext().getClassName() + " responseContext = service.__getContext();");
			printWriter.println("if (responseContext != null) {");
			printWriter.indent();
			printWriter.println("write" + iface.getResponseContext().getClassName() + "(out, responseContext);");
			printWriter.unindent();
			printWriter.println("}");
		}

		printWriter.endBlock();
	}

	public void writeType(RpcStruct type) {
		printWriter.startBlock();

		printWriter.println("public static final " + type.getPackageName() + "." + type.getClassName(true) + " read"
				+ type.getClassName(false) + "(TBaseJSONProtocol protocol) throws TException {");
		printWriter.indent();
		printWriter.println(type.getPackageName() + "." + type.getClassName(true) + " obj = new " + type.getPackageName() + "."
				+ type.getClassName(true) + "();");
		printWriter.println("if (protocol.readStructBegin()) {");
		printWriter.indent();
		printWriter.println("int fieldId;");
		printWriter.println("while(protocol.hasNext()) {");
		printWriter.indent();
		printWriter.println("switch (protocol.readI32()) {");
		printWriter.indent();

		for (RpcField field : type.getOrderedFields()) {
			printWriter.println("case " + field.getKey() + ": {");
			printWriter.indent();
			writeReader(type.getTypeFactory(), field.getType(), "value", 0);
			printWriter.println("obj." + field.getSetterName() + "(value0);");
			printWriter.println("break;");
			printWriter.unindent();
			printWriter.println("}");
		}

		printWriter.println("default:");
		printWriter.indent();
		printWriter.println("protocol.skip();");
		printWriter.unindent();

		printWriter.unindent();
		printWriter.println("}");
		printWriter.unindent();
		printWriter.println("}");
		printWriter.unindent();
		printWriter.println("}");
		printWriter.println("return obj;");
		printWriter.unindent();
		printWriter.println("}");
		printWriter.println();

		printWriter.println("private static final void write" + type.getClassName(false) + "(TJSONProtocolWriter protocol, "
				+ type.getPackageName() + "." + type.getClassName(true) + " obj) throws TException {");

		printWriter.indent();
		printWriter.println("if (obj == null) {");
		printWriter.indent();
		printWriter.println("protocol.writeNull();");
		printWriter.println("return;");
		printWriter.unindent();
		printWriter.println("}");

		printWriter.println("protocol.writeStructBegin(null);");
		for (RpcField field : type.getOrderedFields()) {
			printWriter.println("if (obj." + field.getIsSetName() + "()) {");
			printWriter.indent();
			printWriter.println("protocol.writeI32(" + field.getKey() + ");");
			printWriter.println(getType(field.getType(), false) + " value0 = obj." + field.getGetterName() + "();");
			writeWriter(type.getTypeFactory(), field.getType(), "value", 0);
			printWriter.unindent();
			printWriter.println("}");
		}
		printWriter.println("protocol.writeStructEnd();");
		printWriter.unindent();
		printWriter.println("}");
		printWriter.println();

		printWriter.endBlock();
	}

	private void writeReader(RpcTypeFactory typeFactory, RpcTypeBase fieldType, String name, int level) {
		printWriter.startBlock();

		final String declaration = getType(fieldType, false) + " " + name + level + " = ";
		final String assignment = name + level + " = ";

		switch (fieldType.getTypeKey()) {
		case STRUCT:
			RpcStruct structType = ((RpcTypeStruct) fieldType).getStruct();
			printWriter.println(declaration + "read" + structType.getClassName(false) + "(protocol);");
			break;

		case SET:
			printWriter.println(declaration + "null;");
			printWriter.println("if (protocol.readSetBegin()) {");
			printWriter.indent();
			printWriter.println(assignment + "new Hash" + getType(fieldType, true) + "();");
			RpcTypeSet setMetaData = (RpcTypeSet) fieldType;
			printWriter.println("while (protocol.hasNext()) {");
			printWriter.indent();
			writeReader(typeFactory, setMetaData.getElementType(), name, level + 1);
			printWriter.println(name + level + ".add(" + name + (level + 1) + ");");
			printWriter.unindent();
			printWriter.println("}");
			printWriter.unindent();
			printWriter.println("}");
			break;

		case MAP:
			printWriter.println(declaration + "null;");
			printWriter.println("if (protocol.readMapBegin()) {");
			printWriter.indent();
			printWriter.println(assignment + "new Hash" + getType(fieldType, true) + "();");
			RpcTypeMap mapMetaData = (RpcTypeMap) fieldType;
			printWriter.println("while (protocol.hasNext()) {");
			printWriter.indent();
			writeReader(typeFactory, mapMetaData.getKeyType(), "key", level + 1);
			writeReader(typeFactory, mapMetaData.getValueType(), "value", level + 1);
			printWriter.println(name + level + ".put(key" + (level + 1) + ", value" + (level + 1) + ");");
			printWriter.unindent();
			printWriter.println("}");
			printWriter.unindent();
			printWriter.println("}");
			break;

		case LIST:
			printWriter.println(declaration + "null;");
			printWriter.println("if (protocol.readListBegin()) {");
			printWriter.indent();
			printWriter.println(assignment + "new Array" + getType(fieldType, true) + "();");
			RpcTypeList listMetaData = (RpcTypeList) fieldType;
			printWriter.println("while (protocol.hasNext()) {");
			printWriter.indent();
			writeReader(typeFactory, listMetaData.getElementType(), name, level + 1);
			printWriter.println(name + level + ".add(" + name + (level + 1) + ");");
			printWriter.unindent();
			printWriter.println("}");
			printWriter.unindent();
			printWriter.println("}");
			break;

		default:
			printWriter.println(declaration + "protocol.read" + fieldType.getTypeKey().getThriftTypeString() + "();");
		}

		printWriter.endBlock();
	}

	private void writeWriter(RpcTypeFactory typeFactory, RpcTypeBase fieldType, String name, int level) {
		printWriter.startBlock();

		switch (fieldType.getTypeKey()) {
		case STRUCT:
			RpcStruct structType = ((RpcTypeStruct) fieldType).getStruct();
			printWriter.println("write" + structType.getClassName(false) + "(protocol, " + name + level + ");");
			break;

		case SET:
			RpcTypeSet setMetaData = (RpcTypeSet) fieldType;
			printWriter.println("if (" + name + level + " == null) {");
			printWriter.indent();
			printWriter.println("protocol.writeNull();");
			printWriter.unindent();
			printWriter.println("} else {");
			printWriter.indent();
			printWriter.println("protocol.writeSetBegin(null);");
			printWriter.println("for (" + getType(setMetaData.getElementType(), false) + " " + name + (level + 1) + " : " + name + level
					+ ") {");
			printWriter.indent();
			writeWriter(typeFactory, setMetaData.getElementType(), name, level + 1);
			printWriter.unindent();
			printWriter.println("}");
			printWriter.println("protocol.writeSetEnd();");
			printWriter.unindent();
			printWriter.println("}");
			break;

		case MAP:
			RpcTypeMap mapMetaData = (RpcTypeMap) fieldType;
			printWriter.println("if (" + name + level + " == null) {");
			printWriter.indent();
			printWriter.println("protocol.writeNull();");
			printWriter.unindent();
			printWriter.println("} else {");
			printWriter.indent();
			printWriter.println("protocol.writeMapBegin(null);");
			printWriter.println("for (Map.Entry<" + getType(mapMetaData.getKeyType(), true) + ", "
					+ getType(mapMetaData.getValueType(), true) + "> entry" + level + " : " + name + level + ".entrySet()) {");
			printWriter.indent();
			printWriter.println(getType(mapMetaData.getKeyType(), false) + " key" + (level + 1) + " = entry" + level + ".getKey();");
			printWriter.println(getType(mapMetaData.getValueType(), false) + " value" + (level + 1) + " = entry" + level + ".getValue();");
			writeWriter(typeFactory, mapMetaData.getKeyType(), "key", level + 1);
			writeWriter(typeFactory, mapMetaData.getValueType(), "value", level + 1);
			printWriter.unindent();
			printWriter.println("}");
			printWriter.println("protocol.writeMapEnd();");
			printWriter.unindent();
			printWriter.println("}");
			break;

		case LIST:
			RpcTypeList listMetaData = (RpcTypeList) fieldType;
			printWriter.println("if (" + name + level + " == null) {");
			printWriter.indent();
			printWriter.println("protocol.writeNull();");
			printWriter.unindent();
			printWriter.println("} else {");
			printWriter.indent();
			printWriter.println("protocol.writeListBegin(null);");
			printWriter.println("for (" + getType(listMetaData.getElementType(), false) + " " + name + (level + 1) + " : " + name + level
					+ ") {");
			printWriter.indent();
			writeWriter(typeFactory, listMetaData.getElementType(), name, level + 1);
			printWriter.unindent();
			printWriter.println("}");
			printWriter.println("protocol.writeListEnd();");
			printWriter.unindent();
			printWriter.println("}");
			break;

		default:
			printWriter.println("protocol.write" + fieldType.getTypeKey().getThriftTypeString() + "(" + name + level + ");");
		}

		printWriter.endBlock();
	}

	private String getType(RpcTypeBase valueMetaData, boolean boxed) {
		JavaTypeVisitor visitor = new JavaTypeVisitor(boxed, false);
		valueMetaData.visit(visitor);
		return visitor.toString();
	}
}