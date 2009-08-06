// AUTOGENERATED: See com.dotspots.thrift.gwt.codegen.collections.CollectionGen for more details
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

    public native boolean get(int idx) /*-{
        return this[idx] || false;
    }-*/;

    public native void set(int idx, boolean value) /*-{
        this[idx] = value;
    }-*/;

    public native void add(boolean value) /*-{
        this.push(value);
    }-*/;

}
