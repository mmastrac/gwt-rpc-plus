package com.dotspots.rpcplus.jsonrpc.thrift;

import java.io.UnsupportedEncodingException;
import java.util.Stack;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TField;
import org.apache.thrift.protocol.TList;
import org.apache.thrift.protocol.TMap;
import org.apache.thrift.protocol.TMessage;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.protocol.TSet;
import org.apache.thrift.protocol.TStruct;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class TJSONNativeProtocol {
	protected TTransport trans_;

	public TJSONNativeProtocol(TTransport trans) {
		trans_ = trans;
	}

	public void writeFieldBegin(TField field) throws TException {
		writeJSONInteger(field.id);
	}

	public void writeFieldEnd() throws TException {
	}

	public void writeMapBegin(TMap map) throws TException {
		writeJSONObjectStart();
	}

	public void writeMapEnd() throws TException {
		writeJSONObjectEnd();
	}

	public void writeSetBegin(TSet set) throws TException {
		context_.write();
		trans_.write(LBRACE);
		pushContext(new JSONSetContext());
	}

	public void writeSetEnd() throws TException {
		JSONSetContext setContext = (JSONSetContext) context_;
		if (!setContext.first_) {
			trans_.write(SET_VALUE);
		}

		popContext();
		trans_.write(RBRACE);
	}

	public void writeListBegin(TList list) throws TException {
		writeJSONArrayStart();
	}

	// Context for JSON lists. Will insert/read commas before each item except
	// for the first one
	protected class JSONSetContext extends JSONBaseContext {
		protected boolean first_ = true;

		@Override
		protected void write() throws TException {
			if (first_) {
				first_ = false;
			} else {
				trans_.write(SET_VALUE);
				trans_.write(COMMA);
			}
		}

		@Override
		public void writeStartString() throws TTransportException {
			trans_.write(UNDERSCORE);
		}
	}

	protected static final byte[] UNDERSCORE = new byte[] { '_' };
	protected static final byte[] COMMA = new byte[] { ',' };
	protected static final byte[] COLON = new byte[] { ':' };
	protected static final byte[] LBRACE = new byte[] { '{' };
	protected static final byte[] RBRACE = new byte[] { '}' };
	protected static final byte[] LBRACKET = new byte[] { '[' };
	protected static final byte[] RBRACKET = new byte[] { ']' };
	protected static final byte[] QUOTE = new byte[] { '"' };
	protected static final byte[] BACKSLASH = new byte[] { '\\' };
	protected static final byte[] ZERO = new byte[] { '0' };
	protected static final byte[] NULL = new byte[] { 'n', 'u', 'l', 'l' };
	protected static final byte[] SET_VALUE = new byte[] { ':', '0' };

	protected static final byte[] ESCSEQ = new byte[] { '\\', 'u', '0', '0' };
	protected static final byte[] ESCSEQFULL = new byte[] { '\\', 'u' };

	protected static final long VERSION = 1;

	protected static final byte[] JSON_CHAR_TABLE = {
	/* 0 1 2 3 4 5 6 7 8 9 A B C D E F */
	0, 0, 0, 0, 0, 0, 0, 0, 'b', 't', 'n', 0, 'f', 'r', 0, 0, // 0
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 1
			1, 1, '"', 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, // 2
	};

	protected static final String ESCAPE_CHARS = "\"\\bfnrt";

	protected static final byte[] ESCAPE_CHAR_VALS = { '"', '\\', '\b', '\f', '\n', '\r', '\t', };

	protected static final byte[] NAME_BOOL = new byte[] { 't', 'f' };
	protected static final byte[] NAME_BYTE = new byte[] { 'i', '8' };
	protected static final byte[] NAME_I16 = new byte[] { 'i', '1', '6' };
	protected static final byte[] NAME_I32 = new byte[] { 'i', '3', '2' };
	protected static final byte[] NAME_I64 = new byte[] { 'i', '6', '4' };
	protected static final byte[] NAME_DOUBLE = new byte[] { 'd', 'b', 'l' };
	protected static final byte[] NAME_STRUCT = new byte[] { 'r', 'e', 'c' };
	protected static final byte[] NAME_STRING = new byte[] { 's', 't', 'r' };
	protected static final byte[] NAME_MAP = new byte[] { 'm', 'a', 'p' };
	protected static final byte[] NAME_LIST = new byte[] { 'l', 's', 't' };
	protected static final byte[] NAME_SET = new byte[] { 's', 'e', 't' };

	protected static final TStruct ANONYMOUS_STRUCT = new TStruct();

	// Base class for tracking JSON contexts that may require inserting/reading
	// additional JSON syntax characters
	// This base context does nothing.
	protected class JSONBaseContext {
		protected void write() throws TException {
		}

		protected boolean escapeNum() {
			return false;
		}

		protected boolean nullAsEmpty() {
			return false;
		}

		public void writeStartString() throws TTransportException {
		}
	}

	// Context for JSON lists. Will insert/read commas before each item except
	// for the first one
	protected class JSONListContext extends JSONBaseContext {
		protected boolean first_ = true;

		@Override
		protected void write() throws TException {
			if (first_) {
				first_ = false;
			} else {
				trans_.write(COMMA);
			}
		}

		@Override
		protected boolean nullAsEmpty() {
			// TODO Optimization: use null-as-empty here to optimize when the client supports it (JSON.parse doesn't on
			// most browsers).
			return false;
		}
	}

	// Context for JSON records. Will insert/read colons before the value
	// portion
	// of each record pair, and commas before each key except the first. In
	// addition, will indicate that numbers in the key position need to be
	// escaped in quotes (since JSON keys must be strings).
	protected class JSONPairContext extends JSONBaseContext {
		protected boolean first_ = true;
		protected boolean colon_ = true;

		@Override
		protected void write() throws TException {
			if (first_) {
				first_ = false;
				colon_ = true;
			} else {
				trans_.write(colon_ ? COLON : COMMA);
				colon_ = !colon_;
			}
		}

		@Override
		protected boolean escapeNum() {
			return colon_;
		}
	}

	// Stack of nested contexts that we may be in
	protected Stack<JSONBaseContext> contextStack_ = new Stack<JSONBaseContext>();

	// Current context that we are in
	protected JSONBaseContext context_ = new JSONBaseContext();

	// Push a new JSON context onto the stack.
	protected void pushContext(JSONBaseContext c) {
		contextStack_.push(context_);
		context_ = c;
	}

	// Pop the last JSON context off the stack
	protected void popContext() {
		context_ = contextStack_.pop();
	}

	// Temporary buffer used by several methods
	protected byte[] tmpbuf_ = new byte[8];

	// Convert a byte containing a hex char ('0'-'9' or 'a'-'f') into its
	// corresponding hex value
	protected static final byte hexVal(byte ch) throws TException {
		if ((ch >= '0') && (ch <= '9')) {
			return (byte) ((char) ch - '0');
		} else if ((ch >= 'a') && (ch <= 'f')) {
			return (byte) ((char) ch - 'a');
		} else {
			throw new TProtocolException(TProtocolException.INVALID_DATA, "Expected hex character");
		}
	}

	// Convert a byte containing a hex value to its corresponding hex character
	protected static final byte hexChar(int val) {
		val &= 0x0F;
		if (val < 10) {
			return (byte) (val + '0');
		} else {
			return (byte) (val + 'a' - 10);
		}
	}

	// Write the bytes in array buf as a JSON characters, escaping as needed
	protected void writeJSONString(char[] c) throws TException {
		context_.write();
		trans_.write(QUOTE);

		context_.writeStartString();

		int len = c.length;
		for (int i = 0; i < len; i++) {
			if (c[i] > 127) {
				trans_.write(ESCSEQFULL);
				tmpbuf_[0] = hexChar(c[i] >> 12);
				tmpbuf_[1] = hexChar((c[i] & 0x0f00) >> 8);
				tmpbuf_[2] = hexChar((c[i] & 0x00f0) >> 4);
				tmpbuf_[3] = hexChar((c[i] & 0x000f));
				trans_.write(tmpbuf_, 0, 4);
			} else if ((c[i] & 0x00FF) >= 0x30) {
				if (c[i] == BACKSLASH[0]) {
					trans_.write(BACKSLASH);
					trans_.write(BACKSLASH);
				} else {
					tmpbuf_[0] = (byte) c[i];
					trans_.write(tmpbuf_, 0, 1);
				}
			} else {
				tmpbuf_[0] = JSON_CHAR_TABLE[c[i]];
				if (tmpbuf_[0] == 1) {
					tmpbuf_[0] = (byte) c[i];
					trans_.write(tmpbuf_, 0, 1);
				} else if (tmpbuf_[0] > 1) {
					trans_.write(BACKSLASH);
					trans_.write(tmpbuf_, 0, 1);
				} else {
					trans_.write(ESCSEQ);
					tmpbuf_[0] = hexChar((byte) (c[i] >> 4));
					tmpbuf_[1] = hexChar(c[i]);
					trans_.write(tmpbuf_, 0, 2);
				}
			}
		}
		trans_.write(QUOTE);
	}

	protected void writeJSONNull() throws TException {
		context_.write();

		if (!context_.nullAsEmpty()) {
			trans_.write(NULL);
		}
	}

	// Write out number as a JSON value. If the context dictates so, it will be
	// wrapped in quotes to output as a JSON string.
	protected void writeJSONInteger(long num) throws TException {
		context_.write();
		String str = Long.toString(num);
		boolean escapeNum = context_.escapeNum();
		if (escapeNum) {
			trans_.write(QUOTE);
		}
		try {
			byte[] buf = str.getBytes("UTF-8");
			trans_.write(buf);
		} catch (UnsupportedEncodingException uex) {
			throw new TException("JVM DOES NOT SUPPORT UTF-8");
		}
		if (escapeNum) {
			trans_.write(QUOTE);
		}
	}

	// Write out a double as a JSON value. If it is NaN or infinity or if the
	// context dictates escaping, write out as JSON string.
	protected void writeJSONDouble(double num) throws TException {
		context_.write();
		String str = Double.toString(num);
		boolean special = false;
		switch (str.charAt(0)) {
		case 'N': // NaN
		case 'I': // Infinity
			special = true;
			break;
		case '-':
			if (str.charAt(1) == 'I') { // -Infinity
				special = true;
			}
			break;
		}

		boolean escapeNum = special || context_.escapeNum();
		if (escapeNum) {
			trans_.write(QUOTE);
		}
		try {
			byte[] b = str.getBytes("UTF-8");
			trans_.write(b, 0, b.length);
		} catch (UnsupportedEncodingException uex) {
			throw new TException("JVM DOES NOT SUPPORT UTF-8");
		}
		if (escapeNum) {
			trans_.write(QUOTE);
		}
	}

	// Write out contents of byte array b as a JSON string with base-64 encoded
	// data
	protected void writeJSONBase64(byte[] b) throws TException {
		context_.write();
		trans_.write(QUOTE);
		int len = b.length;
		int off = 0;
		while (len >= 3) {
			// Encode 3 bytes at a time
			TBase64Utils.encode(b, off, 3, tmpbuf_, 0);
			trans_.write(tmpbuf_, 0, 4);
			off += 3;
			len -= 3;
		}
		if (len > 0) {
			// Encode remainder
			TBase64Utils.encode(b, off, len, tmpbuf_, 0);
			trans_.write(tmpbuf_, 0, len + 1);
		}
		trans_.write(QUOTE);
	}

	protected void writeJSONObjectStart() throws TException {
		context_.write();
		trans_.write(LBRACE);
		pushContext(new JSONPairContext());
	}

	protected void writeJSONObjectEnd() throws TException {
		popContext();
		trans_.write(RBRACE);
	}

	protected void writeJSONArrayStart() throws TException {
		context_.write();
		trans_.write(LBRACKET);
		pushContext(new JSONListContext());
	}

	protected void writeJSONArrayEnd() throws TException {
		popContext();
		trans_.write(RBRACKET);
	}

	public void writeMessageBegin(TMessage message) throws TException {
		writeJSONArrayStart();
		writeJSONInteger(VERSION);
		writeJSONString(message.name.toCharArray());
		writeJSONInteger(message.type);
		writeJSONInteger(message.seqid);
	}

	public void writeMessageEnd() throws TException {
		writeJSONArrayEnd();
	}

	public void writeStructBegin(TStruct struct) throws TException {
		writeJSONObjectStart();
	}

	public void writeStructEnd() throws TException {
		writeJSONObjectEnd();
	}

	public void writeFieldStop() {
	}

	public void writeListEnd() throws TException {
		writeJSONArrayEnd();
	}

	public void writeBool(boolean b) throws TException {
		writeJSONInteger(b ? (long) 1 : (long) 0);
	}

	public void writeByte(byte b) throws TException {
		writeJSONInteger(b);
	}

	public void writeI16(short i16) throws TException {
		writeJSONInteger(i16);
	}

	public void writeI32(int i32) throws TException {
		writeJSONInteger(i32);
	}

	public void writeI64(long i64) throws TException {
		writeListBegin(null);
		writeJSONInteger(i64 >> 32);
		writeJSONInteger(i64 & 0xffffffff);
		writeListEnd();
	}

	public void writeDouble(double dub) throws TException {
		writeJSONDouble(dub);
	}

	public void writeString(String str) throws TException {
		if (str == null) {
			writeJSONNull();
		} else {
			char[] c = str.toCharArray();
			writeJSONString(c);
		}
	}

	public void writeBinary(byte[] bin) throws TException {
		writeJSONBase64(bin);
	}

	public void writeNull() throws TException {
		writeJSONNull();
	}
}
