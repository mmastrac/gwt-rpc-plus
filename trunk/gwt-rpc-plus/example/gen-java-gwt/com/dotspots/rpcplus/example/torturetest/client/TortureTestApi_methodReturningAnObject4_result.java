package com.dotspots.rpcplus.example.torturetest.client;

import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class TortureTestApi_methodReturningAnObject4_result extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected TortureTestApi_methodReturningAnObject4_result() {
    }

    /* Factory method */
    public static TortureTestApi_methodReturningAnObject4_result create() {
        return com.google.gwt.core.client.JavaScriptObject.createArray().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native TortureTestApi_methodReturningAnObject4_result create(com.dotspots.rpcplus.example.torturetest.client.ObjectWithComplexTypes success) /*-{
        return [success];
    }-*/;

    public native com.dotspots.rpcplus.example.torturetest.client.ObjectWithComplexTypes getSuccess() /*-{
         return this[0];
    }-*/;

    public native void setSuccess(com.dotspots.rpcplus.example.torturetest.client.ObjectWithComplexTypes success) /*-{
         this[0] = success;
    }-*/;

    public native boolean isSetSuccess() /*-{
         return this[0] != null;
    }-*/;

    public native void unsetSuccess() /*-{
         delete this[0];
    }-*/;

}
