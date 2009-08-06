package com.dotspots.rpcplus.example.torturetest.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.dotspots.rpcplus.client.jscollections.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.transport.*;

@SuppressWarnings("unused")
public final class TortureTestApi_testSetString_result extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected TortureTestApi_testSetString_result() {
    }

    /* Factory method */
    public static TortureTestApi_testSetString_result create() {
        return JavaScriptObject.createArray().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native TortureTestApi_testSetString_result create(JsRpcSetString success) /*-{
        return [success];
    }-*/;

    public native JsRpcSetString getSuccess() /*-{
         return this[0];
    }-*/;

    public native void setSuccess(JsRpcSetString success) /*-{
         this[0] = success;
    }-*/;

    public native boolean isSetSuccess() /*-{
         return this[0] != null;
    }-*/;

    public native void unsetSuccess() /*-{
         delete this[0];
    }-*/;

}
