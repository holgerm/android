package com.qeevee.gq.rules.cond;

import org.keplerproject.luajava.LuaException;
import org.dom4j.Element;
import com.qeevee.gq.lua.Lua;


public class CheckLuaCondition extends Condition { 

	String code;
	
	@Override
	protected boolean init(Element xmlCondition) {
		code = xmlCondition.attributeValue("code");
		if (code == null)
			return false;
		
		return super.init(xmlCondition);
	}

	@Override
	public boolean isFulfilled() {
		try {
			return (Lua.getDefault().checkBooleanExpression(code));
		} catch (LuaException e) {
			//TODO: handle!!!
			e.printStackTrace();
			return false;
		}

	}

}
