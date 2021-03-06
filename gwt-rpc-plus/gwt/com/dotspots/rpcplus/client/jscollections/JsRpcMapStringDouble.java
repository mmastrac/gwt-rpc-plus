// AUTOGENERATED: See com.dotspots.rpcplus.codegen.jscollections.CollectionGen for more details
package com.dotspots.rpcplus.client.jscollections;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.UnsafeNativeLong;
import com.google.gwt.core.client.GWT;
import com.google.gwt.lang.LongLib;

@SuppressWarnings("unused")
public final class JsRpcMapStringDouble extends JavaScriptObject {
    protected JsRpcMapStringDouble() {
    }

    public static JsRpcMapStringDouble create() {
        return JavaScriptObject.createObject().cast();
    }

    public native boolean contains(String idx) /*-{
        return this.hasOwnProperty('_' + idx);
    }-*/;

    public native boolean forEachEntry(JsRpcStringDoubleProcedure procedure) /*-{
        for (x in this) { 
            if (this.hasOwnProperty(x)) {
                if (!procedure.@com.dotspots.rpcplus.client.jscollections.JsRpcStringDoubleProcedure::execute(Ljava/lang/String;D)(x.slice(1), this[x])) return false;
            }
        }
        return true;
    }-*/;

    public native boolean forEachKey(JsRpcStringProcedure procedure) /*-{
        for (x in this) { 
            if (this.hasOwnProperty(x)) {
                if (!procedure.@com.dotspots.rpcplus.client.jscollections.JsRpcStringProcedure::execute(Ljava/lang/String;)(x.slice(1))) return false;
            }
        }
        return true;
    }-*/;

    public native boolean forEachValue(JsRpcDoubleProcedure procedure) /*-{
        for (x in this) { 
            if (this.hasOwnProperty(x)) {
                if (!procedure.@com.dotspots.rpcplus.client.jscollections.JsRpcDoubleProcedure::execute(D)(this[x])) return false;
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

    public native void remove(String idx) /*-{
        delete this['_' + idx];
    }-*/;

    @UnsafeNativeLong
    public native double get(String idx) /*-{
        return this['_' + idx] || 0;
    }-*/;

    @UnsafeNativeLong
    public native void set(String idx, double value) /*-{
        this['_' + idx] = value;
    }-*/;

}
