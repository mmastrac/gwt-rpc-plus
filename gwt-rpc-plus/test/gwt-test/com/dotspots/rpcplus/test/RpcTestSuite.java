package com.dotspots.rpcplus.test;

import junit.framework.TestSuite;

import com.dotspots.rpcplus.test.client.TestFlexibleRPC;
import com.dotspots.rpcplus.test.client.TestFlexibleRPCCrossDomain;
import com.dotspots.rpcplus.test.client.TestJsonTransport;
import com.dotspots.rpcplus.test.client.TestRpc;
import com.dotspots.rpcplus.test.client.TestThriftCollections;
import com.google.gwt.junit.tools.GWTTestSuite;

public class RpcTestSuite extends GWTTestSuite {
	public static TestSuite suite() {
		GWTTestSuite suite = new GWTTestSuite("rpc");
		suite.addTestSuite(TestJsonTransport.class);
		suite.addTestSuite(TestRpc.class);
		suite.addTestSuite(TestThriftCollections.class);
		suite.addTestSuite(TestFlexibleRPC.class);
		suite.addTestSuite(TestFlexibleRPCCrossDomain.class);

		return suite;
	}
}
