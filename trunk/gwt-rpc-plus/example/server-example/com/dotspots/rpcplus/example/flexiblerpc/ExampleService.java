package com.dotspots.rpcplus.example.flexiblerpc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import com.dotspots.rpcplus.example.client.flexiblerpc.SimpleService;
import com.dotspots.rpcplus.flexiblerpc.FlexibleRemoteServiceServlet;
import com.google.gwt.user.server.rpc.RPCServletUtils;

public class ExampleService extends FlexibleRemoteServiceServlet implements SimpleService {
	public int add(int i1, int i2) {
		return i1 + i2;
	}

	@Override
	protected void writeResponse(HttpServletRequest request, HttpServletResponse response, String responsePayload) throws IOException {
		boolean gzipEncode = RPCServletUtils.acceptsGzipEncoding(request) && shouldCompressResponse(request, response, responsePayload);

		final String contentType = request.getContentType();
		if (contentType.startsWith("text/x-gwt-rpc")) {
			RPCServletUtils.writeResponse(getServletContext(), response, responsePayload, gzipEncode);
		} else {
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/html");

			ServletOutputStream output = response.getOutputStream();

			output.print("<html><body><script>window.name='");
			String result = StringEscapeUtils.escapeJavaScript(responsePayload);
			output.write(result.getBytes("UTF-8"));
			output.print("';window.location.replace('" + StringEscapeUtils.escapeJavaScript(request.getParameter("redirect"))
					+ "');</script></body></html>");
		}
	}

	@Override
	protected String readContent(HttpServletRequest request) throws ServletException, IOException {
		if (request.getMethod().equals("POST")) {
			final String contentType = request.getContentType();
			if (contentType != null) {
				if (contentType.startsWith("text/x-gwt-rpc")) {
					return RPCServletUtils.readContentAsUtf8(request, true);
				} else if (contentType.startsWith("application/x-www-form-urlencoded")) {
					return request.getParameter("data");
				}
			}
		}

		throw new UnsupportedOperationException("Invalid RPC attempt");
	}
}
