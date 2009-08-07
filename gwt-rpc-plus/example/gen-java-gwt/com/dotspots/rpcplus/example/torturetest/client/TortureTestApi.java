package com.dotspots.rpcplus.example.torturetest.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class TortureTestApi extends com.dotspots.rpcplus.client.jsonrpc.thrift.ThriftClientStub implements CallResponseProcessor {
    private ContextIn requestContext;
    private ContextOut responseContext;

    private ContextIn popRequestContext() {
        ContextIn requestContext = this.requestContext;
        this.requestContext = null;
        return requestContext;
    }

    public void setRequestContext(ContextIn requestContext) {
        this.requestContext = requestContext;
    }

    public ContextOut popResponseContext() {
        ContextOut responseContext = this.responseContext;
        this.responseContext = null;
        return responseContext;
    }

    @SuppressWarnings("unchecked")
    public void onResponse(int methodId, AsyncCallback<?> asyncCallback, JavaScriptObject rawResponse) {
        CallResponse<?> response = callDecoder.decodeCall(rawResponse);
        this.responseContext = response.getResponseContext().cast();
        int responseCode = response.getResponseCode();
        if (responseCode == 0) {
            ((AsyncCallback<JavaScriptObject>)asyncCallback).onSuccess(response.getResponseObject().getFieldValue(0));
            return;
        }

        // Process exceptions per method
        switch (responseCode * 14 + methodId) {
        case 14:
            asyncCallback.onFailure(new com.dotspots.rpcplus.example.torturetest.client.SimpleException(response.getResponseObject().getFieldValue(1)));
            break;
        case 15:
            asyncCallback.onFailure(new com.dotspots.rpcplus.example.torturetest.client.SimpleException(response.getResponseObject().getFieldValue(1)));
            break;
        case 16:
            asyncCallback.onFailure(new com.dotspots.rpcplus.example.torturetest.client.SimpleException(response.getResponseObject().getFieldValue(1)));
            break;
        case 17:
            asyncCallback.onFailure(new com.dotspots.rpcplus.example.torturetest.client.SimpleException(response.getResponseObject().getFieldValue(1)));
            break;
        case 31:
            asyncCallback.onFailure(new com.dotspots.rpcplus.example.torturetest.client.MoreComplexException(response.getResponseObject().getFieldValue(2)));
            break;
        default:
            asyncCallback.onFailure(new com.dotspots.rpcplus.client.jsonrpc.RpcException("Unknown exception"));
            break;
        }
    }

    public void testThrowsAnException(AsyncCallback<String> callback) {
        call(0, "testThrowsAnException", TortureTestApi_testThrowsAnException_args.create(), popRequestContext(), callback);
    };

    public void testThrowsAnUnpositionedException(AsyncCallback<String> callback) {
        call(1, "testThrowsAnUnpositionedException", TortureTestApi_testThrowsAnUnpositionedException_args.create(), popRequestContext(), callback);
    };

    public void testDeclaresAnException(AsyncCallback<String> callback) {
        call(2, "testDeclaresAnException", TortureTestApi_testDeclaresAnException_args.create(), popRequestContext(), callback);
    };

    public void testThrowsTwoExceptions(AsyncCallback<String> callback) {
        call(3, "testThrowsTwoExceptions", TortureTestApi_testThrowsTwoExceptions_args.create(), popRequestContext(), callback);
    };

    public void testExceptionPassthru(com.dotspots.rpcplus.example.torturetest.client.SimpleException ex, AsyncCallback<com.dotspots.rpcplus.example.torturetest.client.SimpleException> callback) {
        call(4, "testExceptionPassthru", TortureTestApi_testExceptionPassthru_args.create(ex), popRequestContext(), callback);
    };

    public void testPositionalArguments(int int32, String str, AsyncCallback<String> callback) {
        call(5, "testPositionalArguments", TortureTestApi_testPositionalArguments_args.create(int32, str), popRequestContext(), callback);
    };

    public void testSetString(AsyncCallback<JsRpcSetString> callback) {
        call(6, "testSetString", TortureTestApi_testSetString_args.create(), popRequestContext(), callback);
    };

    public void testSetInt(AsyncCallback<JsRpcSetInt> callback) {
        call(7, "testSetInt", TortureTestApi_testSetInt_args.create(), popRequestContext(), callback);
    };

    public void testMapStringString(AsyncCallback<JsRpcMapString<String>> callback) {
        call(8, "testMapStringString", TortureTestApi_testMapStringString_args.create(), popRequestContext(), callback);
    };

    public void methodReturningAnObject(AsyncCallback<com.dotspots.rpcplus.example.torturetest.client.ObjectThatReferencesAnother> callback) {
        call(9, "methodReturningAnObject", TortureTestApi_methodReturningAnObject_args.create(), popRequestContext(), callback);
    };

    public void methodReturningAnObject2(AsyncCallback<com.dotspots.rpcplus.example.torturetest.client.SimpleObjectWithFieldIds> callback) {
        call(10, "methodReturningAnObject2", TortureTestApi_methodReturningAnObject2_args.create(), popRequestContext(), callback);
    };

    public void methodReturningAnObject3(AsyncCallback<com.dotspots.rpcplus.example.torturetest.client.SimpleObjectWithNoFieldIds> callback) {
        call(11, "methodReturningAnObject3", TortureTestApi_methodReturningAnObject3_args.create(), popRequestContext(), callback);
    };

    public void methodReturningAnObject4(AsyncCallback<com.dotspots.rpcplus.example.torturetest.client.ObjectWithComplexTypes> callback) {
        call(12, "methodReturningAnObject4", TortureTestApi_methodReturningAnObject4_args.create(), popRequestContext(), callback);
    };

    public void testBinary(String binaryValue, AsyncCallback<String> callback) {
        call(13, "testBinary", TortureTestApi_testBinary_args.create(binaryValue), popRequestContext(), callback);
    };

}
