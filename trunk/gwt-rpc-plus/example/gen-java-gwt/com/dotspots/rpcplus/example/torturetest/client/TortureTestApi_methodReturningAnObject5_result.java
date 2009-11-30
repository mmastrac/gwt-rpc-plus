package com.dotspots.rpcplus.example.torturetest.client;

import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class TortureTestApi_methodReturningAnObject5_result extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected TortureTestApi_methodReturningAnObject5_result() {
    }

    /* Factory method */
    public static TortureTestApi_methodReturningAnObject5_result create() {
        return com.google.gwt.core.client.JavaScriptObject.createArray().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native TortureTestApi_methodReturningAnObject5_result create(com.dotspots.rpcplus.example.torturetest.client.ObjectWithEnum success) /*-{
        return [success];
    }-*/;

    public native com.dotspots.rpcplus.example.torturetest.client.ObjectWithEnum getSuccess() /*-{
         return this[0];
    }-*/;

    public native void setSuccess(com.dotspots.rpcplus.example.torturetest.client.ObjectWithEnum success) /*-{
         this[0] = success;
    }-*/;

    public native boolean isSetSuccess() /*-{
         return this[0] != null;
    }-*/;

    public native void unsetSuccess() /*-{
         delete this[0];
    }-*/;

}
