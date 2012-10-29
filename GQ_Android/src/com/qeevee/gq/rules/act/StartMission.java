package com.qeevee.gq.rules.act;

import edu.bonn.mobilegaming.geoquest.mission.Mission;

public class StartMission extends Action {

	@Override
	protected boolean checkInitialization() {
		return params.containsKey("id");
	}

	@Override
	public void execute() {
		Mission.get(params.get("id")).startMission();
	}

}
