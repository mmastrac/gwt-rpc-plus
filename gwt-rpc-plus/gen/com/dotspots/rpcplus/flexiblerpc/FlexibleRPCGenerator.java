package com.dotspots.rpcplus.flexiblerpc;

import java.io.PrintWriter;

import com.dotspots.rpcplus.client.flexiblerpc.FlexibleRPC;
import com.dotspots.rpcplus.client.flexiblerpc.FlexibleRPCService;
import com.dotspots.rpcplus.client.flexiblerpc.impl.FlexibleRPCRequestWrapper;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.impl.Serializer;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.user.rebind.rpc.ServiceInterfaceProxyGenerator;

/**
 * Allows more flexible plugging of RPC transports.
 */
public class FlexibleRPCGenerator extends Generator {
	private static final String STRING_QNAME = "java.lang.String";
	private static final String GWT_QNAME = "com.google.gwt.core.client.GWT";
	private static final String REMOTE_SERVICE_QNAME = RemoteService.class.getName();

	@Override
	public String generate(final TreeLogger logger, final GeneratorContext context, final String typeName) throws UnableToCompleteException {
		final TypeOracle typeOracle = context.getTypeOracle();

		ServiceInterfaceProxyGenerator serviceInterfaceProxyGenerator = new ServiceInterfaceProxyGenerator();
		final TreeLogger realProxyLogger = logger.branch(TreeLogger.DEBUG, "Generating normal service proxy for " + typeName);
		serviceInterfaceProxyGenerator.generate(realProxyLogger, context, typeName);

		// Get metadata describing the user's class.
		final JClassType userType = getValidUserType(logger, typeName, typeOracle);

		// Write the new class.
		return generateImpl(logger, context, userType);
	}

	private JClassType getValidUserType(final TreeLogger logger, final String typeName, final TypeOracle typeOracle)
			throws UnableToCompleteException {
		try {
			// Get the type that the user is introducing.
			final JClassType userType = typeOracle.getType(typeName);

			// Get the type this generator is designed to support.
			final JClassType magicType = typeOracle.findType(REMOTE_SERVICE_QNAME);

			// Ensure it's an interface.
			if (userType.isInterface() == null) {
				logger.log(TreeLogger.ERROR, userType.getQualifiedSourceName() + " must be an interface", null);
				throw new UnableToCompleteException();
			}

			// Ensure proper derivation.
			if (!userType.isAssignableTo(magicType)) {
				logger.log(TreeLogger.ERROR, userType.getQualifiedSourceName() + " must be assignable to "
						+ magicType.getQualifiedSourceName(), null);
				throw new UnableToCompleteException();
			}

			return userType;

		} catch (final NotFoundException e) {
			logger.log(TreeLogger.ERROR, "Unable to find required type(s)", e);
			throw new UnableToCompleteException();
		}
	}

	private String computeSubclassName(final JClassType userType) {
		final String baseName = userType.getQualifiedSourceName().replace('.', '_');
		return baseName + "_FlexibleRPCProxy";
	}

	private String generateImpl(final TreeLogger logger, final GeneratorContext context, final JClassType userType)
			throws UnableToCompleteException {
		// Compute the package and class names of the generated class.
		final String pkgName = userType.getPackage().getName();
		final String subName = computeSubclassName(userType);

		// Begin writing the generated source.
		final ClassSourceFileComposerFactory f = new ClassSourceFileComposerFactory(pkgName, subName);
		f.addImport(STRING_QNAME);
		f.addImport(GWT_QNAME);

		f.addImport(Request.class.getName());
		f.addImport(ResponseReader.class.getName().replace('$', '.'));
		f.addImport(AsyncCallback.class.getName());
		f.addImport(FlexibleRPC.class.getName());
		f.addImport(RemoteServiceProxy.class.getName());
		f.addImport(Serializer.class.getName());
		f.addImport(FlexibleRPCRequestWrapper.class.getName());

		f.setSuperclass(userType.getQualifiedSourceName() + "_Proxy");
		f.addImplementedInterface(FlexibleRPCService.class.getName());

		final PrintWriter pw = context.tryCreate(logger, pkgName, subName);
		if (pw != null) {
			final SourceWriter sw = f.createSourceWriter(context, pw);
			sw.println("protected FlexibleRPC flexibleRPC = GWT.create(FlexibleRPC.class);");

			sw.println();
			sw.println("@Override public void setServiceEntryPoint(String address) {");
			sw.indent();
			sw.println("super.setServiceEntryPoint(address);");
			sw.println("flexibleRPC.initialize(this, violateSerializer(this));");
			sw.outdent();
			sw.println("}");

			sw.println();
			sw.println("@Override protected <T> Request doInvoke(ResponseReader responseReader, "
					+ "String methodName, int invocationCount, String requestData, AsyncCallback<T> callback) {");
			sw.indent();
			sw.println("return new FlexibleRPCRequestWrapper(flexibleRPC.doInvoke(responseReader, methodName, invocationCount, requestData, callback));");
			sw.outdent();
			sw.println("}");

			sw.println();
			sw.println("private native Serializer violateSerializer(RemoteServiceProxy remoteServiceProxy) /*-{");
			sw.indent();
			sw.println("return remoteServiceProxy.@" + RemoteServiceProxy.class.getName() + "::serializer;");
			sw.outdent();
			sw.println("}-*/;");

			// Finish.
			sw.commit(logger);
		}

		return f.getCreatedClassName();
	}
}
