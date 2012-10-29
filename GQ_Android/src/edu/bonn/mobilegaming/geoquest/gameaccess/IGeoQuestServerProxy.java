package edu.bonn.mobilegaming.geoquest.gameaccess;

import org.dom4j.Document;

import edu.bonn.mobilegaming.geoquest.GeoQuestProgressHandler;

public interface IGeoQuestServerProxy {
	
	public Document getRepoMetadata(
			final GeoQuestProgressHandler progressHandler);

}
