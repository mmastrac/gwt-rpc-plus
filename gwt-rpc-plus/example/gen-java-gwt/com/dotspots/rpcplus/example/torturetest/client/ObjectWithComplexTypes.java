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
    public static native ObjectWithComplexTypes create(JsRpcMapStringString mapStringToString, JsRpcSetString setOfStrings, JsRpcListString listOfStrings, JsRpcMapIntInt mapOfIntToInt, JsRpcList<JsRpcMapStringString> listOfMapStringToString, JsRpcList<JsRpcMapIntString> listOfMapI32ToString, JsRpcList<JsRpcMapStringInt> listOfMapStringToI32, JsRpcMapString<JsRpcMapIntInt> mapOfMapI32ToI32, JsRpcMapString<JsRpcMapStringString> mapOfMapStringToString) /*-{
        return [mapStringToString,setOfStrings,listOfStrings,mapOfIntToInt,listOfMapStringToString,listOfMapI32ToString,listOfMapStringToI32,mapOfMapI32ToI32,mapOfMapStringToString];
    }-*/;

    public native JsRpcMapStringString getMapStringToString() /*-{
         return this[0];
    }-*/;

    public native void setMapStringToString(JsRpcMapStringString mapStringToString) /*-{
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

    public native JsRpcListString getListOfStrings() /*-{
         return this[2];
    }-*/;

    public native void setListOfStrings(JsRpcListString listOfStrings) /*-{
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

    public native JsRpcList<JsRpcMapStringString> getListOfMapStringToString() /*-{
         return this[4];
    }-*/;

    public native void setListOfMapStringToString(JsRpcList<JsRpcMapStringString> listOfMapStringToString) /*-{
         this[4] = listOfMapStringToString;
    }-*/;

    public native boolean isSetListOfMapStringToString() /*-{
         return this[4] != null;
    }-*/;

    public native void unsetListOfMapStringToString() /*-{
         delete this[4];
    }-*/;

    public native JsRpcList<JsRpcMapIntString> getListOfMapI32ToString() /*-{
         return this[5];
    }-*/;

    public native void setListOfMapI32ToString(JsRpcList<JsRpcMapIntString> listOfMapI32ToString) /*-{
         this[5] = listOfMapI32ToString;
    }-*/;

    public native boolean isSetListOfMapI32ToString() /*-{
         return this[5] != null;
    }-*/;

    public native void unsetListOfMapI32ToString() /*-{
         delete this[5];
    }-*/;

    public native JsRpcList<JsRpcMapStringInt> getListOfMapStringToI32() /*-{
         return this[6];
    }-*/;

    public native void setListOfMapStringToI32(JsRpcList<JsRpcMapStringInt> listOfMapStringToI32) /*-{
         this[6] = listOfMapStringToI32;
    }-*/;

    public native boolean isSetListOfMapStringToI32() /*-{
         return this[6] != null;
    }-*/;

    public native void unsetListOfMapStringToI32() /*-{
         delete this[6];
    }-*/;

    public native JsRpcMapString<JsRpcMapIntInt> getMapOfMapI32ToI32() /*-{
         return this[7];
    }-*/;

    public native void setMapOfMapI32ToI32(JsRpcMapString<JsRpcMapIntInt> mapOfMapI32ToI32) /*-{
         this[7] = mapOfMapI32ToI32;
    }-*/;

    public native boolean isSetMapOfMapI32ToI32() /*-{
         return this[7] != null;
    }-*/;

    public native void unsetMapOfMapI32ToI32() /*-{
         delete this[7];
    }-*/;

    public native JsRpcMapString<JsRpcMapStringString> getMapOfMapStringToString() /*-{
         return this[8];
    }-*/;

    public native void setMapOfMapStringToString(JsRpcMapString<JsRpcMapStringString> mapOfMapStringToString) /*-{
         this[8] = mapOfMapStringToString;
    }-*/;

    public native boolean isSetMapOfMapStringToString() /*-{
         return this[8] != null;
    }-*/;

    public native void unsetMapOfMapStringToString() /*-{
         delete this[8];
    }-*/;

}
