package com.qeevee.gq.rules.act;

import edu.bonn.mobilegaming.geoquest.Variables;

public class DecrementVariable extends Action {

	@Override
	protected boolean checkInitialization() {
		boolean initOK = params.containsKey("var");
		initOK &= ( Variables.getValue(params.get("var")) instanceof Double );
		return initOK;
	}

	@Override
	public void execute() {
		String varName = params.get("var");
		Double value = (Double) Variables.getValue(varName);
		Variables.setValue(varName, value - 1.0d);
	}

}
