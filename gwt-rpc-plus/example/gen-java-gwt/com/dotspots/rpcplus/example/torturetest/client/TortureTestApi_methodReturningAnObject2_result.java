package com.dotspots.rpcplus.example.torturetest.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class TortureTestApi_methodReturningAnObject2_result extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected TortureTestApi_methodReturningAnObject2_result() {
    }

    /* Factory method */
    public static TortureTestApi_methodReturningAnObject2_result create() {
        return JavaScriptObject.createArray().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native TortureTestApi_methodReturningAnObject2_result create(com.dotspots.rpcplus.example.torturetest.client.SimpleObjectWithFieldIds success) /*-{
        return [success];
    }-*/;

    public native com.dotspots.rpcplus.example.torturetest.client.SimpleObjectWithFieldIds getSuccess() /*-{
         return this[0];
    }-*/;

    public native void setSuccess(com.dotspots.rpcplus.example.torturetest.client.SimpleObjectWithFieldIds success) /*-{
         this[0] = success;
    }-*/;

    public native boolean isSetSuccess() /*-{
         return this[0] != null;
    }-*/;

    public native void unsetSuccess() /*-{
         delete this[0];
    }-*/;

}
