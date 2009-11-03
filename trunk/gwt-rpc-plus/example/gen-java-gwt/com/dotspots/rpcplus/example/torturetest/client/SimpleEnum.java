package com.dotspots.rpcplus.example.torturetest.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class SimpleEnum extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    public static final int ONE = 1;
    public static final int TWO = 2;

    // GWT requires a protected constructor
    protected SimpleEnum() {
    }

    /* Factory method */
    public static SimpleEnum create() {
        return JavaScriptObject.createArray().cast();
    }

}
