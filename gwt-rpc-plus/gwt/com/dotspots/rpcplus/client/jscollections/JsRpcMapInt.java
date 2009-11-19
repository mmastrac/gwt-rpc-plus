// AUTOGENERATED: See com.dotspots.rpcplus.codegen.jscollections.CollectionGen for more details
package com.dotspots.rpcplus.client.jscollections;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.lang.LongLib;

@SuppressWarnings("unused")
public final class JsRpcMapInt<E> extends JavaScriptObject {
    protected JsRpcMapInt() {
    }

    public static <E> JsRpcMapInt<E> create() {
        return JavaScriptObject.createObject().cast();
    }

    public native boolean contains(int idx) /*-{
        return this.hasOwnProperty(idx);
    }-*/;

    public Iterable<E> keysIterable() {
        return RpcUtils.<E>getMapIterable(this);
    }

    public native boolean forEachEntry(JsRpcIntObjectProcedure<E> procedure) /*-{
        for (x in this) { 
            if (this.hasOwnProperty(x)) {
                if (!procedure.@com.dotspots.rpcplus.client.jscollections.JsRpcIntObjectProcedure::execute(ILjava/lang/Object;)(x.slice(1), this[x])) return false;
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

    public native boolean forEachValue(JsRpcObjectProcedure<E> procedure) /*-{
        for (x in this) { 
            if (this.hasOwnProperty(x)) {
                if (!procedure.@com.dotspots.rpcplus.client.jscollections.JsRpcObjectProcedure::execute(Ljava/lang/Object;)(this[x])) return false;
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

    public native E get(int idx) /*-{
        return this[idx] || null;
    }-*/;

    public native void set(int idx, E value) /*-{
        this[idx] = value;
    }-*/;

}
