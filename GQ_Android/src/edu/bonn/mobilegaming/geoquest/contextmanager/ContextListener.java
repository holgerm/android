/**
 * Implementation for the diploma thesis "Adaption in digitalen mobilen Lernspielen - Anwendung in GeoQuest"
 * 
 * @author Sabine Polko
 */
package edu.bonn.mobilegaming.geoquest.contextmanager;

import android.location.Location;

public abstract class ContextListener {
	
	
	protected ContextListenerType listenerType;

	public abstract void update(Location loc);
	
	public abstract void update(GameContext context);
	
	public ContextListenerType getType() {
		return listenerType;
	}

}
