package com.dotspots.rpcplus.client.jscollections;

import java.util.Iterator;

import com.google.gwt.core.client.JavaScriptObject;

class JsSetIterator<T> implements Iterator<String> {
	private JsRpcListString keys;
	private int index = 0;
	private final JavaScriptObject jso;

	public JsSetIterator(JavaScriptObject jso) {
		this.jso = jso;
		keys = getKeys(jso);
	}

	public boolean hasNext() {
		return index < keys.size();
	}

	public String next() {
		return keys.get(index++).substring(1);
	}

	public void remove() {
		delete(jso, keys.get(index));
	}

	private native JsRpcListString getKeys(JavaScriptObject jso) /*-{
		var keys = [];
		for (x in jso)
		if (jso.hasOwnProperty(x))
		keys.push(x);

		return keys;
	}-*/;

	private native void delete(JavaScriptObject jso, String key) /*-{
		delete jso[key];
	}-*/;
}
