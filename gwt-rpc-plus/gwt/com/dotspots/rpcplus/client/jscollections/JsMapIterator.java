package com.dotspots.rpcplus.client.jscollections;

import java.util.Iterator;

import com.google.gwt.core.client.JavaScriptObject;

class JsMapIterator<T> implements Iterator<T> {
	private JsRpcListString keys;
	private int index;
	private final JavaScriptObject jso;

	public JsMapIterator(JavaScriptObject jso) {
		this.jso = jso;
		keys = getKeys(jso);
	}

	public boolean hasNext() {
		return index++ < keys.size();
	}

	public T next() {
		return get(jso, keys.get(index));
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

	private native T get(JavaScriptObject jso, String key) /*-{
		return jso[key];
	}-*/;

	private native void delete(JavaScriptObject jso, String key) /*-{
		delete jso[key];
	}-*/;
}
