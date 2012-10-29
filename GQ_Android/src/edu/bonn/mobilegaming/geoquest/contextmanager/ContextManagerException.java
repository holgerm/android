/**
 * Implementation for the diploma thesis "Adaption in digitalen mobilen Lernspielen - Anwendung in GeoQuest"
 * 
 * @author Sabine Polko
 */
package edu.bonn.mobilegaming.geoquest.contextmanager;

public class ContextManagerException extends RuntimeException {
	
	private static final long serialVersionUID = -6231557689498145297L;

	public ContextManagerException() {
		super();
	}
	
	public ContextManagerException(String err) {
		super(err);
	}

}
