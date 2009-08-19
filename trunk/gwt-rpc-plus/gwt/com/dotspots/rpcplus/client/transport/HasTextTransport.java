package com.dotspots.rpcplus.client.transport;

public interface HasTextTransport extends HasTransport<TextTransport> {
	public void setTextTransport(TextTransport transport);

	public TextTransport getTextTransport();
}
