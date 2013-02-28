package com.qeevee.gq.rules.act;

import org.keplerproject.luajava.LuaException;
import com.qeevee.gq.lua.Lua;



public class RunLua extends Action {
	
    @Override
    protected boolean checkInitialization() {
    	return (params.containsKey("code"));
    }

    @Override
    public void execute() {
    	try {
			Lua.getDefault().evalLua(params.get("code"));
		} catch (LuaException e) {
			//TODO: handle!!!
			e.printStackTrace();
		}
    }
}
