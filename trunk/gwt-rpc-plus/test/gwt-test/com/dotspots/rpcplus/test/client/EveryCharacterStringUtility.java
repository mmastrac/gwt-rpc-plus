package com.dotspots.rpcplus.test.client;

import junit.framework.Assert;

public class EveryCharacterStringUtility {
	// Tweakable constants for easier debugging
	private static final int START_CHAR = Character.MIN_VALUE;
	private static final int END_CHAR = Character.MAX_VALUE;

	private static final String ALL_CHARACTER_STRING;

	static {
		final StringBuilder stringBuilder = new StringBuilder();
		for (int i = START_CHAR; i <= END_CHAR; i++) {
			if (i < Character.MIN_SURROGATE || i > Character.MAX_SURROGATE) {
				stringBuilder.append((char) i);
			}
		}

		ALL_CHARACTER_STRING = stringBuilder.toString();
	}

	/**
	 * Gets a string composed of every valid character (not codepoints, however).
	 */
	public static String getAllCharacterString() {
		return ALL_CHARACTER_STRING;
	}

	public static void checkString(String comparison) {
		int index = 0;
		for (int i = START_CHAR; i <= END_CHAR; i++) {
			if (i < Character.MIN_SURROGATE || i > Character.MAX_SURROGATE) {
				final int actual = comparison.charAt(index);
				if (actual != i) {
					Assert.fail("Comparison failed at index " + index + ": character " + charToHex(i) + " was " + charToHex(actual)
							+ " instead.  Length of comparsion was " + comparison.length());
				}

				index++;
			}
		}

		if (comparison.length() != ALL_CHARACTER_STRING.length()) {
			Assert.fail("Comparison was truncated at index " + comparison.length());
		}
	}

	private static String charToHex(int val) {
		return val + " (0x" + Integer.toString(val, 16) + " '" + (char) val + "')";
	}

	public static void main(String[] args) {
		checkString(getAllCharacterString());
	}
}
