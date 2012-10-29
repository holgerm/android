package edu.bonn.mobilegaming.geoquest.contextmanager;

public enum xmlTagsContext {
	MAX_DURATION("maxduration");

	private String xmlTag;
	
	private xmlTagsContext(String tag) {
		xmlTag = tag;
	}
	
	public String getString(){
		return xmlTag;
	}
	
}
