package com.dotspots.rpcplus.test.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.AssertionFailedError;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TIOStreamTransport;
import org.svenson.tokenize.InputStreamSource;
import org.svenson.tokenize.JSONTokenizer;

import com.dotspots.rpcplus.jsonrpc.thrift.TJSONNativeProtocol;
import com.dotspots.rpcplus.jsonrpc.thrift.TJSONOrgProtocol;
import com.dotspots.rpcplus.test.client.EveryCharacterStringUtility;

public class JSONStringEchoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONTokenizer tokener = new JSONTokenizer(new InputStreamSource(req.getInputStream(), false), true);
		TJSONOrgProtocol protocol = new TJSONOrgProtocol(tokener);

		String input;
		try {
			protocol.readListBegin();
			input = protocol.readString();
		} catch (TException e) {
			throw new ServletException(e);
		}

		try {
			EveryCharacterStringUtility.checkString(input);
		} catch (AssertionFailedError e) {
			System.out.println(e.getMessage());
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			return;
		}

		resp.setContentType("text/plain");
		TJSONNativeProtocol outputProtocol = new TJSONNativeProtocol(new TIOStreamTransport(resp.getOutputStream()));
		try {
			outputProtocol.writeListBegin(null);
			outputProtocol.writeString(input);
			outputProtocol.writeListEnd();
		} catch (TException e) {
			throw new ServletException(e);
		}
	}
}
