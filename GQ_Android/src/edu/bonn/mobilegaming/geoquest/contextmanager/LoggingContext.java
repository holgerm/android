/**
 * Implementation for the diploma thesis "Adaption in digitalen mobilen Lernspielen - Anwendung in GeoQuest"
 * 
 * @author Sabine Polko
 */
package edu.bonn.mobilegaming.geoquest.contextmanager;

public class LoggingContext {

	private static long gameDuration = 0;
	
	private int sequenceNumber;
	private long startTime;
	private long coveredDistance;
	
	private boolean isEmpty;
	
	public LoggingContext() {
		sequenceNumber = 0;
		startTime = 0;
		coveredDistance = 0;
		isEmpty = true;
		
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
		isEmpty = false;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
		isEmpty = false;
	}

	public long getCoveredDistance() {
		return coveredDistance;
	}

	public void setCoveredDistance(long coveredDistance) {
		this.coveredDistance = coveredDistance;
		isEmpty = false;
	}
	
	public static void setGameDuration(long milliseconds) {
		LoggingContext.gameDuration = milliseconds;
	}
	
	public static long getGameDuration() {
		return LoggingContext.gameDuration;
	}
	
}
