// AUTOGENERATED: See com.dotspots.rpcplus.codegen.jscollections.CollectionGen for more details
package com.dotspots.rpcplus.client.jscollections;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.UnsafeNativeLong;
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

    public boolean forEach(JsRpcBoolProcedure procedure) {
        for (int i = 0; i < size(); i++) { 
            if (!procedure.execute(get(i))) return false;
        }
        return true;
    };

    public boolean forEach(JsRpcIntBoolProcedure procedure) {
        for (int i = 0; i < size(); i++) { 
            if (!procedure.execute(i, get(i))) return false;
        }
        return true;
    };

    @UnsafeNativeLong
    public native boolean get(int idx) /*-{
        return !!this[idx];
    }-*/;

    @UnsafeNativeLong
    public native void set(int idx, boolean value) /*-{
        this[idx] = value;
    }-*/;

    /**
     * Adds an item to the end of the list, returning the list's new size.
     */
    @UnsafeNativeLong
    public native int add(boolean value) /*-{
        return this.push(value);
    }-*/;

    /**
     * Adds an item to the end of the list, returning the list's new size.
     */
    @UnsafeNativeLong
    public native int push(boolean value) /*-{
        return this.push(value);
    }-*/;

    /**
     * Pops an item off the end of the list, returning it.
     */
    @UnsafeNativeLong
    public native boolean pop() /*-{
        return !!this.pop();
    }-*/;

    /**
     * Peeks at the item at the end of the list.
     */
    public boolean peek() {
        return this.get(this.size() - 1);
    };

    /**
     * Unshifts an item into position 0, returning the new size of the list.
     */
    @UnsafeNativeLong
    public native int unshift(boolean value) /*-{
        return this.unshift(value);
    }-*/;

    /**
     * Shifts an item out of position 0 and returns it.
     */
    @UnsafeNativeLong
    public native boolean shift() /*-{
        return !!this.shift();
    }-*/;

    public native String join(String separator) /*-{
        return this.join(separator);
    }-*/;

    public native JsRpcListBool slice(int index) /*-{
        return this.slice(index);
    }-*/;

    public native JsRpcListBool slice(int from, int to) /*-{
        return this.slice(from, to);
    }-*/;

    public native JsRpcListBool clear() /*-{
        this.splice(0, this.length);
    }-*/;

    public native JsRpcListBool splice(int index) /*-{
        return this.splice(index, this.length);
    }-*/;

    public native JsRpcListBool splice(int index, int howMany) /*-{
        return this.splice(index, howMany);
    }-*/;

}
