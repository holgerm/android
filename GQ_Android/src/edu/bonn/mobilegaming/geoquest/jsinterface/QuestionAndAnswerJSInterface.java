/**
 * 
 */
package edu.bonn.mobilegaming.geoquest.jsinterface;

import android.util.Log;
import edu.bonn.mobilegaming.geoquest.Globals;
import edu.bonn.mobilegaming.geoquest.mission.Mission;
import edu.bonn.mobilegaming.geoquest.mission.WebTech;

/**
 * @author Sabine Polko
 */
public class QuestionAndAnswerJSInterface {
	
	private Mission mission;
	private WebTech webTechView;
	
	private final String LOG_TAG = "QuestionAndAnswerJSInterface";
	
	public QuestionAndAnswerJSInterface(WebTech webTechView, Mission mission) {
		this.mission = mission;
		this.webTechView = webTechView;
	}

	/**
	 * Function that is called by the JS-File, when the mission is finished.
	 * 
	 * @param success true if the mission was successfully completed, otherwise false
	 */
	public void endMission(boolean success) {
		if (success) {
			webTechView.endWebMission(Globals.STATUS_SUCCEEDED);
		} else {
			webTechView.endWebMission(Globals.STATUS_FAIL);
		}
	}
	
	/**
	 * Function is called by the JS-File. It returns the string representation of 
	 * the mission node.
	 * 
	 * @return string 
	 */
	public String getXmlDocument() {
		return mission.xmlMissionNode.asXML();
	}

	
	// TODO -- Sabine -- WebTech log-Funktionen sind in allen JSInterf. gleich! Extrahieren...!!
	/**
	 * Prints a debug log message on LogCat. Can be used for logging in JS-Files.
	 * 
	 * @param str debug message printed on LogCat
	 */
	public void logDebug(String str) {
		Log.d(LOG_TAG, str);
	}
	
	/**
	 * Prints an error log message on LogCat. Can be used for logging in JS-Files.
	 * 
	 * @param str error message printed on LogCat
	 */
	public void logError(String str) {
		Log.e(LOG_TAG, str);
	}
	
	/**
	 * Prints an info log message on LogCat. Can be used for logging in JS-Files.
	 * 
	 * @param str info message printed on LogCat
	 */
	public void logInfo(String str) {
		Log.i(LOG_TAG, str);
	}
	
	public void print(String s) {
		System.out.println("--------------- " + s);
	}
}
