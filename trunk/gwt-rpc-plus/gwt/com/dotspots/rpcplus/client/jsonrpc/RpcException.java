package com.dotspots.rpcplus.client.jsonrpc;

public class RpcException extends Exception {
	private static final long serialVersionUID = 1L;

	public RpcException() {
		super();
	}

	public RpcException(String message, Throwable cause) {
		super(message, cause);
	}

	public RpcException(String message) {
		super(message);
	}

	public RpcException(Throwable cause) {
		super(cause);
	}
}
