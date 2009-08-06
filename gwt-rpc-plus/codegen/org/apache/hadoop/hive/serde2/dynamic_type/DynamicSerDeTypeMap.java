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

import java.util.Collections;
import java.util.Map;

import org.apache.thrift.protocol.TType;

public class DynamicSerDeTypeMap extends DynamicSerDeTypeBase {

	@Override
	public boolean isPrimitive() {
		return false;
	}

	@Override
	public boolean isMap() {
		return true;
	}

	// production is: Map<FieldType(),FieldType()>

	private final byte FD_KEYTYPE = 0;
	private final byte FD_VALUETYPE = 1;

	// returns Map<?,?>
	@Override
	public Class getRealType() {
		try {
			Class c = this.getKeyType().getRealType();
			Class c2 = this.getValueType().getRealType();
			Object o = c.newInstance();
			Object o2 = c2.newInstance();
			Map<?, ?> l = Collections.singletonMap(o, o2);
			return l.getClass();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public DynamicSerDeTypeMap(int i) {
		super(i);
	}

	public DynamicSerDeTypeMap(thrift_grammar p, int i) {
		super(p, i);
	}

	public DynamicSerDeTypeBase getKeyType() {
		return ((DynamicSerDeFieldType) this.jjtGetChild(FD_KEYTYPE)).getMyType();
	}

	public DynamicSerDeTypeBase getValueType() {
		return ((DynamicSerDeFieldType) this.jjtGetChild(FD_VALUETYPE)).getMyType();
	}

	@Override
	public String toString() {
		return "map<" + this.getKeyType().toString() + "," + this.getValueType().toString() + ">";
	}

	@Override
	public byte getType() {
		return TType.MAP;
	}
};
