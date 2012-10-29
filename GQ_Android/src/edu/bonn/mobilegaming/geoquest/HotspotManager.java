package edu.bonn.mobilegaming.geoquest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

public class HotspotManager {
	private static HotspotManager hm;
	private HashMap<String, Hotspot> hotspots;

	private HotspotManager() {
		hotspots = new HashMap<String, Hotspot>();
	}

	@SuppressWarnings("rawtypes")
	public static HotspotManager init(Document doc) {
		hm = new HotspotManager();

		List hotspotNodes = doc.getRootElement().selectNodes("//hotspot");
		Element curHotspotElement;
		for (Iterator iterator = hotspotNodes.iterator(); iterator.hasNext();) {
			curHotspotElement = (Element) iterator.next();
			hm.hotspots.put(curHotspotElement.attributeValue("id"),
					new Hotspot(curHotspotElement));
		}
		return hm;
	}

	public int getNumberOfHotspots() {
		return hotspots.size();
	}

	public Hotspot getHotspot(String key) {
		return hm.hotspots.get(key);
	}

	/**
	 * @return the singleton HotspotManager
	 * @throws IllegalArgumentException
	 *             in case the hotspot manager was not initialized before.
	 */
	public static HotspotManager getinstance() {
		if (hm == null)
			throw new IllegalArgumentException(
					"HotspotManager not initialized!");
		return hm;
	}

}
