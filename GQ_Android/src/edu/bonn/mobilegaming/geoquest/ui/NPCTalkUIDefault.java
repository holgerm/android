package edu.bonn.mobilegaming.geoquest.ui;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.qeevee.ui.ZoomImageView;

import edu.bonn.mobilegaming.geoquest.GeoQuestApp;
import edu.bonn.mobilegaming.geoquest.R;
import edu.bonn.mobilegaming.geoquest.mission.DialogItem;
import edu.bonn.mobilegaming.geoquest.mission.NPCTalk;

public class NPCTalkUIDefault extends NPCTalkUI {

    private ZoomImageView charImage;
    private Button button;
    private TextView dialogText;
    private ScrollView scrollView;

    private DialogItem currentDialogItem = null;

    private static final long milliseconds_per_part = 100;

    private OnClickListener showNextDialogListener = new OnClickListener() {
	public void onClick(View v) {
	    showNextDialogItem();
	}
    };

    private OnClickListener endMissionListener = new OnClickListener() {
	public void onClick(View v) {
	    getNPCTalk().finish();
	}
    };
    private CharSequence nextDialogButtonTextDefault;

    public CharSequence getNextDialogButtonTextDefault() {
	return nextDialogButtonTextDefault;
    }

    private void refreshButton() {
	if (!getNPCTalk().hasMoreDialogItems()) {
	    button.setOnClickListener(endMissionListener);
	    setButtonTextEndMission();
	} else {
	    button.setOnClickListener(showNextDialogListener);
	    setButtonTextNextDialogItem();
	}
    }

    private void setButtonTextNextDialogItem() {
	if (currentDialogItem.getNextDialogButtonText() != null)
	    button.setText(currentDialogItem.getNextDialogButtonText());
	else
	    button.setText(R.string.button_text_next);
    }

    private void setButtonTextEndMission() {
	if (getNPCTalk().getMissionAttribute("endbuttontext") != null)
	    button.setText(getNPCTalk().getMissionAttribute("endbuttontext"));
	else
	    button.setText(R.string.button_text_proceed);
    }

    public NPCTalkUIDefault(NPCTalk activity) {
	super(activity);
	setImage(getNPCTalk().getMissionAttribute("image"));
	setNextDialogButtonText(getNPCTalk()
		.getMissionAttribute("nextdialogbuttontext",
				     R.string.button_text_next));
	showNextDialogItem();
    }

    private void
	    setNextDialogButtonText(CharSequence nextDialogButtonTextDefault) {
	this.nextDialogButtonTextDefault = nextDialogButtonTextDefault;
    }

    @Override
    View createView() {
	LayoutInflater inflater = (LayoutInflater) activity
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	view = inflater.inflate(R.layout.npctalk,
				null);
	charImage = (ZoomImageView) view.findViewById(R.id.npcimage);
	button = (Button) view.findViewById(R.id.proceedButton);
	return view;
    }

    private boolean setImage(CharSequence pathToImageFile) {
	if (pathToImageFile == null) {
	    charImage.setVisibility(View.GONE);
	    return false;
	}
	try {
	    charImage.setRelativePathToImageBitmap(pathToImageFile.toString());
	    return true;
	} catch (IllegalArgumentException iae) {
	    charImage.setVisibility(View.GONE);
	    return false;
	}
    }

    @Override
    public void showNextDialogItem() {
	if (getNPCTalk().hasMoreDialogItems()) {
	    currentDialogItem = getNPCTalk().getNextDialogItem();
	    displaySpeaker();
	    if (currentDialogItem.getAudioFilePath() != null)
		GeoQuestApp.playAudio(currentDialogItem.getAudioFilePath(),
				      currentDialogItem.blocking);
	    WordTicker ticker = new WordTicker();
	    ticker.start();
	}
	refreshButton();
    }

    private void displaySpeaker() {
	if (currentDialogItem.getSpeaker() == null)
	    return;
	dialogText.append(Html.fromHtml("<b>"
		+ currentDialogItem.getSpeaker()
		+ ": </b>"));
    }

    private void updateButtonText(DialogItem di) {
	CharSequence buttonText = null;
	if (getNPCTalk().hasMoreDialogItems()) {
	    if (di.getNextDialogButtonText() != null)
		buttonText = di.getNextDialogButtonText();
	    else
		buttonText = activity.getText(R.string.button_text_next);
	} else {
	    buttonText = activity.getText(R.string.button_text_proceed);
	}
	button.setText(buttonText);
    }

    /**
     * Display the text of the given DialogItem word by word.
     */
    private class WordTicker extends CountDownTimer implements
	    InteractionBlocker {

	private WordTicker() {
	    super(milliseconds_per_part
		    * (currentDialogItem.getNumParts() + 1),
		    milliseconds_per_part);
	    // block interaction on the NPCTalk using this Timer as Blocker
	    // monitor:
	    blockInteraction(this);
	    updateButtonText(currentDialogItem);
	}

	@Override
	public void onTick(long millisUntilFinished) {
	    CharSequence next = currentDialogItem.getNextPart();
	    if (next != null) {
		dialogText.append(next);
		scrollView.fullScroll(View.FOCUS_DOWN);
	    }
	}

	@Override
	public void onFinish() {
	    // Zur Sicherheit, da manchmal Wörter verschluckt werden (nicht
	    // ausreichend genauer timer!)
	    CharSequence next = currentDialogItem.getNextPart();
	    while (next != null) {
		dialogText.append(next);
		scrollView.fullScroll(View.FOCUS_DOWN);
		next = currentDialogItem.getNextPart();
	    }
	    scrollView.fullScroll(View.FOCUS_DOWN);
	    getNPCTalk().hasShownDialogItem(currentDialogItem);
	    // release
	    // blocked
	    // interaction
	    // on the
	    // NPCTalk using
	    // this Timer as
	    // Blocker monitor:
	    getNPCTalk().releaseInteraction(this);
	}

    }

    public void onBlockingStateUpdated(boolean isBlocking) {
	button.setEnabled(!isBlocking);
    }

}
