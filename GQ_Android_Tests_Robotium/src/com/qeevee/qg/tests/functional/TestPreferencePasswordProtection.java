package com.qeevee.qg.tests.functional;

import junit.framework.AssertionFailedError;
import android.preference.PreferenceActivity;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
import com.qeevee.gq.util.test.NetTest;

import edu.bonn.mobilegaming.geoquest.GeoQuestApp;
import edu.bonn.mobilegaming.geoquest.Preferences;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TestPreferencePasswordProtection extends ActivityInstrumentationTestCase2 {
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

	public TestPreferencePasswordProtection() throws ClassNotFoundException {
		super(TARGET_PACKAGE_ID, launcherActivityClass);
	}

	private Solo solo;

	@Override
	protected void setUp() throws Exception {
		if (!NetTest.weAreOnline()) {  
			throw new AssertionFailedError("Not online. Stopping tests that depend on network connections.");
		}
		solo = new Solo(getInstrumentation(), getActivity());
	}
 
	public void testPreferencesPasswordManagement() {
		solo.waitForText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.start_button_game_list_text));
		solo.clickOnMenuItem(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.prefsMenu));

		// Two cases: either (a) pref password is already set or (b) not (e.g.
		// first time call after install)
		assertTrue(solo.getCurrentActivity() instanceof PreferenceActivity);
		boolean passwordSet = ((PreferenceActivity) solo.getCurrentActivity())
				.getPreferenceScreen().getSharedPreferences()
				.contains(Preferences.PREF_KEY_PASSWORD);
		if (passwordSet) {
			((PreferenceActivity) solo.getCurrentActivity())
					.getPreferenceScreen().getSharedPreferences().edit()
					.remove(Preferences.PREF_KEY_PASSWORD).commit();
			// Case (a): no password set. Dialog for setting password is
			// immediately shown
			solo.waitForText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.pref_password_dialog_title));
			// there is only one EditText now, namely the one to enter the
			// password

			// TODO check that CANCEL leads back

			// TODO check that empty password is not accepted
		}

		// Set new password:
		setNewPassword("correctPassword");

		// Check that access to secured prefs is not possible without (i.e.
		// empty String) password:
		tryAccessWithWrongPassword("",
				"Entering empty password should display error");

		// Check that access to secured prefs is not possible with wrong
		// password:
		tryAccessWithWrongPassword("wrongPassword",
				"Entering wrong password should display error");

		// Check that access to secured prefs is possible with correct password:
		solo.clickOnText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.pref_password_title), 0, true);
		assertTrue(solo
				.waitForText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.pw_dialog_descr)));
		solo.enterText(solo.getEditText(0), "correctPasswd");
		solo.clickOnButton("OK");
		assertTrue(
				"Entering right password should bring up new password entry dialog",
				solo.waitForText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.pref_password_dialog_message)));

		// Change password via pref:
		solo.clickOnText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.pref_password_title), 0, true);
		solo.waitForText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.pw_dialog_descr));
		solo.enterText(solo.getEditText(0), "newPassword");
		assertFalse(
				"Entering a new password should lead back to preference list and not show wrong password message",
				solo.waitForText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.pw_dialog_wrong_pw)));

		// Check that old password is not valid anymore:
		tryAccessWithWrongPassword("correctPassword",
				"Entering old and invalidated password should display error");
	}

	private void setNewPassword(String correctPassword) {
		solo.waitForText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.pref_password_dialog_title));
		solo.enterText(solo.getEditText(0), correctPassword);
		solo.enterText(solo.getEditText(1), correctPassword);
		solo.clickOnButton("OK");
		assertTrue(
				"A Category SECURITY should be shown (among others) in pref list",
				solo.waitForText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.pref_cat_security)));
	}

	/**
	 * Tries to enter a wrong password, receives the mistake warning and goes
	 * back to the same situation as before.
	 * 
	 * @param givenPassword
	 *            a wrong password
	 * @param commentWhenExpectationFails
	 *            the comment as shown by JUnit when your expectation fails
	 */
	private void tryAccessWithWrongPassword(String givenPassword,
			String commentWhenExpectationFails) {
		solo.clickOnText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.pref_password_title));
		assertTrue(solo
				.waitForText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.pw_dialog_descr)));
		solo.enterText(solo.getEditText(0), givenPassword);
		solo.clickOnButton("OK");
		assertTrue(
				commentWhenExpectationFails,
				solo.waitForText(getRessourceString(edu.bonn.mobilegaming.geoquest.R.string.pw_dialog_wrong_pw)));
		solo.goBack();
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
