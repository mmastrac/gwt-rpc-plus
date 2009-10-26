// AUTOGENERATED: See com.dotspots.rpcplus.codegen.jscollections.CollectionGen for more details
package com.dotspots.rpcplus.client.jscollections;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.lang.LongLib;

@SuppressWarnings("unused")
public final class JsRpcListLong extends JavaScriptObject {
    protected JsRpcListLong() {
    }

    public static JsRpcListLong create() {
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


    public long get(int idx) {
        return RpcUtils.fromDoubles(get0(idx));
    };

    private native JavaScriptObject get0(int idx) /*-{
        return this[idx] || [0,0];
    }-*/;

    public void set(int idx, long value) {
        set0(idx, RpcUtils.toDoubles(value));
    }

    private native void set0(int idx, JavaScriptObject value) /*-{
        this[idx] = value;
    }-*/;

    public void add(long value) {
        add0(RpcUtils.toDoubles(value));
    }

    public native void add0(JavaScriptObject value) /*-{
        this.push(value);
    }-*/;

}
