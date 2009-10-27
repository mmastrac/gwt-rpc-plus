package com.dotspots.rpcplus.codegen.jscollections;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;

/**
 * Generates a set of strongly-typed and generic GWT collections for use with Thrift.
 * 
 * It automatically handles translation of GWT longs to double pairs and back for hosted mode.
 */
public class CollectionGen {
	private static final Class<?>[] PRIMITIVE_CLASSES = new Class<?>[] { int.class, boolean.class, double.class, long.class, String.class };
	private final File outputDirectory;
	private final String packageName;

	public enum Type {
		LIST, SET, MAP
	}

	public CollectionGen(File outputDirectory, String packageName) {
		this.outputDirectory = outputDirectory;
		this.packageName = packageName;
	}

	public void generateCode() throws FileNotFoundException {
		writeList(Object.class);
		for (Class<?> clazz : PRIMITIVE_CLASSES) {
			writeList(clazz);
		}

		writeSet(int.class);
		writeSet(String.class);

		writeMap(int.class, Object.class);
		for (Class<?> clazz : PRIMITIVE_CLASSES) {
			writeMap(int.class, clazz);
		}

		writeMap(String.class, Object.class);
		for (Class<?> clazz : PRIMITIVE_CLASSES) {
			writeMap(String.class, clazz);
		}

		for (Class<?> clazz : PRIMITIVE_CLASSES) {
			writeProcedure(clazz);
			writeProcedure(String.class, clazz);
			writeProcedure(int.class, clazz);
		}

		writeProcedure(Object.class);
		writeProcedure(String.class, Object.class);
		writeProcedure(int.class, Object.class);
	}

	private String getName(Class<?> clazz) {
		if (clazz == int.class) {
			return "Int";
		}

		if (clazz == boolean.class) {
			return "Bool";
		}

		if (clazz == double.class) {
			return "Double";
		}

		if (clazz == long.class) {
			return "Long";
		}

		if (clazz == String.class) {
			return "String";
		}

		return "";
	}

	private String getNameWithObject(Class<?> clazz) {
		if (clazz == Object.class) {
			return "Object";
		}

		return getName(clazz);
	}

	private String getDefault(Class<?> clazz) {
		if (clazz == int.class || clazz == double.class) {
			return "0";
		}

		if (clazz == boolean.class) {
			return "false";
		}

		if (clazz == long.class) {
			return "[0,0]";
		}

		return "null";
	}

	private void writeList(Class<?> elem) throws FileNotFoundException {
		String name = "List";
		name += getName(elem);

		writeCollection(name, Type.LIST, int.class, elem);
	}

	private void writeSet(Class<?> elem) throws FileNotFoundException {
		String name = "Set";
		name += getName(elem);

		writeCollection(name, Type.SET, elem, null);
	}

	private void writeMap(Class<?> key, Class<?> value) throws FileNotFoundException {
		String name = "Map";
		name += getName(key);
		name += getName(value);

		writeCollection(name, Type.MAP, key, value);
	}

	private String getProcedureName(Class<?> class1, boolean includeGenerics) {
		return getProcedureName(class1, null, includeGenerics);
	}

	private String getProcedureName(Class<?> class1, Class<?> class2, boolean includeGenerics) {
		String name = "JsRpc" + getNameWithObject(class1) + getNameWithObject(class2) + "Procedure";
		return includeGenerics && (class1 == Object.class || class2 == Object.class) ? name + "<E>" : name;
	}

	private void writeProcedure(Class<?> class1) throws FileNotFoundException {
		writeProcedure(class1, null);
	}

	private void writeProcedure(Class<?> class1, Class<?> class2) throws FileNotFoundException {
		File file = new File(outputDirectory, getProcedureName(class1, class2, false) + ".java");

		String keyType = (class1 == Object.class) ? "E" : class1.getSimpleName();
		String valueType = (class2 == null || class2 == Object.class) ? "E" : class2.getSimpleName();

		System.out.println(file.getName());
		PrintWriter printWriter = new PrintWriter(file);

		printWriter.println("// AUTOGENERATED: See " + getClass().getName() + " for more details");
		printWriter.println("package " + packageName + ";");
		printWriter.println();

		printWriter.println("public interface " + getProcedureName(class1, class2, true) + " {");

		printWriter.println("    /**");
		printWriter.println("     * Executes this procedure. A false return value indicates that");
		printWriter.println("     * the application executing this procedure should not invoke this");
		printWriter.println("     * procedure again.");
		printWriter.println("     */");
		if (class2 == null) {
			printWriter.println("    public boolean execute(" + keyType + " value);");
		} else {
			printWriter.println("    public boolean execute(" + keyType + " a, " + valueType + " b);");
		}
		printWriter.println("}");
		printWriter.close();
	}

	private String getBinaryName(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}

