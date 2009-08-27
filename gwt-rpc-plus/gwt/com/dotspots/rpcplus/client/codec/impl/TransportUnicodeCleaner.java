package com.dotspots.rpcplus.client.codec.impl;

class TransportUnicodeCleaner {
	public static native String cleanUnicodeForTransport(String input) /*-{
		return input.replace(/[\u0300-\uffff]/g, function(x) {
		var charCode = x.charCodeAt();
		return charCode < 0x1000 ? ('\\u0' + charCode.toString(16)) : ('\\u' + charCode.toString(16));
		});
	}-*/;
}
