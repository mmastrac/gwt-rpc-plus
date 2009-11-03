package com.dotspots.rpcplus.example.torturetest.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class TortureTestApi extends com.dotspots.rpcplus.client.jsonrpc.thrift.ThriftClientStub<TortureTestApi> {
    public void setRequestContext(ContextIn requestContext) {
        this.requestContext = requestContext;
    }

    public ContextOut popResponseContext() {
        ContextOut responseContext = this.responseContext.cast();
        this.responseContext = null;
        return responseContext;
    }

    public void onException(int methodId, AsyncCallback<?> asyncCallback, int responseCode, BaseJsRpcObject response) {
        // Process exceptions per method
        switch (responseCode * 16 + methodId) {
        case 17:
            asyncCallback.onFailure(new com.dotspots.rpcplus.example.torturetest.client.SimpleException((JavaScriptObject)response.getFieldValue(1)));
            break;
        case 18:
            asyncCallback.onFailure(new com.dotspots.rpcplus.example.torturetest.client.SimpleException((JavaScriptObject)response.getFieldValue(1)));
            break;
        case 19:
            asyncCallback.onFailure(new com.dotspots.rpcplus.example.torturetest.client.SimpleException((JavaScriptObject)response.getFieldValue(1)));
            break;
        case 20:
            asyncCallback.onFailure(new com.dotspots.rpcplus.example.torturetest.client.SimpleException((JavaScriptObject)response.getFieldValue(1)));
            break;
        case 36:
            asyncCallback.onFailure(new com.dotspots.rpcplus.example.torturetest.client.MoreComplexException((JavaScriptObject)response.getFieldValue(2)));
            break;
        default:
            asyncCallback.onFailure(new com.dotspots.rpcplus.client.jsonrpc.RpcException("Unknown exception"));
            break;
        }
    }

    public void testPassthru(String arg, AsyncCallback<String> callback) {
        call(0, "testPassthru", TortureTestApi_testPassthru_args.create(arg), callback);
    };

    public void testThrowsAnException(AsyncCallback<String> callback) {
        call(1, "testThrowsAnException", TortureTestApi_testThrowsAnException_args.create(), callback);
    };

    public void testThrowsAnUnpositionedException(AsyncCallback<String> callback) {
        call(2, "testThrowsAnUnpositionedException", TortureTestApi_testThrowsAnUnpositionedException_args.create(), callback);
    };

    public void testDeclaresAnException(AsyncCallback<String> callback) {
        call(3, "testDeclaresAnException", TortureTestApi_testDeclaresAnException_args.create(), callback);
    };

    public void testThrowsTwoExceptions(AsyncCallback<String> callback) {
        call(4, "testThrowsTwoExceptions", TortureTestApi_testThrowsTwoExceptions_args.create(), callback);
    };

    public void testExceptionPassthru(com.dotspots.rpcplus.example.torturetest.client.SimpleException ex, AsyncCallback<com.dotspots.rpcplus.example.torturetest.client.SimpleException> callback) {
        call(5, "testExceptionPassthru", TortureTestApi_testExceptionPassthru_args.create(ex), callback);
    };

    public void testPositionalArguments(int int32, String str, AsyncCallback<String> callback) {
        call(6, "testPositionalArguments", TortureTestApi_testPositionalArguments_args.create(int32, str), callback);
    };

    public void testSetString(AsyncCallback<JsRpcSetString> callback) {
        call(7, "testSetString", TortureTestApi_testSetString_args.create(), callback);
    };

    public void testSetInt(AsyncCallback<JsRpcSetInt> callback) {
        call(8, "testSetInt", TortureTestApi_testSetInt_args.create(), callback);
    };

    public void testMapStringString(AsyncCallback<JsRpcMapString<String>> callback) {
        call(9, "testMapStringString", TortureTestApi_testMapStringString_args.create(), callback);
    };

    public void methodReturningAnObject(AsyncCallback<com.dotspots.rpcplus.example.torturetest.client.ObjectThatReferencesAnother> callback) {
        call(10, "methodReturningAnObject", TortureTestApi_methodReturningAnObject_args.create(), callback);
    };

    public void methodReturningAnObject2(AsyncCallback<com.dotspots.rpcplus.example.torturetest.client.SimpleObjectWithFieldIds> callback) {
        call(11, "methodReturningAnObject2", TortureTestApi_methodReturningAnObject2_args.create(), callback);
    };

    public void methodReturningAnObject3(AsyncCallback<com.dotspots.rpcplus.example.torturetest.client.SimpleObjectWithNoFieldIds> callback) {
        call(12, "methodReturningAnObject3", TortureTestApi_methodReturningAnObject3_args.create(), callback);
    };

    public void methodReturningAnObject4(AsyncCallback<com.dotspots.rpcplus.example.torturetest.client.ObjectWithComplexTypes> callback) {
        call(13, "methodReturningAnObject4", TortureTestApi_methodReturningAnObject4_args.create(), callback);
    };

    public void methodReturningAnObject5(com.dotspots.rpcplus.example.torturetest.client.ObjectWithEnum arg, AsyncCallback<com.dotspots.rpcplus.example.torturetest.client.ObjectWithEnum> callback) {
        call(14, "methodReturningAnObject5", TortureTestApi_methodReturningAnObject5_args.create(arg), callback);
    };

    public void testBinary(String binaryValue, AsyncCallback<String> callback) {
        call(15, "testBinary", TortureTestApi_testBinary_args.create(binaryValue), callback);
    };

}
