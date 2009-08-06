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

import org.apache.thrift.protocol.TType;

public class DynamicSerDeTypeList extends DynamicSerDeTypeBase {

	@Override
	public boolean isPrimitive() {
		return false;
	}

	@Override
	public boolean isList() {
		return true;
	}

	// production is: list<FieldType()>

	static final private int FD_TYPE = 0;

	@Override
	public Class getRealType() {
		return java.util.ArrayList.class;
	}

	public DynamicSerDeTypeList(int i) {
		super(i);
	}

	public DynamicSerDeTypeList(thrift_grammar p, int i) {
		super(p, i);
	}

	public DynamicSerDeTypeBase getElementType() {
		return ((DynamicSerDeFieldType) this.jjtGetChild(FD_TYPE)).getMyType();
	}

	@Override
	public String toString() {
		return "list<" + this.getElementType().toString() + ">";
	}

	@Override
	public byte getType() {
		return TType.LIST;
	}

}
