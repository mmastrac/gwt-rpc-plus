package com.dotspots.rpcplus.example.torturetest.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.dotspots.rpcplus.client.jscollections.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.transport.*;

@SuppressWarnings("unused")
public final class ContextOut extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected ContextOut() {
    }

    /* Factory method */
    public static ContextOut create() {
        return JavaScriptObject.createArray().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native ContextOut create(int timing, String data) /*-{
        return [timing,data];
    }-*/;

    public native int getTiming() /*-{
         return this[0];
    }-*/;

    public native void setTiming(int timing) /*-{
         this[0] = timing;
    }-*/;

    public native boolean isSetTiming() /*-{
         return this[0] != null;
    }-*/;

    public native void unsetTiming() /*-{
         delete this[0];
    }-*/;

    public native String getData() /*-{
         return this[1];
    }-*/;

    public native void setData(String data) /*-{
         this[1] = data;
    }-*/;

    public native boolean isSetData() /*-{
         return this[1] != null;
    }-*/;

    public native void unsetData() /*-{
         delete this[1];
    }-*/;

}
