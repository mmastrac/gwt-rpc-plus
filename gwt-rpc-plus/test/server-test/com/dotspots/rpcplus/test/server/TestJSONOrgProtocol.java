package com.dotspots.rpcplus.test.server;

import junit.framework.Assert;

import org.apache.thrift.TException;
import org.json.JSONException;
import org.json.JSONTokener;
import org.junit.Test;

import com.dotspots.rpcplus.example.torturetest.SimpleObjectWithFieldIds;
import com.dotspots.rpcplus.example.torturetest.TortureTestApiJson;
import com.dotspots.rpcplus.jsonrpc.thrift.TJSONOrgProtocol;

public class TestJSONOrgProtocol extends Assert {
	@Test
	public void testOneItemMap() throws JSONException, TException {
		JSONTokener tokener = new JSONTokener("{123:456}");

		TJSONOrgProtocol protocol = new TJSONOrgProtocol(tokener);

		assertTrue(protocol.readMapBegin());
		assertEquals(123, protocol.readI32());
		assertEquals(456, protocol.readI32());
		assertFalse(protocol.hasNext());
		protocol.readMapEnd();
	}

	@Test
	public void testOneItemMapWithSpaces() throws JSONException, TException {
		JSONTokener tokener = new JSONTokener("  {  123  :  456  }  ");

		TJSONOrgProtocol protocol = new TJSONOrgProtocol(tokener);

		assertTrue(protocol.readMapBegin());
		assertEquals(123, protocol.readI32());
		assertEquals(456, protocol.readI32());
		assertFalse(protocol.hasNext());
		protocol.readMapEnd();
	}

	@Test
	public void testBiggerMap() throws JSONException, TException {
		JSONTokener tokener = new JSONTokener("{123:456, \"1\":2, 3:4}");

		TJSONOrgProtocol protocol = new TJSONOrgProtocol(tokener);

		assertTrue(protocol.readMapBegin());
		assertEquals(123, protocol.readI32());
		assertEquals(456, protocol.readI32());
		assertTrue(protocol.hasNext());
		assertEquals(1, protocol.readI32());
		assertEquals(2, protocol.readI32());
		assertTrue(protocol.hasNext());
		assertEquals(3, protocol.readI32());
		assertEquals(4, protocol.readI32());
		assertFalse(protocol.hasNext());
		protocol.readMapEnd();
	}

	@Test
	public void testMapAsStruct() throws JSONException, TException {
		JSONTokener tokener = new JSONTokener("{123:456, \"1\":2, 3:4}");

		TJSONOrgProtocol protocol = new TJSONOrgProtocol(tokener);

		assertTrue(protocol.readStructBegin());
		assertEquals(123, protocol.readFieldId());
		assertEquals(456, protocol.readI32());
		assertTrue(protocol.hasNext());
		assertEquals(1, protocol.readFieldId());
		assertEquals(2, protocol.readI32());
		assertTrue(protocol.hasNext());
		assertEquals(3, protocol.readFieldId());
		assertEquals(4, protocol.readI32());
		assertEquals(-1, protocol.readFieldId());
		protocol.readStructEnd();
	}

	@Test
	public void testSerializedObject() throws JSONException, TException {
		JSONTokener tokener = new JSONTokener("{0:\"this is a token\", 1:123}");

		TJSONOrgProtocol protocol = new TJSONOrgProtocol(tokener);
		final SimpleObjectWithFieldIds obj = TortureTestApiJson.readSimpleObjectWithFieldIds(protocol);
	}

	@Test
	public void testListWithSpaces() throws JSONException, TException {
		JSONTokener tokener = new JSONTokener("[0, \"testSetString\", [\"token\", \"data\"], []]\n");
		TJSONOrgProtocol protocol = new TJSONOrgProtocol(tokener);
		assertTrue(protocol.readListBegin());
		assertEquals(0, protocol.readI32());
		assertTrue(protocol.hasNext());
		assertEquals("testSetString", protocol.readString());
		assertTrue(protocol.hasNext());

		assertTrue(protocol.readListBegin());
		assertEquals("token", protocol.readString());
		assertTrue(protocol.hasNext());
		assertEquals("data", protocol.readString());
		assertFalse(protocol.hasNext());
		protocol.readListEnd();

		assertTrue(protocol.hasNext());

		assertTrue(protocol.readListBegin());
		assertFalse(protocol.hasNext());
		protocol.readListEnd();

		assertFalse(protocol.hasNext());
		protocol.readListEnd();
	}
}
