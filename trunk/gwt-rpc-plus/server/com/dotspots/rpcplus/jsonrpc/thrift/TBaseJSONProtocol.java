package com.dotspots.rpcplus.jsonrpc.thrift;

import org.apache.thrift.TException;

public abstract class TBaseJSONProtocol {
	public TBaseJSONProtocol() {
	}

	public abstract boolean hasNext() throws TException;

	public abstract boolean readStructBegin() throws TException;

	public abstract boolean readMapBegin() throws TException;

	public abstract boolean readSetBegin() throws TException;

	public abstract boolean readListBegin() throws TException;

	public abstract String readString() throws TException;

	public abstract byte[] readBinary() throws TException;

	public abstract int readI32() throws TException;

	public abstract long readI64() throws TException;

	public abstract boolean readBool() throws TException;

	public abstract double readDouble() throws TException;

	/**
	 * Skips the current object.
	 */
	public abstract void skip() throws TException;
}
