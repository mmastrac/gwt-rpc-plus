package com.dotspots.rpcplus.client.jscollections;

public class JsRpcListUtils {
	/**
	 * Performs a brute-force, O(n), linear search of an unsorted list for an object reference.
	 */
	public static <E> int linearSearch(JsRpcList<E> list, E value) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == value) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Performs a brute-force, O(n), linear search of an unsorted list.
	 */
	public static <E> int linearSearch(JsRpcListString list, String value) {
		for (int i = 0; i < list.size(); i++) {
			if (RpcUtils.fastStringEquals(list.get(i), value)) {
				return i;
			}
		}

		return -1;
	}
}
