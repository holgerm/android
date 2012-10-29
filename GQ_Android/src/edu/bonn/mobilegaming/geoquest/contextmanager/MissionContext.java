/**
 * Implementation for the diploma thesis "Adaption in digitalen mobilen Lernspielen - Anwendung in GeoQuest"
 * 
 * @author Sabine Polko
 */
package edu.bonn.mobilegaming.geoquest.contextmanager;

import android.location.Location;

public class MissionContext {
	
	private long missionDuration;
	private long elapsedTime;
	private int result;
	private Location location;
	
	private boolean isEmpty;

	public MissionContext() {
		missionDuration = 0;
		elapsedTime = 0;
		result = 0;
		location = null;
		isEmpty = true;
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}

	public long getMissionDuration() {
		return missionDuration;
	}

	public void setMissionDuration(long missionDuration) {
		this.missionDuration = missionDuration;
		isEmpty = false;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
		isEmpty = false;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
		isEmpty = false;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
		isEmpty = false;
	}

}
