package com.qeevee.gq.rules.cond;

import org.dom4j.Element;


public abstract class Condition {

	public abstract boolean isFulfilled();

	protected boolean init(Element xmlCondition) {
		return true;
	}
}
