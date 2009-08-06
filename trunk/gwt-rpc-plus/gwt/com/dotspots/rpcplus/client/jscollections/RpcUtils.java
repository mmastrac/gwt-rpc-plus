package com.dotspots.rpcplus.client.jscollections;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.UnsafeNativeLong;
import com.google.gwt.lang.LongLib;

public class RpcUtils {
	public static JavaScriptObject toDoubles(long value) {
		if (GWT.isScript()) {
			return toDoubles0(value);
		}

		boolean saved = LongLib.RUN_IN_JVM;
		LongLib.RUN_IN_JVM = true;

		try {
			double[] doubles = LongLib.typeChange(value);
			return makeDoubles(doubles[0], doubles[1]);
		} finally {
			LongLib.RUN_IN_JVM = saved;
		}
	}

	public static long fromDoubles(JavaScriptObject doubles) {
		return GWT.isScript() ? fromDoubles0(doubles) : ((long) get(doubles, 0) + (long) get(doubles, 1));
	}

	@UnsafeNativeLong
	private static native JavaScriptObject toDoubles0(long value) /*-{
		return value;
	}-*/;

	@UnsafeNativeLong
	private static native long fromDoubles0(JavaScriptObject value) /*-{
		return value;
	}-*/;

	private static native JavaScriptObject makeDoubles(double d1, double d2) /*-{
		return [d1, d2];
	}-*/;

	public static native double get(JavaScriptObject array, int index) /*-{
		return array[index];
	}-*/;

	public static <T> Iterable<T> getMapIterable(final JavaScriptObject jso) {
		return new Iterable<T>() {
			public Iterator<T> iterator() {
				return new JsMapIterator<T>(jso);
			};
		};
	}

	public static <T> Iterable<String> getSetIterable(final JavaScriptObject jso) {
		return new Iterable<String>() {
			public Iterator<String> iterator() {
				return new JsSetIterator<String>(jso);
			};
		};
	}
}
