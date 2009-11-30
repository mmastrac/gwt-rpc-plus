package com.dotspots.rpcplus.example.torturetest.client;

import com.dotspots.rpcplus.client.transport.*;
import com.dotspots.rpcplus.client.jsonrpc.*;
import com.dotspots.rpcplus.client.jscollections.*;

@SuppressWarnings("unused")
public final class ObjectWithComplexTypes extends com.dotspots.rpcplus.client.jsonrpc.BaseJsRpcObject {
    // GWT requires a protected constructor
    protected ObjectWithComplexTypes() {
    }

    /* Factory method */
    public static ObjectWithComplexTypes create() {
        return com.google.gwt.core.client.JavaScriptObject.createArray().cast();
    }

    /* Factory method, strongly dependent on order of fields */
    public static native ObjectWithComplexTypes create(JsRpcMapString<String> mapStringToString, JsRpcSetString setOfStrings, JsRpcList<String> listOfStrings, JsRpcMapIntInt mapOfIntToInt) /*-{
        return [mapStringToString,setOfStrings,listOfStrings,mapOfIntToInt];
    }-*/;

    public native JsRpcMapString<String> getMapStringToString() /*-{
         return this[0];
    }-*/;

    public native void setMapStringToString(JsRpcMapString<String> mapStringToString) /*-{
         this[0] = mapStringToString;
    }-*/;

    public native boolean isSetMapStringToString() /*-{
         return this[0] != null;
    }-*/;

    public native void unsetMapStringToString() /*-{
         delete this[0];
    }-*/;

    public native JsRpcSetString getSetOfStrings() /*-{
         return this[1];
    }-*/;

    public native void setSetOfStrings(JsRpcSetString setOfStrings) /*-{
         this[1] = setOfStrings;
    }-*/;

    public native boolean isSetSetOfStrings() /*-{
         return this[1] != null;
    }-*/;

    public native void unsetSetOfStrings() /*-{
         delete this[1];
    }-*/;

    public native JsRpcList<String> getListOfStrings() /*-{
         return this[2];
    }-*/;

    public native void setListOfStrings(JsRpcList<String> listOfStrings) /*-{
         this[2] = listOfStrings;
    }-*/;

    public native boolean isSetListOfStrings() /*-{
         return this[2] != null;
    }-*/;

    public native void unsetListOfStrings() /*-{
         delete this[2];
    }-*/;

    public native JsRpcMapIntInt getMapOfIntToInt() /*-{
         return this[3];
    }-*/;

    public native void setMapOfIntToInt(JsRpcMapIntInt mapOfIntToInt) /*-{
         this[3] = mapOfIntToInt;
    }-*/;

    public native boolean isSetMapOfIntToInt() /*-{
         return this[3] != null;
    }-*/;

    public native void unsetMapOfIntToInt() /*-{
         delete this[3];
    }-*/;

}
