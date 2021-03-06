package com.dotspots.rpcplus.test.client;

import java.util.Iterator;

import com.dotspots.rpcplus.client.jscollections.JsRpcIntObjectProcedure;
import com.dotspots.rpcplus.client.jscollections.JsRpcList;
import com.dotspots.rpcplus.client.jscollections.JsRpcListBool;
import com.dotspots.rpcplus.client.jscollections.JsRpcListDouble;
import com.dotspots.rpcplus.client.jscollections.JsRpcListInt;
import com.dotspots.rpcplus.client.jscollections.JsRpcListLong;
import com.dotspots.rpcplus.client.jscollections.JsRpcListString;
import com.dotspots.rpcplus.client.jscollections.JsRpcListUtils;
import com.dotspots.rpcplus.client.jscollections.JsRpcMapInt;
import com.dotspots.rpcplus.client.jscollections.JsRpcMapIntBool;
import com.dotspots.rpcplus.client.jscollections.JsRpcMapIntDouble;
import com.dotspots.rpcplus.client.jscollections.JsRpcMapIntInt;
import com.dotspots.rpcplus.client.jscollections.JsRpcMapIntLong;
import com.dotspots.rpcplus.client.jscollections.JsRpcMapIntString;
import com.dotspots.rpcplus.client.jscollections.JsRpcMapStringLong;
import com.dotspots.rpcplus.client.jscollections.JsRpcMapStringString;
import com.dotspots.rpcplus.client.jscollections.JsRpcSetString;
import com.dotspots.rpcplus.client.jscollections.JsRpcStringProcedure;
import com.dotspots.rpcplus.client.jscollections.JsRpcStringStringProcedure;
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

	public void testListDefaults() {
		assertEquals(false, JsRpcListBool.create().get(0));
		assertEquals(0, JsRpcListInt.create().get(0));
		assertEquals(0L, JsRpcListLong.create().get(0));
		assertEquals(0.0, JsRpcListDouble.create().get(0));
		assertEquals(null, JsRpcListString.create().get(0));
		assertEquals(null, JsRpcList.<JavaScriptObject> create().get(0));
	}

	public void testListPopDefaults() {
		assertEquals(false, JsRpcListBool.create().pop());
		assertEquals(0, JsRpcListInt.create().pop());
		assertEquals(0L, JsRpcListLong.create().pop());
		assertEquals(0.0, JsRpcListDouble.create().pop());
		assertEquals(null, JsRpcListString.create().pop());
		assertEquals(null, JsRpcList.<JavaScriptObject> create().pop());
	}

	public void testListSearch() {
		JsRpcList<JavaScriptObject> list = JsRpcList.create();
		JavaScriptObject o;
		list.add(JavaScriptObject.createArray());
		list.add(o = JavaScriptObject.createArray());
		list.add(null);

		assertEquals(1, JsRpcListUtils.linearSearch(list, o));
		assertEquals(-1, JsRpcListUtils.linearSearch(list, JavaScriptObject.createArray()));
		assertEquals(2, JsRpcListUtils.linearSearch(list, null));
	}

	public void testListStringSearch() {
		JsRpcListString list = JsRpcListString.create();
		list.add("a");
		list.add("b");
		list.add(null);

		assertEquals(0, JsRpcListUtils.linearSearch(list, "a"));
		assertEquals(1, JsRpcListUtils.linearSearch(list, "b"));
		assertEquals(2, JsRpcListUtils.linearSearch(list, null));
		assertEquals(-1, JsRpcListUtils.linearSearch(list, "c"));
	}

	public void testListJoin() {
		JsRpcListString list = JsRpcListString.create();
		list.add("a");
		list.add("b");

		assertEquals("axb", list.join("x"));
	}

	public void testListSlice() {
		JsRpcListString list = JsRpcListString.create();
		list.add("a");
		list.add("b");
		list.add("c");

		assertEquals("bxc", list.slice(1).join("x"));
		assertEquals("bxc", list.slice(1, 3).join("x"));
		assertEquals("c", list.slice(2, 3).join("x"));
		assertEquals("axbxc", list.slice(0).join("x"));
	}

	public void testListSplice() {
		JsRpcListString list = JsRpcListString.create();
		list.add("a");
		list.add("b");
		list.add("c");

		assertEquals("bxc", list.splice(1).join("x"));
		assertEquals("a", list.join("x"));
	}

	public void testListSplice2() {
		JsRpcListString list = JsRpcListString.create();
		list.add("a");
		list.add("b");
		list.add("c");

		assertEquals("axbxc", list.splice(0).join("x"));
		assertEquals("", list.join("x"));
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

	public void testMapDefaults() {
		assertEquals(false, JsRpcMapIntBool.create().get(0));
		assertEquals(0, JsRpcMapIntInt.create().get(0));
		assertEquals(0L, JsRpcMapIntLong.create().get(0));
		assertEquals(0.0, JsRpcMapIntDouble.create().get(0));
		assertEquals(null, JsRpcMapIntString.create().get(0));
		assertEquals(null, JsRpcMapInt.<JavaScriptObject> create().get(0));
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

	public void testMapStringStringForEach() {
		JsRpcMapStringString map = JsRpcMapStringString.create();
		map.set("", "a");
		map.set("watch", "b");

		final boolean[] result = new boolean[2];

		assertTrue(map.forEachEntry(new JsRpcStringStringProcedure() {
			public boolean execute(String a, String b) {
				if (a.equals("") && b.equals("a")) {
					result[0] = true;
				} else if (a.equals("watch") && b.equals("b")) {
					result[1] = true;
				} else {
					fail("Got unexpected entry: " + a + ", " + b);
				}

				return true;
			}
		}));

		assertTrue(result[0]);
		assertTrue(result[1]);
	}

	public void testMapIterable() {
		JsRpcMapStringString map = JsRpcMapStringString.create();
		map.set("a", "A");
		map.set("b", "B");
		map.set("c", "C");

		Iterator<String> it = map.keysIterable().iterator();

		assertTrue(it.hasNext());
		assertTrue(it.hasNext());
		it.next();
		assertTrue(it.hasNext());
		it.next();
		assertTrue(it.hasNext());
		it.next();
		assertFalse(it.hasNext());
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
	 * Test normal iteration.
	 */
	public void testSetIterator() {
		JsRpcSetString set = JsRpcSetString.create();
		set.add("a");
		set.add("b");
		set.add("c");

		boolean[] results = new boolean[3];
		for (String value : set.iterable()) {
			results[value.charAt(0) - 'a'] = true;
		}

		assertTrue(results[0]);
		assertTrue(results[1]);
		assertTrue(results[2]);
	}

	/**
	 * Test multiple/out of order calls to hasNext.
	 */
	public void testSetIterator2() {
		JsRpcSetString set = JsRpcSetString.create();
		set.add("a");
		set.add("b");
		set.add("c");

		boolean[] results = new boolean[3];
		final Iterator<String> it = set.iterable().iterator();
		assertTrue(it.hasNext());
		assertTrue(it.hasNext());
		results[it.next().charAt(0) - 'a'] = true;
		assertTrue(it.hasNext());
		results[it.next().charAt(0) - 'a'] = true;
		assertTrue(it.hasNext());
		results[it.next().charAt(0) - 'a'] = true;
		assertFalse(it.hasNext());

		assertTrue(results[0]);
		assertTrue(results[1]);
		assertTrue(results[2]);
	}

	/**
	 * Remove an element while iterating.
	 */
	public void testSetIterator3() {
		JsRpcSetString set = JsRpcSetString.create();
		set.add("a");
		set.add("b");
		set.add("c");

		boolean[] results = new boolean[3];
		final Iterator<String> it = set.iterable().iterator();
		assertTrue(it.hasNext());
		assertTrue(it.hasNext());
		results[it.next().charAt(0) - 'a'] = true;
		assertTrue(it.hasNext());
		results[it.next().charAt(0) - 'a'] = true;
		it.remove();
		assertTrue(it.hasNext());
		results[it.next().charAt(0) - 'a'] = true;
		assertFalse(it.hasNext());

		assertTrue(results[0]);
		assertTrue(results[1]);
		assertTrue(results[2]);
		assertEquals(2, set.countSize());
	}

	/**
	 * Tests the forEach method over a set.
	 */
	public void testSetStringForEach() {
		JsRpcSetString set = JsRpcSetString.create();
		set.add("");
		set.add("watch");

		final boolean[] result = new boolean[2];

		assertTrue(set.forEach(new JsRpcStringProcedure() {
			public boolean execute(String value) {
				if (value.equals("")) {
					result[0] = true;
				} else if (value.equals("watch")) {
					result[1] = true;
				} else {
					fail("Unexpected value: " + value);
				}

				return true;
			}
		}));

		assertTrue(result[0]);
		assertTrue(result[1]);
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

	private native void removeObjectPrototypeBadness() /*-{
		delete Object.prototype.dontDoThis;
	}-*/;

	private native void addObjectPrototypeBadness() /*-{
		Object.prototype.dontDoThis = true;
	}-*/;
}
