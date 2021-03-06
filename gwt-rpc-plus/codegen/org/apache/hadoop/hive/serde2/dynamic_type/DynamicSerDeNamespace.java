/* Generated By:JJTree: Do not edit this line. DynamicSerDeNamespace.java */

package org.apache.hadoop.hive.serde2.dynamic_type;

public class DynamicSerDeNamespace extends SimpleNode {
	protected String lang;
	protected String namespace;

	public DynamicSerDeNamespace(int id) {
		super(id);
	}

	public DynamicSerDeNamespace(thrift_grammar p, int id) {
		super(p, id);
	}

	public String getLang() {
		return lang;
	}

	public String getNamespace() {
		return namespace;
	}

	@Override
	public String toString() {
		return "namespace " + lang + " " + namespace;
	}

}
