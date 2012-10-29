package com.qeevee.gq.rules.act;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;

import android.util.Log;

/**
 * This factory class creates Action objects as specified in the game.xml
 * specification. Attributes and contained elements of the according action xml
 * tag are provided as parameters. These parameters are stored temporarily as
 * key value pairs in the HashMap that is given to the creating constructor of
 * the according action class.
 * 
 * <br/>
 * <br/>
 * 
 * Currently the following actions are supported:
 * 
 * <br/>
 * <br/>
 * 
 * <table border="1">
 * <tr>
 * <th colspan="2">Action</th>
 * </tr>
 * <tr>
 * <th>Parameter or Element name</th>
 * <th>Meaning</th>
 * </tr>
 * 
 * <tr>
 * <th colspan="2">DecrementVariable</th>
 * </tr>
 * <tr>
 * <td>type (Attribute)</td>
 * <td>DecrementVariable</td>
 * </tr>
 * 
 * <tr>
 * <th colspan="2">EndGame</th>
 * </tr>
 * <tr>
 * <td>type (Attribute)</td>
 * <td>EndGame</td>
 * </tr>
 * <tr>
 * 
 * <th colspan="2">IncrementVariable</th>
 * </tr>
 * <tr>
 * <td>type (Attribute)</td>
 * <td>IncrementVariable</td>
 * </tr>
 * 
 * <tr>
 * <th colspan="2">PlayAudio</th>
 * </tr>
 * <tr>
 * <td>type (Attribute)</td>
 * <td>PlayAudio</td>
 * </tr>
 * <tr>
 * <td>file (Attribute)</td>
 * <td>Relative path to audio file</td>
 * </tr>
 * 
 * <tr>
 * <th colspan="2">SetVariable</th>
 * </tr>
 * <tr>
 * <td>type (Attribute)</td>
 * <td>SetVariable</td>
 * </tr>
 * <tr>
 * <td>var (Attribute)</td>
 * <td>Name of the variable to set.</td>
 * </tr>
 * <tr>
 * <td>value (Element)</td>
 * <td>Expression element</td>
 * </tr>
 * 
 * <tr>
 * <th colspan="2">ShowMessage</th>
 * </tr>
 * <tr>
 * <td>type (Attribute)</td>
 * <td>ShowMessage</td>
 * </tr>
 * <tr>
 * <td>message (Attribute)</td>
 * <td>Message text to be displayed</td>
 * </tr>
 * 
 * <tr>
 * <th colspan="2">StartMission</th>
 * </tr>
 * <tr>
 * <td>type (Attribute)</td>
 * <td>StartMission</td>
 * </tr>
 * <tr>
 * <td>id (Attribute)</td>
 * <td>ID of the mission to be started</td>
 * </tr>
 * 
 * <tr>
 * <th colspan="2">Vibrate</th>
 * </tr>
 * <tr>
 * <td>type (Attribute)</td>
 * <td>Vibrate</td>
 * </tr>
 * <tr>
 * <td>duration (Attribute)</td>
 * <td>Duration of the vibration in milliseconds</td>
 * </tr>
 * 
 * </table>
 * 
 */
public class ActionFactory {

	private static String getActionPackageBaseName() {
		String commandClassName = Action.class.getName();
		int indexOfLastDot = commandClassName.lastIndexOf('.');
		return commandClassName.substring(0, indexOfLastDot + 1);
	}

	private static final String PACKAGE_BASE_FOR_ACTIONS = getActionPackageBaseName();
	private static final String TAG = "ActionFactory";

	public static Action create(Element xmlActionNode) {
		String commandType = xmlActionNode.attributeValue("type");
		if (commandType == null || commandType.equals("")) {
			Log.d(TAG, " invalid type specified or type omitted in "
					+ xmlActionNode);
			return null;
		}

		Object actionObject;
		try {
			@SuppressWarnings("rawtypes")
			Class actionClass = Class.forName(PACKAGE_BASE_FOR_ACTIONS
					+ commandType);
			actionObject = actionClass.newInstance();
		} catch (ClassNotFoundException e) {
			Log.d(TAG, " invalid type specified; action not found: "
					+ commandType);
			return null;
		} catch (IllegalAccessException e) {
			Log.d(TAG, e.toString());
			return null;
		} catch (InstantiationException e) {
			Log.d(TAG, e.toString());
			return null;
		}

		/*
		 * Prepare attributes as parameters in a HashMap:
		 */
		Map<String, String> params = new HashMap<String, String>();
		for (Object attribute : xmlActionNode.attributes()) {
			if (attribute instanceof Attribute) {
				params.put(((Attribute) attribute).getName(),
						((Attribute) attribute).getData().toString());
			}
		}

		/*
		 * Prepare contained xml elements in HashMap elements. Each child of the
		 * action node is represented as a map entry by its tag name as key and
		 * its tag body as value.
		 */
		Map<String, Element> elements = new HashMap<String, Element>();
		for (Object containedParameterElementObject : xmlActionNode
				.selectNodes("*")) {
			if (containedParameterElementObject instanceof Element) {
				try {
					elements.put(((Element) containedParameterElementObject)
							.getName(),
							(Element) containedParameterElementObject);
				} catch (ClassCastException e) {
					Log.d(TAG,
							" action contains invalid child element: "
									+ ((Element) containedParameterElementObject)
											.asXML());
					return null;
				}
			}
		}

		if (actionObject instanceof Action) {
			boolean initializationSuccessful = ((Action) actionObject).init(
					params, elements);
			if (!initializationSuccessful) {
				Log.d(TAG, " initialization failed: " + params.toString());
				return null;
			}
		} else {
			Log.d(TAG, " unknown Action type: " + commandType);
			return null;
		}

		return (Action) actionObject;

	}

}
