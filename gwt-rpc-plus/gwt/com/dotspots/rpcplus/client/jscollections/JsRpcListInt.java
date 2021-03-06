// AUTOGENERATED: See com.dotspots.rpcplus.codegen.jscollections.CollectionGen for more details
package com.dotspots.rpcplus.client.jscollections;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.UnsafeNativeLong;
import com.google.gwt.core.client.GWT;
import com.google.gwt.lang.LongLib;

@SuppressWarnings("unused")
public final class JsRpcListInt extends JavaScriptObject {
    protected JsRpcListInt() {
    }

    public static JsRpcListInt create() {
        return JavaScriptObject.createArray().cast();
    }

    public native boolean contains(int idx) /*-{
        return this.hasOwnProperty(idx);
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

    public boolean forEach(JsRpcIntProcedure procedure) {
        for (int i = 0; i < size(); i++) { 
            if (!procedure.execute(get(i))) return false;
        }
        return true;
    };

    public boolean forEach(JsRpcIntIntProcedure procedure) {
        for (int i = 0; i < size(); i++) { 
            if (!procedure.execute(i, get(i))) return false;
        }
        return true;
    };

    @UnsafeNativeLong
    public native int get(int idx) /*-{
        return this[idx] || 0;
    }-*/;

    @UnsafeNativeLong
    public native void set(int idx, int value) /*-{
        this[idx] = value;
    }-*/;

    /**
     * Adds an item to the end of the list, returning the list's new size.
     */
    @UnsafeNativeLong
    public native int add(int value) /*-{
        return this.push(value);
    }-*/;

    /**
     * Adds an item to the end of the list, returning the list's new size.
     */
    @UnsafeNativeLong
    public native int push(int value) /*-{
        return this.push(value);
    }-*/;

    /**
     * Pops an item off the end of the list, returning it.
     */
    @UnsafeNativeLong
    public native int pop() /*-{
        return this.pop() || 0;
    }-*/;

    /**
     * Peeks at the item at the end of the list.
     */
    public int peek() {
        return this.get(this.size() - 1);
    };

    /**
     * Unshifts an item into position 0, returning the new size of the list.
     */
    @UnsafeNativeLong
    public native int unshift(int value) /*-{
        return this.unshift(value);
    }-*/;

    /**
     * Shifts an item out of position 0 and returns it.
     */
    @UnsafeNativeLong
    public native int shift() /*-{
        return this.shift() || 0;
    }-*/;

    public native String join(String separator) /*-{
        return this.join(separator);
    }-*/;

    public native JsRpcListInt slice(int index) /*-{
        return this.slice(index);
    }-*/;

    public native JsRpcListInt slice(int from, int to) /*-{
        return this.slice(from, to);
    }-*/;

    public native JsRpcListInt clear() /*-{
        this.splice(0, this.length);
    }-*/;

    public native JsRpcListInt splice(int index) /*-{
        return this.splice(index, this.length);
    }-*/;

    public native JsRpcListInt splice(int index, int howMany) /*-{
        return this.splice(index, howMany);
    }-*/;

}
