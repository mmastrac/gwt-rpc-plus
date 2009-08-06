/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.serde2.dynamic_type;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DynamicSerDeFieldList extends DynamicSerDeSimpleNode implements Serializable {

	// private void writeObject(ObjectOutputStream out) throws IOException {
	// out.writeObject(types_by_column_name);
	// out.writeObject(ordered_types);
	// }

	// production: Field()*

	// mapping of the fieldid to the field
	private Map<Integer, DynamicSerDeTypeBase> types_by_id = null;
	private Map<String, DynamicSerDeTypeBase> types_by_column_name = null;
	private DynamicSerDeTypeBase ordered_types[] = null;

	private Map<String, Integer> ordered_column_id_by_name = null;

	public DynamicSerDeFieldList(int i) {
		super(i);
	}

	public DynamicSerDeFieldList(thrift_grammar p, int i) {
		super(p, i);
	}

	private DynamicSerDeField getField(int i) {
		return (DynamicSerDeField) this.jjtGetChild(i);
	}

	final public DynamicSerDeField[] getChildren() {
		int size = this.jjtGetNumChildren();
		DynamicSerDeField result[] = new DynamicSerDeField[size];
		for (int i = 0; i < size; i++) {
			result[i] = (DynamicSerDeField) this.jjtGetChild(i);
		}
		return result;
	}

	private int getNumFields() {
		return this.jjtGetNumChildren();
	}

	public void initialize() {
		if (types_by_id == null) {
			// multiple means of lookup
			types_by_id = new HashMap<Integer, DynamicSerDeTypeBase>();
			types_by_column_name = new HashMap<String, DynamicSerDeTypeBase>();
			ordered_types = new DynamicSerDeTypeBase[this.jjtGetNumChildren()];
			ordered_column_id_by_name = new HashMap<String, Integer>();

			// put them in and also roll them up while we're at it
			// a Field contains a FieldType which in turn contains a type
			for (int i = 0; i < this.jjtGetNumChildren(); i++) {
				DynamicSerDeField mt = this.getField(i);
				DynamicSerDeTypeBase type = mt.getFieldType().getMyType();
				// types get initialized in case they need to setup any
				// internal data structures - e.g., DynamicSerDeStructBase
				type.initialize();
				type.fieldid = mt.fieldid;
				type.name = mt.name;

				types_by_id.put(Integer.valueOf(mt.fieldid), type);
				types_by_column_name.put(mt.name, type);
				ordered_types[i] = type;
				ordered_column_id_by_name.put(mt.name, i);
			}
		}
	}

	private DynamicSerDeTypeBase getFieldByFieldId(int i) {
		return types_by_id.get(i);
	}

	protected DynamicSerDeTypeBase getFieldByName(String fieldname) {
		return types_by_column_name.get(fieldname);
	}

	/**
	 * Indicates whether fields can be out of order or missing. i.e., is it really real thrift serialization. This is
	 * used by dynamicserde to do some optimizations if it knows all the fields exist and are required and are
	 * serialized in order. For now, those optimizations are only done for DynamicSerDe serialized data so always set to
	 * false for now.
	 */
	protected boolean isRealThrift = false;

	protected boolean[] fieldsPresent;

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		String prefix = "";
		for (DynamicSerDeField t : this.getChildren()) {
			result.append(prefix + t.fieldid + ":" + t.getFieldType().getMyType().toString() + " " + t.name);
			prefix = ",";
		}
		return result.toString();
	}
}
