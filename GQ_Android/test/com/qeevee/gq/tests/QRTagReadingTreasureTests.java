package com.qeevee.gq.tests;

import static com.qeevee.gq.tests.TestUtils.getFieldValue;
import static com.qeevee.gq.tests.TestUtils.getResString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.widget.Button;
import android.widget.TextView;

import com.qeevee.gq.history.History;

import edu.bonn.mobilegaming.geoquest.R;
import edu.bonn.mobilegaming.geoquest.Variables;
import edu.bonn.mobilegaming.geoquest.mission.QRTagReadingTreasure;

@RunWith(GQTestRunner.class)
public class QRTagReadingTreasureTests {

    QRTagReadingTreasure mission;
    private int buttonMode;
    private TextView taskTextView;
    private Button button;
    private String DEFAULT_NEXTBUTTONTEXT, DEFAULT_SCANBUTTONTEXT,
	    DEFAULT_TASKDESCRIPTION;
    private int START_SCAN, END_MISSION;

    @Before
    public void cleanUp() {
	// get rid of all variables that have been set, e.g. for checking
	// actions.
	Variables.clean();
	History.getInstance().clear();
    }

    public void initTestMission(String missionID) {
	mission = (QRTagReadingTreasure) TestUtils
		.setUpMissionTest("QRTagReadingTreasure",
				  missionID);
	try {
	    mission.onCreate(null);
	} catch (NullPointerException npe) {
	    fail("Mission with id \"" + missionID + "\" missing. (NPE: "
		    + npe.getMessage() + ")");
	}
	loadFieldsOfObjectUnderTest();
    }

    private void loadFieldsOfObjectUnderTest() {
	taskTextView = (TextView) getFieldValue(mission,
						"taskTextView");
	DEFAULT_NEXTBUTTONTEXT = getResString(R.string.button_text_proceed);
	DEFAULT_SCANBUTTONTEXT = getResString(R.string.qrtagreading_startscanbutton_default);
	DEFAULT_TASKDESCRIPTION = getResString(R.string.qrtagreading_taskdescription_default);
	buttonMode = (Integer) getFieldValue(mission,
					     "buttonMode");
	button = (Button) getFieldValue(mission,
					"okButton");
	START_SCAN = (Integer) getFieldValue(mission,
					     "START_SCAN");
	END_MISSION = (Integer) getFieldValue(mission,
					      "END_MISSION");
    }

    // === TESTS FOLLOW =============================================

    @Test
    public void initialization_With_Explicit_Attribute_Values() {
	// GIVEN:
	// nothing

	// WHEN:
	initTestMission("With_Explicit_Attribute_Values");

	// THEN:
	shouldBeInMode(START_SCAN);
	shouldShowTaskText("This is a demo task description.");
	shouldShowButtonText("Start demo scan ...");
    }

    // === HELPER METHODS FOLLOW =============================================

    private void shouldShowTaskText(String expectedText) {
	assertEquals(expectedText,
		     taskTextView.getText().toString());
    }

    private void shouldShowButtonText(String expectedText) {
	assertEquals(expectedText,
		     button.getText().toString());
    }

    private void shouldBeInMode(int expectedMode) {
	assertEquals(expectedMode,
		     buttonMode);
    }

}
