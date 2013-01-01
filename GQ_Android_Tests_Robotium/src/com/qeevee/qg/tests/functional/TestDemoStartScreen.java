package com.qeevee.qg.tests.functional;

import junit.framework.AssertionFailedError;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.qeevee.gq.util.test.NetTest;

import edu.bonn.mobilegaming.geoquest.GeoQuestApp;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TestDemoStartScreen extends ActivityInstrumentationTestCase2 {
	private static final String TARGET_PACKAGE_ID = "edu.bonn.mobilegaming.geoquest";
	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "edu.bonn.mobilegaming.geoquest.Start";

	private static Class<?> launcherActivityClass;
	static {
		try {
			launcherActivityClass = Class
					.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public TestDemoStartScreen() throws ClassNotFoundException {
		super(TARGET_PACKAGE_ID, launcherActivityClass);
	}

	private Solo solo;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (!NetTest.weAreOnline()) { 
			getActivity().finish();
			throw new AssertionFailedError("Not online. Stopping this test since it depends on network connection.");
		}
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void testStartScreenDemo() {
		solo.waitForText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.start_button_game_list_text));
		solo.clickOnText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.start_button_game_list_text));
		solo.waitForText("Demos");
		solo.clickOnText("Demos");
		solo.waitForText("StartScreen Demo");
		solo.clickOnText("StartScreen Demo");
		solo.waitForActivity("edu.bonn.mobilegaming.geoquest.mission.StartAndExitScreen");
	}
	
	private String getRessourceString(int resID) {
		return GeoQuestApp.getInstance().getText(resID).toString();
	}

	@Override
	public void tearDown() throws Exception {
		try {
			solo.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		getActivity().finish();
		super.tearDown();
	}
}
