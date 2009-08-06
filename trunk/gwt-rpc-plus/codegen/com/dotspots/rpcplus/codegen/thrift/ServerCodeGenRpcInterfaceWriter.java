/**
 * 
 */
package com.dotspots.rpcplus.codegen.thrift;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

final class ServerCodeGenRpcInterfaceWriter implements RpcInterfaceWriter {
	private final PrintWriter printWriter;

	ServerCodeGenRpcInterfaceWriter(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}

	public void endClass(RpcInterface iface) {
		printWriter.println("}");
	}

	public void startClass(RpcInterface iface) {
		printWriter.println("@SuppressWarnings(\"unused\")");
		printWriter.println("public final class " + iface.getClassName() + "Json implements JSONServlet {");
		printWriter.println("    " + iface.getFullyQualifiedClassName() + ".Iface service;");
		printWriter.println();
		printWriter.println("    public void processRequest(TBaseJSONProtocol in, TProtocol out) throws TException {");
		printWriter.println("        in.readListBegin();");
		printWriter.println("        int version = in.readI32();");
		printWriter.println("        in.hasNext();");
		printWriter.println("        String call = in.readString();");
		printWriter.println("        in.hasNext();");
		if (iface.getRequestContext() != null) {
			printWriter.println("        service.__setContext(read" + iface.getRequestContext().getClassName() + "(in));");
			printWriter.println("        in.hasNext();");
		}
		for (RpcMethod method : iface.getMethods()) {
			printWriter.println("        if (call.equals(\"" + method.getName() + "\")) {");
			printWriter.println("            " + method.getName() + "(in, out);");
			printWriter.println("            return;");
			printWriter.println("        }");
		}

		printWriter.println("    }");
		printWriter.println();

		printWriter.println("    public void setService(" + iface.getFullyQualifiedClassName() + ".Iface service) {");
		printWriter.println("        this.service = service;");
		printWriter.println("    }");
		printWriter.println();
	}

	public void writePackage(String packageName) {
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
	}

	public void writeMethod(RpcInterface iface, RpcMethod method) {
		List<String> args = new ArrayList<String>();

		for (RpcField field : method.getArgsInDeclaredOrder()) {
			args.add("args." + field.getGetterName() + "()");
		}

		printWriter.println("    private final void " + method.getName() + "(TBaseJSONProtocol in, TProtocol out) throws TException {");
		printWriter.println("        " + iface.getClassName() + "." + method.getName() + "_args args = read" + iface.getClassName() + "_"
				+ method.getName() + "_args(in);");
		printWriter.println("        " + iface.getClassName() + "." + method.getName() + "_result result = new " + iface.getClassName()
				+ "." + method.getName() + "_result();");
		printWriter.println("        try {");
		if (method.getResult().getTypeKey() == RpcTypeKey.VOID) {
			printWriter.println("            service." + method.getName() + "(" + StringUtils.join(args, ", ") + ");");
		} else {
			printWriter.println("            " + getType(method.getResult(), false) + " methodResult = service." + method.getName() + "("
					+ StringUtils.join(args, ", ") + ");");
			printWriter.println("            result.setSuccess(methodResult);");
		}
		printWriter.println("            out.writeListBegin(null);");
		printWriter.println("            out.writeI32(0); // Version");
		printWriter.println("            out.writeI32(0); // Success");
		printWriter.println("            write" + iface.getClassName() + "_" + method.getName() + "_result(out, result);");
		writeResponseContext(iface);
		printWriter.println("            out.writeListEnd();");

		int index = 1;
		for (RpcStruct type : method.getExceptionTypes()) {
			printWriter.println("        } catch (" + type.getFullyQualifiedClassName() + " e) {");
			printWriter.println("            out.writeListBegin(null);");
			printWriter.println("            out.writeI32(0); // Version");
			printWriter.println("            out.writeI32(" + index + "); // Failure");
			printWriter.println("            result.setFieldValue(" + index + ", e);");
			printWriter.println("            write" + iface.getClassName() + "_" + method.getName() + "_result(out, result);");
			writeResponseContext(iface);
			printWriter.println("            out.writeListEnd();");

			index++;
		}

		printWriter.println("        } catch (Throwable t) {");
		printWriter.println("        }");
		printWriter.println("    };");
		printWriter.println();
	}

