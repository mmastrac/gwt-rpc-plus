package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.codec.impl.JSONFactory;
import com.dotspots.rpcplus.client.common.RPCPlusService;
import com.dotspots.rpcplus.client.transport.HasContentType;
import com.dotspots.rpcplus.client.transport.HasDocument;
import com.dotspots.rpcplus.client.transport.HasJsonTransport;
import com.dotspots.rpcplus.client.transport.HasTextTransport;
import com.dotspots.rpcplus.client.transport.HasTransport;
import com.dotspots.rpcplus.client.transport.HasUrlEndpoint;
import com.dotspots.rpcplus.client.transport.HasWrappedTransport;
import com.dotspots.rpcplus.client.transport.JsonTransport;
import com.dotspots.rpcplus.client.transport.TextTransport;
import com.dotspots.rpcplus.client.transport.Transport;
import com.dotspots.rpcplus.client.transport.TransportFactory;
import com.google.gwt.dom.client.Document;

/**
 * Base class for all provided transport factories.
 */
public abstract class AbstractTransportFactory implements TransportFactory {
	public Transport initialize(String contentType, RPCPlusService service, HasTransport<?> hasTransport) {
		if (hasTransport instanceof HasJsonTransport) {
			return initialize(contentType, service, (HasJsonTransport) hasTransport);
		} else if (hasTransport instanceof HasTextTransport) {
			return initialize(contentType, service, (HasTextTransport) hasTransport);
		}

		assert false : "Unexpected transport type";
		return null;
	}

	protected String getUrl(RPCPlusService service, HasTransport<?> hasTransport) {
		return service.getServiceEntryPoint();
	}

	protected JSONFactory getJSONFactory() {
		return new JSONFactory();
	}

	private Document getDocument(RPCPlusService service, HasTransport<?> hasTransport) {
		return Document.get();
	}

	protected Transport initialize(String contentType, RPCPlusService service, HasJsonTransport hasTransport) {
		Transport transport = createTransport(service, hasTransport);
		if (transport instanceof TextTransport) {
			transport = new JsonOverTextTransport((TextTransport) transport, getJSONFactory());
		}

		initializeTransport(contentType, service, hasTransport, transport);
		hasTransport.setJsonTransport((JsonTransport) transport);
		return transport;
	}

	protected Transport initialize(String contentType, RPCPlusService service, HasTextTransport hasTransport) {
		Transport transport = createTransport(service, hasTransport);
		if (transport instanceof JsonTransport) {
			transport = new TextOverJsonTransport((JsonTransport) transport);
		}

		initializeTransport(contentType, service, hasTransport, transport);
		hasTransport.setTextTransport((TextTransport) transport);
		return transport;
	}

	protected void initializeTransport(String contentType, RPCPlusService service, HasTransport<?> hasTransport, Transport transport) {
		// Transport has a URL?
		if (transport instanceof HasUrlEndpoint) {
			final String url = getUrl(service, hasTransport);
			if (url != null) {
				((HasUrlEndpoint) transport).setUrl(url);
			}
		}

		// Transport has a DOM Document?
		if (transport instanceof HasDocument) {
			((HasDocument) transport).setDocument(getDocument(service, hasTransport));
		}

		// Transport has content type?
		if (transport instanceof HasContentType) {
			((HasContentType) transport).setContentType(contentType);
		}

		if (transport instanceof HasWrappedTransport) {
			// Recursively initialize this transport's wrapped transports
			initializeTransport(contentType, service, hasTransport, ((HasWrappedTransport) transport).getWrappedTransport());
		}
	}

	protected abstract Transport createTransport(RPCPlusService service, HasTransport<?> hasTransport);
}
