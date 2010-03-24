package com.dotspots.rpcplus.example.torturetest.client;

import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class TortureTestApi_testThrowsAnUnpositionedException_result extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected TortureTestApi_testThrowsAnUnpositionedException_result() {
    }

    /* Factory method */
    public static TortureTestApi_testThrowsAnUnpositionedException_result create() {
        return com.google.gwt.core.client.JavaScriptObject.createObject().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native TortureTestApi_testThrowsAnUnpositionedException_result create(com.dotspots.rpcplus.example.torturetest.client.SimpleException ex, String success) /*-{
        return {"-1": ex, "0": success, };
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
         return this[-1];
    }-*/;

    public native void setEx(com.dotspots.rpcplus.example.torturetest.client.SimpleException ex) /*-{
         this[-1] = ex;
    }-*/;

    public native boolean isSetEx() /*-{
         return this[-1] != null;
    }-*/;

    public native void unsetEx() /*-{
         delete this[-1];
    }-*/;

}
