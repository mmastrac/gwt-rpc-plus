package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.codec.JsonDecoder;
import com.dotspots.rpcplus.client.codec.JsonParseException;
import com.dotspots.rpcplus.client.codec.LooseJsonEncoder;
import com.dotspots.rpcplus.client.codec.impl.JSONFactory;
import com.dotspots.rpcplus.client.transport.HasContentType;
import com.dotspots.rpcplus.client.transport.JsonTransport;
import com.dotspots.rpcplus.client.transport.TextTransport;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class JsonOverTextTransport implements JsonTransport, HasContentType {
	private TextTransport textTransport;
	private JsonDecoder decoder;
	private LooseJsonEncoder encoder;

	public JsonOverTextTransport() {
	}

	public JsonOverTextTransport(TextTransport textTransport, JSONFactory factory) {
		this.textTransport = textTransport;
		this.decoder = factory.createJSONDecoder();
		this.encoder = factory.createLooseJSONEncoder();
	}

	public JsonOverTextTransport(TextTransport textTransport, JsonDecoder decoder, LooseJsonEncoder encoder) {
		this.textTransport = textTransport;
		this.decoder = decoder;
		this.encoder = encoder;
	}

	public void setContentType(String contentType) {
		if (textTransport instanceof HasContentType) {
			((HasContentType) textTransport).setContentType(contentType);
		}
	}

	public String getContentType() {
		if (textTransport instanceof HasContentType) {
			return ((HasContentType) textTransport).getContentType();
		}
		return null;
	}

	public void setDecoder(JsonDecoder decoder) {
		this.decoder = decoder;
	}

	public void setEncoder(LooseJsonEncoder encoder) {
		this.encoder = encoder;
	}

	public void setTextTransport(TextTransport textTransport) {
		this.textTransport = textTransport;
	}

	public TextTransport getTextTransport() {
		return textTransport;
	}

	public void call(JavaScriptObject arguments, final AsyncCallback<JavaScriptObject> callback) {
		textTransport.call(encoder.encode(arguments), new AsyncCallback<String>() {
			public void onSuccess(String result) {
				try {
					callback.onSuccess(decoder.decode(result));
				} catch (JsonParseException e) {
					callback.onFailure(e);
				}
			}

			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}
		});
	}
}
