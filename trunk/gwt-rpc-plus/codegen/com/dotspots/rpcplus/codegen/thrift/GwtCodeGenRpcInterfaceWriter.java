/**
 * 
 */
package com.dotspots.rpcplus.codegen.thrift;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.dotspots.rpcplus.client.jsonrpc.RpcException;

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
		printWriter.println("public final class " + iface.getClassName() + " implements CallResponseProcessor {");
		printWriter.println("    private JsonTransport transport;");
		printWriter.println("    private CallEncoder callEncoder;");
		printWriter.println("    private CallDecoder callDecoder;");
		if (iface.getRequestContext() != null) {
			printWriter.println("    private " + iface.getRequestContext().getClassName() + " requestContext;");
		}
		if (iface.getResponseContext() != null) {
			printWriter.println("    private " + iface.getResponseContext().getClassName() + " responseContext;");
		}
		printWriter.println();
		printWriter.println("    public void setTransport(JsonTransport transport) {");
		printWriter.println("        this.transport = transport;");
		printWriter.println("    }");
		printWriter.println();
		printWriter.println("    public void setCallEncoder(CallEncoder callEncoder) {");
		printWriter.println("        this.callEncoder = callEncoder;");
		printWriter.println("    }");
		printWriter.println();
		printWriter.println("    public void setCallDecoder(CallDecoder callDecoder) {");
		printWriter.println("        this.callDecoder = callDecoder;");
		printWriter.println("    }");
		printWriter.println();

		if (iface.getRequestContext() != null) {
			printWriter.println("    private " + iface.getRequestContext().getClassName() + " popRequestContext() {");
			printWriter.println("        " + iface.getRequestContext().getClassName() + " requestContext = this.requestContext;");
			printWriter.println("        this.requestContext = null;");
			printWriter.println("        return requestContext;");
			printWriter.println("    }");
			printWriter.println();
			printWriter.println("    public void setRequestContext(" + iface.getRequestContext().getClassName() + " requestContext) {");
			printWriter.println("        this.requestContext = requestContext;");
			printWriter.println("    }");
			printWriter.println();
		}

		if (iface.getResponseContext() != null) {
			printWriter.println("    public " + iface.getResponseContext().getClassName() + " popResponseContext() {");
			printWriter.println("        " + iface.getResponseContext().getClassName() + " responseContext = this.responseContext;");
			printWriter.println("        this.responseContext = null;");
			printWriter.println("        return responseContext;");
			printWriter.println("    }");
			printWriter.println();
		}

		printWriter.println("    @SuppressWarnings(\"unchecked\")");
		printWriter.println("    public void onResponse(int methodId, AsyncCallback<?> asyncCallback, JavaScriptObject rawResponse) {");
		printWriter.println("        CallResponse<?> response = callDecoder.decodeCall(rawResponse);");
		if (iface.getResponseContext() != null) {
			printWriter.println("        this.responseContext = response.getResponseContext().cast();");
		}
		printWriter.println("        int responseCode = response.getResponseCode();");
		printWriter.println("        if (responseCode == 0) {");
		printWriter.println("            ((AsyncCallback<JavaScriptObject>)asyncCallback).onSuccess(response.getResponseObject().getFieldValue(0));");
		printWriter.println("            return;");
		printWriter.println("        }");
		printWriter.println();
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
				printWriter.println("            asyncCallback.onFailure(new " + exceptionType.getFullyQualifiedClassName()
						+ "(response.getResponseObject().getFieldValue(" + index + ")));");
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
		printWriter.println("import com.google.gwt.core.client.JavaScriptObject;");
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

		String requestContext = iface.getRequestContext() == null ? "null" : "popRequestContext()";

		printWriter.println("    public void " + method.getName() + "(" + StringUtils.join(args, ", ") + ") {");
		final String argsClass = iface.getClassName() + "_" + method.getName() + "_args";
		printWriter.println("        transport.call(" + method.getMethodId() + ", callEncoder.encodeCall(\"" + method.getName() + "\", "
				+ argsClass + ".create(" + StringUtils.join(argsShort, ", ") + "), " + requestContext + "), callback, this);");
		printWriter.println("    };");
		printWriter.println();
	}

	public void writeType(RpcStruct type) {
	}
}