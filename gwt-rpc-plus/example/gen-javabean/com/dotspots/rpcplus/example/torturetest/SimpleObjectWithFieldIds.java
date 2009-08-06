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

public class SimpleObjectWithFieldIds implements TBase, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("SimpleObjectWithFieldIds");
  private static final TField TOKEN_FIELD_DESC = new TField("token", TType.STRING, (short)1);
  private static final TField USER_ID_FIELD_DESC = new TField("userId", TType.I32, (short)2);

  private String token;
  public static final int TOKEN = 1;
  private int userId;
  public static final int USERID = 2;

  private final Isset __isset = new Isset();
  private static final class Isset implements java.io.Serializable {
    public boolean userId = false;
  }

  public static final Map<Integer, FieldMetaData> metaDataMap = Collections.unmodifiableMap(new HashMap<Integer, FieldMetaData>() {{
    put(TOKEN, new FieldMetaData("token", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.STRING)));
    put(USERID, new FieldMetaData("userId", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.I32)));
  }});

  static {
    FieldMetaData.addStructMetaDataMap(SimpleObjectWithFieldIds.class, metaDataMap);
  }

  public SimpleObjectWithFieldIds() {
  }

  public SimpleObjectWithFieldIds(
    String token,
    int userId)
  {
    this();
    this.token = token;
    this.userId = userId;
    this.__isset.userId = true;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SimpleObjectWithFieldIds(SimpleObjectWithFieldIds other) {
    if (other.isSetToken()) {
      this.token = other.token;
    }
    __isset.userId = other.__isset.userId;
    this.userId = other.userId;
  }

  @Override
  public SimpleObjectWithFieldIds clone() {
    return new SimpleObjectWithFieldIds(this);
  }

  public String getToken() {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void unsetToken() {
    this.token = null;
  }

  // Returns true if field token is set (has been asigned a value) and false otherwise
  public boolean isSetToken() {
    return this.token != null;
  }

  public int getUserId() {
    return this.userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
    this.__isset.userId = true;
  }

  public void unsetUserId() {
    this.__isset.userId = false;
  }

  // Returns true if field userId is set (has been asigned a value) and false otherwise
  public boolean isSetUserId() {
    return this.__isset.userId;
  }

  public void setFieldValue(int fieldID, Object value) {
    switch (fieldID) {
    case TOKEN:
      if (value == null) {
        unsetToken();
      } else {
        setToken((String)value);
      }
      break;

    case USERID:
      if (value == null) {
        unsetUserId();
      } else {
        setUserId((Integer)value);
      }
      break;

    default:
      throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
    }
  }

  public Object getFieldValue(int fieldID) {
    switch (fieldID) {
    case TOKEN:
      return getToken();

    case USERID:
      return new Integer(getUserId());

    default:
      throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
    }
  }

  // Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise
  public boolean isSet(int fieldID) {
    switch (fieldID) {
    case TOKEN:
      return isSetToken();
    case USERID:
      return isSetUserId();
    default:
      throw new IllegalArgumentException("Field " + fieldID + " doesn't exist!");
    }
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SimpleObjectWithFieldIds)
      return this.equals((SimpleObjectWithFieldIds)that);
    return false;
  }

  public boolean equals(SimpleObjectWithFieldIds that) {
    if (that == null)
      return false;

    boolean this_present_token = true && this.isSetToken();
    boolean that_present_token = true && that.isSetToken();
    if (this_present_token || that_present_token) {
      if (!(this_present_token && that_present_token))
        return false;
      if (!this.token.equals(that.token))
        return false;
    }

    boolean this_present_userId = true;
    boolean that_present_userId = true;
    if (this_present_userId || that_present_userId) {
      if (!(this_present_userId && that_present_userId))
        return false;
      if (this.userId != that.userId)
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
        case TOKEN:
          if (field.type == TType.STRING) {
            this.token = iprot.readString();
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case USERID:
          if (field.type == TType.I32) {
            this.userId = iprot.readI32();
            this.__isset.userId = true;
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
    if (this.token != null) {
      oprot.writeFieldBegin(TOKEN_FIELD_DESC);
      oprot.writeString(this.token);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(USER_ID_FIELD_DESC);
    oprot.writeI32(this.userId);
    oprot.writeFieldEnd();
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("SimpleObjectWithFieldIds(");
    boolean first = true;

    sb.append("token:");
    if (this.token == null) {
      sb.append("null");
    } else {
      sb.append(this.token);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("userId:");
    sb.append(this.userId);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
    // check that fields of type enum have valid values
  }

}

