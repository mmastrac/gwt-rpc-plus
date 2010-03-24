namespace java com.dotspots.rpcplus.example.torturetest

struct SimpleObjectWithFieldIds {
	1:string token;
	2:i32 userId;
}

struct SimpleObjectWithNoFieldIds {
	string token;
	i32 userId;
}

struct ObjectWithComplexTypes {
	1: map<string, string> mapStringToString;
	2: set<string> setOfStrings;
	3: list<string> listOfStrings;
	4: map<i32, i32> mapOfIntToInt;
}

struct ObjectThatIsReferenced {
	1: i32 id;
}

struct ObjectThatReferencesAnother {
	1: ObjectThatIsReferenced reference;
}

enum SimpleEnum {
	ONE = 1;
	TWO = 2;
}

struct ObjectWithEnum {
	1: SimpleEnum enumValue;
	2: set<SimpleEnum> enumSet;
	3: map<SimpleEnum, SimpleEnum> enumMap;
	4: list<SimpleEnum> enumList;
}

struct ContextIn {
	1: optional string token;
	2: optional string data;
}

struct ContextOut {
	1: optional i32 timing;
	2: optional string data;
}

exception SimpleException {
	1:string message;
}

exception MoreComplexException {
	1:string message;
	2:ObjectWithComplexTypes data;
}

service TortureTestApi {
	string testPassthru(1:string arg);
	
	string testThrowsAnException() throws (1:SimpleException ex);
	string testThrowsAnUnpositionedException() throws (SimpleException ex);
	string testDeclaresAnException() throws (1:SimpleException ex);
	string testThrowsTwoExceptions(i32 which) throws (1:SimpleException ex, 2:MoreComplexException ex2);
	SimpleException testExceptionPassthru(1:SimpleException ex);

	string testPositionalArguments(1:i32 int32, 2:string str);
	
	// Thrift isn't deterministic with these, so we don't officially support them, even though they might work
	/*
	string testUnpositionedArguments(i32 int32, string str);
	string testMixOfArguments(i32 int32, 2:string str, i64 int64, 1:ObjectWithComplexTypes types);
    */
    
	set<string> testSetString();
	set<i32> testSetInt();
	
	map<string, string> testMapStringString();
	
	ObjectThatReferencesAnother methodReturningAnObject();
	SimpleObjectWithFieldIds methodReturningAnObject2();
	SimpleObjectWithNoFieldIds methodReturningAnObject3();
	ObjectWithComplexTypes methodReturningAnObject4();
	ObjectWithEnum methodReturningAnObject5(1:ObjectWithEnum arg);

	binary testBinary(1:binary binaryValue);

	void __setContext(1:ContextIn context);	
	ContextOut __getContext();	
}
