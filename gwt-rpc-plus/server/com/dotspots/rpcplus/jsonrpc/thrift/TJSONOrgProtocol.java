package com.dotspots.rpcplus.jsonrpc.thrift;

import java.util.Stack;

import org.apache.thrift.TException;
import org.json.JSONException;
import org.json.JSONTokener;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;

public class TJSONOrgProtocol extends TBaseJSONProtocol {
	private Stack<State> states = new Stack<State>();
	private final JSONTokener tokener;

	public TJSONOrgProtocol(JSONTokener tokener) throws JSONException {
		this.tokener = tokener;
		states.add(new DefaultState());
	}

	private class State {
		public int readFieldId() throws TException {
			throw new TException("Invalid state for readFieldId(): " + this);
		}

		public boolean hasNext() throws TException {
			throw new TException("Invalid state for hasNext(): " + this);
		}

		public void beforeRead() throws TException {
		}
	}

	private class DefaultState extends State {

		@Override
		public String toString() {
			return "Default";
		}
	}

	private class ListState extends State {

		@Override
		public boolean hasNext() throws TException {
			try {
				return tokener.next() == ',';
			} catch (JSONException e) {
				throw new TException(e);
			}
		}
	}

	private class MapState extends State {
		boolean expectColon = false;

		@Override
		public boolean hasNext() throws TException {
			try {
				return tokener.next() == ',';
			} catch (JSONException e) {
				throw new TException(e);
			}
		}

		@Override
		public void beforeRead() throws TException {
			try {
				if (expectColon && tokener.next() != ':') {
					throw new TException("Missing colon");
				}

				expectColon = !expectColon;
			} catch (JSONException e) {
				throw new TException(e);
			}
		}
	}

	public class MapStructState extends MapState {

		@Override
		public int readFieldId() throws TException {
			try {
				expectColon = true;

				final Object nextValue = tokener.nextValue();
				if (nextValue instanceof Integer) {
					return (Integer) nextValue;
				}
				if (nextValue instanceof String) {
					return Integer.parseInt((String) nextValue);
				}

				throw new TException("Unexpected type: " + nextValue.getClass());
			} catch (JSONException e) {
				// HACK: Need to detect that next char is } or number
				return -1;
			} catch (NumberFormatException e) {
				throw new TException(e);
			}
		}
	}

	public class ListStructState extends ListState {
		private int index = 0;

		@Override
		public int readFieldId() throws TException {
			if (index > 0) {
				try {
					return tokener.next() == ',' ? index++ : -1;
				} catch (JSONException e) {
					throw new TException(e);
				}
			}

			return index++;
		}
	}

	@Override
	public boolean hasNext() throws TException {
		return getState().hasNext();
	}

	@Override
	public boolean readBool() throws TException {
		try {
			getState().beforeRead();
			return ((JSONBoolean) tokener.nextValue()).booleanValue();
		} catch (JSONException e) {
			throw new TException(e);
		}
	}

	@Override
	public double readDouble() throws TException {
		try {
			getState().beforeRead();
			return ((JSONNumber) tokener.nextValue()).doubleValue();
		} catch (JSONException e) {
			throw new TException(e);
		}
	}

	@Override
	public int readFieldId() throws TException {
		try {
			return getState().readFieldId();
		} catch (TException e) {
			throw new TException(e);
		}
	}

	@Override
	public long readI64() throws TException {
		readListBegin();

		long i64 = (long) readDouble() * 0x100000000L + (long) readDouble();

		readListEnd();

		return i64;
	}

	@Override
	public int readI32() throws TException {
		try {
			getState().beforeRead();
			final Object nextValue = tokener.nextValue();
			if (nextValue instanceof Integer) {
				return (Integer) nextValue;
			}
			if (nextValue instanceof String) {
				return Integer.parseInt((String) nextValue);
			}

			throw new TException("Unexpected type: " + nextValue.getClass());
		} catch (JSONException e) {
			throw new TException(e);
		} catch (NumberFormatException e) {
			throw new TException(e);
		}
	}

	@Override
	public boolean readListBegin() throws TException {
		try {
			getState().beforeRead();

			if (tokener.next() == '[') {
				pushState(new ListState());
				return true;
			}
		} catch (JSONException e) {
			throw new TException(e);
		}

		return false;
	}

	@Override
	public void readListEnd() {
		popState();
	}

	@Override
	public boolean readMapBegin() throws TException {
		try {
			getState().beforeRead();

			if (tokener.next() == '{') {
				pushState(new MapState());
				return true;
			}
		} catch (JSONException e) {
			throw new TException(e);
		}

		return false;
	}

	@Override
	public void readMapEnd() {
		popState();
	}

	@Override
	public boolean readSetBegin() {
		return false;
	}

	@Override
	public void readSetEnd() {
		popState();
	}

	@Override
	public String readString() throws TException {
		try {
			getState().beforeRead();
			return tokener.nextValue().toString();
		} catch (JSONException e) {
			throw new TException(e);
		}
	}

	@Override
	public byte[] readBinary() throws TException {
		readString();

		// TODO: Base64 decode this
		return new byte[0];
	}

	@Override
	public boolean readStructBegin() throws TException {
		try {
			getState().beforeRead();

			switch (tokener.next()) {
			case '[':
				pushState(new ListStructState());
				return true;
			case '{':
				pushState(new MapStructState());
				return true;
			}
		} catch (JSONException e) {
			throw new TException(e);
		}

		return false;
	}

	@Override
	public void readStructEnd() {
		popState();
	}

	@Override
	public void skip() {

	}

	private State getState() {
		return states.peek();
	}

	private void pushState(State state) {
		states.add(state);
	}

	private void popState() {
		states.pop();
	}
}
