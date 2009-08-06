// AUTOGENERATED: See com.dotspots.thrift.gwt.codegen.collections.CollectionGen for more details
package com.dotspots.rpcplus.client.jscollections;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.lang.LongLib;

@SuppressWarnings("unused")
public final class JsRpcList<E> extends JavaScriptObject {
    protected JsRpcList() {
    }

    public static <E> JsRpcList<E> create() {
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

    public native E get(int idx) /*-{
        return this[idx] || null;
    }-*/;

    public native void set(int idx, E value) /*-{
        this[idx] = value;
    }-*/;

    public native void add(E value) /*-{
        this.push(value);
    }-*/;

}
