package com.dotspots.rpcplus.example.torturetest.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class ContextIn extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected ContextIn() {
    }

    /* Factory method */
    public static ContextIn create() {
        return JavaScriptObject.createArray().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native ContextIn create(String token, String data) /*-{
        return [token,data];
    }-*/;

    public native String getToken() /*-{
         return this[0];
    }-*/;

    public native void setToken(String token) /*-{
         this[0] = token;
    }-*/;

    public native boolean isSetToken() /*-{
         return this[0] != null;
    }-*/;

    public native void unsetToken() /*-{
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
