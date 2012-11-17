package edu.bonn.mobilegaming.geoquest.mission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.bonn.mobilegaming.geoquest.Globals;
import edu.bonn.mobilegaming.geoquest.R;
import edu.bonn.mobilegaming.geoquest.Variables;

/**
 * Simple multiple choice question and answer mission.
 * 
 * @author Holger Muegge
 */
public class MultipleChoiceQuestion extends InteractiveMission {
    /** layout */
    private LinearLayout mcButtonPanel;
    /** text view for displaying text */
    private TextView mcTextView;
    private Button proceedButton;
    private Button restartButton;

    private int mode = 0; // UNDEFINED MODE AT START IS USED TO TRIGGER INIT!
    private static final int MODE_QUESTION = 1;
    private static final int MODE_REPLY_TO_CORRECT_ANSWER = 2;
    private static final int MODE_REPLY_TO_WRONG_ANSWER = 3;

    public List<Answer> answers = new ArrayList<Answer>();
    private Answer selectedAnswer;
    public String questionText;

    /**
     * The attribute shuffle can be omitted. If it's set to the value "answers",
     * answers will be shuffled.
     */
    public final static String SHUFFLE_ANSWERS = "answers";

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	initContentView();
	initQuestion();
	setMode(MODE_QUESTION);
    }

    /**
     * Clears and (re-) populates the complete view.
     * 
     * @param newMode
     */
    private void setMode(int newMode) {
	if (mode == newMode)
	    return;
	// real change in mode:
	mode = newMode;
	mcButtonPanel.removeAllViews();
	switch (mode) {
	case MODE_QUESTION:
	    setUpQuestionView();
	    break;
	case MODE_REPLY_TO_CORRECT_ANSWER:
	    setMCTextViewToReply();
	    setMCButtonPanel(proceedButton);
	    invokeOnSuccessEvents();
	    break;
	case MODE_REPLY_TO_WRONG_ANSWER:
	    setMCTextViewToReply();
	    setMCButtonPanel(restartButton);
	    invokeOnFailEvents();
	    break;
	}
    }

    private void setMCButtonPanel(Button button) {
	mcButtonPanel.addView(button);
	// set the button text:
	if (selectedAnswer.nextbuttontext != null) {
	    // game specific if specified:
	    button.setText(selectedAnswer.nextbuttontext);
	} else {
	    // generic if not specified:
	    if (button == proceedButton)
		button.setText(getString(R.string.questionandanswer_next));
	    if (button == restartButton)
		button.setText(getString(R.string.questionandanswer_again));
	}
    }

    private void setMCTextViewToReply() {
	if (selectedAnswer.onChoose != null) {
	    mcTextView.setText(selectedAnswer.onChoose);
	} else {
	    if (selectedAnswer.correct) {
		mcTextView
			.setText(getString(R.string.questionandanswer_rightAnswer));
	    } else {
		mcTextView
			.setText(getString(R.string.questionandanswer_wrongAnswer));
	    }
	}
    }

    private void initContentView() {
	setContentView(R.layout.multiplechoice);
	mcTextView = (TextView) findViewById(R.id.mcTextView);
	mcButtonPanel = (LinearLayout) findViewById(R.id.mcButtonPanel);
	// prefab neccessary buttons:
	makeProceedButton();
	makeRestartButton();
    }

    private void makeProceedButton() {
	proceedButton = new Button(this);
	proceedButton.setText(getString(R.string.questionandanswer_next));
	proceedButton.setWidth(LayoutParams.FILL_PARENT);
	proceedButton.setOnClickListener(new OnClickListener() {

	    public void onClick(View v) {
		// TODO rework and test
		if (selectedAnswer.correct) {
		    invokeOnSuccessEvents();
		}
		else {
		    invokeOnFailEvents();
		}
		if (loopUntilSuccess) {
		    if (!selectedAnswer.correct) {
			setMode(MODE_QUESTION);
		    } else {
			finish(Globals.STATUS_SUCCESS);
		    }
		} else {
		    finish(selectedAnswer.correct ? Globals.STATUS_SUCCESS
			    : Globals.STATUS_FAIL);
		}
	    }
	});
    }

    private void makeRestartButton() {
	restartButton = new Button(this);
	restartButton.setText(getString(R.string.questionandanswer_again));
	restartButton.setWidth(LayoutParams.FILL_PARENT);
	restartButton.setOnClickListener(new OnClickListener() {

	    public void onClick(View v) {
		setMode(MODE_QUESTION);
	    }
	});
    }

    @SuppressWarnings("unchecked")
    private void initQuestion() {
	Element xmlQuestion = (Element) mission.xmlMissionNode
		.selectSingleNode("./question");
	questionText = xmlQuestion.selectSingleNode("./questiontext").getText()
		.replaceAll("\\s+",
			    " ").trim();
	List<Element> xmlAnswers = xmlQuestion.selectNodes("./answer");
	for (Iterator<Element> j = xmlAnswers.iterator(); j.hasNext();) {
	    Element xmlAnswer = j.next();
	    Attribute correct = (Attribute) xmlAnswer
		    .selectSingleNode("./@correct");
	    Answer answer = new Answer();
	    if ((correct != null) && (correct.getText().equals("1")))
		answer.correct = true;
	    answer.answertext = xmlAnswer.getText().replaceAll("\\s+",
							       " ").trim();

	    Attribute onChoose = ((Attribute) xmlAnswer
		    .selectSingleNode("./@onChoose"));
	    if (onChoose != null)
		answer.onChoose = onChoose.getText().replaceAll("\\s+",
								" ").trim();

	    Attribute nbt = ((Attribute) xmlAnswer
		    .selectSingleNode("./@nextbuttontext"));
	    if (nbt != null)
		answer.nextbuttontext = nbt.getText().replaceAll("\\s+",
								 " ").trim();

	    answers.add(answer);
	}
	shuffleAnswers();
    }

    private void shuffleAnswers() {
	CharSequence shuffleString = getMissionAttribute("shuffle",
							 R.string.default_question_shuffle_mode)
		.toString();

	if (shuffleString.equals(SHUFFLE_ANSWERS)) {
	    java.util.Collections.shuffle(answers);
	}
    }

    private void setUpQuestionView() {
	mcButtonPanel.removeAllViews();
	mcTextView.setText(questionText);
	for (Iterator<Answer> i = answers.iterator(); i.hasNext();) {
	    Answer answer = i.next();
	    Button answerButton = new Button(MultipleChoiceQuestion.this);
	    answerButton.setText(answer.answertext);
	    answerButton.setWidth(LayoutParams.FILL_PARENT);
	    answerButton.setTag(answer);
	    answerButton.setOnClickListener(new AnswerClickListener());
	    mcButtonPanel.addView(answerButton);
	}
    }

    /**
     * called when the player taps on an answer
     */
    private class AnswerClickListener implements View.OnClickListener {
	public void onClick(View view) {
	    selectedAnswer = (Answer) view.getTag();
	    // set chosen answer text as result in mission specific variable:
	    Variables.registerMissionResult(mission.id,
					    selectedAnswer.answertext);
	    if (selectedAnswer.correct) {
		setMode(MODE_REPLY_TO_CORRECT_ANSWER);
	    } else {
		setMode(MODE_REPLY_TO_WRONG_ANSWER);
	    }
	}
    }

    /**
     * Simple class that encapsulates an answer
     */
    private class Answer {
	public String answertext;
	public Boolean correct = false;
	public String onChoose;
	public String nextbuttontext = null;
    }

    public void onBlockingStateUpdated(boolean blocking) {
	mcButtonPanel.setEnabled(!blocking);
    }

}
