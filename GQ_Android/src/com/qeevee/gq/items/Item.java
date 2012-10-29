package com.qeevee.gq.items;

import org.dom4j.Element;

import android.view.View;
import edu.bonn.mobilegaming.geoquest.mission.MissionActivity;

/**
 * Instances of this class represent a part of a mission, i.e. an item as used
 * in the game.xml specification. There are different types of items each
 * represented by a concrete subclass of this class.
 * 
 * Always use {@link ItemFactory#create(Element)} to instantiate subclasses of
 * Item. Never use a constructor manually.
 * 
 * @author muegge
 * 
 */
public abstract class Item {

	protected Element xmlNode;

	/**
	 * @param containingActivity
	 * @return the view is inserted into this mission's view flipper and which
	 *         represents one interaction item.
	 */
	public abstract View getView(MissionActivity containingActivity);

	protected final void init(Element xmlItemNode) {
		this.xmlNode = xmlItemNode;
	}

	/**
	 * @return <code>true</code> if this Item has a sound attribute,
	 *         <code>false</code> otherwise.
	 */
	public final boolean hasAudio() {
		return (xmlNode.attributeValue("sound") != null && !xmlNode
				.attributeValue("sound").equals(""));
	}
	
	/**
	 * 
	 * @return the path to the audio file or null if there is none
	 */
	public final String getAudioPath() {
		if(hasAudio()) {
			return xmlNode.attributeValue("sound");
		}
		return null;
	}

}
