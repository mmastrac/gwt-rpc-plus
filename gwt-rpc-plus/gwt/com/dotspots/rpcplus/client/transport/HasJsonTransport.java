package com.dotspots.rpcplus.client.transport;

public interface HasJsonTransport extends HasTransport<JsonTransport> {
	public void setJsonTransport(JsonTransport transport);

	public JsonTransport getJsonTransport();
}
