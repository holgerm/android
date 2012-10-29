package com.qeevee.gq.rules.cond;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

public abstract class CompositeCondition extends Condition {

	protected List<Condition> containedConditions = new ArrayList<Condition>(); 
	
	@Override
	protected boolean init(Element xmlCondition) {
		boolean successfullyInitialized = super.init(xmlCondition);
		
		@SuppressWarnings("unchecked")
		List<Element> xmlContainedConditions = xmlCondition.selectNodes("*");
		for (Element containedCondition : xmlContainedConditions) {
			containedConditions.add(ConditionFactory.create(containedCondition));
		}
		
		return successfullyInitialized;
	}


}
