package com.qeevee.gq.tests;

import static com.qeevee.gq.tests.TestUtils.getFieldValue;
import static com.qeevee.gq.tests.TestUtils.setUpMissionTest;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.widget.TextView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

import edu.bonn.mobilegaming.geoquest.mission.MultipleChoiceQuestion;

@RunWith(RobolectricTestRunner.class)
public class MultipleChoiceQuestionMissionTests {
    MultipleChoiceQuestion mcq;

    @Before
    public void setUp() throws Exception {
	mcq = (MultipleChoiceQuestion) setUpMissionTest("MultipleChoiceQuestion",
							    "M_1");
	mcq.onCreate(null);
    }

    @Test
    public void checkQuestionAndAnswers() {
	assertEquals("Question text should be stored correct in model variable",
		     "Text of the question.",
		     mcq.questionText);
	TextView tv = (TextView) getFieldValue(mcq,
					       "mcTextView");
	assertEquals("Question text should be stored correct in UI element",
		     "Text of the question.",
		     tv.getText().toString());
	assertEquals(3,
		     mcq.answers.size());
    }

}
