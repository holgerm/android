package com.qeevee.gq.tests;

import static com.qeevee.gq.tests.TestUtils.getFieldValue;
import static com.qeevee.gq.tests.TestUtils.startGameForTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import com.qeevee.gq.history.History;
import com.qeevee.ui.ZoomImageView;

import edu.bonn.mobilegaming.geoquest.Variables;
import edu.bonn.mobilegaming.geoquest.mission.NPCTalk;
import edu.bonn.mobilegaming.geoquest.ui.NPCTalkUIDefault;
import edu.bonn.mobilegaming.geoquest.ui.UIFactory;

@RunWith(GQTestRunner.class)
public class UIFactorySelectionTests {
    NPCTalk npcTalkM;
    NPCTalkUIDefault ui;
    ZoomImageView imageView;
    TextView talkView;
    Button proceedBT;
    CountDownTimer timer;

    public void initTestMission(String missionID) {
	npcTalkM = (NPCTalk) TestUtils.setUpMissionTest("NPCTalk",
							missionID);
	npcTalkM.onCreate(null);
	ui = (NPCTalkUIDefault) getFieldValue(npcTalkM,
					      "ui");
	imageView = (ZoomImageView) getFieldValue(ui,
						  "charImage");
	talkView = (TextView) getFieldValue(npcTalkM,
					    "dialogText");
	proceedBT = (Button) getFieldValue(npcTalkM,
					   "proceedButton");
	timer = (CountDownTimer) getFieldValue(npcTalkM,
					       "myCountDownTimer");
    }

    @After
    public void cleanUp() {
	// get rid of all variables that have been set, e.g. for checking
	// actions.
	Variables.clean();
	History.getInstance().clear();
    }

    // === TESTS FOLLOW =============================================

    @Test
    public void implicitSelectingDefaultUI() {
	// GIVEN:

	// WHEN:
	startGameForTest("UITests/SimpleNPCGameUsingDefaultUI");

	// THEN:
	shouldUseUI("Default");
    }

    @Test
    public void explicitlySelectingTestUI() {
	// GIVEN:

	// WHEN:
	startGameForTest("UITests/SimpleNPCGameUsingTestUI");

	// THEN:
	shouldUseUI("Test");
    }

    // === HELPER METHODS FOLLOW =============================================

    private void shouldUseUI(String uistyle) {
	Class<?> expectedFactoryClass = null;
	try {
	    expectedFactoryClass = Class.forName(UIFactory.class.getPackage()
		    .getName()
		    + "."
		    + uistyle
		    + UIFactory.class.getSimpleName());
	} catch (ClassNotFoundException e) {
	    fail("UIFactory class for style "
		    + uistyle
		    + " not found. \n"
		    + e.getMessage());
	}
	assertEquals(expectedFactoryClass,
		     UIFactory.getInstance().getClass());

    }
}