	private void writeResponseContext(RpcInterface iface) {
		if (iface.getResponseContext() != null) {
			printWriter.println("            " + iface.getResponseContext().getClassName() + " responseContext = service.__getContext();");
			printWriter.println("            if (responseContext != null) {");
			printWriter.println("                write" + iface.getResponseContext().getClassName() + "(out, responseContext);");
			printWriter.println("            }");
		}
	}

	public void writeType(RpcStruct type) {
		printWriter.println("    public static final " + type.getPackageName() + "." + type.getClassName(true) + " read"
				+ type.getClassName(false) + "(TBaseJSONProtocol protocol) throws TException {");
		printWriter.println("         " + type.getPackageName() + "." + type.getClassName(true) + " obj = new " + type.getPackageName()
				+ "." + type.getClassName(true) + "();");
		printWriter.println("         if (protocol.readStructBegin()) {");
		printWriter.println("             int fieldId;");
		printWriter.println("             while((fieldId = protocol.readFieldId()) != -1) {");
		printWriter.println("                 switch (fieldId) {");

		for (RpcField field : type.getOrderedFields()) {
			printWriter.println("                 case " + field.getKey() + ": {");
			writeReader(type.getTypeFactory(), field.getType(), "value", 0);
			printWriter.println("                     obj." + field.getSetterName() + "(value0);");
			printWriter.println("                     break;");
			printWriter.println("                 }");
		}

		printWriter.println("                 default:");
		printWriter.println("                     protocol.skip();");

		printWriter.println("                 }");
		printWriter.println("             }");
		printWriter.println("             protocol.readStructEnd();");
		printWriter.println("         }");
		printWriter.println("         return obj;");
		printWriter.println("    }");
		printWriter.println();

		printWriter.println("    private static final void write" + type.getClassName(false) + "(TProtocol protocol, "
				+ type.getPackageName() + "." + type.getClassName(true) + " obj) throws TException {");

		printWriter.println("        protocol.writeStructBegin(null);");
		for (RpcField field : type.getOrderedFields()) {
			printWriter.println("        if (obj." + field.getIsSetName() + "()) {");
			printWriter.println("            protocol.writeI32(" + field.getKey() + ");");
			printWriter.println("            " + getType(field.getType(), false) + " value0 = obj." + field.getGetterName() + "();");
			writeWriter(type.getTypeFactory(), field.getType(), "value", 0);
			printWriter.println("        }");
		}
		printWriter.println("        protocol.writeStructEnd();");
		printWriter.println("    }");
		printWriter.println();
	}

	private void writeReader(RpcTypeFactory typeFactory, RpcTypeBase fieldType, String name, int level) {
		String indent = "                 ";
		for (int i = 0; i < level; i++) {
			indent += "    ";
		}

		final String prefix = indent + getType(fieldType, false) + " " + name + level + " = ";

		switch (fieldType.getTypeKey()) {
		case STRUCT:
			RpcStruct structType = ((RpcTypeStruct) fieldType).getStruct();
			printWriter.println(prefix + "read" + structType.getClassName(false) + "(protocol);");

			break;
		case SET:
			printWriter.println(prefix + "new Hash" + getType(fieldType, true) + "();");
			RpcTypeSet setMetaData = (RpcTypeSet) fieldType;
			printWriter.println(indent + "protocol.readSetBegin();");
			printWriter.println(indent + "while (protocol.hasNext()) {");
			writeReader(typeFactory, setMetaData.getElementType(), name, level + 1);
			printWriter.println(indent + "    " + name + level + ".add(" + name + (level + 1) + ");");
			printWriter.println(indent + "}");
			printWriter.println(indent + "protocol.readSetEnd();");
			return;
		case MAP:
			printWriter.println(prefix + "new Hash" + getType(fieldType, true) + "();");
			RpcTypeMap mapMetaData = (RpcTypeMap) fieldType;
			printWriter.println(indent + "protocol.readMapBegin();");
			printWriter.println(indent + "while (protocol.hasNext()) {");
			writeReader(typeFactory, mapMetaData.getKeyType(), "key", level + 1);
			writeReader(typeFactory, mapMetaData.getValueType(), "value", level + 1);
			printWriter.println(indent + "    " + name + level + ".put(key" + (level + 1) + ", value" + (level + 1) + ");");
			printWriter.println(indent + "}");
			printWriter.println(indent + "protocol.readMapEnd();");
			return;
		case LIST:
			printWriter.println(prefix + "new Array" + getType(fieldType, true) + "();");
			RpcTypeList listMetaData = (RpcTypeList) fieldType;
			printWriter.println(indent + "protocol.readListBegin();");
			printWriter.println(indent + "while (protocol.hasNext()) {");
			writeReader(typeFactory, listMetaData.getElementType(), name, level + 1);
			printWriter.println(indent + "    " + name + level + ".add(" + name + (level + 1) + ");");
			printWriter.println(indent + "}");
			printWriter.println(indent + "protocol.readListEnd();");
			break;
		default:
			printWriter.println(prefix + "protocol.read" + fieldType.getTypeKey().getThriftTypeString() + "();");
		}
	}

