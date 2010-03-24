package com.dotspots.rpcplus.example.jsonrpc.thrift;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.thrift.TException;

import com.dotspots.rpcplus.example.torturetest.ContextIn;
import com.dotspots.rpcplus.example.torturetest.ContextOut;
import com.dotspots.rpcplus.example.torturetest.MoreComplexException;
import com.dotspots.rpcplus.example.torturetest.ObjectThatReferencesAnother;
import com.dotspots.rpcplus.example.torturetest.ObjectWithComplexTypes;
import com.dotspots.rpcplus.example.torturetest.ObjectWithEnum;
import com.dotspots.rpcplus.example.torturetest.SimpleEnum;
import com.dotspots.rpcplus.example.torturetest.SimpleException;
import com.dotspots.rpcplus.example.torturetest.SimpleObjectWithFieldIds;
import com.dotspots.rpcplus.example.torturetest.SimpleObjectWithNoFieldIds;
import com.dotspots.rpcplus.example.torturetest.TortureTestApi;
import com.dotspots.rpcplus.example.torturetest.TortureTestApiJson;
import com.dotspots.rpcplus.jsonrpc.thrift.BaseServlet;
import com.dotspots.rpcplus.test.client.EveryCharacterStringUtility;

public class ExampleService extends BaseServlet<TortureTestApi.Iface> implements TortureTestApi.Iface {
	private ContextIn requestContext;
	private ContextOut responseContext;

	public ExampleService() {
		final TortureTestApiJson apiJson = new TortureTestApiJson();
		servlet = apiJson;
		apiJson.setService(this);
	}

	public String testPassthru(String str) {
		EveryCharacterStringUtility.checkString(str);
		return str;
	}

	public Set<String> complexMethod(String str, int count) throws SimpleException, TException {
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < count; i++) {
			set.add(str + i);
		}

		return set;
	}

	public ObjectThatReferencesAnother methodReturningAnObject() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	public SimpleObjectWithFieldIds methodReturningAnObject2() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	public SimpleObjectWithNoFieldIds methodReturningAnObject3() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectWithComplexTypes methodReturningAnObject4() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	public ObjectWithEnum methodReturningAnObject5(ObjectWithEnum arg) throws TException {
		return new ObjectWithEnum(SimpleEnum.ONE, null, null, null);
	}

	public String testDeclaresAnException() throws SimpleException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	public SimpleException testExceptionPassthru(SimpleException ex) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, String> testMapStringString() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	public String testPositionalArguments(int int32, String str) throws TException {
		return int32 + str;
	}

	public Set<Integer> testSetInt() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] testBinary(byte[] binaryValue) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> testSetString() throws TException {
		return new HashSet<String>(Arrays.asList("hi0", "hi1", "hi2", "hi3"));
	}

	public String testThrowsAnException() throws SimpleException, TException {
		throw new SimpleException("Hey!");
	}

	public String testThrowsAnUnpositionedException() throws SimpleException, TException {
		throw new SimpleException("Hey!");
	}

	public String testThrowsTwoExceptions(int which) throws SimpleException, MoreComplexException, TException {
		if (which == 0) {
			throw new SimpleException("Hey!");
		}

		Set<String> set = new HashSet<String>();
		for (int i = 0; i < 4; i++) {
			set.add("hi" + i);
		}

		throw new MoreComplexException("Message!", new ObjectWithComplexTypes(null, set, null, null));
	}

	public ContextOut __getContext() throws TException {
		return this.responseContext;
	}

	public void __setContext(ContextIn requestContext) throws TException {
		this.requestContext = requestContext;
		this.responseContext = new ContextOut();

		if (requestContext.getData() != null) {
			responseContext.setData(">>>" + requestContext.getData());
		}
	}
}
