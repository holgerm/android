package edu.bonn.mobilegaming.geoquest.ui;

import org.dom4j.Element;

import android.content.Context;
import android.view.LayoutInflater;
import edu.bonn.mobilegaming.geoquest.GeoQuestApp;
import edu.bonn.mobilegaming.geoquest.R;
import edu.bonn.mobilegaming.geoquest.mission.NPCTalk;

public class NPCTalkUIDefault extends NPCTalkUI {

    public NPCTalkUIDefault(Element xmlMissionElement, NPCTalk activity) {
	super(xmlMissionElement, activity);

	LayoutInflater inflater = (LayoutInflater) GeoQuestApp.getContext()
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	view = inflater.inflate(R.layout.npctalk,
			 null);
	activity.setContentView(view);
    }

}
