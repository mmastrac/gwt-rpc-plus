Thrift -> GWT type mappings:

Long -> GWT's emulated longs (ie: hi/lo doubles)

Simple types (string/int/etc) -> Native type

set<i32/i16> -> JsRpcSetInt
set<string> -> JsRpcSetString

list<?> -> JsRpcList

map<i32, ?> -> JsRpcMapInt
map<string, ?> -> JsRpcMapString
