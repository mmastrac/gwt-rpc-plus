/**
 * 
 */
package com.dotspots.rpcplus.codegen.thrift;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.dotspots.rpcplus.client.jsonrpc.RpcException;
import com.dotspots.rpcplus.client.jsonrpc.thrift.ThriftClientStub;

final class GwtCodeGenRpcInterfaceWriter extends GwtCodeGenBase implements RpcInterfaceWriter {
	private final PrintWriter printWriter;

	GwtCodeGenRpcInterfaceWriter(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}

	public void endClass(RpcInterface iface) {
		printWriter.println("}");
	}

	public void startClass(RpcInterface iface) {
		printWriter.println("@SuppressWarnings(\"unused\")");
		printWriter.println("public final class " + iface.getClassName() + " extends " + ThriftClientStub.class.getName() + "<"
				+ iface.getClassName() + "> {");

		if (iface.getRequestContext() != null) {
			printWriter.println("    public void setRequestContext(" + iface.getRequestContext().getClassName() + " requestContext) {");
			printWriter.println("        this.requestContext = requestContext;");
			printWriter.println("    }");
			printWriter.println();
		}

		if (iface.getResponseContext() != null) {
			printWriter.println("    public " + iface.getResponseContext().getClassName() + " popResponseContext() {");
			printWriter.println("        " + iface.getResponseContext().getClassName() + " responseContext = this.responseContext.cast();");
			printWriter.println("        this.responseContext = null;");
			printWriter.println("        return responseContext;");
			printWriter.println("    }");
			printWriter.println();
		}

		printWriter.println("    public void onException(int methodId, AsyncCallback<?> asyncCallback, int responseCode, BaseJsRpcObject response) {");
		printWriter.println("        // Process exceptions per method");

		final int methodCount = iface.getMethods().size();
		printWriter.println("        switch (responseCode * " + methodCount + " + methodId) {");

		TreeSet<RpcStruct> possibleExceptions = new TreeSet<RpcStruct>();
		for (RpcMethod method : iface.getMethods()) {
			possibleExceptions.addAll(method.getExceptionTypes());
		}

		for (RpcMethod method : iface.getMethods()) {
			int index = 1;
			for (RpcStruct exceptionType : method.getExceptionTypes()) {
				printWriter.println("        case " + (index * methodCount + method.getMethodId()) + ":");
				printWriter.println("            asyncCallback.onFailure(new " + exceptionType.getFullyQualifiedClassName() + "(("
						+ ClassNames.JAVASCRIPTOBJECT_CLASSNAME + ")response.getFieldValue(" + index + ")));");
				printWriter.println("            break;");
				index++;
			}
		}

		printWriter.println("        default:");
		printWriter.println("            asyncCallback.onFailure(new " + RpcException.class.getName() + "(\"Unknown exception\"));");
		printWriter.println("            break;");
		printWriter.println("        }");
		printWriter.println("    }");
		printWriter.println();
	}

	public void writePackage(String packageName) {
		printWriter.println("package " + packageName + ";");
		printWriter.println();
		printWriter.println("import com.google.gwt.user.client.rpc.AsyncCallback;");
		printWriter.println("import com.dotspots.rpcplus.client.transport.*;");
		printWriter.println("import com.dotspots.rpcplus.client.jsonrpc.*;");
		printWriter.println("import com.dotspots.rpcplus.client.jscollections.*;");
		printWriter.println();
	}

	public void writeMethod(RpcInterface iface, RpcMethod method) {
		List<String> args = new ArrayList<String>();
		List<String> argsShort = new ArrayList<String>();
		for (RpcField field : method.getArgsInDeclaredOrder()) {
			args.add(getType(field) + " " + field.getName());
			argsShort.add(field.getName());
		}

		args.add("AsyncCallback<" + getType(method.getResult(), true) + "> callback");

		printWriter.println("    public void " + method.getName() + "(" + StringUtils.join(args, ", ") + ") {");
		final String argsClass = iface.getClassName() + "_" + method.getName() + "_args";
		printWriter.println("        call(" + method.getMethodId() + ", \"" + method.getName() + "\", " + argsClass + ".create("
				+ StringUtils.join(argsShort, ", ") + "), callback);");
		printWriter.println("    };");
		printWriter.println();
	}

	public void writeType(RpcStruct type) {
	}
}