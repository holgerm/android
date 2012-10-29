package edu.bonn.mobilegaming.geoquest;

/**
 * HotspotListener Interface
 * 
 * @author Folker Hoffmann
 * @author Krischan Udelhoven
 */
public interface HotspotListener {
	/**
	 * is called when the player enters the interaction circle of the hotspot
	 * 
	 * @param h
	 */
	void onEnterRange(HotspotOld h);

	/**
	 * is called when the player leaves the interaction circle of the hotspot
	 * 
	 * @param h
	 */
	void onLeaveRange(HotspotOld h);
}
