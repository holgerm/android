package com.qeevee.gq.rules.act;

import java.util.Map;

import org.dom4j.Element;

public abstract class Action {

	Action() {
	}

	/**
	 * The <code>params</code> map contains all given attributes of the action
	 * XML element. Cf. ActionFactory for details.
	 */
	protected Map<String, String> params;
	/**
	 * The <code>elements</code> map contains all direct XML children of the
	 * action XML element. Cf. ActionFactory for details.
	 */
	protected Map<String, Element> elements;

	protected final boolean init(Map<String, String> params,
			Map<String, Element> elements) {
		this.params = params;
		this.elements = elements;
		return checkInitialization();
	}

	protected abstract boolean checkInitialization();

	public abstract void execute();

}
