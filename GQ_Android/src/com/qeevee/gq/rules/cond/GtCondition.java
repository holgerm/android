package com.qeevee.gq.rules.cond;

public class GtCondition extends ComparisonCondition {

	@Override
	protected boolean compare(Double operandA, Double operandB) {
		return operandA > operandB;
	}


	@Override
	protected boolean compare(String object, String object2) {
		return (object.compareTo(object2) > 0);
	}
}
