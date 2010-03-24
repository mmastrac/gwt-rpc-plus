package com.dotspots.rpcplus.example.torturetest.client;

import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class TortureTestApi_testDeclaresAnException_result extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected TortureTestApi_testDeclaresAnException_result() {
    }

    /* Factory method */
    public static TortureTestApi_testDeclaresAnException_result create() {
        return com.google.gwt.core.client.JavaScriptObject.createArray().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native TortureTestApi_testDeclaresAnException_result create(String success, com.dotspots.rpcplus.example.torturetest.client.SimpleException ex) /*-{
        return [success,ex];
    }-*/;

    public native String getSuccess() /*-{
         return this[0];
    }-*/;

    public native void setSuccess(String success) /*-{
         this[0] = success;
    }-*/;

    public native boolean isSetSuccess() /*-{
         return this[0] != null;
    }-*/;

    public native void unsetSuccess() /*-{
         delete this[0];
    }-*/;

    public native com.dotspots.rpcplus.example.torturetest.client.SimpleException getEx() /*-{
         return this[1];
    }-*/;

    public native void setEx(com.dotspots.rpcplus.example.torturetest.client.SimpleException ex) /*-{
         this[1] = ex;
    }-*/;

    public native boolean isSetEx() /*-{
         return this[1] != null;
    }-*/;

    public native void unsetEx() /*-{
         delete this[1];
    }-*/;

}
