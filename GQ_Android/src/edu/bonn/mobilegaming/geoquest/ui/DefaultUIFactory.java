package edu.bonn.mobilegaming.geoquest.ui;

public class DefaultUIFactory {

    private static DefaultUIFactory instance;

    private DefaultUIFactory() {

    }

    public static DefaultUIFactory getInstance() {
	if (instance == null) {
	    instance = new DefaultUIFactory();
	}
	return instance;
    }
    
    
}
