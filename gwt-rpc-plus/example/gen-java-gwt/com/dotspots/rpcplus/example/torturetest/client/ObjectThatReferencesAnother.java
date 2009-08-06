package com.dotspots.rpcplus.example.torturetest.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.dotspots.rpcplus.client.jscollections.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.transport.*;

@SuppressWarnings("unused")
public final class ObjectThatReferencesAnother extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected ObjectThatReferencesAnother() {
    }

    /* Factory method */
    public static ObjectThatReferencesAnother create() {
        return JavaScriptObject.createArray().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native ObjectThatReferencesAnother create(com.dotspots.rpcplus.example.torturetest.client.ObjectThatIsReferenced reference) /*-{
        return [reference];
    }-*/;

    public native com.dotspots.rpcplus.example.torturetest.client.ObjectThatIsReferenced getReference() /*-{
         return this[0];
    }-*/;

    public native void setReference(com.dotspots.rpcplus.example.torturetest.client.ObjectThatIsReferenced reference) /*-{
         this[0] = reference;
    }-*/;

    public native boolean isSetReference() /*-{
         return this[0] != null;
    }-*/;

    public native void unsetReference() /*-{
         delete this[0];
    }-*/;

}
