package com.dotspots.rpcplus.example.torturetest.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.dotspots.rpcplus.client.jscollections.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.transport.*;

@SuppressWarnings("unused")
public final class TortureTestApi implements CallResponseProcessor {
    private JsonTransport transport;
    private CallEncoder callEncoder;
    private CallDecoder callDecoder;
    private ContextIn requestContext;
    private ContextOut responseContext;

    public void setTransport(JsonTransport transport) {
        this.transport = transport;
    }

    public void setCallEncoder(CallEncoder callEncoder) {
        this.callEncoder = callEncoder;
    }

    public void setCallDecoder(CallDecoder callDecoder) {
        this.callDecoder = callDecoder;
    }

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
        transport.call(0, callEncoder.encodeCall("testThrowsAnException", TortureTestApi_testThrowsAnException_args.create(), popRequestContext()), callback, this);
    };

    public void testThrowsAnUnpositionedException(AsyncCallback<String> callback) {
        transport.call(1, callEncoder.encodeCall("testThrowsAnUnpositionedException", TortureTestApi_testThrowsAnUnpositionedException_args.create(), popRequestContext()), callback, this);
    };

    public void testDeclaresAnException(AsyncCallback<String> callback) {
        transport.call(2, callEncoder.encodeCall("testDeclaresAnException", TortureTestApi_testDeclaresAnException_args.create(), popRequestContext()), callback, this);
    };

    public void testThrowsTwoExceptions(AsyncCallback<String> callback) {
        transport.call(3, callEncoder.encodeCall("testThrowsTwoExceptions", TortureTestApi_testThrowsTwoExceptions_args.create(), popRequestContext()), callback, this);
    };

    public void testExceptionPassthru(com.dotspots.rpcplus.example.torturetest.client.SimpleException ex, AsyncCallback<com.dotspots.rpcplus.example.torturetest.client.SimpleException> callback) {
        transport.call(4, callEncoder.encodeCall("testExceptionPassthru", TortureTestApi_testExceptionPassthru_args.create(ex), popRequestContext()), callback, this);
    };

    public void testPositionalArguments(int int32, String str, AsyncCallback<String> callback) {
        transport.call(5, callEncoder.encodeCall("testPositionalArguments", TortureTestApi_testPositionalArguments_args.create(int32, str), popRequestContext()), callback, this);
    };

    public void testSetString(AsyncCallback<JsRpcSetString> callback) {
        transport.call(6, callEncoder.encodeCall("testSetString", TortureTestApi_testSetString_args.create(), popRequestContext()), callback, this);
    };

    public void testSetInt(AsyncCallback<JsRpcSetInt> callback) {
        transport.call(7, callEncoder.encodeCall("testSetInt", TortureTestApi_testSetInt_args.create(), popRequestContext()), callback, this);
    };

    public void testMapStringString(AsyncCallback<JsRpcMapString<String>> callback) {
        transport.call(8, callEncoder.encodeCall("testMapStringString", TortureTestApi_testMapStringString_args.create(), popRequestContext()), callback, this);
    };

    public void methodReturningAnObject(AsyncCallback<com.dotspots.rpcplus.example.torturetest.client.ObjectThatReferencesAnother> callback) {
        transport.call(9, callEncoder.encodeCall("methodReturningAnObject", TortureTestApi_methodReturningAnObject_args.create(), popRequestContext()), callback, this);
    };

    public void methodReturningAnObject2(AsyncCallback<com.dotspots.rpcplus.example.torturetest.client.SimpleObjectWithFieldIds> callback) {
        transport.call(10, callEncoder.encodeCall("methodReturningAnObject2", TortureTestApi_methodReturningAnObject2_args.create(), popRequestContext()), callback, this);
    };

    public void methodReturningAnObject3(AsyncCallback<com.dotspots.rpcplus.example.torturetest.client.SimpleObjectWithNoFieldIds> callback) {
        transport.call(11, callEncoder.encodeCall("methodReturningAnObject3", TortureTestApi_methodReturningAnObject3_args.create(), popRequestContext()), callback, this);
    };

    public void methodReturningAnObject4(AsyncCallback<com.dotspots.rpcplus.example.torturetest.client.ObjectWithComplexTypes> callback) {
        transport.call(12, callEncoder.encodeCall("methodReturningAnObject4", TortureTestApi_methodReturningAnObject4_args.create(), popRequestContext()), callback, this);
    };

    public void testBinary(String binaryValue, AsyncCallback<String> callback) {
        transport.call(13, callEncoder.encodeCall("testBinary", TortureTestApi_testBinary_args.create(binaryValue), popRequestContext()), callback, this);
    };

}
