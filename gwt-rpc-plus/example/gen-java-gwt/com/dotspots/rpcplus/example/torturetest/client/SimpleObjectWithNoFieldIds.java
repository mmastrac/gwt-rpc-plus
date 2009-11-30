package com.dotspots.rpcplus.example.torturetest.client;

import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class SimpleObjectWithNoFieldIds extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected SimpleObjectWithNoFieldIds() {
    }

    /* Factory method */
    public static SimpleObjectWithNoFieldIds create() {
        return com.google.gwt.core.client.JavaScriptObject.createObject().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native SimpleObjectWithNoFieldIds create(int userId, String token) /*-{
        return {"-2": userId, "-1": token, };
    }-*/;

    public native String getToken() /*-{
         return this[-1];
    }-*/;

    public native void setToken(String token) /*-{
         this[-1] = token;
    }-*/;

    public native boolean isSetToken() /*-{
         return this[-1] != null;
    }-*/;

    public native void unsetToken() /*-{
         delete this[-1];
    }-*/;

    public native int getUserId() /*-{
         return this[-2];
    }-*/;

    public native void setUserId(int userId) /*-{
         this[-2] = userId;
    }-*/;

    public native boolean isSetUserId() /*-{
         return this[-2] != null;
    }-*/;

    public native void unsetUserId() /*-{
         delete this[-2];
    }-*/;

}
