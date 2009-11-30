package com.dotspots.rpcplus.example.torturetest.client;

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
        return com.google.gwt.core.client.JavaScriptObject.createArray().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native ObjectWithEnum create(int enumValue, JsRpcSetInt enumSet, JsRpcMapIntInt enumMap, JsRpcListInt enumList) /*-{
        return [enumValue,enumSet,enumMap,enumList];
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

    public native JsRpcSetInt getEnumSet() /*-{
         return this[1];
    }-*/;

    public native void setEnumSet(JsRpcSetInt enumSet) /*-{
         this[1] = enumSet;
    }-*/;

    public native boolean isSetEnumSet() /*-{
         return this[1] != null;
    }-*/;

    public native void unsetEnumSet() /*-{
         delete this[1];
    }-*/;

    public native JsRpcMapIntInt getEnumMap() /*-{
         return this[2];
    }-*/;

    public native void setEnumMap(JsRpcMapIntInt enumMap) /*-{
         this[2] = enumMap;
    }-*/;

    public native boolean isSetEnumMap() /*-{
         return this[2] != null;
    }-*/;

    public native void unsetEnumMap() /*-{
         delete this[2];
    }-*/;

    public native JsRpcListInt getEnumList() /*-{
         return this[3];
    }-*/;

    public native void setEnumList(JsRpcListInt enumList) /*-{
         this[3] = enumList;
    }-*/;

    public native boolean isSetEnumList() /*-{
         return this[3] != null;
    }-*/;

    public native void unsetEnumList() /*-{
         delete this[3];
    }-*/;

}
