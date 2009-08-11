/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.dotspots.rpcplus.example.torturetest;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import org.apache.thrift.*;
import org.apache.thrift.meta_data.*;

import org.apache.thrift.protocol.*;
import org.apache.thrift.transport.*;

public class ContextOut implements TBase, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("ContextOut");
  private static final TField TIMING_FIELD_DESC = new TField("timing", TType.I32, (short)1);
  private static final TField DATA_FIELD_DESC = new TField("data", TType.STRING, (short)2);

  private int timing;
  public static final int TIMING = 1;
  private String data;
  public static final int DATA = 2;

  private final Isset __isset = new Isset();
  private static final class Isset implements java.io.Serializable {
    public boolean timing = false;
  }

  public static final Map<Integer, FieldMetaData> metaDataMap = Collections.unmodifiableMap(new HashMap<Integer, FieldMetaData>() {{
    put(TIMING, new FieldMetaData("timing", TFieldRequirementType.OPTIONAL, 
        new FieldValueMetaData(TType.I32)));
    put(DATA, new FieldMetaData("data", TFieldRequirementType.OPTIONAL, 
        new FieldValueMetaData(TType.STRING)));
  }});

  static {
    FieldMetaData.addStructMetaDataMap(ContextOut.class, metaDataMap);
  }

  public ContextOut() {
  }

  public ContextOut(
    int timing,
    String data)
  {
    this();
    this.timing = timing;
    this.__isset.timing = true;
    this.data = data;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ContextOut(ContextOut other) {
    __isset.timing = other.__isset.timing;
    this.timing = other.timing;
    if (other.isSetData()) {
      this.data = other.data;
    }
  }

  @Override
  public ContextOut clone() {
    return new ContextOut(this);
  }

  public int getTiming() {
    return this.timing;
  }

  public void setTiming(int timing) {
    this.timing = timing;
    this.__isset.timing = true;
  }

  public void unsetTiming() {
    this.__isset.timing = false;
  }

  // Returns true if field timing is set (has been asigned a value) and false otherwise
  public boolean isSetTiming() {
    return this.__isset.timing;
  }

  public String getData() {
    return this.data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public void unsetData() {
    this.data = null;
  }

  // Returns true if field data is set (has been asigned a value) and false otherwise
  public boolean isSetData() {
    return this.data != null;
  }

  public void setFieldValue(int fieldID, Object value) {
    switch (fieldID) {
    case TIMING:
      if (value == null) {
        unsetTiming();
      } else {
        setTiming((Integer)value);
      }
      break;

    case DATA:
      if (value == null) {
        unsetData();
      } else {
        setData((String)value);
      }
      break;

    default:
      throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
    }
  }

  public Object getFieldValue(int fieldID) {
    switch (fieldID) {
    case TIMING:
      return new Integer(getTiming());

    case DATA:
      return getData();

    default:
      throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
    }
  }

  // Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise
  public boolean isSet(int fieldID) {
    switch (fieldID) {
    case TIMING:
      return isSetTiming();
    case DATA:
      return isSetData();
    default:
      throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
    }
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ContextOut)
      return this.equals((ContextOut)that);
    return false;
  }

  public boolean equals(ContextOut that) {
    if (that == null)
      return false;

    boolean this_present_timing = true && this.isSetTiming();
    boolean that_present_timing = true && that.isSetTiming();
    if (this_present_timing || that_present_timing) {
      if (!(this_present_timing && that_present_timing))
        return false;
      if (this.timing != that.timing)
        return false;
    }

    boolean this_present_data = true && this.isSetData();
    boolean that_present_data = true && that.isSetData();
    if (this_present_data || that_present_data) {
      if (!(this_present_data && that_present_data))
        return false;
      if (!this.data.equals(that.data))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public void read(TProtocol iprot) throws TException {
    TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == TType.STOP) { 
        break;
      }
      switch (field.id)
      {
        case TIMING:
          if (field.type == TType.I32) {
            this.timing = iprot.readI32();
            this.__isset.timing = true;
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case DATA:
          if (field.type == TType.STRING) {
            this.data = iprot.readString();
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          TProtocolUtil.skip(iprot, field.type);
          break;
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();

    validate();
  }

  public void write(TProtocol oprot) throws TException {
    validate();

    oprot.writeStructBegin(STRUCT_DESC);
    if (isSetTiming()) {
      oprot.writeFieldBegin(TIMING_FIELD_DESC);
      oprot.writeI32(this.timing);
      oprot.writeFieldEnd();
    }
    if (this.data != null) {
      if (isSetData()) {
        oprot.writeFieldBegin(DATA_FIELD_DESC);
        oprot.writeString(this.data);
        oprot.writeFieldEnd();
      }
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ContextOut(");
    boolean first = true;

    if (isSetTiming()) {
      sb.append("timing:");
      sb.append(this.timing);
      first = false;
    }
    if (isSetData()) {
      if (!first) sb.append(", ");
      sb.append("data:");
      if (this.data == null) {
        sb.append("null");
      } else {
        sb.append(this.data);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
    // check that fields of type enum have valid values
  }

}
