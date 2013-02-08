package edu.bonn.mobilegaming.geoquest.ui;

import edu.bonn.mobilegaming.geoquest.mission.NPCTalk;

public abstract class NPCTalkUI extends MissionUI {

    public NPCTalkUI(NPCTalk activity) {
	super(activity);
    }

    public abstract boolean setImage(String pathToImageFile);

}
