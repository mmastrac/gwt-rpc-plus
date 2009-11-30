package com.dotspots.rpcplus.example.torturetest.client;

import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class TortureTestApi_testPassthru_args extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected TortureTestApi_testPassthru_args() {
    }

    /* Factory method */
    public static TortureTestApi_testPassthru_args create() {
        return com.google.gwt.core.client.JavaScriptObject.createArray().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native TortureTestApi_testPassthru_args create(String arg) /*-{
        return [arg];
    }-*/;

    public native String getArg() /*-{
         return this[0];
    }-*/;

    public native void setArg(String arg) /*-{
         this[0] = arg;
    }-*/;

    public native boolean isSetArg() /*-{
         return this[0] != null;
    }-*/;

    public native void unsetArg() /*-{
         delete this[0];
    }-*/;

}
