package com.qeevee.gq.xml;

public interface GQML_DEFAULTS {

	/**
	 * Default radius for hotspots if neither in hotspots nor in hotspot
	 * elements an attribute <code>radius</code> is set.
	 */
	static final int HOTSPOT_RADIUS = 25;

	/**
	 * Default initial visibility for hotspots if neither in hotspots nor in hotspot
	 * elements an attribute <code>initialVisibility</code> is set.
	 */
	static final boolean HOTSPOT_INITIAL_VISIBILITY = true;
	
	/**
	 * Default initial activation for hotspots if neither in hotspots nor in hotspot
	 * elements an attribute <code>initialActivation</code> is set.
	 */
	static final boolean HOTSPOT_INITIAL_ACTIVATION = true;
}
