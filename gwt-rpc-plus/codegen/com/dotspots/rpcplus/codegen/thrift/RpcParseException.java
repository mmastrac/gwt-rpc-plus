package com.dotspots.rpcplus.codegen.thrift;

/**
 * Generic exception for thrift parsing.
 */
public class RpcParseException extends Exception {
	private static final long serialVersionUID = 1L;

	public RpcParseException() {
		super();
	}

	public RpcParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public RpcParseException(String message) {
		super(message);
	}

	public RpcParseException(Throwable cause) {
		super(cause);
	}

}
