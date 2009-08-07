package com.dotspots.rpcplus.example.torturetest.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class TortureTestApi_testExceptionPassthru_args extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected TortureTestApi_testExceptionPassthru_args() {
    }

    /* Factory method */
    public static TortureTestApi_testExceptionPassthru_args create() {
        return JavaScriptObject.createArray().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native TortureTestApi_testExceptionPassthru_args create(com.dotspots.rpcplus.example.torturetest.client.SimpleException ex) /*-{
        return [ex];
    }-*/;

    public native com.dotspots.rpcplus.example.torturetest.client.SimpleException getEx() /*-{
         return this[0];
    }-*/;

    public native void setEx(com.dotspots.rpcplus.example.torturetest.client.SimpleException ex) /*-{
         this[0] = ex;
    }-*/;

    public native boolean isSetEx() /*-{
         return this[0] != null;
    }-*/;

    public native void unsetEx() /*-{
         delete this[0];
    }-*/;

}
