package com.dotspots.rpcplus.example.torturetest.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class TortureTestApi_testPositionalArguments_args extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected TortureTestApi_testPositionalArguments_args() {
    }

    /* Factory method */
    public static TortureTestApi_testPositionalArguments_args create() {
        return JavaScriptObject.createArray().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native TortureTestApi_testPositionalArguments_args create(int int32, String str) /*-{
        return [int32,str];
    }-*/;

    public native int getInt32() /*-{
         return this[0];
    }-*/;

    public native void setInt32(int int32) /*-{
         this[0] = int32;
    }-*/;

    public native boolean isSetInt32() /*-{
         return this[0] != null;
    }-*/;

    public native void unsetInt32() /*-{
         delete this[0];
    }-*/;

    public native String getStr() /*-{
         return this[1];
    }-*/;

    public native void setStr(String str) /*-{
         this[1] = str;
    }-*/;

    public native boolean isSetStr() /*-{
         return this[1] != null;
    }-*/;

    public native void unsetStr() /*-{
         delete this[1];
    }-*/;

}
