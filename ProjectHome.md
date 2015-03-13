# gwt-rpc-plus #

gwt-rpc-plus is a set of building blocks for enhancing and replacing GWT's built-in RPC framework.

### JavaScript Collections ###

gwt-rpc-plus includes a set of bare-metal, strongly-typed JS collections optimized for both RPC and client-side use.  The collection code is generated for each combination of key/value type.

The collections also include a GNU Trove-inspired set of procedures to iterate over the keys, values and entries of all the collections:

```
    JsRpcMapStringString map = JsRpcMapStringString.create();
    map.set("foo", "bar");
    map.set("baz", "blah");

    StringBuilder builder;
    map.forEachKey(new JsRpcStringProcedure() {
        public boolean execute(String value) {
            builder.append(value);
            return true;
        }
    });
```

### Text and JSON Transports ###

gwt-rpc-plus includes a framework for writing generic text and JSON transports. It also includes an implementation of a window.name transport for cross-domain RPC (see [here](http://development.lombardi.com/?p=611) and [here](http://timepedia.blogspot.com/2008/07/cross-domain-formpanel-submissions-in.html) for more info).

To get started, just inherit the FlexibleRPC module. This will replace GWT's default remote service proxies with more flexible, pluggable versions.

```
	<inherits name="com.dotspots.rpcplus.FlexibleRPC" />
```

You can then define your own TransportFactory and start plugging in new transports. Alternatively, you can use the CrossDomainTransportFactory to plug in a transport factory pre-configured to make cross-domain requests:

```
	<replace-with class="com.dotspots.rpcplus.client.transport.impl.CrossDomainTransportFactory">
		<when-type-is class="com.dotspots.rpcplus.client.transport.TransportFactory" />
	</replace-with>
```

### Safe Native JSON Parsing ###

gwt-rpc-plus includes drivers for parsing and emitting JSON in all major browsers, using native methods where possible.

```
    JsonEncoder = new JSONFactory();
    JsRpcListLong list = JsRpcListLong.create();
    list.add(0);
    list.add(0xffff0000ffff0000L);
    String text = encoder.encode(list);
```

### Custom Transports for GWT RPC ###

gwt-rpc-plus allows you to replace the default transport in GWT RPC with any transport capable of transmitting and receiving text.  It includes an implementation of cross-domain GWT RPC.

### Thrift RPC ###

gwt-rpc-plus also includes an entirely new RPC framework, built on [Apache Thrift](http://incubator.apache.org/thrift/) and the above blocks.  Thrift IDL files are used to generate client and server stubs that pass versioned JSON back and forth that can be directly deserialized, manipulated and serialized on the client.

## Future plans ##

  * Cross-module communication
  * Strongly typed communication with worker threads
  * More transport types (Gadget/widget transports, etc)