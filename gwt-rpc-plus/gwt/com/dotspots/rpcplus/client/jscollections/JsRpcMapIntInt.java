// AUTOGENERATED: See com.dotspots.rpcplus.codegen.jscollections.CollectionGen for more details
package com.dotspots.rpcplus.client.jscollections;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.lang.LongLib;

@SuppressWarnings("unused")
public final class JsRpcMapIntInt extends JavaScriptObject {
    protected JsRpcMapIntInt() {
    }

    public static JsRpcMapIntInt create() {
        return JavaScriptObject.createObject().cast();
    }

    public native boolean contains(int idx) /*-{
        return (idx in this);
    }-*/;

    public native boolean forEachEntry(JsRpcIntIntProcedure procedure) /*-{
        for (x in this) { 
            if (this.hasOwnProperty(x)) {
                if (!procedure.@com.dotspots.rpcplus.client.jscollections.JsRpcIntIntProcedure::execute(II)(x.slice(1), this[x])) return false;
            }
        }
        return true;
    }-*/;

    public native boolean forEachKey(JsRpcIntProcedure procedure) /*-{
        for (x in this) { 
            if (this.hasOwnProperty(x)) {
                if (!procedure.@com.dotspots.rpcplus.client.jscollections.JsRpcIntProcedure::execute(I)(x.slice(1))) return false;
            }
        }
        return true;
    }-*/;

    public native boolean forEachValue(JsRpcIntProcedure procedure) /*-{
        for (x in this) { 
            if (this.hasOwnProperty(x)) {
                if (!procedure.@com.dotspots.rpcplus.client.jscollections.JsRpcIntProcedure::execute(I)(this[x])) return false;
            }
        }
        return true;
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

    public native int get(int idx) /*-{
        return this[idx] || 0;
    }-*/;

    public native void set(int idx, int value) /*-{
        this[idx] = value;
    }-*/;

}
