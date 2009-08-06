package com.dotspots.rpcplus.codegen.thrift;

import java.util.List;

public class StringUtils {
	public static String join(List<?> objects, String sep) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < objects.size(); i++) {
			if (i > 0) {
				builder.append(sep);
			}
			builder.append(objects.get(i));
		}

		return builder.toString();
	}
}
