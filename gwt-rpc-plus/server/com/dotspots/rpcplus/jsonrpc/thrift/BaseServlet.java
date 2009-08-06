package com.dotspots.rpcplus.jsonrpc.thrift;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet<T> extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JSONServlet servlet;

	private ThriftRequestProcessor processor = new ThriftRequestProcessor();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processor.handleRequest(servlet, req, resp);
	}
}
