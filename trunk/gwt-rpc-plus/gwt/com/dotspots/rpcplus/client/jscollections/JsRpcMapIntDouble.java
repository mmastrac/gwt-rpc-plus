// AUTOGENERATED: See com.dotspots.thrift.gwt.codegen.collections.CollectionGen for more details
package com.dotspots.rpcplus.client.jscollections;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.lang.LongLib;

@SuppressWarnings("unused")
public final class JsRpcMapIntDouble extends JavaScriptObject {
    protected JsRpcMapIntDouble() {
    }

    public static JsRpcMapIntDouble create() {
        return JavaScriptObject.createObject().cast();
    }

    public native boolean contains(int idx) /*-{
        return (idx in this);
    }-*/;

    /**
     * Counts the size of a collection through brute force (slow).
     */
    public native int countSize() /*-{
        var l = 0; for (x in this) if (this.hasOwnProperty(x)) l++; return l;
    }-*/;

    public native boolean isEmpty() /*-{
        for (x in this) if (this.hasOwnProperty(x)) return false; return true;
    }-*/;

    public native void remove(int idx) /*-{
        delete this[idx];
    }-*/;

    public native double get(int idx) /*-{
        return this[idx] || 0;
    }-*/;

    public native void set(int idx, double value) /*-{
        this[idx] = value;
    }-*/;

}
