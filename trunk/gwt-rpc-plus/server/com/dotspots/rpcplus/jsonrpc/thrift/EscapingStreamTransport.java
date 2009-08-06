package com.dotspots.rpcplus.jsonrpc.thrift;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransportException;

public class EscapingStreamTransport extends TIOStreamTransport {
	private byte[] LESS_THAN_ESCAPED = "\\<".getBytes();
	private byte[] GREATER_THAN_ESCAPED = "\\>".getBytes();
	private byte[] SINGLE_QUOTE_ESCAPED = "\\'".getBytes();
	private byte[] SLASH_ESCAPED = "\\\\".getBytes();

	public EscapingStreamTransport(ServletOutputStream os) {
		super(os);
	}

	@Override
	public void write(byte[] buffer, int start, int length) throws TTransportException {
		try {
			for (int i = start; i < start + length; i++) {
				if (buffer[i] == '<') {
					outputStream_.write(LESS_THAN_ESCAPED);
				} else if (buffer[i] == '>') {
					outputStream_.write(GREATER_THAN_ESCAPED);
				} else if (buffer[i] == '\'') {
					outputStream_.write(SINGLE_QUOTE_ESCAPED);
				} else if (buffer[i] == '\\') {
					outputStream_.write(SLASH_ESCAPED);
				} else {
					outputStream_.write(buffer[i]);
				}
			}
		} catch (IOException e) {
			throw new TTransportException(e);
		}
	}
}
