package com.qeevee.gq.items;

import org.dom4j.Element;

import android.util.Log;

/**
 * This factory class creates item objects, i.e. instances of subclasses of
 * class Item. It uses the XML element representing the item from the game.xml
 * for the creation.
 * 
 * @author muegge
 * 
 */
public class ItemFactory {

	private static String getItemsPackageBaseName() {
		String commandClassName = Item.class.getName();
		int indexOfLastDot = commandClassName.lastIndexOf('.');
		return commandClassName.substring(0, indexOfLastDot + 1);
	}

	private static final String PACKAGE_BASE_FOR_ITEMS = getItemsPackageBaseName();
	private static final String TAG = "ItemFactory";

	/**
	 * @param xmlItemNode
	 * @return an instance of the concrete subclass of Item that is specified in
	 *         the given XML element by the <code>type</code> attribute.
	 */
	public static Item create(Element xmlItemNode) {
		String itemType = xmlItemNode.attributeValue("type");
		if (itemType == null || itemType.equals("")) {
			Log.d(TAG, " invalid type specified or type omitted in "
					+ xmlItemNode);
			return null;
		}

		Item itemObject;
		try {
			@SuppressWarnings("rawtypes")
			Class itemClass = Class.forName(PACKAGE_BASE_FOR_ITEMS
					+ itemType);
			itemObject = (Item) itemClass.newInstance();
		} catch (ClassNotFoundException e) {
			Log.d(TAG, " invalid type specified; item not found: "
					+ itemType);
			return null;
		} catch (IllegalAccessException e) {
			Log.d(TAG, e.toString());
			return null;
		} catch (InstantiationException e) {
			Log.d(TAG, e.toString());
			return null;
		} catch (ClassCastException e) {
			Log.d(TAG, e.toString());
			return null;
		}

		itemObject.init(xmlItemNode);
		return itemObject;

	}

}
