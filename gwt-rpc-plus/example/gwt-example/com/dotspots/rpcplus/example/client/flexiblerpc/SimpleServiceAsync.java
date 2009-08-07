package com.dotspots.rpcplus.example.client.flexiblerpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SimpleServiceAsync {
	public void add(int i1, int i2, AsyncCallback<Integer> callback);
}
