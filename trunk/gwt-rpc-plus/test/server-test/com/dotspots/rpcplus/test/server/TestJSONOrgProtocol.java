package com.dotspots.rpcplus.test.server;

import junit.framework.Assert;

import org.apache.thrift.TException;
import org.junit.Test;
import org.svenson.tokenize.JSONTokenizer;

import com.dotspots.rpcplus.example.torturetest.SimpleObjectWithFieldIds;
import com.dotspots.rpcplus.example.torturetest.TortureTestApiJson;
import com.dotspots.rpcplus.jsonrpc.thrift.TJSONProtocolReader;

public class TestJSONOrgProtocol extends Assert {
	@Test
	public void testOneItemMap() throws TException {
		JSONTokenizer tokener = newTokenizer("{123:456}");

		TJSONProtocolReader protocol = new TJSONProtocolReader(tokener);

		assertTrue(protocol.readMapBegin());
		assertTrue(protocol.hasNext());
		assertEquals(123, protocol.readI32());
		assertEquals(456, protocol.readI32());
		assertFalse(protocol.hasNext());

	}

	@Test
	public void testOneItemMapWithSpaces() throws TException {
		JSONTokenizer tokener = newTokenizer("  {  123  :  456  }  ");

		TJSONProtocolReader protocol = new TJSONProtocolReader(tokener);

		assertTrue(protocol.readMapBegin());
		assertTrue(protocol.hasNext());
		assertEquals(123, protocol.readI32());
		assertEquals(456, protocol.readI32());
		assertFalse(protocol.hasNext());

	}

	@Test
	public void testBiggerMap() throws TException {
		JSONTokenizer tokener = newTokenizer("{123:456, \"1\":2, 3:4}");

		TJSONProtocolReader protocol = new TJSONProtocolReader(tokener);

		assertTrue(protocol.readMapBegin());
		assertTrue(protocol.hasNext());
		assertEquals(123, protocol.readI32());
		assertEquals(456, protocol.readI32());
		assertTrue(protocol.hasNext());
		assertEquals(1, protocol.readI32());
		assertEquals(2, protocol.readI32());
		assertTrue(protocol.hasNext());
		assertEquals(3, protocol.readI32());
		assertEquals(4, protocol.readI32());
		assertFalse(protocol.hasNext());

	}

	@Test
	public void testMapAsStruct() throws TException {
		JSONTokenizer tokener = newTokenizer("{123:456, \"1\":2, 3:4}");

		TJSONProtocolReader protocol = new TJSONProtocolReader(tokener);

		assertTrue(protocol.readStructBegin());
		assertTrue(protocol.hasNext());
		assertEquals(123, protocol.readI32());
		assertEquals(456, protocol.readI32());
		assertTrue(protocol.hasNext());
		assertEquals(1, protocol.readI32());
		assertEquals(2, protocol.readI32());
		assertTrue(protocol.hasNext());
		assertEquals(3, protocol.readI32());
		assertEquals(4, protocol.readI32());
		assertFalse(protocol.hasNext());

	}

	@Test
	public void testSerializedObject() throws TException {
		JSONTokenizer tokener = newTokenizer("{0:\"this is a token\", 1:123}");

		TJSONProtocolReader protocol = new TJSONProtocolReader(tokener);
		final SimpleObjectWithFieldIds obj = TortureTestApiJson.readSimpleObjectWithFieldIds(protocol);
	}

	@Test
	public void testListWithSpaces() throws TException {
		JSONTokenizer tokener = newTokenizer("[0, \"testSetString\", [\"token\", \"data\"], []]\n");
		TJSONProtocolReader protocol = new TJSONProtocolReader(tokener);
		assertTrue(protocol.readListBegin());
		assertTrue(protocol.hasNext());
		assertEquals(0, protocol.readI32());
		assertTrue(protocol.hasNext());
		assertEquals("testSetString", protocol.readString());
		assertTrue(protocol.hasNext());

		assertTrue(protocol.readListBegin());
		assertTrue(protocol.hasNext());
		assertEquals("token", protocol.readString());
		assertTrue(protocol.hasNext());
		assertEquals("data", protocol.readString());
		assertFalse(protocol.hasNext());

		assertTrue(protocol.hasNext());

		assertTrue(protocol.readListBegin());
		assertFalse(protocol.hasNext());

		assertFalse(protocol.hasNext());
	}

	@Test
	public void testListWithNull() throws TException {
		JSONTokenizer tokener = newTokenizer("[0, \"testSetString\", null, [0]]\n");
		TJSONProtocolReader protocol = new TJSONProtocolReader(tokener);
		assertTrue(protocol.readListBegin());
		assertTrue(protocol.hasNext());
		assertEquals(0, protocol.readI32());
		assertTrue(protocol.hasNext());
		assertEquals("testSetString", protocol.readString());
		assertTrue(protocol.hasNext());

		assertFalse(protocol.readListBegin());

		assertTrue(protocol.hasNext());

		assertTrue(protocol.readListBegin());
		assertEquals(0, protocol.readI32());
		assertFalse(protocol.hasNext());

		assertFalse(protocol.hasNext());

	}

	@Test
	public void testStructWithNull() throws TException {
		JSONTokenizer tokener = newTokenizer("[0, \"testSetString\", null, [0]]\n");
		TJSONProtocolReader protocol = new TJSONProtocolReader(tokener);
		assertTrue(protocol.readListBegin());
		assertTrue(protocol.hasNext());
		assertEquals(0, protocol.readI32());
		assertTrue(protocol.hasNext());
		assertEquals("testSetString", protocol.readString());
		assertTrue(protocol.hasNext());

		assertFalse(protocol.readStructBegin());

		assertTrue(protocol.hasNext());

		assertTrue(protocol.readListBegin());
		assertEquals(0, protocol.readI32());
		assertFalse(protocol.hasNext());

		assertFalse(protocol.hasNext());

	}

	@Test
	public void testListWithEmptyElements() throws TException {
		JSONTokenizer tokener = newTokenizer("[,,,]\n");
		TJSONProtocolReader protocol = new TJSONProtocolReader(tokener);
		assertTrue(protocol.readListBegin());
		assertTrue(protocol.hasNext());
		assertEquals(0, protocol.readI32());
		assertTrue(protocol.hasNext());
		assertEquals(null, protocol.readString());
		assertTrue(protocol.hasNext());
		assertEquals(0, protocol.readI64());
		assertTrue(protocol.hasNext());
		assertEquals(0.0, protocol.readDouble());
		assertFalse(protocol.hasNext());

	}

	@Test
	public void testEmptyList() throws TException {
		JSONTokenizer tokener = newTokenizer("[]\n");
		TJSONProtocolReader protocol = new TJSONProtocolReader(tokener);
		assertTrue(protocol.readListBegin());
		assertFalse(protocol.hasNext());

	}

	@Test
	public void testEmptyListStruct() throws TException {
		JSONTokenizer tokener = newTokenizer("[]\n");
		TJSONProtocolReader protocol = new TJSONProtocolReader(tokener);
		assertTrue(protocol.readStructBegin());
		assertFalse(protocol.hasNext());

	}

	@Test
	public void testEmptyMapStruct() throws TException {
		JSONTokenizer tokener = newTokenizer("{}\n");
		TJSONProtocolReader protocol = new TJSONProtocolReader(tokener);
		assertTrue(protocol.readStructBegin());
		assertFalse(protocol.hasNext());
	}

	private JSONTokenizer newTokenizer(String json) {
		return new JSONTokenizer(json, true);
	}
}
