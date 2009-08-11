package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.jsonrpc.RpcException;
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

public class WindowNameTransportRequest {
	private final String arguments;
	private final AsyncCallback<String> callback;
	private boolean running;

	// Globally unique ID
	private static int nameSerial;

	private static final String SEND_PREFIX = "wnt-";
	private static final String RECEIVE_PREFIX = "wnr-";

	private final int timeout;
	private final Document document;
	private final String url;

	/**
	 * The document holding the iframe. Pinned here so IE doesn't GC it.
	 */
	private Document iframeDocument;

	/**
	 * The iframe that we're posting into.
	 */
	private IFrameElement iframe;

	/**
	 * The form that we're posting to the iframe.
	 */
	private FormElement form;

	/**
	 * A timer that tracks when we should consider this request timed out.
	 */
	private Timer timeoutTimer;

	private String serial;
	private String iframeName;
	private String responseName;

	public WindowNameTransportRequest(String arguments, final AsyncCallback<String> callback, Document document, String url, int timeout) {
		this.arguments = arguments;
		this.callback = callback;
		this.document = document;
		this.url = url;
		this.timeout = timeout;
	}

	/**
	 * Cancels a running request. Safe to call more than once.
	 */
	public void cancel() {
		running = false;

		if (timeoutTimer != null) {
			timeoutTimer.cancel();
			timeoutTimer = null;
		}

		if (iframe != null) {
			detachIFrameListener(iframe);

			// TODO: GWT 2.0
			// iframe.removeFromParent();
			// form.removeFromParent();

			removeFromParent(iframe);
			removeFromParent(form);

			iframeDocument = null;
			iframe = null;
			form = null;

			// Allow IE to reclaim the htmlfile document
			collectGarbage();
		}
	}

	public void start() {
		serial = nameSerial++ + "-" + Duration.currentTimeMillis();
		iframeName = SEND_PREFIX + serial;
		responseName = RECEIVE_PREFIX + serial;

		try {
			createTimeoutTimer();

			// Create the attached iframe
			createAttachedIFrame();
			setIFrameContentWindowName(iframe, iframeName);
			attachIFrameListener(iframe, this);

			// Create and populate the form
			createForm();
			populateForm();

			TransportLogger.INSTANCE.logSend(arguments);

			form.submit();
			running = true;
		} catch (Throwable t) {
			callback.onFailure(new RpcException("Unexpected error while submitting request", t));
			cancel();
		}
	}

	public void check() {
		if (!running) {
			return;
		}

		String currentIframeName = getIFrameContentWindowName(iframe);
		if (currentIframeName != null && !currentIframeName.equals(iframeName) && currentIframeName.startsWith(responseName)) {
			cancel();

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
		if (!running) {
			return;
		}

		cancel();
		callback.onFailure(new RpcException("RPC failed"));
	}

	private void createTimeoutTimer() {
		timeoutTimer = new Timer() {
			@Override
			public void run() {
				cancel();
				callback.onFailure(new RpcException("RPC timed out"));
			}
		};
		timeoutTimer.schedule(timeout);
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

	private void createAttachedIFrame() {
		if (isActiveXSupported()) {
			iframeDocument = createHtmlFile();
			iframe = iframeDocument.getElementById("iframe").cast();
		} else {
			// Create and attach the iframe
			iframe = document.createIFrameElement();
			makeInvisible(iframe);
			document.getBody().appendChild(iframe);
			iframeDocument = iframe.getOwnerDocument();
		}
	}

	private native Document createHtmlFile() /*-{
		var htmlfile = new ActiveXObject("htmlfile");
		htmlfile.open();
		htmlfile.write("<html><body><iframe id='iframe'></iframe></body></html>");
		htmlfile.close();
		return htmlfile;
	}-*/;

	private void createForm() {
		form = iframeDocument.createFormElement();
		iframeDocument.getBody().appendChild(form);
		form.setAction(url);
		form.setMethod(FormPanel.METHOD_POST);
		form.setTarget(iframeName);
		form.setEnctype(FormPanel.ENCODING_URLENCODED);

		// If we are using IE, we don't need to hide the form (it's running under the already-invisible ActiveX
		// htmlfile)
		if (!isActiveXSupported()) {
			makeInvisible(form);
		}
	}

	private void populateForm() {
		InputElement input;

		input = iframeDocument.createHiddenInputElement();
		input.setName("serial");
		input.setValue(serial);
		form.appendChild(input);

		input = iframeDocument.createHiddenInputElement();
		input.setName("data");
		input.setValue(arguments);
		form.appendChild(input);

		input = iframeDocument.createHiddenInputElement();
		input.setName("type");
		input.setValue("window.name");
		form.appendChild(input);

		input = iframeDocument.createHiddenInputElement();
		input.setName("redirect");
		input.setValue(Window.Location.getProtocol() + "//" + Window.Location.getHost() + "/favicon.ico");
		form.appendChild(input);
	}

	private static native boolean isActiveXSupported() /*-{
		return ("ActiveXObject" in $wnd);
	}-*/;

	private static native void collectGarbage() /*-{
		if ("CollectGarbage" in $wnd)
			CollectGarbage();
	}-*/;

	private static native void attachIFrameListener(IFrameElement iframe, WindowNameTransportRequest self) /*-{
		iframe.onload = function() { self.@com.dotspots.rpcplus.client.transport.impl.WindowNameTransportRequest::check()(); };
		iframe.onerror = function() { self.@com.dotspots.rpcplus.client.transport.impl.WindowNameTransportRequest::error()(); };
		iframe.onreadystatechange = function() { self.@com.dotspots.rpcplus.client.transport.impl.WindowNameTransportRequest::check()(); };
	}-*/;

	private static native void detachIFrameListener(IFrameElement iframe) /*-{
		iframe.onload = function() {};
		iframe.onerror = function() {};
		iframe.onreadystatechange = function() {};
	}-*/;

	private static native void setIFrameContentWindowName(IFrameElement iframe, String name) /*-{
		// For IE
		iframe.contentWindow.name = name;

		// For safari
		iframe.setAttribute('name', name);
	}-*/;

	private static native String getIFrameContentWindowName(IFrameElement iframe) /*-{
		try {
			return iframe.contentWindow.name || null;
		} catch (e) {
			// Probably a permission error
			return null;
		}
	}-*/;
}
