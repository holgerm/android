package com.qeevee.gq.rules.cond;


public class NotCondition extends CompositeCondition {

	@Override
	public boolean isFulfilled() {
		return !containedConditions.get(0).isFulfilled();
	}

}
