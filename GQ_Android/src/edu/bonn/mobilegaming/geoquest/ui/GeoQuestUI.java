package edu.bonn.mobilegaming.geoquest.ui;

import android.view.View;
import edu.bonn.mobilegaming.geoquest.GeoQuestActivity;

public abstract class GeoQuestUI {
    
    GeoQuestActivity activity = null;
    View view = null;

    public GeoQuestUI(GeoQuestActivity activity) {
	this.activity = activity;
	view = createView();
	activity.setContentView(view);
    }

    abstract View createView();

}
