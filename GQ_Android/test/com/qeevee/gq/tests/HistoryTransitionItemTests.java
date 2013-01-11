package com.qeevee.gq.tests;

import static com.qeevee.gq.tests.TestNPCTalkUtils.letCurrentDialogItemAppearCompletely;
import static com.qeevee.gq.tests.TestUtils.getFieldValue;
import static com.qeevee.gq.tests.TestUtils.historyListShouldHaveLength;
import static com.qeevee.gq.tests.TestUtils.nthLastItemInHistoryShouldBe;
import static com.qeevee.gq.tests.TestUtils.prepareMission;
import static com.qeevee.gq.tests.TestUtils.startGameForTest;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.os.CountDownTimer;

import com.qeevee.gq.history.Actor;
import com.qeevee.gq.history.History;
import com.qeevee.gq.history.TextItem;
import com.qeevee.gq.history.TextType;
import com.xtremelabs.robolectric.RobolectricTestRunner;

import edu.bonn.mobilegaming.geoquest.Start;
import edu.bonn.mobilegaming.geoquest.mission.NPCTalk;

@RunWith(RobolectricTestRunner.class)
public class HistoryTransitionItemTests {

    CountDownTimer timer;
    NPCTalk npcTalk;
    Start start;

    public void initGameWithFirstMission() {
	start = startGameForTest("HistoryTests/TransitionLinearList");
	npcTalk = (NPCTalk) prepareMission("NPCTalk",
					   "NPC_1",
					   start);
	timer = (CountDownTimer) getFieldValue(npcTalk,
					       "myCountDownTimer");
    }

    @After
    public void cleanUp() {
	History.getInstance().clear();
    }

    // === TESTS FOLLOW =============================================

    @Test
    public void prepare_NPC_1() {
	// GIVEN:
	initGameWithFirstMission();

	// WHEN:

	// THEN:
	historyListShouldHaveLength(0);
    }

    @Test
    public void start_NPC_1() {
	// GIVEN:
	initGameWithFirstMission();

	// WHEN:
	npcTalk.onCreate(null);
	letCurrentDialogItemAppearCompletely(npcTalk,
					     timer);

	// THEN:
	historyListShouldHaveLength(1);
	nthLastItemInHistoryShouldBe(1,
				     TextItem.class,
				     TextType.PLAIN,
				     Actor.NPC);
    }
}
