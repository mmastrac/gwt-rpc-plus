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

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/html");

		ServletOutputStream output = response.getOutputStream();

		output.print("<html><body><script>window.name='");
		String result = StringEscapeUtils.escapeJavaScript(responsePayload);
		output.write(result.getBytes("UTF-8"));
		output.print("';window.location.replace('" + StringEscapeUtils.escapeJavaScript(request.getParameter("redirect"))
				+ "');</script></body></html>");
	}

	@Override
	protected String readContent(HttpServletRequest request) throws ServletException, IOException {
		return request.getParameter("data");
	}

}
