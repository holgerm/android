package com.qeevee.gq.tests.ui.mock;

import edu.bonn.mobilegaming.geoquest.mission.NPCTalk;
import edu.bonn.mobilegaming.geoquest.ui.DefaultUIFactory;
import edu.bonn.mobilegaming.geoquest.ui.NPCTalkUI;

public class MockUIFactory extends DefaultUIFactory {

    @Override
    public NPCTalkUI createUI(NPCTalk activity) {
	return new NPCTalkUIMock(activity);
    }
    

}
