// AUTOGENERATED: See com.dotspots.rpcplus.codegen.jscollections.CollectionGen for more details
package com.dotspots.rpcplus.client.jscollections;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.lang.LongLib;

@SuppressWarnings("unused")
public final class JsRpcListBool extends JavaScriptObject {
    protected JsRpcListBool() {
    }

    public static JsRpcListBool create() {
        return JavaScriptObject.createArray().cast();
    }

    public native boolean contains(int idx) /*-{
        return (idx in this);
    }-*/;

    public native int size() /*-{
        return this.length;
    }-*/;

    public native boolean isEmpty() /*-{
        return !this.length;
    }-*/;

    public native void remove(int idx) /*-{
        this.splice(idx, 1);
    }-*/;

    public native boolean forEach(JsRpcBoolProcedure procedure) /*-{
        for (var i = 0; i < this.length; i++) { 
            if (!procedure.@com.dotspots.rpcplus.client.jscollections.JsRpcBoolProcedure::execute(Z)(this[i])) return false;
        }
        return true;
    }-*/;

    public native boolean forEach(JsRpcIntBoolProcedure procedure) /*-{
        for (var i = 0; i < this.length; i++) { 
            if (!procedure.@com.dotspots.rpcplus.client.jscollections.JsRpcIntBoolProcedure::execute(IZ)(i, this[i])) return false;
        }
        return true;
    }-*/;

    public native boolean get(int idx) /*-{
        // Coerce to boolean in case underlying value is integer
        return !!this[idx];
    }-*/;

    public native void set(int idx, boolean value) /*-{
        this[idx] = value;
    }-*/;

    public native void add(boolean value) /*-{
        this.push(value);
    }-*/;

}
