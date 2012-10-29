package com.qeevee.gq.rules.cond;


public class OrCondition extends CompositeCondition {

	@Override
	public boolean isFulfilled() {
		boolean fulfilled = false; // empty or condition is false
		
		for (Condition containedCondition : containedConditions) {
			fulfilled |= containedCondition.isFulfilled();
			if (fulfilled ==  true)
				return true; // Lazy or evaluation.
		}

		return fulfilled;
	}

}
