package com.qeevee.gq.rules.cond;

import org.dom4j.Element;

import android.util.Log;

import com.qeevee.util.StringTools;

public class ConditionFactory {

	public static final String PACKAGE_NAME = getPackageName();
	private static final String TAG = "ConditionFactory";

	private static String getPackageName() {
		String conditionClassName = Condition.class.getName();
		int indexOfLastDot = conditionClassName.lastIndexOf('.');
		return conditionClassName.substring(0, indexOfLastDot + 1);
	}

	/**
	 * Creates a condition object appropriate for the given xml element. E.g. if
	 * an {@code <and>...</and>} element is given as parameter, an instance of
	 * class {@code AndCondition} will be created.
	 * 
	 * @param xmlCondition
	 * @return
	 */
	public static Condition create(Element xmlCondition) {
		String conditionClassName = StringTools.caseUp(xmlCondition.getName())
				+ "Condition";
 
		Condition condition;
		try {
			@SuppressWarnings("rawtypes")
			Class conditionClass = Class.forName(PACKAGE_NAME
					+ conditionClassName);
			condition = (Condition) conditionClass.newInstance();
		} catch (ClassNotFoundException e) {
			Log.d(TAG, " invalid type specified; condition type not found: "
					+ conditionClassName);
			return null;
		} catch (IllegalAccessException e) {
			Log.d(TAG, e.toString());
			return null;
		} catch (InstantiationException e) {
			Log.d(TAG, e.toString());
			return null;
		} catch (ClassCastException e) {
			Log.d(TAG, " unknown Condition type: " + conditionClassName);
			return null;
		}

		if (!condition.init(xmlCondition)) {
			Log.d(TAG, " initialization failed.");
			return null;
		}
		
		return condition;
	}

}
