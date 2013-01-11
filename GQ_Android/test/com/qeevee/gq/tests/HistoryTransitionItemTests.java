package com.qeevee.gq.tests;

import static com.qeevee.gq.tests.TestUtils.historyListShouldHaveLength;
import static com.qeevee.gq.tests.TestUtils.nthLastItemInHistoryShouldBe;
import static com.qeevee.gq.tests.TestUtils.startGameForTest;
import static com.qeevee.gq.tests.TestUtils.prepareMission;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.qeevee.gq.history.Actor;
import com.qeevee.gq.history.History;
import com.qeevee.gq.history.TextItem;
import com.qeevee.gq.history.TextType;
import com.xtremelabs.robolectric.RobolectricTestRunner;

import edu.bonn.mobilegaming.geoquest.GeoQuestActivity;
import edu.bonn.mobilegaming.geoquest.Start;
import edu.bonn.mobilegaming.geoquest.mission.NPCTalk;

@RunWith(RobolectricTestRunner.class)
public class HistoryTransitionItemTests {

    @After
    public void cleanUp() {
	History.getInstance().clear();
    }

    // === TESTS FOLLOW =============================================

    @Test
    public void prepare_NPC_1() {
	// GIVEN:
	Start start = startGameForTest("HistoryTests/TransitionLinearList");

	// WHEN:
	NPCTalk mission = (NPCTalk) prepareMission("NPCTalk",
						   "NPC_1",
						   start);

	// THEN:
	historyListShouldHaveLength(0);
    }

    @Test
    public void start_NPC_1() {
	// GIVEN:
	Start start = startGameForTest("HistoryTests/TransitionLinearList");
	NPCTalk mission = (NPCTalk) prepareMission("NPCTalk",
						   "NPC_1",
						   start);

	// WHEN:
	mission.onCreate(null);

	// THEN:
	historyListShouldHaveLength(1);
	nthLastItemInHistoryShouldBe(1,
				     TextItem.class,
				     TextType.PLAIN,
				     Actor.NPC);
    }
}