		final String arrayName = Array.newInstance(clazz, 0).getClass().getName();
		return arrayName.substring(1).replace('.', '/');
	}

	private void writeCollection(String name, Type type, Class<?> key, Class<?> value) throws FileNotFoundException {
		final String fullName = "JsRpc" + name;
		File file = new File(outputDirectory, fullName + ".java");

		System.out.println(fullName);
		PrintWriter printWriter = new PrintWriter(file);

		boolean getter = (value != null);
		String genericsFull = (value == Object.class) ? "<E> " : "";
		String genericsShort = (value == Object.class) ? "<E>" : "";
		String valueType = (value == null || value == Object.class) ? "E" : value.getSimpleName();
		String keyType = (key == null || key == Object.class) ? "E" : key.getSimpleName();
		String valueBinaryName = getBinaryName(value);
		String keyBinaryName = getBinaryName(key);
		String indexer = (key == String.class) ? "['_' + idx]" : "[idx]";
		String keyString = (key == String.class) ? "'_' + idx" : "idx";
		String defaultValue = getDefault(value);

		try {
			printWriter.println("// AUTOGENERATED: See " + getClass().getName() + " for more details");
			printWriter.println("package " + packageName + ";");
			printWriter.println();

			printWriter.println("import com.google.gwt.core.client.JavaScriptObject;");
			printWriter.println("import com.google.gwt.core.client.GWT;");
			printWriter.println("import com.google.gwt.lang.LongLib;");
			printWriter.println();

			printWriter.println("@SuppressWarnings(\"unused\")");
			printWriter.println("public final class " + fullName + genericsFull.trim() + " extends JavaScriptObject {");
			printWriter.println("    protected " + fullName + "() {");
			printWriter.println("    }");
			printWriter.println();

			printWriter.println("    public static " + genericsFull + fullName + genericsShort + " create() {");
			if (type == Type.LIST) {
				printWriter.println("        return JavaScriptObject.createArray().cast();");
			} else {
				printWriter.println("        return JavaScriptObject.createObject().cast();");
			}
			printWriter.println("    }");
			printWriter.println();

			printWriter.println("    public native boolean contains(" + key.getSimpleName() + " idx) /*-{");
			printWriter.println("        return (" + keyString + " in this);");
			printWriter.println("    }-*/;");
			printWriter.println();

			if (type == Type.LIST) {
				printWriter.println("    public native int size() /*-{");
				printWriter.println("        return this.length;");
				printWriter.println("    }-*/;");
				printWriter.println();
				printWriter.println("    public native boolean isEmpty() /*-{");
				printWriter.println("        return !this.length;");
				printWriter.println("    }-*/;");
				printWriter.println();
				printWriter.println("    public native void remove(" + key.getSimpleName() + " idx) /*-{");
				printWriter.println("        this.splice(idx, 1);");
				printWriter.println("    }-*/;");
				printWriter.println();
				// TODO: long
				if (value != long.class) {
					printWriter.println("    public native boolean forEach(" + getProcedureName(value, true) + " procedure) /*-{");
					printWriter.println("        for (var i = 0; i < this.length; i++) { ");
					printWriter.println("            if (!procedure.@" + packageName + "." + getProcedureName(value, false) + "::execute("
							+ valueBinaryName + ")(this[i])) return false;");
					printWriter.println("        }");
					printWriter.println("        return true;");
					printWriter.println("    }-*/;");
					printWriter.println();
					printWriter.println("    public native boolean forEach(" + getProcedureName(int.class, value, true)
							+ " procedure) /*-{");
					printWriter.println("        for (var i = 0; i < this.length; i++) { ");
					printWriter.println("            if (!procedure.@" + packageName + "." + getProcedureName(int.class, value, false)
							+ "::execute(I" + valueBinaryName + ")(i, this[i])) return false;");
					printWriter.println("        }");
					printWriter.println("        return true;");
					printWriter.println("    }-*/;");
					printWriter.println();
				}
			} else {
				if (type == Type.MAP) {
					if (value == Object.class || value == String.class) {
						printWriter.println("    public Iterable<" + valueType + "> keysIterable() {");
						printWriter.println("        return RpcUtils.<" + valueType + ">getMapIterable(this);");
						printWriter.println("    }");
						printWriter.println();
					}

					if (value != long.class) {
						printWriter.println("    public native boolean forEachEntry(" + getProcedureName(key, value, true)
								+ " procedure) /*-{");
						printWriter.println("        for (x in this) { ");
						printWriter.println("            if (this.hasOwnProperty(x)) {");
						printWriter.println("                if (!procedure.@" + packageName + "." + getProcedureName(key, value, false)
								+ "::execute(" + keyBinaryName + valueBinaryName + ")(x.slice(1), this[x])) return false;");
						printWriter.println("            }");
						printWriter.println("        }");
						printWriter.println("        return true;");
						printWriter.println("    }-*/;");
						printWriter.println();

						printWriter.println("    public native boolean forEachKey(" + getProcedureName(key, true) + " procedure) /*-{");
						printWriter.println("        for (x in this) { ");
						printWriter.println("            if (this.hasOwnProperty(x)) {");
						printWriter.println("                if (!procedure.@" + packageName + "." + getProcedureName(key, false)
								+ "::execute(" + keyBinaryName + ")(x.slice(1))) return false;");
						printWriter.println("            }");
						printWriter.println("        }");
						printWriter.println("        return true;");
						printWriter.println("    }-*/;");
						printWriter.println();

						printWriter.println("    public native boolean forEachValue(" + getProcedureName(value, true) + " procedure) /*-{");
						printWriter.println("        for (x in this) { ");
						printWriter.println("            if (this.hasOwnProperty(x)) {");
						printWriter.println("                if (!procedure.@" + packageName + "." + getProcedureName(value, false)
								+ "::execute(" + valueBinaryName + ")(this[x])) return false;");
						printWriter.println("            }");
						printWriter.println("        }");
						printWriter.println("        return true;");
						printWriter.println("    }-*/;");
						printWriter.println();
					}
				}
				if (type == Type.SET) {
					if (key == Object.class || key == String.class) {
						printWriter.println("    public Iterable<" + keyType + "> iterable() {");
						printWriter.println("        return RpcUtils.<" + keyType + ">getSetIterable(this);");
						printWriter.println("    }");
						printWriter.println();
					}
					printWriter.println("    public native boolean forEach(" + getProcedureName(key, true) + " procedure) /*-{");
					printWriter.println("        for (x in this) { ");
					printWriter.println("            if (this.hasOwnProperty(x)) {");
					printWriter.println("                if (!procedure.@" + packageName + "." + getProcedureName(key, false)
							+ "::execute(" + keyBinaryName + ")(x.slice(1))) return false;");
					printWriter.println("            }");
					printWriter.println("        }");
					printWriter.println("        return true;");
					printWriter.println("    }-*/;");
					printWriter.println();
				}
				printWriter.println("    /**");
				printWriter.println("     * Counts the size of a collection through brute force (slow).");
				printWriter.println("     */");
				printWriter.println("    public native int countSize() /*-{");
				printWriter.println("        var l = 0; for (x in this) if (this.hasOwnProperty(x)) l++; return l;");
				printWriter.println("    }-*/;");
				printWriter.println();
				printWriter.println("    public native boolean isEmpty() /*-{");
				printWriter.println("        for (x in this) if (this.hasOwnProperty(x)) return false; return true;");
				printWriter.println("    }-*/;");
				printWriter.println();
				printWriter.println("    public native void remove(" + key.getSimpleName() + " idx) /*-{");
				printWriter.println("        delete this" + indexer + ";");
				printWriter.println("    }-*/;");
				printWriter.println();
			}

			if (getter) {
				if (value == long.class) {
					printWriter.println("    public " + valueType + " get(" + key.getSimpleName() + " idx) {");
					printWriter.println("        return RpcUtils.fromDoubles(get0(idx));");
					printWriter.println("    };");
					printWriter.println();

					printWriter.println("    private native JavaScriptObject get0(" + key.getSimpleName() + " idx) /*-{");
					printWriter.println("        return this" + indexer + " || [0,0];");
					printWriter.println("    }-*/;");
				} else {
					printWriter.println("    public native " + valueType + " get(" + key.getSimpleName() + " idx) /*-{");
					if (value == boolean.class) {
						printWriter.println("        // Coerce to boolean in case underlying value is integer");
						printWriter.println("        return !!this" + indexer + ";");
					} else {
						printWriter.println("        return this" + indexer + " || " + defaultValue + ";");
					}
					printWriter.println("    }-*/;");
				}
				printWriter.println();

				if (value == long.class) {
					printWriter.println("    public void set(" + key.getSimpleName() + " idx, " + valueType + " value) {");
					printWriter.println("        set0(idx, RpcUtils.toDoubles(value));");
					printWriter.println("    }");
					printWriter.println();

					printWriter.println("    private native void set0(" + key.getSimpleName() + " idx, JavaScriptObject value) /*-{");
					printWriter.println("        this" + indexer + " = value;");
					printWriter.println("    }-*/;");
				} else {
					printWriter.println("    public native void set(" + key.getSimpleName() + " idx, " + valueType + " value) /*-{");
					printWriter.println("        this" + indexer + " = value;");
					printWriter.println("    }-*/;");
				}
				printWriter.println();
			}

			if (type == Type.LIST) {
				if (value == long.class) {
					printWriter.println("    public void add(" + valueType + " value) {");
					printWriter.println("        add0(RpcUtils.toDoubles(value));");
					printWriter.println("    }");
					printWriter.println();

					printWriter.println("    private native void add0(JavaScriptObject value) /*-{");
					printWriter.println("        this.push(value);");
					printWriter.println("    }-*/;");
				} else {
					printWriter.println("    public native void add(" + valueType + " value) /*-{");
					printWriter.println("        this.push(value);");
					printWriter.println("    }-*/;");
				}
				printWriter.println();
			}
			if (type == Type.SET) {
				printWriter.println("    public native void add(" + key.getSimpleName() + " idx) /*-{");
				printWriter.println("        this" + indexer + " = 0;");
				printWriter.println("    }-*/;");
				printWriter.println();
			}

			printWriter.println("}");
		} finally {
			printWriter.close();
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		new CollectionGen(new File("/Users/matthew/Documents/workspace/gwt-rpc-plus/gwt/com/dotspots/rpcplus/client/jscollections"),
				"com.dotspots.rpcplus.client.jscollections").generateCode();
	}
}
