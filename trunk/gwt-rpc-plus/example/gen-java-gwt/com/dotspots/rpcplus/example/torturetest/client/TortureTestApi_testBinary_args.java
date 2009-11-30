package com.dotspots.rpcplus.example.torturetest.client;

import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class TortureTestApi_testBinary_args extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected TortureTestApi_testBinary_args() {
    }

    /* Factory method */
    public static TortureTestApi_testBinary_args create() {
        return com.google.gwt.core.client.JavaScriptObject.createArray().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native TortureTestApi_testBinary_args create(String binaryValue) /*-{
        return [binaryValue];
    }-*/;

    public native String getBinaryValue() /*-{
         return this[0];
    }-*/;

    public native void setBinaryValue(String binaryValue) /*-{
         this[0] = binaryValue;
    }-*/;

    public native boolean isSetBinaryValue() /*-{
         return this[0] != null;
    }-*/;

    public native void unsetBinaryValue() /*-{
         delete this[0];
    }-*/;

}
