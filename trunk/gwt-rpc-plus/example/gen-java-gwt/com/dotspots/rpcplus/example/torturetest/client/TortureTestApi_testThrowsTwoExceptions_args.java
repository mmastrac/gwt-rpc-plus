package com.dotspots.rpcplus.example.torturetest.client;

import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class TortureTestApi_testThrowsTwoExceptions_args extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected TortureTestApi_testThrowsTwoExceptions_args() {
    }

    /* Factory method */
    public static TortureTestApi_testThrowsTwoExceptions_args create() {
        return com.google.gwt.core.client.JavaScriptObject.createObject().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native TortureTestApi_testThrowsTwoExceptions_args create(int which) /*-{
        return {"-1": which, };
    }-*/;

    public native int getWhich() /*-{
         return this[-1];
    }-*/;

    public native void setWhich(int which) /*-{
         this[-1] = which;
    }-*/;

    public native boolean isSetWhich() /*-{
         return this[-1] != null;
    }-*/;

    public native void unsetWhich() /*-{
         delete this[-1];
    }-*/;

}
