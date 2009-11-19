// AUTOGENERATED: See com.dotspots.rpcplus.codegen.jscollections.CollectionGen for more details
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

    public native boolean forEach(JsRpcObjectProcedure<E> procedure) /*-{
        for (var i = 0; i < this.length; i++) { 
            if (!procedure.@com.dotspots.rpcplus.client.jscollections.JsRpcObjectProcedure::execute(Ljava/lang/Object;)(this[i])) return false;
        }
        return true;
    }-*/;

    public native boolean forEach(JsRpcIntObjectProcedure<E> procedure) /*-{
        for (var i = 0; i < this.length; i++) { 
            if (!procedure.@com.dotspots.rpcplus.client.jscollections.JsRpcIntObjectProcedure::execute(ILjava/lang/Object;)(i, this[i])) return false;
        }
        return true;
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

    public native void pop() /*-{
        this.pop();
    }-*/;

    public E peek() {
        return this.get(this.size() - 1);
    };

    public native String join(String separator) /*-{
        return this.join(separator);
    }-*/;

    public native JsRpcList<E> slice(int index) /*-{
        return this.slice(index);
    }-*/;

    public native JsRpcList<E> slice(int from, int to) /*-{
        return this.slice(from, to);
    }-*/;

    public native JsRpcList<E> clear() /*-{
        this.splice(0, this.length);
    }-*/;

    public native JsRpcList<E> splice(int index) /*-{
        return this.splice(index, this.length);
    }-*/;

    public native JsRpcList<E> splice(int index, int howMany) /*-{
        return this.splice(index, howMany);
    }-*/;

}
