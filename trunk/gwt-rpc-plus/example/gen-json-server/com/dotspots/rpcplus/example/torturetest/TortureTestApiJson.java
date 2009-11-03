package com.dotspots.rpcplus.example.torturetest;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocolUtil;
import org.apache.thrift.protocol.TField;
import org.apache.thrift.protocol.TType;
import org.apache.thrift.protocol.TProtocol;

import com.dotspots.rpcplus.jsonrpc.thrift.*;

@SuppressWarnings("unused")
public final class TortureTestApiJson implements JSONServlet {
    com.dotspots.rpcplus.example.torturetest.TortureTestApi.Iface service;

    public void processRequest(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {
        in.readListBegin();
        in.hasNext();
        int version = in.readI32();
        in.hasNext();
        String call = in.readString();
        in.hasNext();
        // Read request context
        service.__setContext(readContextIn(in));
        in.hasNext();
        if (call.equals("testPassthru")) {
            testPassthru(in, out);
            return;
        }
        if (call.equals("testThrowsAnException")) {
            testThrowsAnException(in, out);
            return;
        }
        if (call.equals("testThrowsAnUnpositionedException")) {
            testThrowsAnUnpositionedException(in, out);
            return;
        }
        if (call.equals("testDeclaresAnException")) {
            testDeclaresAnException(in, out);
            return;
        }
        if (call.equals("testThrowsTwoExceptions")) {
            testThrowsTwoExceptions(in, out);
            return;
        }
        if (call.equals("testExceptionPassthru")) {
            testExceptionPassthru(in, out);
            return;
        }
        if (call.equals("testPositionalArguments")) {
            testPositionalArguments(in, out);
            return;
        }
        if (call.equals("testSetString")) {
            testSetString(in, out);
            return;
        }
        if (call.equals("testSetInt")) {
            testSetInt(in, out);
            return;
        }
        if (call.equals("testMapStringString")) {
            testMapStringString(in, out);
            return;
        }
        if (call.equals("methodReturningAnObject")) {
            methodReturningAnObject(in, out);
            return;
        }
        if (call.equals("methodReturningAnObject2")) {
            methodReturningAnObject2(in, out);
            return;
        }
        if (call.equals("methodReturningAnObject3")) {
            methodReturningAnObject3(in, out);
            return;
        }
        if (call.equals("methodReturningAnObject4")) {
            methodReturningAnObject4(in, out);
            return;
        }
        if (call.equals("methodReturningAnObject5")) {
            methodReturningAnObject5(in, out);
            return;
        }
        if (call.equals("testBinary")) {
            testBinary(in, out);
            return;
        }
    }

    public void setService(com.dotspots.rpcplus.example.torturetest.TortureTestApi.Iface service) {
        this.service = service;
    }

    private final void testPassthru(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {
        TortureTestApi.testPassthru_args args = readTortureTestApi_testPassthru_args(in);
        TortureTestApi.testPassthru_result result = new TortureTestApi.testPassthru_result();
        try {
            String methodResult = service.testPassthru(args.getArg());
            result.setSuccess(methodResult);
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(0); // Success
            writeTortureTestApi_testPassthru_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (Throwable t) {
            // Log unexpected exceptions
            java.util.logging.Logger.getLogger("com.dotspots.rpcplus.example.torturetest.TortureTestApi").log(java.util.logging.Level.SEVERE, "Unexpected error", t);
        }
    };

    private final void testThrowsAnException(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {
        TortureTestApi.testThrowsAnException_args args = readTortureTestApi_testThrowsAnException_args(in);
        TortureTestApi.testThrowsAnException_result result = new TortureTestApi.testThrowsAnException_result();
        try {
            String methodResult = service.testThrowsAnException();
            result.setSuccess(methodResult);
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(0); // Success
            writeTortureTestApi_testThrowsAnException_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (com.dotspots.rpcplus.example.torturetest.SimpleException e) {
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(1); // Failure
            result.setFieldValue(1, e);
            writeTortureTestApi_testThrowsAnException_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (Throwable t) {
            // Log unexpected exceptions
            java.util.logging.Logger.getLogger("com.dotspots.rpcplus.example.torturetest.TortureTestApi").log(java.util.logging.Level.SEVERE, "Unexpected error", t);
        }
    };

    private final void testThrowsAnUnpositionedException(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {
        TortureTestApi.testThrowsAnUnpositionedException_args args = readTortureTestApi_testThrowsAnUnpositionedException_args(in);
        TortureTestApi.testThrowsAnUnpositionedException_result result = new TortureTestApi.testThrowsAnUnpositionedException_result();
        try {
            String methodResult = service.testThrowsAnUnpositionedException();
            result.setSuccess(methodResult);
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(0); // Success
            writeTortureTestApi_testThrowsAnUnpositionedException_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (com.dotspots.rpcplus.example.torturetest.SimpleException e) {
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(1); // Failure
            result.setFieldValue(1, e);
            writeTortureTestApi_testThrowsAnUnpositionedException_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (Throwable t) {
            // Log unexpected exceptions
            java.util.logging.Logger.getLogger("com.dotspots.rpcplus.example.torturetest.TortureTestApi").log(java.util.logging.Level.SEVERE, "Unexpected error", t);
        }
    };

    private final void testDeclaresAnException(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {
        TortureTestApi.testDeclaresAnException_args args = readTortureTestApi_testDeclaresAnException_args(in);
        TortureTestApi.testDeclaresAnException_result result = new TortureTestApi.testDeclaresAnException_result();
        try {
            String methodResult = service.testDeclaresAnException();
            result.setSuccess(methodResult);
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(0); // Success
            writeTortureTestApi_testDeclaresAnException_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (com.dotspots.rpcplus.example.torturetest.SimpleException e) {
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(1); // Failure
            result.setFieldValue(1, e);
            writeTortureTestApi_testDeclaresAnException_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (Throwable t) {
            // Log unexpected exceptions
            java.util.logging.Logger.getLogger("com.dotspots.rpcplus.example.torturetest.TortureTestApi").log(java.util.logging.Level.SEVERE, "Unexpected error", t);
        }
    };

    private final void testThrowsTwoExceptions(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {
        TortureTestApi.testThrowsTwoExceptions_args args = readTortureTestApi_testThrowsTwoExceptions_args(in);
        TortureTestApi.testThrowsTwoExceptions_result result = new TortureTestApi.testThrowsTwoExceptions_result();
        try {
            String methodResult = service.testThrowsTwoExceptions();
            result.setSuccess(methodResult);
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(0); // Success
            writeTortureTestApi_testThrowsTwoExceptions_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (com.dotspots.rpcplus.example.torturetest.SimpleException e) {
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(1); // Failure
            result.setFieldValue(1, e);
            writeTortureTestApi_testThrowsTwoExceptions_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (com.dotspots.rpcplus.example.torturetest.MoreComplexException e) {
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(2); // Failure
            result.setFieldValue(2, e);
            writeTortureTestApi_testThrowsTwoExceptions_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (Throwable t) {
            // Log unexpected exceptions
            java.util.logging.Logger.getLogger("com.dotspots.rpcplus.example.torturetest.TortureTestApi").log(java.util.logging.Level.SEVERE, "Unexpected error", t);
        }
    };

    private final void testExceptionPassthru(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {
        TortureTestApi.testExceptionPassthru_args args = readTortureTestApi_testExceptionPassthru_args(in);
        TortureTestApi.testExceptionPassthru_result result = new TortureTestApi.testExceptionPassthru_result();
        try {
            com.dotspots.rpcplus.example.torturetest.SimpleException methodResult = service.testExceptionPassthru(args.getEx());
            result.setSuccess(methodResult);
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(0); // Success
            writeTortureTestApi_testExceptionPassthru_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (Throwable t) {
            // Log unexpected exceptions
            java.util.logging.Logger.getLogger("com.dotspots.rpcplus.example.torturetest.TortureTestApi").log(java.util.logging.Level.SEVERE, "Unexpected error", t);
        }
    };

    private final void testPositionalArguments(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {
        TortureTestApi.testPositionalArguments_args args = readTortureTestApi_testPositionalArguments_args(in);
        TortureTestApi.testPositionalArguments_result result = new TortureTestApi.testPositionalArguments_result();
        try {
            String methodResult = service.testPositionalArguments(args.getInt32(), args.getStr());
            result.setSuccess(methodResult);
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(0); // Success
            writeTortureTestApi_testPositionalArguments_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (Throwable t) {
            // Log unexpected exceptions
            java.util.logging.Logger.getLogger("com.dotspots.rpcplus.example.torturetest.TortureTestApi").log(java.util.logging.Level.SEVERE, "Unexpected error", t);
        }
    };

    private final void testSetString(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {
        TortureTestApi.testSetString_args args = readTortureTestApi_testSetString_args(in);
        TortureTestApi.testSetString_result result = new TortureTestApi.testSetString_result();
        try {
            Set<String> methodResult = service.testSetString();
            result.setSuccess(methodResult);
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(0); // Success
            writeTortureTestApi_testSetString_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (Throwable t) {
            // Log unexpected exceptions
            java.util.logging.Logger.getLogger("com.dotspots.rpcplus.example.torturetest.TortureTestApi").log(java.util.logging.Level.SEVERE, "Unexpected error", t);
        }
    };

    private final void testSetInt(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {
        TortureTestApi.testSetInt_args args = readTortureTestApi_testSetInt_args(in);
        TortureTestApi.testSetInt_result result = new TortureTestApi.testSetInt_result();
        try {
            Set<Integer> methodResult = service.testSetInt();
            result.setSuccess(methodResult);
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(0); // Success
            writeTortureTestApi_testSetInt_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (Throwable t) {
            // Log unexpected exceptions
            java.util.logging.Logger.getLogger("com.dotspots.rpcplus.example.torturetest.TortureTestApi").log(java.util.logging.Level.SEVERE, "Unexpected error", t);
        }
    };

    private final void testMapStringString(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {
        TortureTestApi.testMapStringString_args args = readTortureTestApi_testMapStringString_args(in);
        TortureTestApi.testMapStringString_result result = new TortureTestApi.testMapStringString_result();
        try {
            Map<String, String> methodResult = service.testMapStringString();
            result.setSuccess(methodResult);
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(0); // Success
            writeTortureTestApi_testMapStringString_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (Throwable t) {
            // Log unexpected exceptions
            java.util.logging.Logger.getLogger("com.dotspots.rpcplus.example.torturetest.TortureTestApi").log(java.util.logging.Level.SEVERE, "Unexpected error", t);
        }
    };

    private final void methodReturningAnObject(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {
        TortureTestApi.methodReturningAnObject_args args = readTortureTestApi_methodReturningAnObject_args(in);
        TortureTestApi.methodReturningAnObject_result result = new TortureTestApi.methodReturningAnObject_result();
        try {
            com.dotspots.rpcplus.example.torturetest.ObjectThatReferencesAnother methodResult = service.methodReturningAnObject();
            result.setSuccess(methodResult);
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(0); // Success
            writeTortureTestApi_methodReturningAnObject_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (Throwable t) {
            // Log unexpected exceptions
            java.util.logging.Logger.getLogger("com.dotspots.rpcplus.example.torturetest.TortureTestApi").log(java.util.logging.Level.SEVERE, "Unexpected error", t);
        }
    };

    private final void methodReturningAnObject2(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {
        TortureTestApi.methodReturningAnObject2_args args = readTortureTestApi_methodReturningAnObject2_args(in);
        TortureTestApi.methodReturningAnObject2_result result = new TortureTestApi.methodReturningAnObject2_result();
        try {
            com.dotspots.rpcplus.example.torturetest.SimpleObjectWithFieldIds methodResult = service.methodReturningAnObject2();
            result.setSuccess(methodResult);
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(0); // Success
            writeTortureTestApi_methodReturningAnObject2_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (Throwable t) {
            // Log unexpected exceptions
            java.util.logging.Logger.getLogger("com.dotspots.rpcplus.example.torturetest.TortureTestApi").log(java.util.logging.Level.SEVERE, "Unexpected error", t);
        }
    };

    private final void methodReturningAnObject3(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {
        TortureTestApi.methodReturningAnObject3_args args = readTortureTestApi_methodReturningAnObject3_args(in);
        TortureTestApi.methodReturningAnObject3_result result = new TortureTestApi.methodReturningAnObject3_result();
        try {
            com.dotspots.rpcplus.example.torturetest.SimpleObjectWithNoFieldIds methodResult = service.methodReturningAnObject3();
            result.setSuccess(methodResult);
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(0); // Success
            writeTortureTestApi_methodReturningAnObject3_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (Throwable t) {
            // Log unexpected exceptions
            java.util.logging.Logger.getLogger("com.dotspots.rpcplus.example.torturetest.TortureTestApi").log(java.util.logging.Level.SEVERE, "Unexpected error", t);
        }
    };

    private final void methodReturningAnObject4(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {
        TortureTestApi.methodReturningAnObject4_args args = readTortureTestApi_methodReturningAnObject4_args(in);
        TortureTestApi.methodReturningAnObject4_result result = new TortureTestApi.methodReturningAnObject4_result();
        try {
            com.dotspots.rpcplus.example.torturetest.ObjectWithComplexTypes methodResult = service.methodReturningAnObject4();
            result.setSuccess(methodResult);
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(0); // Success
            writeTortureTestApi_methodReturningAnObject4_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (Throwable t) {
            // Log unexpected exceptions
            java.util.logging.Logger.getLogger("com.dotspots.rpcplus.example.torturetest.TortureTestApi").log(java.util.logging.Level.SEVERE, "Unexpected error", t);
        }
    };

    private final void methodReturningAnObject5(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {
        TortureTestApi.methodReturningAnObject5_args args = readTortureTestApi_methodReturningAnObject5_args(in);
        TortureTestApi.methodReturningAnObject5_result result = new TortureTestApi.methodReturningAnObject5_result();
        try {
            com.dotspots.rpcplus.example.torturetest.ObjectWithEnum methodResult = service.methodReturningAnObject5(args.getArg());
            result.setSuccess(methodResult);
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(0); // Success
            writeTortureTestApi_methodReturningAnObject5_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (Throwable t) {
            // Log unexpected exceptions
            java.util.logging.Logger.getLogger("com.dotspots.rpcplus.example.torturetest.TortureTestApi").log(java.util.logging.Level.SEVERE, "Unexpected error", t);
        }
    };

    private final void testBinary(TBaseJSONProtocol in, TJSONProtocolWriter out) throws TException {
        TortureTestApi.testBinary_args args = readTortureTestApi_testBinary_args(in);
        TortureTestApi.testBinary_result result = new TortureTestApi.testBinary_result();
        try {
            byte[] methodResult = service.testBinary(args.getBinaryValue());
            result.setSuccess(methodResult);
            out.writeListBegin(null);
            out.writeI32(0); // Version
            out.writeI32(0); // Success
            writeTortureTestApi_testBinary_result(out, result);
            ContextOut responseContext = service.__getContext();
            if (responseContext != null) {
                writeContextOut(out, responseContext);
            }
            out.writeListEnd();
        } catch (Throwable t) {
            // Log unexpected exceptions
            java.util.logging.Logger.getLogger("com.dotspots.rpcplus.example.torturetest.TortureTestApi").log(java.util.logging.Level.SEVERE, "Unexpected error", t);
        }
    };

    public static final com.dotspots.rpcplus.example.torturetest.ContextIn readContextIn(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.ContextIn obj = new com.dotspots.rpcplus.example.torturetest.ContextIn();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 String value0 = protocol.readString();
                     obj.setToken(value0);
                     break;
                 }
                 case 1: {
                 String value0 = protocol.readString();
                     obj.setData(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeContextIn(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.ContextIn obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetToken()) {
            protocol.writeI32(0);
            String value0 = obj.getToken();
            protocol.writeString(value0);
        }
        if (obj.isSetData()) {
            protocol.writeI32(1);
            String value0 = obj.getData();
            protocol.writeString(value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.MoreComplexException readMoreComplexException(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.MoreComplexException obj = new com.dotspots.rpcplus.example.torturetest.MoreComplexException();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 String value0 = protocol.readString();
                     obj.setMessage(value0);
                     break;
                 }
                 case 1: {
                 com.dotspots.rpcplus.example.torturetest.ObjectWithComplexTypes value0 = readObjectWithComplexTypes(protocol);
                     obj.setData(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeMoreComplexException(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.MoreComplexException obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetMessage()) {
            protocol.writeI32(0);
            String value0 = obj.getMessage();
            protocol.writeString(value0);
        }
        if (obj.isSetData()) {
            protocol.writeI32(1);
            com.dotspots.rpcplus.example.torturetest.ObjectWithComplexTypes value0 = obj.getData();
            writeObjectWithComplexTypes(protocol, value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.ObjectThatIsReferenced readObjectThatIsReferenced(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.ObjectThatIsReferenced obj = new com.dotspots.rpcplus.example.torturetest.ObjectThatIsReferenced();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 int value0 = protocol.readI32();
                     obj.setId(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeObjectThatIsReferenced(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.ObjectThatIsReferenced obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetId()) {
            protocol.writeI32(0);
            int value0 = obj.getId();
            protocol.writeI32(value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsAnUnpositionedException_result readTortureTestApi_testThrowsAnUnpositionedException_result(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsAnUnpositionedException_result obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsAnUnpositionedException_result();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 String value0 = protocol.readString();
                     obj.setSuccess(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testThrowsAnUnpositionedException_result(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsAnUnpositionedException_result obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetSuccess()) {
            protocol.writeI32(0);
            String value0 = obj.getSuccess();
            protocol.writeString(value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testExceptionPassthru_args readTortureTestApi_testExceptionPassthru_args(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testExceptionPassthru_args obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testExceptionPassthru_args();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 com.dotspots.rpcplus.example.torturetest.SimpleException value0 = readSimpleException(protocol);
                     obj.setEx(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testExceptionPassthru_args(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testExceptionPassthru_args obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetEx()) {
            protocol.writeI32(0);
            com.dotspots.rpcplus.example.torturetest.SimpleException value0 = obj.getEx();
            writeSimpleException(protocol, value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testSetString_result readTortureTestApi_testSetString_result(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testSetString_result obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testSetString_result();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 Set<String> value0 = new HashSet<String>();
                 protocol.readSetBegin();
                 while (protocol.hasNext()) {
                     String value1 = protocol.readString();
                     value0.add(value1);
                 }
                     obj.setSuccess(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testSetString_result(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testSetString_result obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetSuccess()) {
            protocol.writeI32(0);
            Set<String> value0 = obj.getSuccess();
            protocol.writeSetBegin(null);
            for (String value1 : value0) {
                protocol.writeString(value1);
            }
            protocol.writeSetEnd();
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testMapStringString_args readTortureTestApi_testMapStringString_args(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testMapStringString_args obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testMapStringString_args();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testMapStringString_args(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testMapStringString_args obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.ObjectThatReferencesAnother readObjectThatReferencesAnother(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.ObjectThatReferencesAnother obj = new com.dotspots.rpcplus.example.torturetest.ObjectThatReferencesAnother();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 com.dotspots.rpcplus.example.torturetest.ObjectThatIsReferenced value0 = readObjectThatIsReferenced(protocol);
                     obj.setReference(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeObjectThatReferencesAnother(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.ObjectThatReferencesAnother obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetReference()) {
            protocol.writeI32(0);
            com.dotspots.rpcplus.example.torturetest.ObjectThatIsReferenced value0 = obj.getReference();
            writeObjectThatIsReferenced(protocol, value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testExceptionPassthru_result readTortureTestApi_testExceptionPassthru_result(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testExceptionPassthru_result obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testExceptionPassthru_result();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 com.dotspots.rpcplus.example.torturetest.SimpleException value0 = readSimpleException(protocol);
                     obj.setSuccess(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testExceptionPassthru_result(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testExceptionPassthru_result obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetSuccess()) {
            protocol.writeI32(0);
            com.dotspots.rpcplus.example.torturetest.SimpleException value0 = obj.getSuccess();
            writeSimpleException(protocol, value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject_result readTortureTestApi_methodReturningAnObject_result(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject_result obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject_result();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 com.dotspots.rpcplus.example.torturetest.ObjectThatReferencesAnother value0 = readObjectThatReferencesAnother(protocol);
                     obj.setSuccess(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_methodReturningAnObject_result(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject_result obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetSuccess()) {
            protocol.writeI32(0);
            com.dotspots.rpcplus.example.torturetest.ObjectThatReferencesAnother value0 = obj.getSuccess();
            writeObjectThatReferencesAnother(protocol, value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsTwoExceptions_args readTortureTestApi_testThrowsTwoExceptions_args(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsTwoExceptions_args obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsTwoExceptions_args();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testThrowsTwoExceptions_args(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsTwoExceptions_args obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsAnUnpositionedException_args readTortureTestApi_testThrowsAnUnpositionedException_args(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsAnUnpositionedException_args obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsAnUnpositionedException_args();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testThrowsAnUnpositionedException_args(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsAnUnpositionedException_args obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testPassthru_result readTortureTestApi_testPassthru_result(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testPassthru_result obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testPassthru_result();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 String value0 = protocol.readString();
                     obj.setSuccess(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testPassthru_result(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testPassthru_result obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetSuccess()) {
            protocol.writeI32(0);
            String value0 = obj.getSuccess();
            protocol.writeString(value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject5_args readTortureTestApi_methodReturningAnObject5_args(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject5_args obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject5_args();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 com.dotspots.rpcplus.example.torturetest.ObjectWithEnum value0 = readObjectWithEnum(protocol);
                     obj.setArg(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_methodReturningAnObject5_args(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject5_args obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetArg()) {
            protocol.writeI32(0);
            com.dotspots.rpcplus.example.torturetest.ObjectWithEnum value0 = obj.getArg();
            writeObjectWithEnum(protocol, value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testSetInt_args readTortureTestApi_testSetInt_args(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testSetInt_args obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testSetInt_args();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testSetInt_args(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testSetInt_args obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.SimpleObjectWithNoFieldIds readSimpleObjectWithNoFieldIds(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.SimpleObjectWithNoFieldIds obj = new com.dotspots.rpcplus.example.torturetest.SimpleObjectWithNoFieldIds();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case -2: {
                 int value0 = protocol.readI32();
                     obj.setUserId(value0);
                     break;
                 }
                 case -1: {
                 String value0 = protocol.readString();
                     obj.setToken(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeSimpleObjectWithNoFieldIds(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.SimpleObjectWithNoFieldIds obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetUserId()) {
            protocol.writeI32(-2);
            int value0 = obj.getUserId();
            protocol.writeI32(value0);
        }
        if (obj.isSetToken()) {
            protocol.writeI32(-1);
            String value0 = obj.getToken();
            protocol.writeString(value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsAnException_args readTortureTestApi_testThrowsAnException_args(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsAnException_args obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsAnException_args();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testThrowsAnException_args(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsAnException_args obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testMapStringString_result readTortureTestApi_testMapStringString_result(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testMapStringString_result obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testMapStringString_result();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 Map<String, String> value0 = new HashMap<String, String>();
                 protocol.readMapBegin();
                 while (protocol.hasNext()) {
                     String key1 = protocol.readString();
                     String value1 = protocol.readString();
                     value0.put(key1, value1);
                 }
                     obj.setSuccess(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testMapStringString_result(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testMapStringString_result obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetSuccess()) {
            protocol.writeI32(0);
            Map<String, String> value0 = obj.getSuccess();
            protocol.writeMapBegin(null);
            for (Map.Entry<String, String> entry0 : value0.entrySet()) {
                String key1 = entry0.getKey();
                String value1 = entry0.getValue();
                protocol.writeString(key1);
                protocol.writeString(value1);
            }
            protocol.writeMapEnd();
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testPositionalArguments_args readTortureTestApi_testPositionalArguments_args(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testPositionalArguments_args obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testPositionalArguments_args();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 int value0 = protocol.readI32();
                     obj.setInt32(value0);
                     break;
                 }
                 case 1: {
                 String value0 = protocol.readString();
                     obj.setStr(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testPositionalArguments_args(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testPositionalArguments_args obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetInt32()) {
            protocol.writeI32(0);
            int value0 = obj.getInt32();
            protocol.writeI32(value0);
        }
        if (obj.isSetStr()) {
            protocol.writeI32(1);
            String value0 = obj.getStr();
            protocol.writeString(value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.SimpleException readSimpleException(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.SimpleException obj = new com.dotspots.rpcplus.example.torturetest.SimpleException();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 String value0 = protocol.readString();
                     obj.setMessage(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeSimpleException(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.SimpleException obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetMessage()) {
            protocol.writeI32(0);
            String value0 = obj.getMessage();
            protocol.writeString(value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testDeclaresAnException_result readTortureTestApi_testDeclaresAnException_result(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testDeclaresAnException_result obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testDeclaresAnException_result();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 String value0 = protocol.readString();
                     obj.setSuccess(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testDeclaresAnException_result(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testDeclaresAnException_result obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetSuccess()) {
            protocol.writeI32(0);
            String value0 = obj.getSuccess();
            protocol.writeString(value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testBinary_args readTortureTestApi_testBinary_args(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testBinary_args obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testBinary_args();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 byte[] value0 = protocol.readBinary();
                     obj.setBinaryValue(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testBinary_args(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testBinary_args obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetBinaryValue()) {
            protocol.writeI32(0);
            byte[] value0 = obj.getBinaryValue();
            protocol.writeBinary(value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testPositionalArguments_result readTortureTestApi_testPositionalArguments_result(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testPositionalArguments_result obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testPositionalArguments_result();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 String value0 = protocol.readString();
                     obj.setSuccess(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testPositionalArguments_result(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testPositionalArguments_result obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetSuccess()) {
            protocol.writeI32(0);
            String value0 = obj.getSuccess();
            protocol.writeString(value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.ObjectWithComplexTypes readObjectWithComplexTypes(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.ObjectWithComplexTypes obj = new com.dotspots.rpcplus.example.torturetest.ObjectWithComplexTypes();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 Map<String, String> value0 = new HashMap<String, String>();
                 protocol.readMapBegin();
                 while (protocol.hasNext()) {
                     String key1 = protocol.readString();
                     String value1 = protocol.readString();
                     value0.put(key1, value1);
                 }
                     obj.setMapStringToString(value0);
                     break;
                 }
                 case 1: {
                 Set<String> value0 = new HashSet<String>();
                 protocol.readSetBegin();
                 while (protocol.hasNext()) {
                     String value1 = protocol.readString();
                     value0.add(value1);
                 }
                     obj.setSetOfStrings(value0);
                     break;
                 }
                 case 2: {
                 List<String> value0 = new ArrayList<String>();
                 protocol.readListBegin();
                 while (protocol.hasNext()) {
                     String value1 = protocol.readString();
                     value0.add(value1);
                 }
                     obj.setListOfStrings(value0);
                     break;
                 }
                 case 3: {
                 Map<Integer, Integer> value0 = new HashMap<Integer, Integer>();
                 protocol.readMapBegin();
                 while (protocol.hasNext()) {
                     int key1 = protocol.readI32();
                     int value1 = protocol.readI32();
                     value0.put(key1, value1);
                 }
                     obj.setMapOfIntToInt(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeObjectWithComplexTypes(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.ObjectWithComplexTypes obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetMapStringToString()) {
            protocol.writeI32(0);
            Map<String, String> value0 = obj.getMapStringToString();
            protocol.writeMapBegin(null);
            for (Map.Entry<String, String> entry0 : value0.entrySet()) {
                String key1 = entry0.getKey();
                String value1 = entry0.getValue();
                protocol.writeString(key1);
                protocol.writeString(value1);
            }
            protocol.writeMapEnd();
        }
        if (obj.isSetSetOfStrings()) {
            protocol.writeI32(1);
            Set<String> value0 = obj.getSetOfStrings();
            protocol.writeSetBegin(null);
            for (String value1 : value0) {
                protocol.writeString(value1);
            }
            protocol.writeSetEnd();
        }
        if (obj.isSetListOfStrings()) {
            protocol.writeI32(2);
            List<String> value0 = obj.getListOfStrings();
            protocol.writeListBegin(null);
            for (String value1 : value0) {
                protocol.writeString(value1);
            }
            protocol.writeListEnd();
        }
        if (obj.isSetMapOfIntToInt()) {
            protocol.writeI32(3);
            Map<Integer, Integer> value0 = obj.getMapOfIntToInt();
            protocol.writeMapBegin(null);
            for (Map.Entry<Integer, Integer> entry0 : value0.entrySet()) {
                int key1 = entry0.getKey();
                int value1 = entry0.getValue();
                protocol.writeI32(key1);
                protocol.writeI32(value1);
            }
            protocol.writeMapEnd();
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testBinary_result readTortureTestApi_testBinary_result(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testBinary_result obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testBinary_result();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 byte[] value0 = protocol.readBinary();
                     obj.setSuccess(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testBinary_result(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testBinary_result obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetSuccess()) {
            protocol.writeI32(0);
            byte[] value0 = obj.getSuccess();
            protocol.writeBinary(value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject5_result readTortureTestApi_methodReturningAnObject5_result(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject5_result obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject5_result();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 com.dotspots.rpcplus.example.torturetest.ObjectWithEnum value0 = readObjectWithEnum(protocol);
                     obj.setSuccess(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_methodReturningAnObject5_result(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject5_result obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetSuccess()) {
            protocol.writeI32(0);
            com.dotspots.rpcplus.example.torturetest.ObjectWithEnum value0 = obj.getSuccess();
            writeObjectWithEnum(protocol, value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject3_result readTortureTestApi_methodReturningAnObject3_result(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject3_result obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject3_result();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 com.dotspots.rpcplus.example.torturetest.SimpleObjectWithNoFieldIds value0 = readSimpleObjectWithNoFieldIds(protocol);
                     obj.setSuccess(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_methodReturningAnObject3_result(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject3_result obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetSuccess()) {
            protocol.writeI32(0);
            com.dotspots.rpcplus.example.torturetest.SimpleObjectWithNoFieldIds value0 = obj.getSuccess();
            writeSimpleObjectWithNoFieldIds(protocol, value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testDeclaresAnException_args readTortureTestApi_testDeclaresAnException_args(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testDeclaresAnException_args obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testDeclaresAnException_args();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testDeclaresAnException_args(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testDeclaresAnException_args obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.ObjectWithEnum readObjectWithEnum(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.ObjectWithEnum obj = new com.dotspots.rpcplus.example.torturetest.ObjectWithEnum();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 int value0 = protocol.readI32();
                     obj.setEnumValue(value0);
                     break;
                 }
                 case 1: {
                 Set<Integer> value0 = new HashSet<Integer>();
                 protocol.readSetBegin();
                 while (protocol.hasNext()) {
                     int value1 = protocol.readI32();
                     value0.add(value1);
                 }
                     obj.setEnumSet(value0);
                     break;
                 }
                 case 2: {
                 Map<Integer, Integer> value0 = new HashMap<Integer, Integer>();
                 protocol.readMapBegin();
                 while (protocol.hasNext()) {
                     int key1 = protocol.readI32();
                     int value1 = protocol.readI32();
                     value0.put(key1, value1);
                 }
                     obj.setEnumMap(value0);
                     break;
                 }
                 case 3: {
                 List<Integer> value0 = new ArrayList<Integer>();
                 protocol.readListBegin();
                 while (protocol.hasNext()) {
                     int value1 = protocol.readI32();
                     value0.add(value1);
                 }
                     obj.setEnumList(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeObjectWithEnum(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.ObjectWithEnum obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetEnumValue()) {
            protocol.writeI32(0);
            int value0 = obj.getEnumValue();
            protocol.writeI32(value0);
        }
        if (obj.isSetEnumSet()) {
            protocol.writeI32(1);
            Set<Integer> value0 = obj.getEnumSet();
            protocol.writeSetBegin(null);
            for (int value1 : value0) {
                protocol.writeI32(value1);
            }
            protocol.writeSetEnd();
        }
        if (obj.isSetEnumMap()) {
            protocol.writeI32(2);
            Map<Integer, Integer> value0 = obj.getEnumMap();
            protocol.writeMapBegin(null);
            for (Map.Entry<Integer, Integer> entry0 : value0.entrySet()) {
                int key1 = entry0.getKey();
                int value1 = entry0.getValue();
                protocol.writeI32(key1);
                protocol.writeI32(value1);
            }
            protocol.writeMapEnd();
        }
        if (obj.isSetEnumList()) {
            protocol.writeI32(3);
            List<Integer> value0 = obj.getEnumList();
            protocol.writeListBegin(null);
            for (int value1 : value0) {
                protocol.writeI32(value1);
            }
            protocol.writeListEnd();
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject2_result readTortureTestApi_methodReturningAnObject2_result(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject2_result obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject2_result();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 com.dotspots.rpcplus.example.torturetest.SimpleObjectWithFieldIds value0 = readSimpleObjectWithFieldIds(protocol);
                     obj.setSuccess(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_methodReturningAnObject2_result(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject2_result obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetSuccess()) {
            protocol.writeI32(0);
            com.dotspots.rpcplus.example.torturetest.SimpleObjectWithFieldIds value0 = obj.getSuccess();
            writeSimpleObjectWithFieldIds(protocol, value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testSetString_args readTortureTestApi_testSetString_args(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testSetString_args obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testSetString_args();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testSetString_args(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testSetString_args obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject4_result readTortureTestApi_methodReturningAnObject4_result(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject4_result obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject4_result();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 com.dotspots.rpcplus.example.torturetest.ObjectWithComplexTypes value0 = readObjectWithComplexTypes(protocol);
                     obj.setSuccess(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_methodReturningAnObject4_result(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject4_result obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetSuccess()) {
            protocol.writeI32(0);
            com.dotspots.rpcplus.example.torturetest.ObjectWithComplexTypes value0 = obj.getSuccess();
            writeObjectWithComplexTypes(protocol, value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.ContextOut readContextOut(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.ContextOut obj = new com.dotspots.rpcplus.example.torturetest.ContextOut();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 int value0 = protocol.readI32();
                     obj.setTiming(value0);
                     break;
                 }
                 case 1: {
                 String value0 = protocol.readString();
                     obj.setData(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeContextOut(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.ContextOut obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetTiming()) {
            protocol.writeI32(0);
            int value0 = obj.getTiming();
            protocol.writeI32(value0);
        }
        if (obj.isSetData()) {
            protocol.writeI32(1);
            String value0 = obj.getData();
            protocol.writeString(value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject3_args readTortureTestApi_methodReturningAnObject3_args(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject3_args obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject3_args();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_methodReturningAnObject3_args(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject3_args obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.SimpleObjectWithFieldIds readSimpleObjectWithFieldIds(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.SimpleObjectWithFieldIds obj = new com.dotspots.rpcplus.example.torturetest.SimpleObjectWithFieldIds();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 String value0 = protocol.readString();
                     obj.setToken(value0);
                     break;
                 }
                 case 1: {
                 int value0 = protocol.readI32();
                     obj.setUserId(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeSimpleObjectWithFieldIds(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.SimpleObjectWithFieldIds obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetToken()) {
            protocol.writeI32(0);
            String value0 = obj.getToken();
            protocol.writeString(value0);
        }
        if (obj.isSetUserId()) {
            protocol.writeI32(1);
            int value0 = obj.getUserId();
            protocol.writeI32(value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject2_args readTortureTestApi_methodReturningAnObject2_args(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject2_args obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject2_args();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_methodReturningAnObject2_args(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject2_args obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testPassthru_args readTortureTestApi_testPassthru_args(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testPassthru_args obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testPassthru_args();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 String value0 = protocol.readString();
                     obj.setArg(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testPassthru_args(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testPassthru_args obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetArg()) {
            protocol.writeI32(0);
            String value0 = obj.getArg();
            protocol.writeString(value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.SimpleEnum readSimpleEnum(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.SimpleEnum obj = new com.dotspots.rpcplus.example.torturetest.SimpleEnum();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeSimpleEnum(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.SimpleEnum obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject4_args readTortureTestApi_methodReturningAnObject4_args(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject4_args obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject4_args();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_methodReturningAnObject4_args(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject4_args obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsTwoExceptions_result readTortureTestApi_testThrowsTwoExceptions_result(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsTwoExceptions_result obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsTwoExceptions_result();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 String value0 = protocol.readString();
                     obj.setSuccess(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testThrowsTwoExceptions_result(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsTwoExceptions_result obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetSuccess()) {
            protocol.writeI32(0);
            String value0 = obj.getSuccess();
            protocol.writeString(value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject_args readTortureTestApi_methodReturningAnObject_args(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject_args obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject_args();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_methodReturningAnObject_args(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.methodReturningAnObject_args obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsAnException_result readTortureTestApi_testThrowsAnException_result(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsAnException_result obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsAnException_result();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 String value0 = protocol.readString();
                     obj.setSuccess(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testThrowsAnException_result(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testThrowsAnException_result obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetSuccess()) {
            protocol.writeI32(0);
            String value0 = obj.getSuccess();
            protocol.writeString(value0);
        }
        protocol.writeStructEnd();
    }

    public static final com.dotspots.rpcplus.example.torturetest.TortureTestApi.testSetInt_result readTortureTestApi_testSetInt_result(TBaseJSONProtocol protocol) throws TException {
         com.dotspots.rpcplus.example.torturetest.TortureTestApi.testSetInt_result obj = new com.dotspots.rpcplus.example.torturetest.TortureTestApi.testSetInt_result();
         if (protocol.readStructBegin()) {
             int fieldId;
             while(protocol.hasNext()) {
                 switch (protocol.readI32()) {
                 case 0: {
                 Set<Integer> value0 = new HashSet<Integer>();
                 protocol.readSetBegin();
                 while (protocol.hasNext()) {
                     int value1 = protocol.readI32();
                     value0.add(value1);
                 }
                     obj.setSuccess(value0);
                     break;
                 }
                 default:
                     protocol.skip();
                 }
             }
         }
         return obj;
    }

    private static final void writeTortureTestApi_testSetInt_result(TJSONProtocolWriter protocol, com.dotspots.rpcplus.example.torturetest.TortureTestApi.testSetInt_result obj) throws TException {
        if (obj == null) {
            protocol.writeNull();
            return;
        }
        protocol.writeStructBegin(null);
        if (obj.isSetSuccess()) {
            protocol.writeI32(0);
            Set<Integer> value0 = obj.getSuccess();
            protocol.writeSetBegin(null);
            for (int value1 : value0) {
                protocol.writeI32(value1);
            }
            protocol.writeSetEnd();
        }
        protocol.writeStructEnd();
    }

}