	private void writeWriter(RpcTypeFactory typeFactory, RpcTypeBase fieldType, String name, int level) {
		String indent = "             ";
		for (int i = 0; i < level; i++) {
			indent += "    ";
		}

		switch (fieldType.getTypeKey()) {
		case STRUCT:
			RpcStruct structType = ((RpcTypeStruct) fieldType).getStruct();
			printWriter.println(indent + "write" + structType.getClassName(false) + "(protocol, " + name + level + ");");

			break;
		case SET:
			RpcTypeSet setMetaData = (RpcTypeSet) fieldType;
			printWriter.println(indent + "protocol.writeSetBegin(null);");
			printWriter.println(indent + "for (" + getType(setMetaData.getElementType(), false) + " " + name + (level + 1) + " : " + name
					+ level + ") {");
			writeWriter(typeFactory, setMetaData.getElementType(), name, level + 1);
			printWriter.println(indent + "}");
			printWriter.println(indent + "protocol.writeSetEnd();");
			return;
		case MAP:
			RpcTypeMap mapMetaData = (RpcTypeMap) fieldType;
			printWriter.println(indent + "protocol.writeMapBegin(null);");
			printWriter.println(indent + "for (Map.Entry<" + getType(mapMetaData.getKeyType(), false) + ", "
					+ getType(mapMetaData.getValueType(), false) + "> entry" + level + " : " + name + level + ".entrySet()) {");
			printWriter.println(indent + "    " + getType(mapMetaData.getKeyType(), false) + " key" + (level + 1) + " = entry" + level
					+ ".getKey();");
			printWriter.println(indent + "    " + getType(mapMetaData.getValueType(), false) + " value" + (level + 1) + " = entry" + level
					+ ".getKey();");
			writeWriter(typeFactory, mapMetaData.getKeyType(), "key", level + 1);
			writeWriter(typeFactory, mapMetaData.getValueType(), "value", level + 1);
			printWriter.println(indent + "}");
			printWriter.println(indent + "protocol.writeMapEnd();");
			return;
		case LIST:
			RpcTypeList listMetaData = (RpcTypeList) fieldType;
			printWriter.println(indent + "protocol.writeListBegin(null);");
			printWriter.println(indent + "for (" + getType(listMetaData.getElementType(), false) + " " + name + (level + 1) + " : " + name
					+ level + ") {");
			writeWriter(typeFactory, listMetaData.getElementType(), name, level + 1);
			printWriter.println(indent + "}");
			printWriter.println(indent + "protocol.writeListEnd();");
			break;
		default:
			printWriter.println(indent + "protocol.write" + fieldType.getTypeKey().getThriftTypeString() + "(" + name + level + ");");
		}
	}

	private String getType(RpcTypeBase valueMetaData, boolean boxed) {
		JavaTypeVisitor visitor = new JavaTypeVisitor(boxed, false);
		valueMetaData.visit(visitor);
		return visitor.toString();
	}
}