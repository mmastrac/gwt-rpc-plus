package com.dotspots.rpcplus.test.client;

import com.dotspots.rpcplus.client.jscollections.JsRpcIntObjectProcedure;
import com.dotspots.rpcplus.client.jscollections.JsRpcList;
import com.dotspots.rpcplus.client.jscollections.JsRpcListBool;
import com.dotspots.rpcplus.client.jscollections.JsRpcListInt;
import com.dotspots.rpcplus.client.jscollections.JsRpcListLong;
import com.dotspots.rpcplus.client.jscollections.JsRpcMapInt;
import com.dotspots.rpcplus.client.jscollections.JsRpcMapIntLong;
import com.dotspots.rpcplus.client.jscollections.JsRpcMapStringLong;
import com.dotspots.rpcplus.client.jscollections.JsRpcMapStringString;
import com.dotspots.rpcplus.client.jscollections.JsRpcSetString;
import com.dotspots.rpcplus.client.jscollections.JsRpcStringProcedure;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;

public class TestThriftCollections extends GWTTestCase {
	@Override
	public String getModuleName() {
		return "com.dotspots.rpcplus.test.Test";
	}

	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();
		addObjectPrototypeBadness();
	}

	@Override
	protected void gwtTearDown() throws Exception {
		super.gwtTearDown();
		removeObjectPrototypeBadness();
	}

	public void testEmptySizes() {
		assertEquals(0, JsRpcList.<Object> create().size());
		assertEquals(0, JsRpcSetString.create().countSize());
		assertEquals(0, JsRpcMapStringString.create().countSize());
	}

	public void testList() {
		JsRpcList<JavaScriptObject> list = JsRpcList.create();
		list.add(JavaScriptObject.createArray());
		list.add(JavaScriptObject.createArray());

		assertEquals(2, list.size());
	}

	public void testListForEach() {
		JsRpcList<JavaScriptObject> list = JsRpcList.create();
		list.add(JavaScriptObject.createArray());
		list.add(JavaScriptObject.createArray());

		final int[] result = new int[2];

		assertTrue(list.forEach(new JsRpcIntObjectProcedure<JavaScriptObject>() {
			public boolean execute(int a, JavaScriptObject b) {
				result[a] = 1;
				return true;
			}
		}));

		assertEquals(1, result[0]);
		assertEquals(1, result[1]);
	}

	public void testListForEach2() {
		JsRpcList<JavaScriptObject> list = JsRpcList.create();
		list.add(JavaScriptObject.createArray());
		list.add(JavaScriptObject.createArray());

		final int[] result2 = new int[2];

		assertFalse(list.forEach(new JsRpcIntObjectProcedure<JavaScriptObject>() {
			public boolean execute(int a, JavaScriptObject b) {
				result2[a] = 1;
				if (a == 0) {
					return false;
				} else {
					return true;
				}
			}
		}));

		assertEquals(1, result2[0]);
		assertEquals(0, result2[1]);
	}

	public void testListLong() {
		JsRpcListLong list = JsRpcListLong.create();
		list.add(1);
		list.add(0xffffeeeeddddccccL);
		list.add(0x777777771111ccccL);
		list.add(0x00000000ffffccccL);

		assertEquals(4, list.size());

		assertEquals(1, list.get(0));
		assertEquals(0xffffeeeeddddccccL, list.get(1));
		assertEquals(0x777777771111ccccL, list.get(2));
		assertEquals(0x00000000ffffccccL, list.get(3));

		list.remove(1);

		assertEquals(3, list.size());
		assertEquals(1, list.get(0));
		assertEquals(0x777777771111ccccL, list.get(1));
		assertEquals(0x00000000ffffccccL, list.get(2));
	}

	public void testMap() {
		JsRpcMapInt<JavaScriptObject> map = JsRpcMapInt.create();
		map.set(0, JavaScriptObject.createArray());
		map.set(10000, JavaScriptObject.createArray());

		assertEquals(2, map.countSize());
	}

	public void testMapIntLong() {
		JsRpcMapIntLong map = JsRpcMapIntLong.create();
		map.set(0, 1L);
		map.set(10000, 0xffffffffffffffffL);

		assertEquals(2, map.countSize());

		assertEquals(1L, map.get(0));
		assertEquals(0xffffffffffffffffL, map.get(10000));

		map.remove(0);

		assertEquals(1, map.countSize());

		assertEquals(0L, map.get(0));
		assertEquals(0xffffffffffffffffL, map.get(10000));
	}

	public void testMapStringLong() {
		JsRpcMapStringLong map = JsRpcMapStringLong.create();
		map.set("", 1L);
		map.set("watch", 2L);
		map.set("watch", 0xffffffffffffffffL);

		assertEquals(2, map.countSize());
		assertEquals(1L, map.get(""));
		assertEquals(0xffffffffffffffffL, map.get("watch"));

		map.remove("");
		assertEquals(1, map.countSize());
		assertEquals(0L, map.get(""));
		assertEquals(0xffffffffffffffffL, map.get("watch"));
	}

	public void testMapStringString() {
		JsRpcMapStringString map = JsRpcMapStringString.create();
		map.set("", "a");
		map.set("watch", "b");
		map.set("watch", "c");

		assertEquals(2, map.countSize());
		assertEquals("a", map.get(""));
		assertEquals("c", map.get("watch"));

		map.remove("");
		assertEquals(1, map.countSize());
		assertEquals(null, map.get(""));
		assertEquals("c", map.get("watch"));
	}

	public void testSetStringIterable() {
		JsRpcSetString set = JsRpcSetString.create();
		set.add("");
		set.add("watch");

		boolean foundBlank = false;
		boolean foundWatch = false;

		for (String s : set.iterable()) {
			if (s.equals("")) {
				foundBlank = true;
			} else if (s.equals("watch")) {
				foundWatch = true;
			} else {
				fail("Found unexpected key: " + s);
			}
		}

		assertTrue(foundBlank);
		assertTrue(foundWatch);
	}

	public void testSetString() {
		JsRpcSetString set = JsRpcSetString.create();
		set.add("");
		set.add("watch");
		set.add("watch");

		assertEquals(2, set.countSize());
		assertTrue(set.contains(""));
		assertTrue(set.contains("watch"));
		assertFalse(set.contains("_"));

		set.remove("_");
		assertEquals(2, set.countSize());

		set.remove("");
		assertEquals(1, set.countSize());
		assertFalse(set.contains(""));
		assertTrue(set.contains("watch"));
		assertFalse(set.contains("_"));
	}

	/**
	 * Tests the forEach method over a set.
	 */
	public void testSetStringForEach() {
		JsRpcSetString set = JsRpcSetString.create();
		set.add("");
		set.add("watch");

		final boolean[] result = new boolean[3];

		assertTrue(set.forEach(new JsRpcStringProcedure() {
			public boolean execute(String value) {
				if (value.equals("")) {
					result[0] = true;
				} else if (value.equals("watch")) {
					result[1] = true;
				} else {
					result[2] = true;
				}

				return true;
			}
		}));

		assertTrue(result[0]);
		assertTrue(result[1]);
		assertFalse(result[2]);
	}

	/**
	 * Test that a list of ints can work as a list of booleans (this is legal).
	 */
	public void testListBooleanWithInts() {
		JsRpcListInt intList = JsRpcListInt.create();
		intList.add(1);
		intList.add(0);
		intList.add(1);
		intList.add(2);

		JsRpcListBool boolList = intList.cast();
		assertTrue(boolList.get(0));
		assertFalse(boolList.get(1));
		assertTrue(boolList.get(2));
		assertTrue(boolList.get(3));
	}

	private native void removeObjectPrototypeBadness() /*-{
		delete Object.prototype.dontDoThis;
	}-*/;

	private native void addObjectPrototypeBadness() /*-{
		Object.prototype.dontDoThis = true;
	}-*/;

	public void testIsEmpty() {
		JsRpcList<JavaScriptObject> list1 = JsRpcList.create();
		assertTrue(list1.isEmpty());
		list1.add(JavaScriptObject.createArray());
		assertFalse(list1.isEmpty());
		list1.remove(0);
		assertTrue(list1.isEmpty());

		JsRpcListLong list2 = JsRpcListLong.create();
		assertTrue(list2.isEmpty());
		list2.add(0L);
		assertFalse(list2.isEmpty());
		list2.remove(0);
		assertTrue(list2.isEmpty());

		JsRpcSetString set1 = JsRpcSetString.create();
		assertTrue(set1.isEmpty());
		set1.add("");
		assertFalse(set1.isEmpty());
		set1.remove("");
		assertTrue(set1.isEmpty());

	}
}
