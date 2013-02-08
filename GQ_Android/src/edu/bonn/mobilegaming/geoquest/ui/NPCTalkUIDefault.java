package edu.bonn.mobilegaming.geoquest.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import edu.bonn.mobilegaming.geoquest.GeoQuestApp;
import edu.bonn.mobilegaming.geoquest.R;
import edu.bonn.mobilegaming.geoquest.mission.NPCTalk;

public class NPCTalkUIDefault extends NPCTalkUI {

    public NPCTalkUIDefault(NPCTalk activity) {
	super(activity);
    }

    @Override
    View createView() {
	LayoutInflater inflater = (LayoutInflater) GeoQuestApp.getContext()
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	return inflater.inflate(R.layout.npctalk,
				null);
    }

    @Override
    public boolean setImage(String pathToImageFile) {
	// TODO Auto-generated method stub
	return false;
    }

}
