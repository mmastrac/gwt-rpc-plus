// AUTOGENERATED: See com.dotspots.thrift.gwt.codegen.collections.CollectionGen for more details
package com.dotspots.rpcplus.client.jscollections;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.lang.LongLib;

@SuppressWarnings("unused")
public final class JsRpcMapStringLong extends JavaScriptObject {
    protected JsRpcMapStringLong() {
    }

    public static JsRpcMapStringLong create() {
        return JavaScriptObject.createObject().cast();
    }

    public native boolean contains(String idx) /*-{
        return ('_' + idx in this);
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

    public native void remove(String idx) /*-{
        delete this['_' + idx];
    }-*/;

    public long get(String idx) {
        return RpcUtils.fromDoubles(get0(idx));
    };

    private native JavaScriptObject get0(String idx) /*-{
        return this['_' + idx] || [0,0];
    }-*/;

    public void set(String idx, long value) {
        set0(idx, RpcUtils.toDoubles(value));
    }

    private native void set0(String idx, JavaScriptObject value) /*-{
        this['_' + idx] = value;
    }-*/;

}
