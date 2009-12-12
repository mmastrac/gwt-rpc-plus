package com.dotspots.rpcplus.jsonrpc.thrift;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TIOStreamTransport;
import org.svenson.JSONParseException;
import org.svenson.tokenize.InputStreamSource;
import org.svenson.tokenize.JSONTokenizer;
import org.svenson.tokenize.StringJSONSource;

import com.dotspots.rpcplus.client.transport.CommonMimeTypes;

public class ThriftRequestProcessor {
	private static final ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
	private static final ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();

	/**
	 * Gets the currently active HTTP request (only valid within a JSON request).
	 */
	public static HttpServletRequest getCurrentRequest() {
		return request.get();
	}

	/**
	 * Gets the currently active HTTP response (only valid within a JSON request).
	 */
	public static HttpServletResponse getCurrentResponse() {
		return response.get();
	}

	public void handleRequest(JSONServlet servlet, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		try {
			request.set(req);
			response.set(resp);

			if (req.getMethod().equals("POST")) {
				final String contentType = req.getContentType();
				if (contentType != null) {
					if (contentType.startsWith("application/json")) {
						handleJSONPost(servlet, req, resp);
						return;
					} else if (contentType.startsWith("application/x-www-form-urlencoded")) {
						handleFormPost(servlet, req, resp);
						return;
					}
				}
			}

			handleExplainUsage(servlet, resp);
		} catch (TException e) {
			throw new ServletException(e);
		} catch (JSONParseException e) {
			throw new ServletException(e);
		} finally {
			request.set(null);
			response.set(null);
		}
	}

	public void handleExplainUsage(JSONServlet servlet, HttpServletResponse resp) throws IOException {
		resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
		resp.setContentType(CommonMimeTypes.HTML_MIME_TYPE);
		resp.getOutputStream().print("<html><body>This is a thrift JSON endpoint which requires a POST</body></html>");
		return;
	}

	public void handleJSONPost(JSONServlet servlet, HttpServletRequest req, HttpServletResponse resp) throws IOException,
			JSONParseException, TException {
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType(CommonMimeTypes.JSON_MIME_TYPE);

		JSONTokenizer tokener = new JSONTokenizer(new InputStreamSource(req.getInputStream(), false), true);
		TJSONProtocolReader protocol = new TJSONProtocolReader(tokener);

		TJSONProtocolWriter nativeProtocol = new TJSONProtocolWriter(new TIOStreamTransport(resp.getOutputStream()));
		servlet.processRequest(protocol, nativeProtocol);
	}

	public void handleFormPost(JSONServlet servlet, HttpServletRequest req, HttpServletResponse resp) throws IOException,
			JSONParseException, TException {
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType(CommonMimeTypes.HTML_MIME_TYPE);
		resp.setCharacterEncoding("UTF-8");

		String data = req.getParameter("data");
		String redirect = req.getParameter("redirect");
		String serial = req.getParameter("serial");
		String type = req.getParameter("type");

		if (type == null || type.equals("window.name")) {
			resp.getOutputStream().print("<html><body><script>window.name='wnr-" + serial);
			JSONTokenizer tokener = new JSONTokenizer(new StringJSONSource(data), true);
			TJSONProtocolReader protocol = new TJSONProtocolReader(tokener);
			TJSONProtocolWriter nativeProtocol = new TJSONProtocolWriter(new EscapingStreamTransport(resp.getOutputStream()));
			servlet.processRequest(protocol, nativeProtocol);
			resp.getOutputStream().print("';window.location.replace('" + redirect + "');</script></body></html>");
		} else if (type.equals("postmessage")) {
			resp.getOutputStream().print("<html><body><script>window.parent.postMessage('");
			JSONTokenizer tokener = new JSONTokenizer(new StringJSONSource(data), true);
			TJSONProtocolReader protocol = new TJSONProtocolReader(tokener);
			TJSONProtocolWriter nativeProtocol = new TJSONProtocolWriter(new EscapingStreamTransport(resp.getOutputStream()));
			servlet.processRequest(protocol, nativeProtocol);
			resp.getOutputStream().print("', '*');</script></body></html>");
		}
	}
}
