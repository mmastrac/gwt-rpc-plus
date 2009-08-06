package com.dotspots.rpcplus.codegen.thrift;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hive.serde2.dynamic_type.DynamicSerDeField;

/**
 * Represents a struct (or exception type) in a thrift IDL file.
 */
public class RpcStruct implements Comparable<RpcStruct> {
	private HashSet<RpcStruct> types = new HashSet<RpcStruct>();
	private HashMap<Integer, RpcField> fields = new HashMap<Integer, RpcField>();
	private boolean isList = true;
	private final RpcTypeFactory typeFactory;
	private boolean isException;
	private final String name;
	private final String namespace;

	public RpcStruct(RpcTypeFactory typeFactory, String name, String namespace, boolean isException, DynamicSerDeField[] fieldList)
			throws RpcParseException {
		this.typeFactory = typeFactory;
		this.name = name;
		this.namespace = namespace;
		this.isException = isException;

		int order = 0;

		if (fieldList != null) {
			boolean hasReturnValue = false;
			for (DynamicSerDeField field : fieldList) {
				if (field.getFieldValue() == 0) {
					hasReturnValue = true;
				}
			}

			for (DynamicSerDeField entry : fieldList) {
				final RpcTypeBase fieldType = typeFactory.get(entry.getFieldType().getMyType());
				types.addAll(fieldType.getSerializationTypes());

				// The key values are > 0 for normal args and < 0 for
				// auto-numbered args. If the key == 0, this is a special return
				// value key.

				// We renumber args if there is no return value to avoid an
				// empty space at the start of every list
				final int key = entry.getFieldValue();
				final int realKey = (hasReturnValue || key < 0) ? key : key - 1;
				final int declaredOrder = order++;
				final RpcField field = new RpcField(realKey, declaredOrder, entry.getName(), fieldType);

				fields.put(key, field);

				// We don't support list mode if there are any keys < 0
				if (key < 0) {
					isList = false;
				}
			}

			types.add(this);
		}
	}

	public RpcStruct(RpcTypeFactory typeFactory, String name, String namespace, boolean isException, RpcField[] fieldList)
			throws RpcParseException {
		this.typeFactory = typeFactory;
		this.name = name;
		this.namespace = namespace;
		this.isException = isException;

		if (fieldList != null) {
			for (RpcField entry : fieldList) {
				final RpcTypeBase fieldType = entry.getType();
				types.addAll(fieldType.getSerializationTypes());
				fields.put(entry.getKey(), entry);

				// We don't support list mode if there are any keys < 0
				if (entry.getKey() < 0) {
					isList = false;
				}
			}

			types.add(this);
		}
	}

	public RpcTypeFactory getTypeFactory() {
		return typeFactory;
	}

	public Collection<RpcStruct> getSerializationTypes() {
		return types;
	}

	public Map<Integer, RpcField> getFields() {
		return fields;
	}

	public List<RpcField> getOrderedFields() {
		ArrayList<RpcField> fields = new ArrayList<RpcField>(getFields().values());
		Collections.sort(fields, new Comparator<RpcField>() {
			public int compare(RpcField o1, RpcField o2) {
				return o1.getKey() - o2.getKey();
			}
		});

		return fields;
	}

	public List<RpcField> getDeclaredOrderedFields() {
		ArrayList<RpcField> fields = new ArrayList<RpcField>(getFields().values());
		Collections.sort(fields, new Comparator<RpcField>() {
			public int compare(RpcField o1, RpcField o2) {
				return o1.getDeclaredOrder() - o2.getDeclaredOrder();
			}
		});

		return fields;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof RpcStruct) && (((RpcStruct) obj).getFullyQualifiedClassName().equals(getFullyQualifiedClassName()));
	}

	@Override
	public int hashCode() {
		return getFullyQualifiedClassName().hashCode();
	}

	public void write(RpcObjectWriter rpcObjectWriter) {
		rpcObjectWriter.writePackage(getPackageName());

		rpcObjectWriter.startClass(this, getClassName(false));

		rpcObjectWriter.writeConstructor(this);

		for (RpcField field : getDeclaredOrderedFields()) {
			rpcObjectWriter.writeGetter(this, field);
			rpcObjectWriter.writeSetter(this, field);
			rpcObjectWriter.writeIsSet(this, field);
			rpcObjectWriter.writeUnset(this, field);
		}

		rpcObjectWriter.endClass(this, getClassName(false));
	}

	public String getClassName(boolean useNestedTypes) {
		if (useNestedTypes) {
			return name.replace('$', '.');
		}

		return name.replace('$', '_');
	}

	public String getPackageName() {
		return namespace + typeFactory.getSuffixPackage();
	}

	public String getClassName() {
		return name;
	}

	public String getFullyQualifiedClassName() {
		return getPackageName() + "." + getClassName();
	}

	@Override
	public String toString() {
		return getFullyQualifiedClassName();
	}

	public boolean isList() {
		return isList;
	}

	public boolean isException() {
		return isException;
	}

	public String getFilename() {
		return getFullyQualifiedClassName().replace('.', '/').replace('$', '_') + ".java";
	}

	public int compareTo(RpcStruct o) {
		return getFullyQualifiedClassName().compareTo(o.getFullyQualifiedClassName());
	}
}
