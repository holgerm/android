package com.qeevee.qg.tests.functional;

import junit.framework.AssertionFailedError;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.qeevee.gq.util.test.NetTest;
import com.qeevee.gq.util.test.Util;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TestStartActivity extends ActivityInstrumentationTestCase2 {
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

	public TestStartActivity() throws ClassNotFoundException {
		super(TARGET_PACKAGE_ID, launcherActivityClass);
	}

	private Solo solo;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (!NetTest.weAreOnline()) {
			getActivity().finish();
			throw new AssertionFailedError(
					"Not online. Stopping this test since it depends on network connection.");
		}
		solo = new Solo(getInstrumentation(), getActivity());

		// TODO we assume Internet Connection here but we should care for
		// off line case and other problems like no SD Card etc.
	}

	public void testStartActivity() {
		solo.waitForActivity(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
		assertEquals("Only the Start Activity should be opened", 1, solo
				.getAllOpenedActivities().size());
		// Check that the Name of the Game is visible:
		assertTrue("Start Activity should show Name GeoQuest on Screen",
				solo.searchText("GeoQuest", true));
		solo.sendKey(Solo.MENU);

		// menu shown now:
		assertTrue(
				"Start Activity should show Reload Repositories Menu Item",
				solo.searchText(Util
						.getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.reloadGamesMenu)));
		assertTrue(
				"Start Activity should show Quit Menu Item",
				solo.searchText(Util
						.getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.quitMenu)));
		assertTrue(
				"Start Activity should show Preferences Menu Item",
				solo.searchText(Util
						.getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.prefsMenu)));
	}

	/**
	 * TODO: Depending on whether recently (after the last installation) a game
	 * has been played the start activity screen looks different.
	 * </br></br>
	 * The following images show that; first the screen when no game has been
	 * played before; second the screen if a game has recently been played.
	 * 
	 * <ol>
	 * <li><img src="StartActivityScreenGivenNoGameHadBeenPlayedBefore.png" align="middle"/></li>
	 * 
	 * <li><img src=
	 * "StartActivityScreenGivenTheGameStartScreenDemoHasBeenPlayedBefore.png" align="middle"/></li>
	 * </ol>
	 */
    public void testSelectANewQuestButton() {
	solo.waitForText(Util
		.getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.start_button_game_list_text));
	solo.clickOnText(Util
		.getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.start_button_game_list_text));

	// Check that a hint on the repositories is visible:
	assertTrue("RepoList should show heading",
		   solo.waitForText(Util
			   .getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.start_repoList_header)));
	assertEquals("Start and RepositoryList Activities should be open",
		     2,
		     solo.getAllOpenedActivities().size());
	solo.clickOnText("Demos",
			 0,
			 true);

	// Check that a hint on the repositories is visible:
	assertTrue("GameList should show heading",
		   solo.waitForText(Util
			   .getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.start_gameList_headerPart)));
	assertEquals("Start RepositoryList and GameList Activities should be open",
		     3,
		     solo.getAllOpenedActivities().size());
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
