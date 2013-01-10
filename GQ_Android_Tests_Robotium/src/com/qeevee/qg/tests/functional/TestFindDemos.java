package com.qeevee.qg.tests.functional;

import junit.framework.AssertionFailedError;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.qeevee.gq.util.test.NetTest;

import edu.bonn.mobilegaming.geoquest.GeoQuestApp;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TestFindDemos extends ActivityInstrumentationTestCase2 {
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

	public TestFindDemos() throws ClassNotFoundException {
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

	public void testStartAndFindDemos() {
		// TODO we assume Internet Connection here but we should care for
		// off line case and other problems like no SD Card etc.

		solo.waitForText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.start_button_game_list_text));
		assertEquals("Only the Start Activity should be opened", 1, solo
				.getAllOpenedActivities().size());
		// Check that the Name of the Game is visible:
		assertTrue("Start Activity should show Name GeoQuest on Screen",
				solo.searchText("GeoQuest", true));

		solo.clickOnText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.start_button_game_list_text));

		// Check that a hint on the repositories is visible:
		assertTrue(
				"RepoList should show heading",
				solo.waitForText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.start_repoList_header)));
		assertEquals("Start and RepositoryList Activities should be open", 2,
				solo.getAllOpenedActivities().size());
		solo.clickOnText("Demos", 0, true);

		// Check that a hint on the repositories is visible:
		assertTrue(
				"GameList should show heading",
				solo.waitForText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.start_gameList_headerPart)));
		assertEquals(
				"Start RepositoryList and GameList Activities should be open",
				3, solo.getAllOpenedActivities().size());
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
