package com.dotspots.rpcplus.codegen.thrift;

import java.io.IOException;
import java.io.OutputStream;

public class IOUtils {
	public static void closeQuietly(OutputStream stm) {
		try {
			stm.close();
		} catch (IOException e) {
		}
	}
}
