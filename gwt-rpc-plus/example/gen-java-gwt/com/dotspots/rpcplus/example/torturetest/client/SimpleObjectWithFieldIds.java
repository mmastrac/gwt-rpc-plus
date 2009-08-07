package com.dotspots.rpcplus.example.torturetest.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class SimpleObjectWithFieldIds extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected SimpleObjectWithFieldIds() {
    }

    /* Factory method */
    public static SimpleObjectWithFieldIds create() {
        return JavaScriptObject.createArray().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native SimpleObjectWithFieldIds create(String token, int userId) /*-{
        return [token,userId];
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

    public native int getUserId() /*-{
         return this[1];
    }-*/;

    public native void setUserId(int userId) /*-{
         this[1] = userId;
    }-*/;

    public native boolean isSetUserId() /*-{
         return this[1] != null;
    }-*/;

    public native void unsetUserId() /*-{
         delete this[1];
    }-*/;

}
