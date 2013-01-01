package com.qeevee.gq.util.test;

import com.jayway.android.robotium.solo.Solo;

import edu.bonn.mobilegaming.geoquest.GeoQuestApp;


public class Util {

	public static final String TARGET_PACKAGE_ID = "edu.bonn.mobilegaming.geoquest";
	public static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "edu.bonn.mobilegaming.geoquest.Start";
	public static Class<?> launcherActivityClass;

	static {
		try {
			launcherActivityClass = Class
					.forName(Util.LAUNCHER_ACTIVITY_FULL_CLASSNAME);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Starts GeoQuest App loads the repo list and selects Demos repository and within the given demo game.
	 * 
	 * @param solo
	 * @param demoName
	 */
	public static void startDemo(Solo solo, String demoName) {
		solo.waitForText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.start_button_game_list_text));
		solo.clickOnText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.start_button_game_list_text));
		solo.waitForText("Demos");
		solo.clickOnText("Demos");
		solo.waitForText(demoName);
		solo.clickOnText(demoName);
	}

	public static String getRessourceString(int resID) {
		return GeoQuestApp.getInstance().getText(resID).toString();
	}
}
