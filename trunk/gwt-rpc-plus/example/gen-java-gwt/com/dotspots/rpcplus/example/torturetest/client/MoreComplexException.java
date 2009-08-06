package com.dotspots.rpcplus.example.torturetest.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.dotspots.rpcplus.client.jscollections.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.transport.*;

@SuppressWarnings("unused")
public final class MoreComplexException extends Exception {
    private JavaScriptObject e;

    // GWT requires a protected constructor
    public MoreComplexException(JavaScriptObject e) {
        this.e = e;
    }

    /* Factory method, strongly dependent on order of fields */
    public static native MoreComplexException create(String message, com.dotspots.rpcplus.example.torturetest.client.ObjectWithComplexTypes data) /*-{
        return @com.dotspots.rpcplus.example.torturetest.client.MoreComplexException::new(Lcom/google/gwt/core/client/JavaScriptObject;)([message,data]);
    }-*/;

    public native String getMessage() /*-{
         return @com.dotspots.rpcplus.example.torturetest.client.MoreComplexException::e[0];
    }-*/;

    public native void setMessage(String message) /*-{
         @com.dotspots.rpcplus.example.torturetest.client.MoreComplexException::e[0] = message;
    }-*/;

    public native boolean isSetMessage() /*-{
         return @com.dotspots.rpcplus.example.torturetest.client.MoreComplexException::e[0] != null;
    }-*/;

    public native void unsetMessage() /*-{
         delete @com.dotspots.rpcplus.example.torturetest.client.MoreComplexException::e[0];
    }-*/;

    public native com.dotspots.rpcplus.example.torturetest.client.ObjectWithComplexTypes getData() /*-{
         return @com.dotspots.rpcplus.example.torturetest.client.MoreComplexException::e[1];
    }-*/;

    public native void setData(com.dotspots.rpcplus.example.torturetest.client.ObjectWithComplexTypes data) /*-{
         @com.dotspots.rpcplus.example.torturetest.client.MoreComplexException::e[1] = data;
    }-*/;

    public native boolean isSetData() /*-{
         return @com.dotspots.rpcplus.example.torturetest.client.MoreComplexException::e[1] != null;
    }-*/;

    public native void unsetData() /*-{
         delete @com.dotspots.rpcplus.example.torturetest.client.MoreComplexException::e[1];
    }-*/;

}
