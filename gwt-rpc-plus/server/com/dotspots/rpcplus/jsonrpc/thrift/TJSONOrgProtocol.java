package com.dotspots.rpcplus.jsonrpc.thrift;

import java.util.Stack;

import org.apache.thrift.TException;
import org.svenson.tokenize.JSONTokenizer;
import org.svenson.tokenize.Token;
import org.svenson.tokenize.TokenType;

import com.google.gwt.json.client.JSONException;

public class TJSONOrgProtocol extends TBaseJSONProtocol {
	private Stack<State> states = new Stack<State>();
	private final JSONTokenizer tokener;

	public TJSONOrgProtocol(JSONTokenizer tokener) {
		this.tokener = tokener;
		states.add(new DefaultState());
	}

	private class State {
		public boolean hasNext() throws TException {
			throw new TException("Invalid state for hasNext(): " + this);
		}

		public Token readToken() throws TException {
			return tokener.next();
		}
	}

	private class DefaultState extends State {
		@Override
		public String toString() {
			return "Default";
		}
	}

	private class ListState extends State {
		boolean first = true;

		@Override
		public Token readToken() throws TException {
			// Virtual null token
			switch (tokener.peekToken().type()) {
			case COMMA:
			case BRACKET_CLOSE:
				return Token.getToken(TokenType.NULL);
			}

			return super.readToken();
		}

		@Override
		public boolean hasNext() throws TException {
			try {
				if (first) {
					first = false;

					final Token token = tokener.peekToken();
					switch (token.type()) {
					case BRACKET_CLOSE:
						return false;
					case COLON:
						throw new TException("Unexpected token: " + token);
					default:
						return true;
					}
				} else {
					return (tokener.next().type() == TokenType.COMMA);
				}
			} catch (JSONException e) {
				throw new TException(e);
			}
		}
	}

	private class MapState extends State {
		boolean expectColon = false;
		boolean first = true;

		@Override
		public boolean hasNext() throws TException {
			try {
				final Token token = tokener.peekToken();
				switch (token.type()) {
				case BRACE_CLOSE:
					return false;
				case COLON:
					throw new TException("Unexpected token: " + token);
				default:
					return true;
				}
			} catch (JSONException e) {
				throw new TException(e);
			}
		}

		@Override
		public Token readToken() throws TException {
			try {
				if (expectColon) {
					tokener.expectNext(TokenType.COLON);
				}
				if (!first && !expectColon) {
					tokener.expectNext(TokenType.COMMA);
				}

				expectColon = !expectColon;
				first = false;
			} catch (JSONException e) {
				throw new TException(e);
			}

			return super.readToken();
		}
	}

	public class MapStructState extends MapState {
	}

	public class ListStructState extends ListState {
		private int index = 0;

		boolean isIndex = true;

		@Override
		public Token readToken() throws TException {
			if (isIndex) {
				isIndex = false;
				return Token.getToken(TokenType.INTEGER, (long) index++);
			}

			isIndex = true;
			return super.readToken();
		}
	}

	@Override
	public boolean hasNext() throws TException {
		final boolean hasNext = getState().hasNext();
		if (!hasNext) {
			popState();
		}

		return hasNext;
	}

	@Override
	public boolean readBool() throws TException {
		try {
			return (Boolean) getState().readToken().value();
		} catch (JSONException e) {
			throw new TException(e);
		}
	}

	@Override
	public double readDouble() throws TException {
		try {
			final Token nextValue = getState().readToken();
			switch (nextValue.type()) {
			case NULL:
				return 0;
			case INTEGER:
			case DECIMAL:
				return ((Number) nextValue.value()).doubleValue();
			case STRING:
				return Double.parseDouble((String) nextValue.value());
			}

			throw new TException("Unexpected type: " + nextValue.toString());
		} catch (JSONException e) {
			throw new TException(e);
		} catch (NumberFormatException e) {
			throw new TException(e);
		}
	}

	@Override
	public long readI64() throws TException {
		final Token token = getState().readToken();
		switch (token.type()) {
		case NULL:
			return 0;

		case BRACKET_OPEN:
			long i64 = (long) ((Number) tokener.expectNext(TokenType.INTEGER).value()).doubleValue() * 0x100000000L;
			tokener.expectNext(TokenType.COMMA);
			i64 += ((Number) tokener.expectNext(TokenType.INTEGER).value()).doubleValue();

			return i64;
		}

		throw new TException("Unexpected token: " + token);
	}

	@Override
	public int readI32() throws TException {
		try {
			final Token nextValue = getState().readToken();
			switch (nextValue.type()) {
			case NULL:
				return 0;
			case INTEGER:
			case DECIMAL:
				return ((Number) nextValue.value()).intValue();
			case STRING:
				return Integer.parseInt((String) nextValue.value());
			}

			throw new TException("Unexpected type: " + nextValue.toString());
		} catch (JSONException e) {
			throw new TException(e);
		} catch (NumberFormatException e) {
			throw new TException(e);
		}
	}

	@Override
	public boolean readListBegin() throws TException {
		try {
			final Token token = getState().readToken();
			switch (token.type()) {
			case BRACKET_OPEN:
				pushState(new ListState());
				return true;
			case NULL:
				return false;
			}
		} catch (JSONException e) {
			throw new TException(e);
		}

		return false;
	}

	@Override
	public boolean readMapBegin() throws TException {
		try {
			switch (getState().readToken().type()) {
			case BRACE_OPEN:
				pushState(new MapState());
				return true;
			case NULL:
				return false;
			}
		} catch (JSONException e) {
			throw new TException(e);
		}

		return false;
	}

	@Override
	public boolean readSetBegin() {
		return false;
	}

	@Override
	public String readString() throws TException {
		try {
			return (String) getState().readToken().value();
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
			switch (getState().readToken().type()) {
			case BRACKET_OPEN:
				pushState(new ListStructState());
				return true;
			case BRACE_OPEN:
				pushState(new MapStructState());
				return true;
			case NULL:
				return false;
			}
		} catch (JSONException e) {
			throw new TException(e);
		}

		return false;
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
