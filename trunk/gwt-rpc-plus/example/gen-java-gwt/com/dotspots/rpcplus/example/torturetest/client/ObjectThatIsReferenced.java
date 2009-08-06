package com.dotspots.rpcplus.example.torturetest.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.dotspots.rpcplus.client.jscollections.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.transport.*;

@SuppressWarnings("unused")
public final class ObjectThatIsReferenced extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected ObjectThatIsReferenced() {
    }

    /* Factory method */
    public static ObjectThatIsReferenced create() {
        return JavaScriptObject.createArray().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native ObjectThatIsReferenced create(int id) /*-{
        return [id];
    }-*/;

    public native int getId() /*-{
         return this[0];
    }-*/;

    public native void setId(int id) /*-{
         this[0] = id;
    }-*/;

    public native boolean isSetId() /*-{
         return this[0] != null;
    }-*/;

    public native void unsetId() /*-{
         delete this[0];
    }-*/;

}
