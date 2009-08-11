package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.jsonrpc.RpcException;
import com.dotspots.rpcplus.client.transport.TextTransport;
import com.dotspots.rpcplus.client.transport.TransportLogger;
import com.google.gwt.core.client.Duration;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.FormElement;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;

/**
 * Assumes that someone is waiting on the end of the URL for a form post and will return JSON by assigning it to
 * window.name and redirecting back to us.
 * 
 * Supports navigation-click-sound-free operation on IE through ActiveXObject("htmlfile").
 */
public class WindowNameTransport implements TextTransport {
	// Globally unique ID
	private static int nameSerial;

	private static final String SEND_PREFIX = "wnt-";
	private static final String RECEIVE_PREFIX = "wnr-";

	private String url;
	private Document document;
	private int timeout = 30000;

	private interface IFrameListener {
		public void check();

		public void error();
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public void call(String arguments, final AsyncCallback<String> callback) {
		try {
			final String serial = nameSerial++ + "-" + Duration.currentTimeMillis();
			final String iframeName = SEND_PREFIX + serial;
			final String responseName = RECEIVE_PREFIX + serial;

			// Create the attached iframe
			final IFrameElement iframe = createAttachedIFrame();
			setIFrameContentWindowName(iframe, iframeName);
			Document document = iframe.getOwnerDocument();

			// Create and attach the form
			final FormElement form = createForm(document, iframeName);

			final Timer timeoutTimer = new Timer() {
				@Override
				public void run() {
					cleanup(iframe, form, null);
					callback.onFailure(new RpcException("RPC timed out"));
				}
			};
			timeoutTimer.schedule(timeout);

			attachIFrameListener(iframe, new IFrameListener() {
				public void check() {
					String currentIframeName = getIFrameContentWindowName(iframe);
					if (currentIframeName != null && !currentIframeName.equals(iframeName) && currentIframeName.startsWith(responseName)) {
						try {
							cleanup(iframe, form, timeoutTimer);
						} catch (Throwable t) {
							callback.onFailure(new RpcException("Unexpected error cleaning up iframe"));
						}

						currentIframeName = currentIframeName.substring(responseName.length());
						TransportLogger.INSTANCE.logReceive(currentIframeName);

						try {
							callback.onSuccess(currentIframeName);
						} catch (Throwable t) {
							callback.onFailure(t);
						}
					}
				}

				public void error() {
					timeoutTimer.cancel();
					callback.onFailure(new RpcException("RPC failed"));
				}
			});

			TransportLogger.INSTANCE.logSend(arguments);
			populateForm(document, form, arguments, serial);

			form.submit();
		} catch (Throwable t) {
			callback.onFailure(new RpcException("Unexpected error while submitting request", t));
		}
	}

	private void cleanup(final IFrameElement iframe, final FormElement form, final Timer timeout) {
		if (timeout != null) {
			timeout.cancel();
		}

		try {
			detachIFrameListener(iframe);
		} catch (Throwable t) {
			// IE will sometimes fail here
		}

		// TODO: GWT 2.0
		// iframe.removeFromParent();
		// form.removeFromParent();

		removeFromParent(iframe);
		removeFromParent(form);
	}

	private void removeFromParent(Element elem) {
		elem.getParentElement().removeChild(elem);
	}

	private void makeInvisible(final Element elem) {
		// TODO: GWT 2.0
		// elem.getStyle().setVisibility(Visibility.HIDDEN);
		// elem.getStyle().setHeight(10, Unit.PX);
		// elem.getStyle().setWidth(10, Unit.PX);
		// elem.getStyle().setPosition(Position.ABSOLUTE);

		elem.getStyle().setProperty("visibility", "hidden");
		elem.getStyle().setPropertyPx("height", 10);
		elem.getStyle().setPropertyPx("width", 10);
		elem.getStyle().setProperty("position", "absolute");
	}

	private IFrameElement createAttachedIFrame() {
		if (isActiveXSupported()) {
			Document htmlfile = createHtmlFile();
			return htmlfile.getElementById("iframe").cast();
		}

		// Create and attach the iframe
		final IFrameElement iframe = document.createIFrameElement();
		makeInvisible(iframe);
		document.getBody().appendChild(iframe);

		return iframe;
	}

	private native Document createHtmlFile() /*-{
		var htmlfile = new ActiveXObject("htmlfile");
		htmlfile.open();
		htmlfile.write("<html><body><iframe id='iframe'></iframe></body></html>");
		htmlfile.close();
		return htmlfile;
	}-*/;

	private FormElement createForm(Document document, final String iframeName) {
		final FormElement form = document.createFormElement();
		document.getBody().appendChild(form);
		form.setAction(url);
		form.setMethod(FormPanel.METHOD_POST);
		form.setTarget(iframeName);
		form.setEnctype(FormPanel.ENCODING_URLENCODED);

		// If we are using IE, we don't need to hide the form (it's running under the already-invisible ActiveX
		// htmlfile)
		if (!isActiveXSupported()) {
			makeInvisible(form);
		}

		return form;
	}

	private void populateForm(Document document, final FormElement form, String arguments, String serial) {
		InputElement input;

		input = document.createHiddenInputElement();
		input.setName("serial");
		input.setValue(serial);
		form.appendChild(input);

		input = document.createHiddenInputElement();
		input.setName("data");
		input.setValue(arguments);
		form.appendChild(input);

		input = document.createHiddenInputElement();
		input.setName("type");
		input.setValue("window.name");
		form.appendChild(input);

		input = document.createHiddenInputElement();
		input.setName("redirect");
		input.setValue(Window.Location.getProtocol() + "//" + Window.Location.getHost() + "/favicon.ico");
		form.appendChild(input);
	}

	private native boolean isActiveXSupported() /*-{
		return ("ActiveXObject" in $wnd);
	}-*/;

	private native void attachIFrameListener(IFrameElement iframe, IFrameListener iframeListener) /*-{
		iframe.onload = function() { iframeListener.@com.dotspots.rpcplus.client.transport.impl.WindowNameTransport$IFrameListener::check()(); };
		iframe.onerror = function() { iframeListener.@com.dotspots.rpcplus.client.transport.impl.WindowNameTransport$IFrameListener::error()(); };
		iframe.onreadystatechange = function() { iframeListener.@com.dotspots.rpcplus.client.transport.impl.WindowNameTransport$IFrameListener::check()(); };
	}-*/;

	private native void detachIFrameListener(IFrameElement iframe) /*-{
		iframe.onload = function() {};
		iframe.onerror = function() {};
		iframe.onreadystatechange = function() {};
	}-*/;

	private native void setIFrameContentWindowName(IFrameElement iframe, String name) /*-{
		// For IE
		iframe.contentWindow.name = name;

		// For safari
		iframe.setAttribute('name', name);
	}-*/;

	private native String getIFrameContentWindowName(IFrameElement iframe) /*-{
		try {
			return iframe.contentWindow.name || null;
		} catch (e) {
			// Probably a permission error
			return null;
		}
	}-*/;
}
