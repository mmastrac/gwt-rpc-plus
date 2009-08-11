package com.dotspots.rpcplus.jsonrpc.thrift;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TIOStreamTransport;
import org.json.JSONException;
import org.json.JSONTokener;

public class ThriftRequestProcessor {
	public void handleRequest(JSONServlet servlet, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		try {
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
		} catch (JSONException e) {
			throw new ServletException(e);
		}
	}

	public void handleExplainUsage(JSONServlet servlet, HttpServletResponse resp) throws IOException {
		resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
		resp.setContentType("text/html");
		resp.getOutputStream().print("<html><body>This is a thrift JSON endpoint which requires a POST</body></html>");
		return;
	}

	public void handleJSONPost(JSONServlet servlet, HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException,
			TException {
		JSONTokener tokener = new JSONTokener(new InputStreamReader(req.getInputStream()));
		TJSONOrgProtocol protocol = new TJSONOrgProtocol(tokener);

		TJSONNativeProtocol nativeProtocol = new TJSONNativeProtocol(new TIOStreamTransport(resp.getOutputStream()));
		servlet.processRequest(protocol, nativeProtocol);
	}

	public void handleFormPost(JSONServlet servlet, HttpServletRequest req, HttpServletResponse resp) throws IOException, JSONException,
			TException {
		resp.getOutputStream().print("<html><body><script>window.name='");
		String json = req.getParameter("data");
		String redirect = req.getParameter("redirect");
		JSONTokener tokener = new JSONTokener(json);
		TJSONOrgProtocol protocol = new TJSONOrgProtocol(tokener);
		TJSONNativeProtocol nativeProtocol = new TJSONNativeProtocol(new EscapingStreamTransport(resp.getOutputStream()));
		servlet.processRequest(protocol, nativeProtocol);
		resp.getOutputStream().print("';window.location.replace('" + redirect + "');</script></body></html>");
	}
}