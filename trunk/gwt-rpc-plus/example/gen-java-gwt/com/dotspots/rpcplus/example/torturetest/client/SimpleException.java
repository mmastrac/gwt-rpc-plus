package com.dotspots.rpcplus.example.torturetest.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class SimpleException extends Exception {
    private JavaScriptObject e;

    // GWT requires a protected constructor
    public SimpleException(JavaScriptObject e) {
        this.e = e;
    }

    /* Factory method, strongly dependent on order of fields */
    public static native SimpleException create(String message) /*-{
        return @com.dotspots.rpcplus.example.torturetest.client.SimpleException::new(Lcom/google/gwt/core/client/JavaScriptObject;)([message]);
    }-*/;

    public native String getMessage() /*-{
         return this.@com.dotspots.rpcplus.example.torturetest.client.SimpleException::e[0];
    }-*/;

    public native void setMessage(String message) /*-{
         this.@com.dotspots.rpcplus.example.torturetest.client.SimpleException::e[0] = message;
    }-*/;

    public native boolean isSetMessage() /*-{
         return this.@com.dotspots.rpcplus.example.torturetest.client.SimpleException::e[0] != null;
    }-*/;

    public native void unsetMessage() /*-{
         delete this.@com.dotspots.rpcplus.example.torturetest.client.SimpleException::e[0];
    }-*/;

}
