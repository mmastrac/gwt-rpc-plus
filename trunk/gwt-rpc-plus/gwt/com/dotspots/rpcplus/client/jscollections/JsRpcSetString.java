// AUTOGENERATED: See com.dotspots.thrift.gwt.codegen.collections.CollectionGen for more details
package com.dotspots.rpcplus.client.jscollections;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.lang.LongLib;

@SuppressWarnings("unused")
public final class JsRpcSetString extends JavaScriptObject {
    protected JsRpcSetString() {
    }

    public static JsRpcSetString create() {
        return JavaScriptObject.createObject().cast();
    }

    public native boolean contains(String idx) /*-{
        return ('_' + idx in this);
    }-*/;

    public Iterable<String> iterable() {
        return RpcUtils.<String>getSetIterable(this);
    }

    /**
     * Counts the size of a collection through brute force (slow).
     */
    public native int countSize() /*-{
        var l = 0; for (x in this) if (this.hasOwnProperty(x)) l++; return l;
    }-*/;

    public native boolean isEmpty() /*-{
        for (x in this) if (this.hasOwnProperty(x)) return false; return true;
    }-*/;

    public native void remove(String idx) /*-{
        delete this['_' + idx];
    }-*/;

    public native void add(String idx) /*-{
        this['_' + idx] = 0;
    }-*/;

}
