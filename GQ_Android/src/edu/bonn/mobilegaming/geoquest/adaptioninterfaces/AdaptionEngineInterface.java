/**
 * Implementation for the diploma thesis "Adaption in digitalen mobilen Lernspielen - Anwendung in GeoQuest"
 * 
 * @author Sabine Polko
 */
package edu.bonn.mobilegaming.geoquest.adaptioninterfaces;

import org.dom4j.Document;

/**
 * THIS INTERFACE MUST BE CONSISTENT TO THE CLASS 
 * GQAdaptioEngine.src.edu.bonn.mobilegaming.geoquest.adaptionengine.AdaptionEngine
 *
 * It's used in a reflective way to get access to the methods in the AdaptionEngine.
 */
public interface AdaptionEngineInterface {
	
	public static final String xmlTagAdaptionType = "adaptiontype";
	
	public void setType(String adaptionType);
	
	public String getAlternativeMission(String missionId);
	
	public void createPools(Document contextPool);
}
