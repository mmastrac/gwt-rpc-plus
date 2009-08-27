package com.dotspots.rpcplus.test.client;

import junit.framework.Assert;

public class EveryCharacterStringUtility {
	// Tweakable constants for easier debugging
	private static final int START_CHAR = Character.MIN_VALUE;
	private static final int END_CHAR = Character.MAX_VALUE;

	/**
	 * Gets a string composed of every valid character (not codepoints, however).
	 */
	public static String getAllCharacterString() {
		final StringBuilder stringBuilder = new StringBuilder();
		for (int i = START_CHAR; i <= END_CHAR; i++) {
			stringBuilder.append((char) i);
		}

		return stringBuilder.toString();
	}

	public static void checkString(String comparison) {
		for (int i = 0; i < comparison.length(); i++) {
			final int expected = i + START_CHAR;
			final int actual = comparison.charAt(i);
			if (actual != expected) {
				Assert.fail("Comparison failed at index " + i + ": character " + charToHex(expected) + " was " + charToHex(actual)
						+ " instead.  Length of comparsion was " + comparison.length());
			}
		}

		if (comparison.length() != END_CHAR - START_CHAR + 1) {
			Assert.fail("Comparison was truncated at index " + comparison.length());
		}
	}

	private static String charToHex(int val) {
		return val + " (0x" + Integer.toString(val, 16) + " '" + (char) val + "')";
	}
}
