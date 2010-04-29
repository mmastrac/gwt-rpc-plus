package com.dotspots.rpcplus.codegen.thrift;

import java.io.PrintWriter;
import java.util.Stack;

/**
 * A simple {@link PrintWriter} wrapper that supports automatic indenting.
 */
public class IndentingPrintWriter {
	private static final String INDENT = "    ";

	private final PrintWriter printWriter;
	private String indent = "";

	private Stack<String> indentCheckpoint = new Stack<String>();

	public IndentingPrintWriter(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}

	public void println(String string) {
		printWriter.println(indent + string);
	}

	public void println() {
		// Don't need to indent blank lines
		printWriter.println();
	}

	public void indent() {
		indent += INDENT;
	}

	public void unindent() {
		if (indent.length() == 0) {
			assert false : "Unbalanced unindent";
			return;
		}

		indent = indent.substring(0, indent.length() - INDENT.length());
	}

	public void startBlock() {
		indentCheckpoint.add(indent);
	}

	public void endBlock() {
		if (!indent.equals(indentCheckpoint.pop())) {
			assert false : "Indent state unbalanced";
		}
	}
}
