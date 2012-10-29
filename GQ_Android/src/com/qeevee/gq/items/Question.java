package com.qeevee.gq.items;

import android.view.View;
import edu.bonn.mobilegaming.geoquest.mission.MissionActivity;

public class Question extends Item {
	
	static final int UNANSWERED = 0;
	static final int CORRECTLY_ANSWERED = 1;
	static final int WORNG_ANSWERED = 2;
	
	protected int answerState = UNANSWERED;

	@Override
	public View getView(MissionActivity containingActivity) {
		// TODO Auto-generated method stub
		return null;
	}

}
