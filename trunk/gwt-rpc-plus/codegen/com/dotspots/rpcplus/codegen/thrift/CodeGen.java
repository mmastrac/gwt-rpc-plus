package com.dotspots.rpcplus.codegen.thrift;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * Generates RPC files from a thrift service API.
 */
public class CodeGen {
	public void generateGwtCode(File outputDirectory, RpcInterface iface) throws ClassNotFoundException, FileNotFoundException {
		// outputDirectory.delete();

		System.out.println("Generating GWT stubs...");

		for (RpcStruct type : iface.getSerializationTypes()) {
			File output = new File(outputDirectory, type.getFilename());
			output.getParentFile().mkdirs();

			FileOutputStream fileOutputStream = new FileOutputStream(output);
			try {
				final PrintWriter printWriter = new PrintWriter(fileOutputStream);

				type.write(new GwtCodeGenRpcObjectWriter(printWriter));

				printWriter.flush();
			} finally {
				IOUtils.closeQuietly(fileOutputStream);
			}
		}

		File output = new File(outputDirectory, iface.getFilename());
		output.getParentFile().mkdirs();

		FileOutputStream fileOutputStream = new FileOutputStream(output);
		try {
			final PrintWriter printWriter = new PrintWriter(fileOutputStream);

			iface.write(new GwtCodeGenRpcInterfaceWriter(printWriter));
			System.out.println(output.getName());

			printWriter.flush();
		} finally {
			IOUtils.closeQuietly(fileOutputStream);
		}
	}

	public void generateServerCode(File outputDirectory, RpcInterface iface) throws ClassNotFoundException, FileNotFoundException {
		// outputDirectory.delete();

		System.out.println("Generating server stubs...");

		File output = new File(outputDirectory, iface.getFilename("Json"));
		output.getParentFile().mkdirs();

		FileOutputStream fileOutputStream = new FileOutputStream(output);
		try {
			final PrintWriter printWriter = new PrintWriter(fileOutputStream);

			iface.write(new ServerCodeGenRpcInterfaceWriter(printWriter));
			System.out.println(output.getName());

			printWriter.flush();
		} finally {
			IOUtils.closeQuietly(fileOutputStream);
		}
	}

	public static void main(String[] args) throws RpcParseException, ClassNotFoundException, FileNotFoundException {
		System.out.println("gwt-rpc-plus Thrift Compiler: http://code.google.com/p/gwt-rpc-plus/");
		System.out.println();

		// args = new String[] { "--server", "--output", "example/gen-json-server", "--idl",
		// "example/torturetest.thrift" };

		Options options = new Options();
		options.addOption("server", false, "Generate server code");
		options.addOption("client", false, "Generate client code");
		options.addOption("output", true, "Output directory");
		options.addOption("suffix", true, "Package suffix");
		options.addOption("idl", true, "IDL file");
		options.addOption("idldir", true, "IDL directory");
		options.addOption("include", true, "Include directories");
		options.getOption("include").setArgs(Option.UNLIMITED_VALUES);

		CommandLineParser parser = new PosixParser();
		CommandLine line;
		try {
			line = parser.parse(options, args);
		} catch (ParseException exp) {
			System.out.println(exp);
			doHelp(options);
			return;
		}

		if (!line.hasOption("client") && !line.hasOption("server")) {
			doHelp(options);
			return;
		}

		if (line.hasOption("idldir")) {
			File dir = new File(line.getOptionValue("idldir"));
			for (File file : dir.listFiles()) {
				if (!file.isDirectory() && file.getName().endsWith(".thrift") || file.getName().endsWith(".idl")) {
					compile(line, file.getAbsolutePath());
				}
			}
		} else {
			final String idl = line.getOptionValue("idl");
			compile(line, idl);
		}
	}

	private static void doHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(CodeGen.class.getName(), options, true);
	}

	private static void compile(CommandLine line, final String idl) throws RpcParseException, FileNotFoundException, ClassNotFoundException {
		CodeGen codeGen = new CodeGen();
		RpcTypeFactory typeFactory1 = new RpcTypeFactory(line.getOptionValue("suffix"));
		System.out.println("Parsing " + idl);

		final RpcThriftParser thriftParser = new RpcThriftParser(new FileInputStream(idl),
				line.getOptionValues("include") == null ? Collections.<String> emptyList() : Arrays.asList(line.getOptionValues("include")));

		File output = new File(line.getOptionValue("output"));
		final boolean client = line.hasOption("client");
		final boolean server = line.hasOption("server");

		for (RpcInterface iface1 : thriftParser.computeInterfaces(typeFactory1)) {
			if (client) {
				codeGen.generateGwtCode(output, iface1);
			}
			if (server) {
				codeGen.generateServerCode(output, iface1);
			}
		}
	}
}
