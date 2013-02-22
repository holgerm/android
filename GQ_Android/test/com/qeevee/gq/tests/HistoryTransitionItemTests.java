package com.qeevee.gq.tests;

import static com.qeevee.gq.tests.TestNPCTalkUtils.forwardUntilLastDialogItemIsShown;
import static com.qeevee.gq.tests.TestNPCTalkUtils.letCurrentDialogItemAppearCompletely;
import static com.qeevee.gq.tests.TestUtils.callMethod;
import static com.qeevee.gq.tests.TestUtils.getFieldValue;
import static com.qeevee.gq.tests.TestUtils.historyListShouldHaveLength;
import static com.qeevee.gq.tests.TestUtils.nthLastItemInHistoryShouldBe;
import static com.qeevee.gq.tests.TestUtils.prepareMission;
import static com.qeevee.gq.tests.TestUtils.startGameForTest;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.qeevee.gq.history.Actor;
import com.qeevee.gq.history.History;
import com.qeevee.gq.history.TextItem;
import com.qeevee.gq.history.TextType;
import com.qeevee.gq.history.TransitionItem;

import edu.bonn.mobilegaming.geoquest.Start;
import edu.bonn.mobilegaming.geoquest.mission.NPCTalk;
import edu.bonn.mobilegaming.geoquest.ui.NPCTalkUI;

@RunWith(GQTestRunner.class)
public class HistoryTransitionItemTests {

    NPCTalk npctalk_1, npctalk_2;
    Start start;
    NPCTalkUI npcTalkUI;

    protected void initGameWithFirstMission() {
	start = startGameForTest("HistoryTests/TransitionLinearList");
	npctalk_1 = (NPCTalk) prepareMission("NPCTalk",
					     "NPC_1",
					     start);
    }

    protected void readPrivateVariables() {
	npcTalkUI = (NPCTalkUI) getFieldValue(npctalk_1,
					      "ui");
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
	npctalk_1.onCreate(null);
	letCurrentDialogItemAppearCompletely(npctalk_1);

	// THEN:
	historyListShouldHaveLength(1);
	nthLastItemInHistoryShouldBe(1,
				     TextItem.class,
				     TextType.PLAIN,
				     Actor.NPC);
    }

    @Test
    public void to_end_of_NPC_1() {
	// GIVEN:
	initGameWithFirstMission();
	npctalk_1.onCreate(null);

	// WHEN:
	forwardUntilLastDialogItemIsShown(npctalk_1);

	// THEN:
	historyListShouldHaveLength(3);
	nthLastItemInHistoryShouldBe(1,
				     TextItem.class,
				     TextType.PLAIN,
				     Actor.NPC);
    }

    @Test
    public void finish_NPC_1() {
	// GIVEN:
	initGameWithFirstMission();
	npctalk_1.onCreate(null);
	readPrivateVariables();
	forwardUntilLastDialogItemIsShown(npctalk_1);

	// WHEN:
	npcTalkUI.showNextDialogItem();

	// THEN:
	historyListShouldHaveLength(4);
	nthLastItemInHistoryShouldBe(1,
				     TransitionItem.class);
	TransitionItem transitionItem = (TransitionItem) History.getInstance()
		.getNthLastItem(1);
	shouldHavePredeccessorOfType(transitionItem,
				     NPCTalk.class);
	shouldHaveSucceccessorOfType(transitionItem,
				     null);
    }

    @Test
    public void from_NPC_1_to_NPC_2() {
	// GIVEN:
	initGameWithFirstMission();
	npctalk_1.onCreate(null);
	readPrivateVariables();
	forwardUntilLastDialogItemIsShown(npctalk_1);

	// WHEN:
	initSecondMission();
	npcTalkUI.showNextDialogItem();
	letCurrentDialogItemAppearCompletely(npctalk_2);

	// THEN:
	historyListShouldHaveLength(5);
	nthLastItemInHistoryShouldBe(1,
				     TextItem.class,
				     TextType.PLAIN,
				     Actor.NPC);
	TransitionItem transitionItem = (TransitionItem) History.getInstance()
		.getNthLastItem(2);
	shouldHavePredeccessorOfType(transitionItem,
				     NPCTalk.class);
	shouldHaveSucceccessorOfType(transitionItem,
				     NPCTalk.class);
    }

    // === HELPER METHODS FOLLOW =============================================

    private void initSecondMission() {
	npctalk_2 = (NPCTalk) prepareMission("NPCTalk",
					     "NPC_2",
					     start);
	npctalk_2.onCreate(null);
    }

    private void shouldHavePredeccessorOfType(TransitionItem transitionItem,
					      Class<?> expectedType) {
	shouldHaveNeighborOfType(transitionItem,
				 expectedType,
				 -1);
    }

    private void shouldHaveSucceccessorOfType(TransitionItem transitionItem,
					      Class<NPCTalk> expectedType) {
	shouldHaveNeighborOfType(transitionItem,
				 expectedType,
				 1);
    }

    private void shouldHaveNeighborOfType(TransitionItem transitionItem,
					  Class<?> expectedType,
					  int n) {
	Class<?> realType = (Class<?>) callMethod(transitionItem,
						  "getNeighborClass",
						  new Class[] { int.class },
						  new Object[] { n });
	assertEquals(expectedType,
		     realType);
    }
}
