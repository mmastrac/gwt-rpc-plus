package com.dotspots.thrift.gwt.server;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TMemoryBuffer;
import org.junit.Assert;
import org.junit.Test;

import com.dotspots.rpcplus.example.torturetest.ObjectWithComplexTypes;
import com.dotspots.rpcplus.example.torturetest.SimpleObjectWithFieldIds;
import com.dotspots.rpcplus.example.torturetest.TortureTestApiJson;
import com.dotspots.rpcplus.jsonrpc.thrift.TJSONNativeProtocol;

public class TestJSONNativeProtocol {
	@Test
	public void testSimpleObject() throws Exception {
		final String expected = "{\"1\":\"myString\",\"2\":123}";
		SimpleObjectWithFieldIds obj = new SimpleObjectWithFieldIds();
		obj.setToken("myString");
		obj.setUserId(123);

		roundTrip(expected, obj);
	}

	@Test
	public void testSimpleObjectEmpty() throws Exception {
		SimpleObjectWithFieldIds obj = new SimpleObjectWithFieldIds();
		roundTrip("{\"2\":0}", obj);
	}

	@Test
	public void testObjectWithComplexTypes() throws Exception {
		ObjectWithComplexTypes obj = new ObjectWithComplexTypes();

		final List<String> list = Arrays.asList("a1", "b1");
		obj.setListOfStrings(list);

		final Map<String, String> map = new HashMap<String, String>();
		map.put("a", "b");
		map.put("c", "d");
		obj.setMapStringToString(map);

		final Set<String> set = new HashSet<String>(Arrays.asList("a2", "b2"));
		obj.setSetOfStrings(set);

		roundTrip("{\"1\":{\"c\":\"d\",\"a\":\"b\"},\"2\":{\"b2\":0,\"a2\":0},\"3\":[\"a1\",\"b1\"]}", obj);
	}

	@Test
	public void testObjectWithComplexTypesEmpty() throws Exception {
		ObjectWithComplexTypes obj = new ObjectWithComplexTypes();

		roundTrip("{}", obj);
	}

	private void roundTrip(final String expected, TBase obj) throws TException, UnsupportedEncodingException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		TMemoryBuffer buffer = new TMemoryBuffer(10000);
		TJSONNativeProtocol protocol = new TJSONNativeProtocol(buffer);

		// Write to the protocol
		obj.write(protocol);

		// Check it
		String str = buffer.toString("UTF-8");
		Assert.assertEquals(expected, str);

		// Read from the buffer we just wrote
		buffer = new TMemoryBuffer(10000);
		buffer.write(str.getBytes("UTF-8"));

		protocol = new TJSONNativeProtocol(buffer);

		TBase obj2 = null;
		for (Method method : TortureTestApiJson.class.getDeclaredMethods()) {
			if (method.getName().equalsIgnoreCase("read" + obj.getClass().getSimpleName())) {
				method.setAccessible(true);
				obj2 = (TBase) method.invoke(null, protocol);
			}
		}

		Assert.assertNotNull("Couldn't find read method for " + obj.getClass().getSimpleName(), obj2);

		// Now write it out again
		buffer = new TMemoryBuffer(10000);
		protocol = new TJSONNativeProtocol(buffer);

		obj2.write(protocol);

		// Make sure it's still as expected
		str = buffer.toString("UTF-8");
		Assert.assertEquals(expected, str);
	}
}
