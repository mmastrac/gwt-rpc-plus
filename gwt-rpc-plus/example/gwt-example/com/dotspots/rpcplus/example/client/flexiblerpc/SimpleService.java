package com.dotspots.rpcplus.example.client.flexiblerpc;

import com.google.gwt.user.client.rpc.RemoteService;

public interface SimpleService extends RemoteService {
	public int add(int i1, int i2);
}
