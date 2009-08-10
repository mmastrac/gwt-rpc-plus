package com.dotspots.rpcplus.client.jsonrpc.thrift;

import java.util.ArrayList;

import com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject;
import com.dotspots.rpcplus.client.jsonrpc.CallDecoder;
import com.dotspots.rpcplus.client.jsonrpc.CallEncoder;
import com.dotspots.rpcplus.client.jsonrpc.CallResponse;
import com.dotspots.rpcplus.client.transport.JsonTransport;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Base class for all generated Thrift stubs.
 */
public abstract class ThriftClientStub<T extends ThriftClientStub<T>> {
	private JsonTransport transport;
	private CallEncoder callEncoder;
	private CallDecoder callDecoder;
	private ArrayList<CallInterceptor<T>> callInterceptors;

	protected JavaScriptObject requestContext;
	protected JavaScriptObject responseContext;

	protected abstract void onException(int methodId, AsyncCallback<?> asyncCallback, int responseCode, BaseJsRpcObject baseJsRpcObject);

	private JavaScriptObject popRequestContext() {
		JavaScriptObject requestContext = this.requestContext;
		this.requestContext = null;
		return requestContext;
	}

	public void addCallInterceptor(CallInterceptor<T> interceptor) {
		if (callInterceptors == null) {
			callInterceptors = new ArrayList<CallInterceptor<T>>();
		}

		callInterceptors.add(interceptor);
	}

	public void removeCallInterceptor(CallInterceptor<T> interceptor) {
		if (callInterceptors != null) {
			callInterceptors.remove(interceptor);
		}
	}

	public void setTransport(JsonTransport transport) {
		this.transport = transport;
	}

	public void setCallEncoder(CallEncoder callEncoder) {
		this.callEncoder = callEncoder;
	}

	public void setCallDecoder(CallDecoder callDecoder) {
		this.callDecoder = callDecoder;
	}

	protected <R> void call(final int methodId, String call, JavaScriptObject args, final AsyncCallback<R> callback) {
		fireOnBeforeCall();

		transport.call(callEncoder.encodeCall(call, args, popRequestContext()), new AsyncCallback<JavaScriptObject>() {
			public void onFailure(Throwable caught) {
				fireOnAfterCall();
				callback.onFailure(caught);
			}

			public void onSuccess(JavaScriptObject result) {
				CallResponse<?> response = callDecoder.decodeCall(result);
				responseContext = response.getResponseContext();
				int responseCode = response.getResponseCode();
				if (responseCode == 0) {
					@SuppressWarnings("unchecked")
					final R castResult = (R) response.getResponseObject().getFieldValue(0);
					callback.onSuccess(castResult);

					return;
				}

				fireOnAfterCall();
				onException(methodId, callback, responseCode, response.getResponseContext());
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void fireOnBeforeCall() {
		if (callInterceptors != null) {
			for (CallInterceptor<T> interceptor : callInterceptors) {
				interceptor.onBeforeCall((T) this);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void fireOnAfterCall() {
		if (callInterceptors != null) {
			for (CallInterceptor<T> interceptor : callInterceptors) {
				interceptor.onAfterCall((T) this);
			}
		}
	}
}