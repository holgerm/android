package edu.bonn.mobilegaming.geoquest.mission;

import org.dom4j.Element;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.qeevee.gq.xml.XMLUtilities;

import edu.bonn.mobilegaming.geoquest.BlockableAndReleasable;
import edu.bonn.mobilegaming.geoquest.GeoQuestActivity;
import edu.bonn.mobilegaming.geoquest.GeoQuestApp;
import edu.bonn.mobilegaming.geoquest.Globals;
import edu.bonn.mobilegaming.geoquest.MissionOrToolActivity;
import edu.bonn.mobilegaming.geoquest.contextmanager.ContextManager;
import edu.bonn.mobilegaming.geoquest.ui.InteractionBlocker;
import edu.bonn.mobilegaming.geoquest.ui.InteractionBlockingManager;

public abstract class MissionActivity extends GeoQuestActivity implements
		MissionOrToolActivity {
	protected Mission mission;

	private ContextManager contextManager;
	protected int missionResultInPercent = 100;

	/** Intent used to return values to the parent mission */
	protected Intent result;

	/**
	 * Back button Handler quits the Mission, when back button is hit.
	 */
	@Override
	public boolean onKeyDown(final int keyCode, KeyEvent event) {
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK: // Back => Cancel
			if (mission.cancelStatus == 0) {
				Log.d(this.getClass().getName(),
						"Back Button was pressed, but mission may not be cancelled.");
				return true;
			} else {
				finish(mission.cancelStatus);
				return true;
			}
		case KeyEvent.KEYCODE_SEARCH:
			// ignore search button
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Finishes the mission activity and sets the result code
	 * 
	 * TODO only real stati should be allowed, i.e. FAIL and SUCCESS. Hence, we
	 * can use boolena here and make calls much more readable. Cf.
	 * {@link MultipleChoiceQuestion}.
	 * 
	 * @param status
	 *            Mission.STATUS_SUCCESS or FAIL or NEW
	 */
	public void finish(Double status) {
		mission.setStatus(status);

		// TODO -- Sabine -- Result anpassen., bzw. in jeder Mission einzel
		// aufrufen. mission.getResult()....
		// contextManager.setEndValues(mission.id, missionResultInPercent);
		mission.applyOnEndRules();

		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		GeoQuestApp.setCurrentActivity(this);
	}

	/**
	 * Called by the android framework when the mission is created. Reads some
	 * basic informations from the xml file.
	 * 
	 * @param savedInstanceState
	 *            Bundle with the id of the mission. If null then this is the
	 *            first mission and the id is zero.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(this.getClass().getName(), "creating activity");
		GeoQuestApp.setCurrentActivity(this);
		ibm = new InteractionBlockingManager(this);

		super.onCreate(savedInstanceState);

		// get extras
		Bundle extras = getIntent().getExtras();
		String id = extras.getString("missionID");
		mission = Mission.get(id);

		contextManager = GeoQuestActivity.contextManager;
		contextManager.setStartValues(id);

		mission.setStatus(Globals.STATUS_RUNNING);
		mission.applyOnStartRules();
	}

	/**
	 * TODO should be moved to an XMLHelper class as a static helper method.
	 * Requires the mission node to be passed as additional argument.
	 * 
	 * TODO create versions for boolean and Integer also.
	 * 
	 * Also move the static flag NECESSARY_ATTRIBUTE.
	 * 
	 * @param attributeName
	 * @param defaultAsResourceID
	 *            either a defined resource string or
	 *            {@link XMLUtilities#NECESSARY_ATTRIBUTE} to specify that this
	 *            attribute MUST be given explicitly, or
	 *            {@link XMLUtilities#OPTIONAL_ATTRIBUTE} to state that it can
	 *            be omitted even without a default. In the latter case the
	 *            method returns null.
	 * @return the corresponding attribute value as specified in the game.xml or
	 *         null if the attribute is optional and not specified
	 * @throws IllegalArgumentException
	 *             if the attribute is necessary but not given in the game.xml
	 */
	protected CharSequence getMissionAttribute(String attributeName,
			int defaultAsResourceID) {
		String attributeAsText = mission.xmlMissionNode
				.attributeValue(attributeName);
		if (attributeAsText == null)
			if (defaultAsResourceID == XMLUtilities.NECESSARY_ATTRIBUTE)
				// attribute needed but not found => error in game.xml:
				throw new IllegalArgumentException("Necessary attribute \""
						+ attributeName
						+ "\" missing. Rework game specification.");
			else if (defaultAsResourceID == XMLUtilities.OPTIONAL_ATTRIBUTE) {
				// optional attribute not set in game.xml => return null:
				return null;
			} else
				// attribute not set in game.xml but given as parameter => use
				// referenced resource as default and return its value:
				return GeoQuestApp.getInstance().getText(defaultAsResourceID);
		else
			return (CharSequence) attributeAsText;
	}

	public Element getXML() {
		return mission.xmlMissionNode;
	}
	
	protected InteractionBlockingManager ibm;

	public BlockableAndReleasable blockInteraction(InteractionBlocker newBlocker) {
		return ibm.blockInteraction(newBlocker);
	}

	public void releaseInteraction(InteractionBlocker blocker) {
		ibm.releaseInteraction(blocker);
	}

}