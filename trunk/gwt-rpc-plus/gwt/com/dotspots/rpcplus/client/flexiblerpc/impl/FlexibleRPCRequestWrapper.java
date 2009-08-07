package com.dotspots.rpcplus.client.flexiblerpc.impl;

import com.dotspots.rpcplus.client.flexiblerpc.FlexibleRPCRequest;
import com.google.gwt.http.client.Request;

public class FlexibleRPCRequestWrapper extends Request {
	private final FlexibleRPCRequest request;

	public FlexibleRPCRequestWrapper(FlexibleRPCRequest request) {
		this.request = request;
	}

	@Override
	public boolean isPending() {
		// TODO Auto-generated method stub
		return super.isPending();
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		super.cancel();
	}
}
