package com.dotspots.rpcplus.jsonrpc.thrift;

import java.util.Stack;

import org.apache.thrift.TException;
import org.svenson.JSONParseException;
import org.svenson.tokenize.JSONTokenizer;
import org.svenson.tokenize.Token;
import org.svenson.tokenize.TokenType;

public class TJSONProtocolReader extends TBaseJSONProtocol {
	private Stack<State> states = new Stack<State>();
	private final JSONTokenizer tokener;

	public TJSONProtocolReader(JSONTokenizer tokener) {
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
			} catch (JSONParseException e) {
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
				if (first) {
					first = false;

					final Token token = tokener.peekToken();
					switch (token.type()) {
					case BRACE_CLOSE:
						return false;
					case COLON:
						throw new TException("Unexpected token: " + token);
					default:
						return true;
					}
				} else {
					return (tokener.next().type() == TokenType.COMMA);
				}
			} catch (JSONParseException e) {
				throw new TException(e);
			}
		}

		@Override
		public Token readToken() throws TException {
			boolean removePrefix = !expectColon;

			try {
				if (expectColon) {
					tokener.expectNext(TokenType.COLON);
				}

				expectColon = !expectColon;
			} catch (JSONParseException e) {
				throw new TException(e);
			}

			if (removePrefix) {
				Token token = super.readToken();
				if (token.isType(TokenType.STRING)) {
					final String value = (String) token.value();
					if (value.startsWith("_")) {
						return Token.getToken(TokenType.STRING, value.substring(1));
					}
				}

				return token;
			}
			return super.readToken();
		}
	}

	public class SetState extends State {
		boolean first = true;

		@Override
		public boolean hasNext() throws TException {
			try {
				if (first) {
					first = false;

					final Token token = tokener.peekToken();
					switch (token.type()) {
					case BRACE_CLOSE:
						return false;
					case COLON:
						throw new TException("Unexpected token: " + token);
					default:
						return true;
					}
				} else {
					tokener.expectNext(TokenType.COLON);
					tokener.expectNext(TokenType.INTEGER);
					return (tokener.next().type() == TokenType.COMMA);
				}
			} catch (JSONParseException e) {
				throw new TException(e);
			}
		}

		@Override
		public Token readToken() throws TException {
			final Token token = super.readToken();
			if (token.type() == TokenType.STRING) {
				return Token.getToken(TokenType.STRING, ((String) token.value()).substring(1));
			}

			return token;
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
			final Token nextValue = getState().readToken();
			switch (nextValue.type()) {
			case NULL:
			case FALSE:
				return false;
			case TRUE:
				return true;
			}

			throw new TException("Unexpected type: " + nextValue.toString());
		} catch (JSONParseException e) {
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
		} catch (JSONParseException e) {
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
			tokener.expectNext(TokenType.BRACKET_CLOSE);
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
		} catch (JSONParseException e) {
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
		} catch (JSONParseException e) {
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
		} catch (JSONParseException e) {
			throw new TException(e);
		}

		return false;
	}

	@Override
	public boolean readSetBegin() throws TException {
		try {
			switch (getState().readToken().type()) {
			case BRACE_OPEN:
				pushState(new SetState());
				return true;
			case NULL:
				return false;
			}
		} catch (JSONParseException e) {
			throw new TException(e);
		}

		return false;
	}

	@Override
	public String readString() throws TException {
		try {
			return (String) getState().readToken().value();
		} catch (JSONParseException e) {
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
		} catch (JSONParseException e) {
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
