/**
 * 
 */
package com.dotspots.rpcplus.codegen.thrift;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

final class GwtCodeGenRpcObjectWriter extends GwtCodeGenBase implements RpcObjectWriter {
	private final PrintWriter printWriter;

	GwtCodeGenRpcObjectWriter(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}

	public void endClass(RpcStruct type, String className) {
		printWriter.println("}");
	}

	public void startClass(RpcStruct type, String className) {
		printWriter.println("@SuppressWarnings(\"unused\")");
		printWriter.println("public final class " + className + " extends "
				+ (type.isException() ? "Exception" : ClassNames.BASEJSRPCOBJECT_CLASSNAME) + " {");

		if (type.getConstants().size() > 0) {
			for (Map.Entry<String, Integer> entry : type.getConstants().entrySet()) {
				printWriter.println("    public static final int " + entry.getKey() + " = " + entry.getValue() + ";");
			}
			printWriter.println();
		}

		if (type.isException()) {
			printWriter.println("    private " + ClassNames.JAVASCRIPTOBJECT_CLASSNAME + " e;");
			printWriter.println();
			printWriter.println("    // GWT requires a protected constructor");
			printWriter.println("    public " + className + "(" + ClassNames.JAVASCRIPTOBJECT_CLASSNAME + " e) {");
			printWriter.println("        this.e = e;");
			printWriter.println("    }");
		} else {
			printWriter.println("    // GWT requires a protected constructor");
			printWriter.println("    protected " + className + "() {");
			printWriter.println("    }");
		}
		printWriter.println();
	}

	public void writeConstructor(RpcStruct type) {
		if (!type.isException()) {
			printWriter.println("    /* Factory method */");
			printWriter.println("    public static " + type.getClassName(false) + " create() {");
			if (type.isList()) {
				printWriter.println("        return " + ClassNames.JAVASCRIPTOBJECT_CLASSNAME + ".createArray().cast();");
			} else {
				printWriter.println("        return " + ClassNames.JAVASCRIPTOBJECT_CLASSNAME + ".createObject().cast();");
			}
			printWriter.println("    }");
			printWriter.println();
		}

		final List<RpcField> fields = type.getOrderedFields();

		if (fields.size() > 0) {
			List<String> argDefinitions = new ArrayList<String>();
			int maxField = 0;
			for (RpcField field : fields) {
				argDefinitions.add(getType(field) + " " + field.getName());
				maxField = Math.max(maxField, field.getKey());
			}

			StringBuilder nativeCode = new StringBuilder();

			if (type.isList()) {
				List<String> args = new ArrayList<String>(maxField + 1);
				for (int i = 0; i < maxField + 1; i++) {
					args.add("");
				}

				for (RpcField entry : fields) {
					args.set(entry.getKey(), entry.getName());
				}

				nativeCode.append('[').append(StringUtils.join(args, ",")).append(']');
			} else {
				nativeCode.append('{');
				for (RpcField entry : fields) {
					nativeCode.append('"').append(entry.getKey()).append("\": ").append(entry.getName()).append(", ");
				}
				nativeCode.append('}');
			}

			boolean hasLongField = false;
			for (RpcField field : fields) {
				if (field.getType().getTypeKey() == RpcTypeKey.I64) {
					hasLongField = true;
					break;
				}
			}

			if (hasLongField) {
				System.err.println("WARNING: Unable to generate constructor for type " + type.getClassName()
						+ " because it has a long field");
				printWriter.println("    // TODO: Could not generate constructor because of long field");
			} else {
				printWriter.println("    /* Factory method, strongly dependent on order of fields */");
				printWriter.println("    public static native " + type.getClassName(false) + " create("
						+ StringUtils.join(argDefinitions, ", ") + ") /*-{");
				if (type.isException()) {
					printWriter.println("        return @" + type.getFullyQualifiedClassName()
							+ "::new(Lcom/google/gwt/core/client/JavaScriptObject;)(" + nativeCode + ");");
				} else {
					printWriter.println("        return " + nativeCode + ";");
				}
				printWriter.println("    }-*/;");
			}

			printWriter.println();
		}
	}

	public void writePackage(String packageName) {
		printWriter.println("package " + packageName + ";");
		printWriter.println();
		printWriter.println("import com.dotspots.rpcplus.client.transport.*;");
		printWriter.println("import com.dotspots.rpcplus.client.jsonrpc.*;");
		printWriter.println("import com.dotspots.rpcplus.client.jscollections.*;");
		printWriter.println();
	}

	public void writeGetter(RpcStruct type, RpcField field) {
		if (field.getType().getTypeKey() == RpcTypeKey.I64) {
			printWriter.println("    public " + getType(field) + " " + field.getGetterName() + "() {");
			printWriter.println("         return RpcUtils.fromDoubles(" + field.getGetterName() + "0());");
			printWriter.println("    }");
			printWriter.println();

			printWriter.println("    private native " + ClassNames.JAVASCRIPTOBJECT_CLASSNAME + " " + field.getGetterName() + "0() /*-{");
			printWriter.println("         return " + getJavaScriptInstance(type) + "[" + field.getKey() + "];");
			printWriter.println("    }-*/;");
		} else {
			printWriter.println("    public native " + getType(field) + " " + field.getGetterName() + "() /*-{");
			if (field.getType().getTypeKey() == RpcTypeKey.BOOL) {
				printWriter.println("         return !!" + getJavaScriptInstance(type) + "[" + field.getKey() + "];");
			} else {
				printWriter.println("         return " + getJavaScriptInstance(type) + "[" + field.getKey() + "];");
			}
			printWriter.println("    }-*/;");
		}

		printWriter.println();
	}

	public void writeSetter(RpcStruct type, RpcField field) {
		if (field.getType().getTypeKey() == RpcTypeKey.I64) {
			printWriter.println("    public void " + field.getSetterName() + "(" + getType(field) + " " + field.getName() + ") {");
			printWriter.println("         " + field.getSetterName() + "0(RpcUtils.toDoubles(" + field.getName() + "));");
			printWriter.println("    }");
			printWriter.println();

			printWriter.println("    private native void " + field.getSetterName() + "0(" + ClassNames.JAVASCRIPTOBJECT_CLASSNAME + " "
					+ field.getName() + ") /*-{");
			printWriter.println("         " + getJavaScriptInstance(type) + "[" + field.getKey() + "] = " + field.getName() + ";");
			printWriter.println("    }-*/;");
		} else {
			printWriter.println("    public native void " + field.getSetterName() + "(" + getType(field) + " " + field.getName() + ") /*-{");
			printWriter.println("         " + getJavaScriptInstance(type) + "[" + field.getKey() + "] = " + field.getName() + ";");
			printWriter.println("    }-*/;");
		}

		printWriter.println();
	}

	public void writeIsSet(RpcStruct type, RpcField field) {
		printWriter.println("    public native boolean " + field.getIsSetName() + "() /*-{");
		printWriter.println("         return " + getJavaScriptInstance(type) + "[" + field.getKey() + "] != null;");
		printWriter.println("    }-*/;");
		printWriter.println();
	}

	public void writeUnset(RpcStruct type, RpcField field) {
		printWriter.println("    public native void " + field.getUnsetName() + "() /*-{");
		printWriter.println("         delete " + getJavaScriptInstance(type) + "[" + field.getKey() + "];");
		printWriter.println("    }-*/;");
		printWriter.println();
	}

	private String getJavaScriptInstance(RpcStruct type) {
		if (type.isException()) {
			return "this.@" + type.getFullyQualifiedClassName() + "::e";
		} else {
			return "this";
		}
	}
}