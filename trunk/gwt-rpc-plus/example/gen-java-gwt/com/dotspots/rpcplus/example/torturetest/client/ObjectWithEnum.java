package com.dotspots.rpcplus.example.torturetest.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class ObjectWithEnum extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected ObjectWithEnum() {
    }

    /* Factory method */
    public static ObjectWithEnum create() {
        return JavaScriptObject.createArray().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native ObjectWithEnum create(int enumValue) /*-{
        return [enumValue];
    }-*/;

    public native int getEnumValue() /*-{
         return this[0];
    }-*/;

    public native void setEnumValue(int enumValue) /*-{
         this[0] = enumValue;
    }-*/;

    public native boolean isSetEnumValue() /*-{
         return this[0] != null;
    }-*/;

    public native void unsetEnumValue() /*-{
         delete this[0];
    }-*/;

}
