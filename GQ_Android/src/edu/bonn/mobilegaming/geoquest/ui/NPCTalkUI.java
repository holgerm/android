package edu.bonn.mobilegaming.geoquest.ui;

import org.dom4j.Element;

import android.view.View;
import edu.bonn.mobilegaming.geoquest.mission.NPCTalk;

public abstract class NPCTalkUI {

    NPCTalk activity = null;
    View view = null;

    public NPCTalkUI(Element xmlMissionElement,
		     NPCTalk activity) {
	this.activity = activity;
    }

    public View getView() {
	return view;
    }

}
