package com.qeevee.gq.lua;

import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import edu.bonn.mobilegaming.geoquest.Variables;

import android.util.Log;


public class Lua {
	
	static Lua defaultInstance;
	
	/**
     *    Static accessor method for retrieving the default shared instance.
	 */
	public static Lua getDefault() {
		if(defaultInstance == null){
			defaultInstance = new Lua();
		}
		return defaultInstance;
	}
	
	
	
	//TODO capital L?
	private LuaState L;
	
	Lua(){
		L = LuaStateFactory.newLuaState();
		L.openLibs();
		
		registerJavaFunctions();
	}
	
	
	
	private void registerJavaFunctions() {
		try {
			/**
			 * void print(str [, str2 [, str3 ...]]])
			 * 
			 * Overwrites the lua print function.
			 * Can have an arbitrary number of parameters.
			 * Output will be printed in Log (via Lod.d()) and stored in a variable output.
			 * So it can be returned after calling evalLua().
			 */
			JavaFunction print = new JavaFunction(L) {
				@Override
				public int execute() throws LuaException {
					for (int i = 2; i <= L.getTop(); i++) {
						int type = L.type(i);
						String stype = L.typeName(type);
						String val = null;
						if (stype.equals("userdata")) {
							Object obj = L.toJavaObject(i);
							if (obj != null)
								val = obj.toString();
						} else if (stype.equals("boolean")) {
							val = L.toBoolean(i) ? "true" : "false";
						} else {
							val = L.toString(i);
						}
						if (val == null)
							val = stype;	
						Log.d("Lua print()",val); //multiple arguments in print() fct will be printed in multiple lines in Log.
						output.append(val);
						output.append("\t");
					}
					output.append("\n");
					
					//number of variables returned
					return 0;
				}
			};
			print.register("print");
			
			
			/**
			 * SomeLuaType getVar(String varName) 
			 * 
			 * Returns the value of a Geoquest variable in the corresponding lua type (if possible).
			 */
			JavaFunction getVar = new JavaFunction(L) {
				@Override
				public int execute() throws LuaException {
					
					String varName = L.toString(-1);
					Object var = Variables.getValue(varName);
					L.pushObjectValue(var); //this will push the object as corresponding lua type, if possible.
					
					//number of variables returned
					return 1;
				}
			};
			getVar.register("getVar");
			
			
			/**
			 * void setVar(String varName, value)
			 * 
			 * Sets the value of a GeoQuest variable.
			 * Only the following types are allowed: Number (always treated as double), Boolean, String, JavaObject
			 */
			JavaFunction setVar = new JavaFunction(L) {
				@Override
				public int execute() throws LuaException {
					
					String varName = L.toString(-1);//TODO indexes correct?	
					LuaObject value = L.getLuaObject(-2);
					if(value.isNumber()){
						Variables.setValue(varName, value.getNumber());
					}else if(value.isBoolean()){
						Variables.setValue(varName, value.getBoolean());
					}else if(value.isString()){
						Variables.setValue(varName, value.getString());
					}else if(value.isNil()){
						throw new LuaException("Unable to set variable "+varName+" to nil. nil is not allowed.");
					}else if(value.isTable()){
						throw new LuaException("Unable to set variable "+varName+", because it is a table.");
					}else if(value.isJavaObject()){
						Variables.setValue(varName, value.getObject());
					}else{
						throw new LuaException("Unable to set variable "+varName+", because its type is not supported.");
					}
					
					//number of variables returned
					return 0;
				}
			};
			setVar.register("setVar");
			
			
		} catch (Exception e) {
			System.err.println("Cannot override print");
		}
	}


	
	
	private final StringBuilder output = new StringBuilder();
	
	/**
	 * Loads and runs the given string.
	 * @param src
	 * @return the output of the script (that is typical printed into the console).
	 * @throws LuaException
	 */
	public String evalLua(String src) throws LuaException {
		output.setLength(0);
		
		L.setTop(0);						//clears the stack
		int ok = L.LloadString(src);		//loads lua chunk (without running it). pushes it as function on top of stack.
		if (ok == 0) {
			//place debug.traceback on the stack.
			L.getGlobal("debug");			//stack: src debug
			L.getField(-1, "traceback");	//stack: src debug traceback
			L.remove(-2);					//stack: src traceback
			L.insert(-2);					//stack: traceback src
			ok = L.pcall(0, 0, -2);			//call src. -2 is the stack index of error message handler
			if (ok == 0) {
				String res = output.toString();
				output.setLength(0);
				return res;
			}
		}
		throw new LuaException(errorReason(ok) + ": " + L.toString(-1));
	}
	
	/**
	 * Checks a boolean expression in lua (ex.: "a == 3", "myFunc(a)") and returns whether its true or not.
	 * @param expr
	 * @return 
	 * @throws LuaException
	 */
	public boolean checkBooleanExpression(String expr) throws LuaException {
		// the string passed to lua is treated as function. So we give it a proper form.
		String s = "return (" + expr + ");";
		
		L.setTop(0);
		int ok = L.LloadString(s);
		if (ok == 0) {
			L.getGlobal("debug");
			L.getField(-1, "traceback");
			L.remove(-2);
			L.insert(-2);
			ok = L.pcall(0, 1, -2); //call expression. output is put on top of stack.
			if (ok == 0) {
				if(!L.isBoolean(-1)) throw new LuaException("Output of expression is not a boolean. Output: ["+ L.toString()+"]. Expression: ["+expr+"]");
				return L.toBoolean(-1);
			}
		}
		throw new LuaException(errorReason(ok) + ": " + L.toString(-1));	
	}
	
	
	
	private String errorReason(int error) {
		switch (error) {
		case 4:
			return "Out of memory";
		case 3:
			return "Syntax error";
		case 2:
			return "Runtime error";
		case 1:
			return "Yield error";
		}
		return "Unknown error " + error;
	}
	
	
}