package com.dotspots.rpcplus.client.transport.impl;

import com.dotspots.rpcplus.client.jsonrpc.RpcException;
import com.dotspots.rpcplus.client.transport.TransportLogger;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.FormElement;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;

/**
 * Base class for the two cross-domain frame request types: window.name and postMessage.
 */
abstract class CrossDomainFrameTransportRequest {
	private final String arguments;
	protected final AsyncCallback<String> callback;

	private static final int NO_TIMER = -1;

	protected static final String SEND_PREFIX = "wnt-";
	protected static final String RECEIVE_PREFIX = "wnr-";

	private final int timeout;
	protected final Document document;
	private final String url;

	protected boolean running;

	/**
	 * The document holding the iframe. Pinned here so IE doesn't GC it.
	 */
	private Document iframeDocument;

	/**
	 * The iframe that we're posting into.
	 */
	protected IFrameElement iframe;

	/**
	 * The form that we're posting to the iframe.
	 */
	private FormElement form;

	/**
	 * A timer that tracks when we should consider this request timed out.
	 */
	private int timeoutTimerId = NO_TIMER;

	protected String serial;
	protected String iframeName;

	public CrossDomainFrameTransportRequest(String arguments, final AsyncCallback<String> callback, Document document, String url,
			int timeout) {
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

		if (timeoutTimerId != NO_TIMER) {
			clearTimeout(timeoutTimerId);
			timeoutTimerId = NO_TIMER;
		}

		if (iframe != null) {
			detachIFrameListeners();

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
		serial = UniqueRequestGenerator.createUniqueId();
		iframeName = SEND_PREFIX + serial;

		try {
			timeoutTimerId = createTimeoutTimer(timeout, this);

			// Create the attached iframe
			createAttachedIFrame();
			setIFrameContentWindowName(iframe, iframeName);
			attachIFrameListeners();

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

	@SuppressWarnings("unused")
	private void error() {
		if (!running) {
			return;
		}

		cancel();
		callback.onFailure(new RpcException("RPC failed"));
	}

	@SuppressWarnings("unused")
	private void timeout() {
		cancel();
		callback.onFailure(new RpcException("RPC timed out"));
	}

	private native void clearTimeout(int timeoutId) /*-{
		$wnd.clearTimeout(timeoutId);
	}-*/;

	/**
	 * Creates a raw timeout on the window to avoid issues with the GWT Timer class.
	 */
	private native int createTimeoutTimer(int timeout, CrossDomainFrameTransportRequest self) /*-{
		return $wnd.setTimeout(function() { self.@com.dotspots.rpcplus.client.transport.impl.CrossDomainFrameTransportRequest::timeout()() }, timeout);
	}-*/;

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

	protected void populateForm() {
		appendHiddenInput("serial", serial);
		appendHiddenInput("data", arguments);
		appendHiddenInput("type", getRequestType());
	}

	protected void appendHiddenInput(String name, String value) {
		InputElement input = iframeDocument.createHiddenInputElement();
		input.setName(name);
		input.setValue(value);
		form.appendChild(input);
	}

	private static native boolean isActiveXSupported() /*-{
		return ("ActiveXObject" in $wnd);
	}-*/;

	private static native void collectGarbage() /*-{
		if ("CollectGarbage" in $wnd)
		CollectGarbage();
	}-*/;

	private static native void setIFrameContentWindowName(IFrameElement iframe, String name) /*-{
		// For IE
		iframe.contentWindow.name = name;

		// For safari
		iframe.setAttribute('name', name);
	}-*/;

	protected abstract String getRequestType();

	protected abstract void detachIFrameListeners();

	protected abstract void attachIFrameListeners();
}
