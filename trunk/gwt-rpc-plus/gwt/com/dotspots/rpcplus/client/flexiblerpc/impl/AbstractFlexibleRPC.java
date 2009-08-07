package com.dotspots.rpcplus.client.flexiblerpc.impl;

import com.dotspots.rpcplus.client.flexiblerpc.FlexibleRPC;
import com.dotspots.rpcplus.client.flexiblerpc.FlexibleRPCRequest;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter;
import com.google.gwt.user.client.rpc.impl.Serializer;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;

public abstract class AbstractFlexibleRPC implements FlexibleRPC {
	protected RemoteServiceProxy proxy;
	protected Serializer serializer;
	protected SerializationStreamFactory serializationStreamFactory;

	public void initialize(RemoteServiceProxy proxy, Serializer serializer) {
		this.proxy = proxy;
		this.serializer = serializer;
		this.serializationStreamFactory = proxy;
	}

	public <T> FlexibleRPCRequest doInvoke(ResponseReader responseReader, String methodName, int requestId, String requestData,
			AsyncCallback<T> callback) {
		return doInvoke(methodName, requestId, requestData, new RequestCallbackAdapter<T>(serializationStreamFactory, methodName,
				requestId, callback, responseReader));
	}

	/**
	 * Marks a request as completed.
	 * 
	 * @param statusCode
	 * @param data
	 */
	protected <T> void onResponseComplete(RequestCallbackAdapter<T> requestCallbackAdapter, int statusCode, String data) {
		requestCallbackAdapter.onResponseReceived(null, createResponseWrapper(statusCode, data));
	}

	/**
	 * Marks a request as completed.
	 * 
	 * @param statusCode
	 * @param data
	 */
	protected <T> void onResponseError(RequestCallbackAdapter<T> requestCallbackAdapter, Throwable exception) {
		requestCallbackAdapter.onError(null, exception);
	}

	/**
	 * Marks a request as completed.
	 * 
	 * @param statusCode
	 * @param data
	 */
	protected <T> void onResponseSuccess(RequestCallbackAdapter<T> requestCallbackAdapter, String data) {
		if (data.startsWith("//ER")) {
			onResponseComplete(requestCallbackAdapter, Response.SC_INTERNAL_SERVER_ERROR, data.substring(4));
		} else {
			onResponseComplete(requestCallbackAdapter, Response.SC_OK, data);
		}
	}

	private FlexibleRPCResponseWrapper createResponseWrapper(int statusCode, String data) {
		return new FlexibleRPCResponseWrapper(statusCode, data);
	}

	protected abstract <T> FlexibleRPCRequest doInvoke(String methodName, int requestId, String requestData,
			RequestCallbackAdapter<T> callback);
}
