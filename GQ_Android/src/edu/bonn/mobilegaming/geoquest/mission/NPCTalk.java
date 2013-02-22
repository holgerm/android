package edu.bonn.mobilegaming.geoquest.mission;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import com.qeevee.gq.history.Actor;
import com.qeevee.gq.history.TextItem;

import android.os.Bundle;
import edu.bonn.mobilegaming.geoquest.Globals;
import edu.bonn.mobilegaming.geoquest.ui.NPCTalkUI;
import edu.bonn.mobilegaming.geoquest.ui.UIFactory;

/**
 * Just a talking NPC. The NPC has a Image and text is based on dialogItems. The
 * text scrolls down on the screen and after each dialogItem the player have to
 * press a button to proceed.
 * 
 * @author Holger Muegge
 * @author Folker Hoffmann
 * @author Krischan Udelhoven
 */

public class NPCTalk extends MissionActivity {
    @SuppressWarnings("unused")
    private static final String TAG = "NPCTalk";

    private Iterator<Element> dialogItemIterator;

    @SuppressWarnings("unused")
    private NPCTalkUI ui;

    /**
     * Called by the android framework when the mission is created. Setups the
     * View and calls the readXML method to get the dialogItems. The dialog
     * starts with the first dialogItem.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	List<Element> dialogItemList = mission.xmlMissionNode
		.selectNodes("./dialogitem");
	dialogItemIterator = dialogItemList.iterator();
	ui = UIFactory.getInstance().createUI(this);
    }

    @Override
    public void finish() {
	if (hasMoreDialogItems())
	    super.finish(Globals.STATUS_FAIL);
	else
	    super.finish(Globals.STATUS_SUCCEEDED);
    }

    /**
     * @return true if this mission still has at least one more dialogs item to
     *         show.
     */
    public boolean hasMoreDialogItems() {
	return dialogItemIterator.hasNext();
    }

    public DialogItem getNextDialogItem() {
	return new DialogItem(dialogItemIterator.next());
    }

    public void hasShownDialogItem(DialogItem shownDialogItem) {
	/*
	 * Store history item. TODO: add more arguments for image, audio and
	 * thumbnail.
	 */
	new TextItem(shownDialogItem.getText(), this, Actor.NPC);

    }

    public void onBlockingStateUpdated(boolean isBlocking) {
	// TODO stop letting MissionOrToolActivity implement
	// BlockableAndReleasable!

    }
}
