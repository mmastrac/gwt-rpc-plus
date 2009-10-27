package com.dotspots.rpcplus.test.server;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TMemoryBuffer;
import org.junit.Assert;
import org.junit.Test;
import org.svenson.tokenize.JSONTokenizer;

import com.dotspots.rpcplus.example.torturetest.ObjectWithComplexTypes;
import com.dotspots.rpcplus.example.torturetest.SimpleObjectWithFieldIds;
import com.dotspots.rpcplus.example.torturetest.TortureTestApiJson;
import com.dotspots.rpcplus.jsonrpc.thrift.TJSONProtocolReader;
import com.dotspots.rpcplus.jsonrpc.thrift.TJSONProtocolWriter;

public class TestJSONNativeProtocol extends Assert {
	@Test
	public void testSimpleObject() throws Exception {
		final String expected = "{\"0\":\"myString\",\"1\":123}";
		SimpleObjectWithFieldIds obj = new SimpleObjectWithFieldIds();
		obj.setToken("myString");
		obj.setUserId(123);

		roundTrip(expected, obj);
	}

	@Test
	public void testSimpleObjectEmpty() throws Exception {
		SimpleObjectWithFieldIds obj = new SimpleObjectWithFieldIds();
		roundTrip("{}", obj);
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

		final Set<String> set = new TreeSet<String>(Arrays.asList("a2", "b2"));
		obj.setSetOfStrings(set);

		roundTrip("{\"0\":{\"_c\":\"d\",\"_a\":\"b\"},\"1\":{\"_a2\":0,\"_b2\":0},\"2\":[\"a1\",\"b1\"]}", obj);
	}

	@Test
	public void testObjectWithComplexTypesNull() throws Exception {
		ObjectWithComplexTypes obj = new ObjectWithComplexTypes();

		final List<String> list = Arrays.asList(null, null);
		obj.setListOfStrings(list);

		final Map<String, String> map = new HashMap<String, String>();
		map.put("a", null);
		map.put("c", null);
		obj.setMapStringToString(map);

		roundTrip("{\"0\":{\"_c\":null,\"_a\":null},\"2\":[null,null]}", obj);
	}

	@Test
	public void testObjectWithComplexTypesEmpty() throws Exception {
		ObjectWithComplexTypes obj = new ObjectWithComplexTypes();

		roundTrip("{}", obj);
	}

	private void roundTrip(final String expected, TBase obj) throws TException, UnsupportedEncodingException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		TMemoryBuffer buffer = new TMemoryBuffer(10000);
		TJSONProtocolWriter protocol = new TJSONProtocolWriter(buffer);

		// Write to the protocol
		for (Method method : TortureTestApiJson.class.getDeclaredMethods()) {
			if (method.getName().equalsIgnoreCase("write" + obj.getClass().getSimpleName())) {
				method.setAccessible(true);
				method.invoke(null, protocol, obj);
				break;
			}
		}

		// Check it
		String str = buffer.toString("UTF-8");
		Assert.assertEquals(expected, str);

		// Read from the buffer we just wrote
		TJSONProtocolReader readProtocol = new TJSONProtocolReader(new JSONTokenizer(str, true));

		TBase obj2 = null;
		for (Method method : TortureTestApiJson.class.getDeclaredMethods()) {
			if (method.getName().equalsIgnoreCase("read" + obj.getClass().getSimpleName())) {
				method.setAccessible(true);
				obj2 = (TBase) method.invoke(null, readProtocol);
				break;
			}
		}

		Assert.assertNotNull("Couldn't find read method for " + obj.getClass().getSimpleName(), obj2);

		// Now write it out again
		buffer = new TMemoryBuffer(10000);
		protocol = new TJSONProtocolWriter(buffer);

		for (Method method : TortureTestApiJson.class.getDeclaredMethods()) {
			if (method.getName().equalsIgnoreCase("write" + obj.getClass().getSimpleName())) {
				method.setAccessible(true);
				obj2 = (TBase) method.invoke(null, protocol, obj2);
				break;
			}
		}

		// Make sure it's still as expected
		str = buffer.toString("UTF-8");
		Assert.assertEquals(expected, str);
	}
}
