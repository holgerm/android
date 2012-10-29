package com.qeevee.gq.rules.cond;


public class AndCondition extends CompositeCondition {

	@Override
	public boolean isFulfilled() {
		boolean fulfilled = true; // empty and condition is true
		
		for (Condition containedCondition : containedConditions) {
			fulfilled &= containedCondition.isFulfilled();
			if (fulfilled ==  false)
				return false;
		}

		return fulfilled;
	}

}
